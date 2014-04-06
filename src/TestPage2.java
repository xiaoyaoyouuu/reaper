import junit.framework.TestCase;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class TestPage2 extends TestCase{
	private Selenium selenium;  
	   protected void setUp() throws Exception {  
		   String url = "www.baidu.com"; 
		  selenium = new DefaultSelenium("localhost", 5678, "*iexplore", url);  
	     selenium.start();  
	                 
	   super.setUp();                       
	            
	    }  
	  
	    protected void tearDown() throws Exception {  
	           
	        selenium.stop();  
	       super.tearDown();  
	               
	  
	  }  
}
