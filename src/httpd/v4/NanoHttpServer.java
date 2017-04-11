package httpd.v4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This example was taken from the Chapter "Netzwerkprogrammierung" of the Book
 * "Programmieren mit Java II", Pearson. http://sol.cs.hm.edu/4129/
 * http://sol.cs.hm.edu/4129/html/384-minimalerwebserver.xhtml
 * 
 * And then radically refactored.
 * 
 * @author Reinhard Schiedermeier
 * @author Barbara Kleinen
 * 
 *         Starting to actually hand out files...
 */
public class NanoHttpServer {

	public static void main(String[] args) throws IOException {
		int port = 4006;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);

		NanoHttpServer server = new NanoHttpServer();
		server.listen(port);
	}

	public void listen(int port) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				System.out.println("NanoHTTPServer listening on port " + port);
				handleOneRequest(serverSocket);
			}
		}
	}

	private void handleOneRequest(ServerSocket serverSocket) {
		try (Socket socket = serverSocket.accept();
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(
						output))) {

			System.out.println("-------------------- request from "
					+ socket.getRemoteSocketAddress());
			String requestedFile = readRequest(reader);

			try {
				String content = readFile(requestedFile);
				// Response-Header senden
				writer.println("HTTP/1.0 200 OK");
				writer.println("Content-Type: text/html; charset=ISO-8859-1");
				writer.println("Server: NanoHTTPServer");
				writer.println();
				// Response-Body senden
				writer.println(content);

			} catch (IOException iox) {
				System.out.println(iox.getClass());
				iox.printStackTrace();
				System.out.println("caught " + iox.getMessage());
				writer.println("HTTP/1.0 s Internal Server Error");
				writer.println("Content-Type: text/html; charset=ISO-8859-1");
				writer.println("Server: NanoHTTPServer");
				writer.println();
			}
		} catch (IOException iox) {
			System.out.println(iox.getClass());
			iox.printStackTrace();
			System.out.println("caught " + iox.getMessage());

		}
	}

	private String readFile(String requestedFile) throws IOException {
		String content = null;
		if (requestedFile != null) {
			content = new String(Files.readAllBytes(Paths.get("public_html/"
					+ requestedFile)));
		}
		return content;
	}

	private String readRequest(BufferedReader reader) throws IOException {
		// Request-Header empfangen und ausgeben

		String requestedFile = null;
		boolean first_line = true;

		for (String line = reader.readLine(); !line.isEmpty(); line = reader
				.readLine()) {
			System.out.println("read line: " + line);
			if (first_line) {
				System.out.println("first line");
				String[] tokens = line.split("\\s");
				System.out.println("VERB: " + tokens[0]);
				System.out.println("requesting file " + tokens[1]);
				requestedFile = tokens[1];
				first_line = false;
			}
		}
		if ("/".equals(requestedFile))
			requestedFile = "index.html";
		return requestedFile;
	}
}
// GET /infotext.html HTTP/1.1