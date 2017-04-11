package httpd.v5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * responsible for finding the requested resource on disk and making it
 * available to send it back to the browser.
 * 
 * @author kleinen
 *
 */
public class ResourceReader {

	public static final String public_html_dir = "public_html/";
	

	/**
	 * Currently reads the whole file to a strings and returns this String.
	 * Could be modified to copy the resource character by character to the
	 * SocketOutputStream.
	 * 
	 * @param requestedFile
	 * @return String with the complete file content
	 * @throws IOException 
	 */
	public String readFile(String requestedFile) throws IOException {

		if ("/".equals(requestedFile))
			requestedFile = "index.html";

		String content = null;
		if (requestedFile != null) {
			content = new String(Files.readAllBytes(Paths.get(public_html_dir
					+ requestedFile)));
		}

		return content;
	}


}
