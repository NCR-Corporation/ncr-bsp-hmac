import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SendHMACTest {

	@Test
	@DisplayName("GET Request")
	public void testSendGet() {
		String javaHome = System.getenv("SECRET_KEY");
		// String secretKey = '';
		// String sharedKey = '';
		// String organization = '';
		// String output = sendGet.callGet("INSERT_SECRET", "INSERT_SHARED",
		// "INSERT_ORGANIZATION");
		Integer status;
		try {
			status = SendGet.callGet("0a92503074b64a1faf1d3cf16b2d0638", "2129ffe8f58743758056edc814d67f4e",
					"8133687b23e84b2ea8dd267d8a519e89");
		} catch (Exception e) {
			e.printStackTrace();
			status = 500;
		}
		assertEquals(status, 200);
	}
	
	@Test
	@DisplayName("POST Request")
	public void testSendPost() {
		String javaHome = System.getenv("SECRET_KEY");
		// String secretKey = '';
		// String sharedKey = '';
		// String organization = '';
		// String output = sendGet.callGet("INSERT_SECRET", "INSERT_SHARED",
		// "INSERT_ORGANIZATION");
		Integer status;
		try {
			status = SendPost.callPost("0a92503074b64a1faf1d3cf16b2d0638", "2129ffe8f58743758056edc814d67f4e",
					"8133687b23e84b2ea8dd267d8a519e89");
		} catch (Exception e) {
			e.printStackTrace();
			status = 500;
		}
		assertEquals(status, 200);
	}
}