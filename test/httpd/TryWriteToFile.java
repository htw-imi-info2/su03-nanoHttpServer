package httpd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TryWriteToFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		String msg = "hallo!\n";
		Files.write(Paths.get("./duke.txt"), msg.getBytes());
	}

}
