import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omniwyse.mobiletest.data.DataManager;

public class OWyseTest {

	RemoteWebDriver driver;

	@Test
	public void f() throws Exception {
		String repositoryPath = "D:\\oWyseTestBase\\";
		DataManager.currentDate = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
		System.out.println(DataManager.currentDate);
		DataManager.resultPath="D:\\oWyseTestBase\\Results\\" + DataManager.currentDate + "\\";
		System.out.println("this is our repository path " + repositoryPath);
		Files.createDirectories(Paths.get("D:\\oWyseTestBase\\Results\\"+DataManager.currentDate));//stmt for creating folder dynamically...
		new DataManager().executeTestPlan(repositoryPath, DataManager.currentDate);
		System.out.println("Input(TestPlan) file reading is done..");
	}

	@SuppressWarnings("rawtypes")
	@BeforeClass
	public void beforeClass() throws MalformedURLException, InterruptedException {
		
		/*DesiredCapabilities capabilities = new DesiredCapabilities();
		
 		capabilities.setCapability("deviceName", "0123456789ABCDEF");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android"); 
		capabilities.setCapability(CapabilityType.VERSION, "5.0.1");
		capabilities.setCapability("platformName", "Android"); 
		capabilities.setCapability("appPackage", "com.quixey.launch");
		capabilities.setCapability("appActivity", "com.quixey.launch.ui.FirstActivity");

		OmniDriver.driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		OmniDriver.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		Thread.sleep(5000);
		OmniDriver.driver.findElementByName("Launch").click();
		Thread.sleep(2000);
		OmniDriver.driver.findElementByName("Always").click();
		
		
		Thread.sleep(10000);
*/
	}

	@AfterClass
	public void afterClass() {

		/* OmniDriver.driver.quit();*/
	}

}
