package Browser;


import Browser.Util;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class TestScript03 {

	public static WebDriver driver;	
	private static String baseUrl; 
    
	public static void setUp() throws Exception {
	
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver/chromedriver.exe");
		
		driver = new ChromeDriver();
		baseUrl = Util.BASE_URL;
		
		driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
		driver.get(baseUrl + "/V4/");
	}

	public static void main(String[] args) throws Exception {
		String[][] testData = Util.getDataFromExcel(Util.FILE_PATH,Util.SHEET_NAME, Util.TABLE_NAME);
		String actualTitle;
		String actualBoxTitle;
		String userName,password;
		
		for(int i=0;i<testData.length;i++){
			userName = testData[i][0];
			password = testData[i][1];
			
			setUp();

			driver.findElement(By.name("uid")).clear();
			driver.findElement(By.name("uid")).sendKeys(userName);
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(password); 
			driver.findElement(By.name("btnLogin")).click();
		
		 try{
			 Alert alt = driver.switchTo().alert();
			 actualBoxTitle = alt.getText();
			 alt.accept();
			 if(actualBoxTitle.contains(Util.EXPECTED_ERROR)){
				 System.out.println("Testcase SS["+ i +"]:Passed");	 
			 }
			 else{
				 System.out.println("Testcase SS["+ i +"]:Failed");	 
			 }
		 }
		 
		 catch(NoAlertPresentException Ex){
			 actualTitle = driver.getTitle();
				if (actualTitle.contains(Util.EXPECTED_TITLE)) {
					System.out.println("Testcase SS["+ i +"]:Passed");
				} 
				else {
					System.out.println("Testcase SS["+ i +"]:Failed");
				}

		 }
		Thread.sleep(1000);
		driver.close();
		}
	}

}



