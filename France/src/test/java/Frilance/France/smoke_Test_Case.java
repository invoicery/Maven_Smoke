package Frilance.France;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

//import com.beust.jcommander.Parameter;
//import com.beust.jcommander.Parameters;

import Utils.ReadExcel;

public class smoke_Test_Case {
	WebDriver driver;
	@BeforeTest
	@Parameters("Browser")
	public void openBrowser(String Browser)
	{
		if(Browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
			driver=new ChromeDriver();
		}

		if(Browser.equalsIgnoreCase("Firefox"))
		{

			System.setProperty("webdriver.gecko.driver", ".\\geckodriver.exe");
			driver=new FirefoxDriver();
		}
		if(Browser.equalsIgnoreCase("IE"))
		{
			File ieFile = new File("C:\\Users\\Agicon 10\\Downloads\\IEDriverServer_x64_3.12.0\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());
			DesiredCapabilities ieCaps = DesiredCapabilities.internetExplorer();
			ieCaps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://frilansinternational-fr.staging.ffdev.se/en/");
			driver = new InternetExplorerDriver(ieCaps);

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30l, TimeUnit.SECONDS);
		driver.get("https://frilansinternational-fr.staging.ffdev.se/en/"); 
		//driver.findElement(By.linkText("Login")).click();
	}


	@Test(priority=1)
	public  void ValidCredentialsUser() throws IOException
	{
		String[][] data=ReadExcel.getdata("Book1.xlsx", "Sheet2");
		for(int i=1;i<data.length;i++)
		{
			String Username=data[i][1];
			String Password=data[i][2];
			driver.findElement(By.linkText("Login")).click();
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys(Username);
			WebElement ps=driver.findElement(By.id("password"));
			ps.sendKeys(Password);
			ps.submit();
			Thread.sleep(10000);
			// to match actual and expected

			String actmess=driver.findElement(By.cssSelector("#app > div > div:nth-child(1) > header > div > div.right-section > div > a.login.loginBackend.logout")).getText();
			String expmess="Logout";
			Assert.assertEquals(actmess, expmess);
			//logout button click
			driver.findElement(By.cssSelector("#app > div > div:nth-child(1) > header > div > div.right-section > div > a.login.loginBackend.logout")).click();
			//driver.findElement(By.xpath("//a[.='Logout']")).click();

		}



	}


}
