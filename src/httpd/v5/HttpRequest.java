package httpd.v5;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
	private String firstLine = null;

	public HttpRequest(BufferedReader reader) throws IOException {
		readRequest(reader);
	}

	private void readRequest(BufferedReader reader) throws IOException {
		boolean isFirstLine = true;

		for (String line = reader.readLine(); !line.isEmpty(); line = reader
				.readLine()) {
			System.out.println("read line: " + line);
			if (isFirstLine) {
				firstLine = line;
				isFirstLine = false;
			}
		}
	}

	public HttpResponse getResponse() {
		// some logic distinguishing between different
		// requests could go here
		return handleGetRequest();
	}

	private HttpResponse handleGetRequest() {
		String requestedFile = getRequestedResourceName();
		return generateResponse(requestedFile);
	}

	private String getRequestedResourceName() {
		String[] tokens = firstLine.split("\\s");
		return tokens[1];
	}

	private HttpResponse generateResponse(String requestedFile) {
		HttpResponse response = new HttpResponse();
		ResourceReader fileReader = new ResourceReader();
		try {
			String content = fileReader.readFile(requestedFile);
			response.setCode(200);
			response.setContent(content);
		} catch (IOException e) {
			response.setCode(404);
			response.setContent("<html><body>File not found!</body></html>\n");
		}
		return response;
	}

}
