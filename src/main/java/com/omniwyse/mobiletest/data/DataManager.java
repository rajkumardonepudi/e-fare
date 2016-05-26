package com.omniwyse.mobiletest.data;

import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.omniwyse.mobiletest.driver.OmniDriver;
import com.omniwyse.mobiletest.driver.SeleniumFramework;
import com.omniwyse.mobiletest.report.Reporter;
import com.omniwyse.mobiletest.test.functions.AppFunction;
import com.omniwyse.mobiletest.test.functions.AppFunctionStep;
import com.omniwyse.mobiletest.test.object.EfareObjectRepository;
import com.omniwyse.mobiletest.test.object.ObjectRepository;
import com.omniwyse.mobiletest.test.object.QuixeyObjectRepository;
import com.omniwyse.mobiletest.test.param.Param;
import com.omniwyse.mobiletest.test.suites.TestSuite;

public class DataManager {
	// read all the app functions
	// read Input Test File
	// read Suite/Test Cases
	// read Test Case Data
	// Execute Test Cases - Map App Functions
	static final String FILE_SEPERATOR = "\\";
	public static HashMap<String, AppFunction> appFunctions = new HashMap<String, AppFunction>();
	public static HashMap<String, ObjectRepository> objRepositories = new HashMap<String, ObjectRepository>();
	public static HashMap<String, HashMap<String, String>> appElements = new HashMap<String, HashMap<String, String>>();
	public static HashMap<String, HashMap<String, String>> configInfo = new HashMap<String, HashMap<String, String>>();
	public static String resultPath;
	public static String currentDate;

	//
	public void executeTestPlan(String repositoryPath, String currentDate) throws Exception {
		System.out.println("Result Path is: " + resultPath);
		System.out.println("starts reading input(TestPlan) file..");
		List<TestSuite> testSuites = new ArrayList<TestSuite>();
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "TestPlan" + FILE_SEPERATOR + "InputFramework_quixeyTest.xlsx")));
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				if (ExcelUtils.getCellByIndex(sheet, rowIndex, 3).getNumericCellValue() != 1) {
					continue;
				}
				resultPath = "D:\\oWyseTestBase\\Results\\" + DataManager.currentDate + FILE_SEPERATOR;
				TestSuite testSuite = new TestSuite(repositoryPath, sheet.getRow(rowIndex));
				testSuites.add(testSuite);
				objRepositories.clear();
				// Reporter.report(testSuite);
			}
		}
		Reporter.writeReport(testSuites, currentDate);
		System.out.println("*****************************************************");
	}

	public void executeMethods(String repositoryPath, String product) throws Exception {
		extractAppFunctions(repositoryPath + product + FILE_SEPERATOR);
		System.out.println("+++++++start reading of config+++++++");
		extractConfig(repositoryPath, product);
		driverInitializer(product);
		extractObjRepository(repositoryPath + product + FILE_SEPERATOR, product);
		if ("quixey".equals(product))
			extractAppSectionElements(repositoryPath + product + FILE_SEPERATOR);
		resultPath = resultPath + product + FILE_SEPERATOR;
		System.out.println("resultPath::::" + resultPath);
		Files.createDirectories(Paths.get(DataManager.resultPath));
	}

	private void extractAppSectionElements(String repositoryPath) throws FileNotFoundException, IOException {
		for (String name : new File(repositoryPath + "quixeyAppElementsMap\\").list()) {
			System.out.println("Current app section elements name is " + name);
			if (!name.contains("~$")) {
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "quixeyAppElementsMap" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						HashMap<String, String> map = new HashMap<String, String>();
						String key = ExcelUtils.getCellValByIndex(sheet, rowIndex, 0) + "|" + ExcelUtils.getCellValByIndex(sheet, rowIndex, 1) + "|"
								+ ExcelUtils.getCellValByIndex(sheet, rowIndex, 2);
						map.put(ExcelUtils.getCellValByIndex(sheet, 0, 3), ExcelUtils.getCellValByIndex(sheet, rowIndex, 3));
						map.put(ExcelUtils.getCellValByIndex(sheet, 0, 4), ExcelUtils.getCellValByIndex(sheet, rowIndex, 4));
						appElements.put(key, map);
					}
				}
			}
		}
		System.out.println("All objects " + appElements);

	}

	private void extractObjRepository(String repositoryPath, String product) throws FileNotFoundException, IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (String name : new File(repositoryPath + "ObjectRepository\\").list()) {
			System.out.println("Current object repository name is " + name);
			if (!name.contains("~$")) {
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "ObjectRepository" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

						ObjectRepository objRep = (ObjectRepository) Class
								.forName("com.omniwyse.mobiletest.test.object." + product.substring(0, 1).toUpperCase() + product.substring(1) + "ObjectRepository")
								.getConstructor(XSSFRow.class).newInstance(sheet.getRow(rowIndex));
						objRepositories.put(Class.forName("com.omniwyse.mobiletest.test.object." + product.substring(0, 1).toUpperCase() + product.substring(1) + "ObjectRepository")
								.getMethod("getName", null).invoke(objRep, null).toString(), objRep);
					}
				}
			}
		}
		System.out.println("all objects " + objRepositories);

	}

	private void extractConfig(String repositoryPath, String product) throws FileNotFoundException, IOException {

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + product + FILE_SEPERATOR + "Config.xlsx")));
		XSSFSheet sheet = wb.getSheetAt(0);
		HashMap<String, String> map = new HashMap<String, String>();
		configInfo.put(product, map);
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			map.put(ExcelUtils.getCellValByIndex(sheet, rowIndex, 0), ExcelUtils.getCellValByIndex(sheet, rowIndex, 1));
		}
		System.out.println("configInfo map:::>>>>>>>>>>>> " + configInfo);
	}

	public static Boolean getBooleanValue(XSSFRow row) {
		if (row.getCell(1) != null && "pass".equalsIgnoreCase(row.getCell(1).toString()) || "true".equalsIgnoreCase(row.getCell(1).toString()))
			return true;
		else
			return false;
	}

	private HashMap<String, AppFunction> extractAppFunctions(String repositoryPath) throws Exception {
		for (String name : new File(repositoryPath + "TestAppFunctions\\").list()) {
			System.out.println("Current app function name is " + name);
			if (!name.contains("~$")) {
				AppFunction appFun = new AppFunction();
				List<AppFunctionStep> functions = new ArrayList<AppFunctionStep>();
				@SuppressWarnings("resource")
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(repositoryPath + "TestAppFunctions" + FILE_SEPERATOR + name)));
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					XSSFSheet sheet = wb.getSheetAt(i);

					for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						System.out.println(sheet.getSheetName());
						if (ExcelUtils.getCellByIndex(sheet, rowIndex, 3).getNumericCellValue() != 1) {
							continue;
						}
						AppFunctionStep appFunData = new AppFunctionStep();
						appFunData.setAction(ExcelUtils.getCellValByIndex(sheet, rowIndex, 0));
						appFunData.setObject(ExcelUtils.getCellValByIndex(sheet, rowIndex, 1));
						appFunData.setExpectedResult(ExcelUtils.getCellBooleanByIndex(sheet, rowIndex, 2));
						List<Param> params = new ArrayList<Param>();
						int colIndex = 4;
						while (ExcelUtils.getCellByIndex(sheet, rowIndex, colIndex) != null) {
							Param param = new Param(ExcelUtils.getCellByIndex(sheet, rowIndex, colIndex));
							params.add(param);
							colIndex++;
						}
						System.out.println("Parameter values based on app function data " + params);
						appFunData.setParams(params);
						functions.add(appFunData);
						System.out.println("App function data based on file of " + sheet.getSheetName() + " is " + functions);
					}
				}
				appFun.setName(name.toString().split("\\.")[0]);
				appFun.setFunctions(functions);
				appFunctions.put(name.split("\\.")[0], appFun);
			}
		}
		System.out.println("All app functions " + appFunctions);
		return appFunctions;
	}
	
	@SuppressWarnings("rawtypes")
	public void driverInitializer(String product) throws MalformedURLException, InterruptedException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		if ("Appium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			capabilities.setCapability("deviceName", "0123456789ABCDEF");
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
			capabilities.setCapability(CapabilityType.VERSION, "5.0.1");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("appPackage", "com.quixey.launch");
			capabilities.setCapability("appActivity", "com.quixey.launch.ui.FirstActivity");

			OmniDriver.driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			OmniDriver.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			Thread.sleep(10000);
		}

		if ("Selenium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			System.setProperty("webdriver.chrome.driver", "D:\\IBaseDev\\oWyseTest\\Drivers\\chromedriver.exe");
			SeleniumFramework.driver = new ChromeDriver(capabilities);
			SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SeleniumFramework.driver.manage().window().maximize();
			//SeleniumFramework.driver.get("http://cdta-qa.gfcp.io/efare/");
		}
		if ("UIAutomater".equals(DataManager.configInfo.get(product).get("Framework")))
			OmniDriver.driver = null;//

	}

}