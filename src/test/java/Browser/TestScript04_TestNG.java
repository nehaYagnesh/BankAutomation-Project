package Browser;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestScript04_TestNG {

	private WebDriver driver;
	private String baseUrl;
	
	@DataProvider(name = "BankTestData")
	public Object[][] testData() throws Exception{
		return Util.getDataFromExcel(Util.FILE_PATH, Util.SHEET_NAME, Util.TABLE_NAME);
	}
	
	@BeforeMethod
	public void setUp() throws Exception{

		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver/chromedriver.exe");
		
		driver = new ChromeDriver();
		baseUrl = Util.BASE_URL;
		
		driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseUrl + "/V4/");
	}
	
	
	@Test(dataProvider="BankTestData")
	public void testcase4(String username,String password)throws Exception{
	String actualTitle;
	String actualBoxMsg;
	driver.findElement(By.name("uid")).clear();
	driver.findElement(By.name("uid")).sendKeys(username);
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys(password);
	driver.findElement(By.name("btnLogin")).click();
	
	try{
		Alert alt = driver.switchTo().alert();
		actualBoxMsg = alt.getText();
		alt.accept();
		assertEquals(actualBoxMsg, Util.EXPECTED_ERROR);
	}
	catch(Exception e){
		actualTitle = driver.getTitle();
		assertEquals(actualTitle,Util.EXPECTED_TITLE);
	}
	}
	
	@AfterMethod
	public void tearDown()throws Exception{
		driver.close();
	}
	
	
}
