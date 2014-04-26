package com.reaper.reap;
import java.io.File;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;


public class HtmlUnitTest {
	public static void main(String[] args) throws Exception{
		final WebClient webClient = new WebClient();
	    final HtmlPage page = webClient.getPage("http://www.vmall.com/");
	    HtmlAnchor loginAnchor1 = page.getAnchorByText("[��¼]");
	    HtmlPage loginPage = loginAnchor1.click();
	    HtmlImage CaptchaImg = (HtmlImage)loginPage.getByXPath("//img[@id='randomCodeImg']").get(0);
	    CaptchaImg.saveAs(new File("img\\captcha.gif"));
	    
	    HtmlDivision userNameDiv =  (HtmlDivision)loginPage.getByXPath("//label[@for='login_userName']/div").get(0);
	    userNameDiv.setTextContent("yukim.2008@163.com");
	    HtmlDivision userPWDDiv =  (HtmlDivision)loginPage.getByXPath("//label[@for='login_password']/div").get(0);
	    userPWDDiv.setTextContent("123456789");
	    HtmlDivision captchaDiv =  (HtmlDivision)loginPage.getByXPath("//label[@for='randomCode']/div").get(0);
	    captchaDiv.setTextContent("gggg");
	    HtmlSubmitInput loginButton = (HtmlSubmitInput)loginPage.getByXPath("//input[@class='button-login']").get(0);
	    loginButton.click();

	    webClient.closeAllWindows();
	    
	}
}

