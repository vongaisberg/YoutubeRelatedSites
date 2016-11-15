import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 */

/**
 * @author Maximilian
 *
 */
public class YoutubeRelatedSites {
	Map<URL, String> map = new HashMap<URL, String>();
	ArrayList<URL> list;

	/**
	 * @throws IOException
	 * 
	 */
	public YoutubeRelatedSites(URL channelURL) throws IOException {

		String content = sendHTTPRequest(channelURL);
		map = new HashMap<URL, String>();
		list = new ArrayList<URL>();
		String[] parts = content.split("<li class=\"channel-links-item\">");
		for (int i = 1; i < parts.length; i++) {
			int start = parts[i].indexOf("<a href=\"");
			int stop = parts[i].indexOf("</li>", start);
			String part = parts[i].substring(start, stop);
			// System.out.println(part);
			String link = part.substring(9, part.indexOf("\"", 9));
			start = part.indexOf("<span class=\"about-channel-link-text\">") + 38;
			stop = part.indexOf("</span>", start);
			String text;
			try {
				text = part.substring(start, stop).trim();
			} catch (StringIndexOutOfBoundsException e) {
				text = null;
			}

			map.put(new URL(link), text);
			list.add(new URL(link));

		}

	}

	public Map<URL, String> getLinks() {
		return map;
	}

	public URL getTwitter() {
		for (URL url : list) {
			if (url.getHost().equals("twitter.com")
					|| url.getHost().equals("www.twitter.com"))
				return url;
		}
		return null;
	}

	public URL getFacebook() {
		for (URL url : list) {
			if (url.getHost().equals("facebook.com")
					|| url.getHost().equals("www.facebook.com"))
				return url;
		}
		return null;
	}

	public URL getGooglePlus() {
		for (URL url : list) {
			if (url.getHost().equals("plus.google.com"))
				return url;
		}
		return null;
	}

	public URL getTwitch() {
		for (URL url : list) {
			if (url.getHost().equals("twitch.tv")
					|| url.getHost().equals("www.twitch.com"))
				return url;
		}
		return null;
	}

	public URL getYouTube() {
		for (URL url : list) {
			if (url.getHost().equals("youtube.com")
					|| url.getHost().equals("www.youtube.com"))
				return url;
		}
		return null;
	}

	public URL getInstagram() {
		for (URL url : list) {
			if (url.getHost().equals("instagram.com")
					|| url.getHost().equals("www.instagram.com"))
				return url;
		}
		return null;
	}

	public URL getSnapchat() {
		for (URL url : list) {
			if (url.getHost().equals("snapchat.com")
					|| url.getHost().equals("www.snapchat.com"))
				return url;
		}
		return null;
	}

	public URL getSpotify() {
		for (URL url : list) {
			if (url.getHost().equals("spotify.com")
					|| url.getHost().equals("play.spotify.com"))
				return url;
		}
		return null;
	}

	public URL getTumblr() {
		for (URL url : list) {
			if (url.getHost().contains("tumblr.com"))
				return url;
		}
		return null;
	}

	public URL getSoundcloud() {
		for (URL url : list) {
			if (url.getHost().equals("soundcloud.com")
					|| url.getHost().equals("www.soundcloud.com"))
				return url;
		}
		return null;
	}

	/**
	 * Send HTTP requests to a webserver and fetch the answer. Will send
	 * <code>http.agent=Chrome</code>
	 * 
	 * @param url
	 *            The <code>URL</code> you want to send a request to
	 * @return The answer from that <code>URL</code>
	 * @throws IOException
	 *             if connection failed
	 */
	public static String sendHTTPRequest(URL url) throws IOException {
		System.setProperty("http.agent", "Chrome");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String answer = "";
		String line = "";
		while (null != (line = br.readLine())) {
			answer = answer + line + "\n";
		}
		br.close();
		return answer;
	}

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		YoutubeRelatedSites yrs = new YoutubeRelatedSites(
				new URL(
						"https://www.youtube.com/user/WatchMojo/about?&ab_channel=WatchMojo.com"));
		Map map = yrs.getLinks();
		Set set = map.keySet();
		System.out.println(yrs.getTwitter());
		for (Object object : set) {
			System.out.println(((URL) object).toString());
			System.out.println(map.get((URL) object));
		}

	}
}
