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

	public URL[] getTwitter() {
		return searchforURL("twitter.com");
	}

	public URL[] getFacebook() {
		return searchforURL("facebook.com");
	}

	public URL[] getGooglePlus() {
		return searchforURL("plus.google.com");
	}

	public URL[] getTwitch() {
		return searchforURL("twitch.com");
	}

	public URL[] getYouTube() {
		return searchforURL("youtube.com");
	}

	public URL[] getInstagram() {
		return searchforURL("instagram.com");
	}

	public URL[] getSnapchat() {
		return searchforURL("snapchat.com");
	}

	public URL[] getSpotify() {
		return searchforURL("spotify.com");
	}

	public URL[] getTumblr() {
		return searchforURL("tumblr.com");
	}

	public URL[] getSoundcloud() {
		return searchforURL("soundcloud.com");
	}

	public URL[] searchforURL(String keyword) {
		ArrayList<URL> list2 = new ArrayList<URL>();
		for (URL url : list) {
			if (url.getHost().contains(keyword))
				list2.add(url);
		}
		if (list2.size() == 0)
			return null;
		URL[] urls = new URL[list2.size()];
		for (int i = 0; i < list2.size(); i++)
			urls[i] = list2.get(i);
		return urls;

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
		System.out.println(yrs.getTwitter()[0].toString());
		for (Object object : set) {
			System.out.println(((URL) object).toString());
			System.out.println(map.get((URL) object));
		}

	}
}
