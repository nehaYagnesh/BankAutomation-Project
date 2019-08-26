package Browser;

import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestScript1 {
	public static void main(String[] args) throws InterruptedException {
		String projectPath =  System.getProperty("user.dir");
	    System.out.println("projectPath : "+projectPath);
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		String baseUrl = "http://www.demo.guru99.com/V4/";
		driver.get(baseUrl);
		driver.findElement(By.name("uid")).sendKeys("mngr219136");
		driver.findElement(By.name("password")).sendKeys("ujYvEbY");
		driver.findElement(By.name("btnLogin")).click();
		WebElement hometitle = driver.findElement(By.linkText("Demo Site"));
		AssertJUnit.assertEquals(true, hometitle.isDisplayed());
		Thread.sleep(3000);
		driver.close();

}
}