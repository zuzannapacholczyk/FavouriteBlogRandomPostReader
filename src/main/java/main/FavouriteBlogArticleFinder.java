package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class FavouriteBlogArticleFinder {
	static String favouriteBlogUrl = "http://kobietydokodu.pl/";
	static Random generator = new Random();

	static void getLinks(String url, List<String> urls) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select("a[href*=/kobietydokodu.pl/]");
		for (Element element : elements) {
			String foundUrl = element.absUrl("href");
			if (!foundUrl.contains("#") && !foundUrl.endsWith(".png") && !urls.contains(foundUrl)) {
				System.out.println(element.absUrl("href"));
				urls.add(url);
				getLinks(foundUrl, urls);
			}
		}
	}

	public static void main(String[] args) {
		List<String> foundUrls = new ArrayList<String>();
		try {
			getLinks(favouriteBlogUrl, foundUrls);

			String luckyShot = foundUrls.get(generator.nextInt(foundUrls.size() - 1));
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(luckyShot));
			} else {
				Runtime runtime = Runtime.getRuntime();

				runtime.exec("/usr/bin/firefox -new-window " + luckyShot);
			}
		} catch (Exception e) {
			System.out.println("Sorry but there was a problem with the browser.");
		}
	}
}
