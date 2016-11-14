import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author Maximilian
 *
 */
public class YoutubeRelatedSites {
	Map<String, String> map = new HashMap<String, String>();
	ArrayList<String> list;

	/**
	 * @throws IOException
	 * 
	 */
	public YoutubeRelatedSites(URL channelURL) throws IOException {

		String content = sendHTTPRequest(channelURL);
		map = new HashMap<String, String>();
		list = new ArrayList<String>();
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
				text = part.substring(start, stop);
			} catch (StringIndexOutOfBoundsException e) {
				text = null;
			}

			map.put(link, text);
			list.add(link);

		}

	}

	public Map<String, String> getLinks() {
		return map;
	}

	public String getTwitter() {
		for (String string : list) {
			if (string.contains("twitter.com"))
				return string;
		}
		return null;
	}

	public String getFacebook() {
		for (String string : list) {
			if (string.contains("facebook.com"))
				return string;
		}
		return null;
	}

	public String getGooglePlus() {
		for (String string : list) {
			if (string.contains("plus.google.com"))
				return string;
		}
		return null;
	}

	public String getTwitch() {
		for (String string : list) {
			if (string.contains("twitch.tv"))
				return string;
		}
		return null;
	}

	public String getYoutube() {
		for (String string : list) {
			if (string.contains("youtube.com"))
				return string;
		}
		return null;
	}

	public String getInstagram() {
		for (String string : list) {
			if (string.contains("instagram.com"))
				return string;
		}
		return null;
	}

	public String getSnapchat() {
		for (String string : list) {
			if (string.contains("snapchat.com"))
				return string;
		}
		return null;
	}

	public String getSpotify() {
		for (String string : list) {
			if (string.contains("spotify.com"))
				return string;
		}
		return null;
	}

	public String getTumblr() {
		for (String string : list) {
			if (string.contains("tumblr.com"))
				return string;
		}
		return null;
	}

	public String getSoundcloud() {
		for (String string : list) {
			if (string.contains("soundcloud.com"))
				return string;
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
		new YoutubeRelatedSites(
				new URL(
						"https://www.youtube.com/user/WatchMojo/about?&ab_channel=WatchMojo.com"));
	}
}
