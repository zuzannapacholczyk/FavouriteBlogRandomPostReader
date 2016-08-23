package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class FavouriteBlogRandomArticleFinder {
	static String favouriteBlogUrl = "http://kobietydokodu.pl/";
	static String query = "site:" + favouriteBlogUrl;
	static Random generator = new Random();

	static List<String> findSubpagesWithGoogle() throws IOException {
		List<String> foundUrls = new ArrayList<String>();
		Document document = Jsoup.connect("http://www.google.com/search?q=" + query)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();
		Elements elements = document.select("a[href]");
		for (Element element : elements) {
			String foundUrl = element.attr("href");
			if (foundUrl.startsWith("/url?q=")) {
				foundUrls.add(element.absUrl("href"));
			}
		}
		return foundUrls;
	}

	private static void displayLuckyPageInBrowser(String luckyShot) throws IOException, URISyntaxException {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI(luckyShot));
		} else {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("/usr/bin/firefox -new-window " + luckyShot);
		}
	}

	public static void main(String[] args) {
		try {
			List<String> foundSubsites = findSubpagesWithGoogle();
			String luckyShot = foundSubsites.get(generator.nextInt(foundSubsites.size() - 1));
			displayLuckyPageInBrowser(luckyShot);
		} catch (Exception e) {
			System.out.println("Sorry but there was a problem with the tool. Just visit kobietydokodu.pl :)");
		}
	}
}
