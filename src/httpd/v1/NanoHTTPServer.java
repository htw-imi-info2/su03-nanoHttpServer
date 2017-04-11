package httpd.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * This is a very simple web server. It ignores the request and hands out one 
 * html page contained in the source code.
 * 
 * 
 * This example was taken from the Chapter "Netzwerkprogrammierung"
 * of the Book "Programmieren mit Java II", Pearson.
 * http://sol.cs.hm.edu/4129/
 * http://sol.cs.hm.edu/4129/html/384-minimalerwebserver.xhtml
 * With a slight modification in the args handling.
 * @author Reinhard Schiedermeier
 */
public class NanoHTTPServer {
	private static final String responseBody = "<!DOCTYPE html>"
			+ "<html>"
			+ "    <head>"
			+ "          <meta http-equiv=Content-type content=\"text/html; charset=us-ascii\">"
			+ "          <title>Meine Homepage</title>"
			+ "    </head>"
			+ "    <body>"
			+ "          <div>"
			+ "              <h1>Willkommen auf meiner <em>Homepage!</em></h1>"
			+ "   		</div>" 
			+"     </body>" 
			+ "</html>";

	public static void main(String[] args) throws IOException {
		int port = 4004;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true)
				try (Socket socket = serverSocket.accept();
						InputStream input = socket.getInputStream();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(input));
						OutputStream output = socket.getOutputStream();
						PrintWriter writer = new PrintWriter(
								new OutputStreamWriter(output))) {
					// Request-Header empfangen und ignorieren
					for (String line = reader.readLine(); !line.isEmpty(); line = reader
							.readLine())
						;
					System.out.println("request from "
							+ socket.getRemoteSocketAddress());

					// Response-Header senden
					writer.println("HTTP/1.0 200 OK");
					writer.println("Content-Type: text/html; charset=ISO-8859-1");
					writer.println("Server: NanoHTTPServer");
					writer.println();
					// Response-Body senden
					writer.println(responseBody);
				} catch (IOException iox) {
					// Fehler
				}
		}
	}
}
// GET /infotext.html HTTP/1.1