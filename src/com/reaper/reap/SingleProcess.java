package com.reaper.reap;

import static org.junit.Assert.fail;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.reaper.common.AccountInfo;


public class SingleProcess {
	private static final Logger log = Logger.getLogger(SingleProcess.class);
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.vmall.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testHuaweiLogin(AccountInfo accountInfo) throws Exception {
	  log.debug("account " + accountInfo.getAccountName() + " process starts ");
	  
    driver.get(baseUrl + "/");
    driver.findElement(By.linkText("[登录]")).click();
    
    //下载验证码图片，此方式不可用
//    ChkImgProcessor.downloadImg(driver.findElement(By.id("randomCodeImgImgmgImg")).getAttribute("src"), "img\\huawei.gif");
//    String checkingCode = CQZDMDLL.getCheckResult("img\\huawei.gif");
    
    Set<Cookie> allCookies = driver.manage().getCookies();

    //打印出所有cookie
//    for (Cookie loadedCookie : allCookies) {
//
//       System.out.println(String.format("%s -> %s",loadedCookie.getName(), loadedCookie.getValue()));
//       System.out.println("-------------");
//
//    }
    
//    File scrnsht =
//    		((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//    		FileUtils.copyFile(scrnsht, new
//    		File("img\\screenShot.gif"));
    
    driver.findElement(By.id("login_userName")).clear();
    driver.findElement(By.id("login_userName")).sendKeys(accountInfo.getAccountName());
    driver.findElement(By.id("login_password")).clear();
    driver.findElement(By.id("login_password")).sendKeys(accountInfo.getPwd());
    driver.findElement(By.cssSelector("div.verify.vam")).click();
    

    driver.findElement(By.id("randomCode")).clear();
    driver.findElement(By.id("randomCode")).sendKeys("rrrr");
    driver.findElement(By.cssSelector("input.button-login")).click();
    driver.findElement(By.id("loginSubmitForm")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
