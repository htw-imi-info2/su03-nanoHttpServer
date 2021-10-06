package httpd.v2;

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
 * This example was taken from the Chapter "Netzwerkprogrammierung"
 * of the Book "Programmieren mit Java II", Pearson.
 * http://sol.cs.hm.edu/4129/
 * http://sol.cs.hm.edu/4129/html/384-minimalerwebserver.xhtml
 * With a slight modification in the args handling.
 * @author Reinhard Schiedermeier
 *
 * It contains one change: every line received is printed to standard out.
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
			+ "              Ein Bild <em>!</em>:"
			+ "              <reader>"
			+ "              <img src=\"flickr_5921129497_a50d7b99dc_z.jpg\" alt=\"ein Bild\">"
			+ "          </div>"
			+ "          <div>"
			+ "              Und hier sind <a href=friends.html>meine Freunde.</a>"
			+ "          </div>" + "    </body>" + "</html>";

	public static void main(String[] args) throws IOException {
		int port = 5002;
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
					System.out.println("-------------------- request from "
							+ socket.getRemoteSocketAddress());
					
					// Request-Header empfangen und ausgeben
					for (String line = reader.readLine(); !line.isEmpty(); line = reader
							.readLine())
						System.out.println("read line: "+line);
					

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