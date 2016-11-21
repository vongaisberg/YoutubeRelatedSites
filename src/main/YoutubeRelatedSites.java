package main;

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
 * Get the so called "related links" of a Youtube channel page
 * 
 * @see <a href="http://grunzwanzling.me/YoutubeRelatedSites/">Javadoc
 *      Website</a>
 * 
 * @author <a href="http://grunzwanzling.me">Maximilian von Gaisberg
 *         (Grunzwanzling)</a>
 *
 */
public class YoutubeRelatedSites {
	Map<URL, String> map = new HashMap<URL, String>();
	ArrayList<URL> list;
	String content;

	/**
	 * Analyze a YouTube-channel and filter out all the related sites
	 * 
	 * @param channelURL
	 *            The <code>URL</code> of the channel to analyze
	 * @throws IOException
	 *             If something went wrong
	 */
	public YoutubeRelatedSites(URL channelURL) throws IOException {

		content = sendHTTPRequest(channelURL);
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

	public String getTwitterName(String username) {
		if (content.indexOf("twitter.com/") == -1)
			return null;

		String name = content.substring(content.indexOf("twitter.com/") + 12,
				content.indexOf("\"", content.indexOf("twitter.com/") + 12));
		name = name.replace("#!/", "");
		name = name.replace("/", "");
		return "@" + name;
	}

	/**
	 * Get all the related links of the YouTube-channel
	 * 
	 * @return A <code>Map</code> of all the links
	 */
	public Map<URL, String> getLinks() {
		return map;
	}

	/**
	 * Get all the Twitter links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getTwitter() {
		return searchforURL("twitter.com");
	}

	/**
	 * Get all the Facebook links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getFacebook() {
		return searchforURL("facebook.com");
	}

	/**
	 * Get all the Google Plus links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getGooglePlus() {
		return searchforURL("plus.google.com");
	}

	/**
	 * Get all the Twitch links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getTwitch() {
		return searchforURL("twitch.com");
	}

	/**
	 * Get all the YouTube links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getYouTube() {
		return searchforURL("youtube.com");
	}

	/**
	 * Get all the Instagram links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getInstagram() {
		return searchforURL("instagram.com");
	}

	/**
	 * Get all the Snapchat links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getSnapchat() {
		return searchforURL("snapchat.com");
	}

	/**
	 * Get all the Spotify links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getSpotify() {
		return searchforURL("spotify.com");
	}

	/**
	 * Get all the Tumblr links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getTumblr() {
		return searchforURL("tumblr.com");
	}

	/**
	 * Get all the Soundcloud links
	 * 
	 * @return A <code>URL[]</code> with all the links
	 */
	public URL[] getSoundcloud() {
		return searchforURL("soundcloud.com");
	}

	/**
	 * Search for all the <code>URL</code> objects witch contain a specific
	 * keyword
	 * 
	 * @param keyword
	 *            The keyword to search for
	 * @return The <code>URL[]</code> with the given keyword
	 */
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
	 * Get the linktext for a <code>URL</code>
	 * 
	 * @param url
	 *            The <code>URL</code> to check
	 * @return The linktext
	 */
	public String getLinkText(URL url) {
		return map.get(url);
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

	/**
	 * Just for demonstration purposes
	 * 
	 * @param args
	 * @throws MalformedURLException
	 * @throws IOException
	 */
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
