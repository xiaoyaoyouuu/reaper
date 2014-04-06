package com.reaper.reap;


import static org.junit.Assert.fail;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.reaper.common.CQZDMDLL;
import com.reaper.common.ChkImgProcessor;


public class HuaweiLogin {
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
  public void testHuaweiLogin() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.linkText("[登录]")).click();
    ChkImgProcessor.downloadImg(driver.findElement(By.id("randomCodeImg")).getAttribute("src"), "img\\huawei.gif");
    String checkingCode = CQZDMDLL.getCheckResult("img\\huawei.gif");
    
    Set<Cookie> allCookies = driver.manage().getCookies();

    for (Cookie loadedCookie : allCookies) {

       System.out.println(String.format("%s -> %s",loadedCookie.getName(), loadedCookie.getValue()));
       System.out.println("-------------");

    }
    
    File scrnsht =
    		((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    		FileUtils.copyFile(scrnsht, new
    		File("img\\screenShot.gif"));
    
    driver.findElement(By.id("login_userName")).clear();
    driver.findElement(By.id("login_userName")).sendKeys("yukim.2008@163.com");
    driver.findElement(By.id("login_password")).clear();
    driver.findElement(By.id("login_password")).sendKeys("123456789");
    driver.findElement(By.cssSelector("div.verify.vam")).click();
    

    driver.findElement(By.id("randomCode")).clear();
    driver.findElement(By.id("randomCode")).sendKeys(checkingCode);
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
