package Browser;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestScript06 {

	private WebDriver driver;
	private String baseUrl;
	
	@DataProvider(name = "BankTestData")
	public Object[][] testData(){
		Object[][] data = new Object[4][2];
		
		data[0][0] = Util1.USER_NAME;
		data[0][1] = Util1.PASSWORD;
		
		data[1][0] = "neha";
		data[1][1]= Util1.PASSWORD;
		
		data[2][0] = Util1.USER_NAME;
		data[2][1]= "78dsk";
		
		data[3][0] = "neha";
		data[3][1]= "78dsk";
		
		return data;
	}
	
	
	@BeforeMethod
	public void setUp()throws Exception{
		baseUrl = Util1.BASE_URL;
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseUrl+"/V4/");
	}
	
	@Test(dataProvider="BankTestData")
	public void test05(String username, String password)throws Exception{
		String actualBoxMsg;
		
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys(username);
		// Enter valid Password
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		// Click Login
		driver.findElement(By.name("btnLogin")).click();	

	try{ 
	   
       	Alert alt = driver.switchTo().alert();
		actualBoxMsg = alt.getText(); // get content of the Alter Message
		alt.accept();
		 // Compare Error Text with Expected Error Value					
		Assert.assertEquals(actualBoxMsg,Util1.EXPECTED_ERROR);
		
	}    
    catch (NoAlertPresentException Ex){ 
    	// Get text displayed on login page 
		String pageText = driver.findElement(By.xpath("//*[@class='heading3']//*[contains(text(),'Manger Id')]")).getText();

		// Extract the dynamic text mngrXXXX on page		
		String[] parts = pageText.split(Util1.PATTERN);
		String dynamicText = parts[1];
       // System.out.println("Dynamic string is"+dynamicText+"NEha");  Here it the dynamic string has a space at the start so we are using the substring from position 1 
		// Check that the dynamic text is of pattern mngrXXXX
		// First 4 characters must be "mngr"
		Assert.assertTrue(dynamicText.substring(1, 5).equals(Util1.FIRST_PATTERN));
		// remain stores the "XXXX" in pattern mngrXXXX
		String remain = dynamicText.substring(dynamicText.length() - 4);
		// Check remain string must be numbers;
		Assert.assertTrue(remain.matches(Util1.SECOND_PATTERN));
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		/* FileUtils is class from apache org.apache.commons.io package, you need to download org.apache.commons.io.jar and then configure that jar file in your class path.
		 * 
		 * For selenium automation users
			Download Library file from http://www.java2s.com/Code/Jar/o/Downloadorgapachecommonsiojar.htm
			Extract
			Right click on the proj name from the explorer >> Build path >>Config Build Path
		 */
		FileUtils.copyFile(srcFile, new File ("C:\\Neha\\Testing\\Selenium\\Guru Bank Automation\\OutputScreenshots\\screenshot.png"));
    } 
	}
	@AfterMethod
	public void tearDown() throws Exception{
		driver.close();
	}
}
