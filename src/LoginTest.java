import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class LoginTest {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://mail.163.com/");
		selenium.start();
	}

	@Test
	public void test163() throws Exception {
		selenium.open("/");
		selenium.type("id=pwdInput", "xiaoyao198512678");
		selenium.click("id=loginBtn");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
