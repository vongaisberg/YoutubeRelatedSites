import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

	/**
	 * @throws IOException
	 * 
	 */
	public YoutubeRelatedSites(URL channelURL) throws IOException {

		String content = sendHTTPRequest(channelURL);
		Map<String, String> map = new HashMap<String, String>();
		String[] parts = content.split("<li class=\"channel-links-item\">");
		for (int i = 1; i < parts.length; i++) {
			int start = parts[i].indexOf("<a href=\"");
			int stop = parts[i].indexOf("</li>", start);
			String part = parts[i].substring(start, stop);
			// System.out.println(part);
			String link = part.substring(9, part.indexOf("\"", 9));
			System.out.println(link);
			start = part.indexOf("<span class=\"about-channel-link-text\">") + 38;
			stop = part.indexOf("</span>", start);
			String text;
			try {
				text = part.substring(start, stop);
				System.out.println(text);
			} catch (StringIndexOutOfBoundsException e) {
				text = null;
			}

			map.put(link, text);

		}

	}

	public Map<String, String> getLinks() {
		return map;
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
