package Browser;


import Browser.Util;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;




public class TestScript2 {

	public static WebDriver driver;	
	private static String baseUrl; 

	public static void setUp() throws Exception {
		/*
		 * Tells the Selenium client library to connect to the Webdriver
		 * service using firefox
		 * 
		 * In some PC's, Selenium can not find the binary file of Firefox because
		 * user doesn't install Firefox at its default location. We need to tell
		 * Selenium where the firefox.exe is
		 *
		/*File pathToBinary = new File(Util.FIREFOX_PATH);
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		 */
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = Util.BASE_URL;
		// Specifies the amount of time the driver should wait when searching for an element if it is not immediately present.
		// Refer - http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/WebDriver.Timeouts.html
		/*driver.manage().timeouts()
		.implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);*/
		driver.get(baseUrl + "/V4/");
	}

	public static void main(String[] args) throws Exception {
		String actualTitle;
		setUp();
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(Util.USER_NAME);  
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(Util.PASSWORD);  
		driver.findElement(By.name("btnLogin")).click();
		actualTitle = driver.getTitle();
		if (actualTitle.contains(Util.EXPECTED_TITLE)) {
			System.out.println("Test case: Passed");
		} 
		else {
			System.out.println("Test case : Failed");
		}

		driver.close();
	}

}



