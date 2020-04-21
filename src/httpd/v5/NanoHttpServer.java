package httpd.v5;

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
 * This example was taken from the Chapter "Netzwerkprogrammierung" of the Book
 * "Programmieren mit Java II", Pearson. http://sol.cs.hm.edu/4129/
 * http://sol.cs.hm.edu/4129/html/384-minimalerwebserver.xhtml
 * 
 * And then radically refactored.
 * 
 * @author Reinhard Schiedermeier
 * @author Barne Kleinen
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
				try (Socket socket = serverSocket.accept();
						InputStream input = socket.getInputStream();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(input));
						OutputStream output = socket.getOutputStream();
						PrintWriter writer = new PrintWriter(
								new OutputStreamWriter(output))

				) {
					System.out.println("-------------------- request from "
							+ socket.getRemoteSocketAddress());
					HttpRequest request = new HttpRequest(reader);
					HttpResponse response = request.getResponse();
					response.send(writer);
				} catch (Exception e) {
					System.out.println("Caught Exception in While Loop:");
					throw e;
				}

			}
		}
	}
}
