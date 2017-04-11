package httpd.v5;

import java.io.PrintWriter;

public class HttpResponse {
	int code;
	String content;

	public HttpResponse() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void send(PrintWriter writer) {
		// Response-Header senden
		if (code == 200) {
			writer.println("HTTP/1.0 200 OK");
			writer.println("Content-Type: text/html; charset=ISO-8859-1");
			writer.println("Server: NanoHTTPServer");
			writer.println();

		} else {
			writer.println("HTTP/1.0 404 File not found");
			writer.println("Content-Type: text/html; charset=ISO-8859-1");
			writer.println("Server: NanoHTTPServer");
			writer.println();
		}
		// Response-Body senden
		if (content != null)
			writer.println(content);

	}
}
