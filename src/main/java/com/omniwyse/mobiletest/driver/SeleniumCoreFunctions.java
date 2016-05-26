package com.omniwyse.mobiletest.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumCoreFunctions {
	// static WebDriver driver;
	public static void main(String[] args) {
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "D:\\IBaseDev\\testify\\Drivers\\chromedriver.exe"); DesiredCapabilities
		 * capabilities = new DesiredCapabilities(); driver = new
		 * ChromeDriver(capabilities); driver.manage().timeouts().implicitlyWait(90,
		 * TimeUnit.SECONDS); driver.manage().window().maximize();
		 */
		// TODO Auto-generated method stub

	}

	public static void clickById(String Id) {
		SeleniumFramework.driver.findElement(By.id(Id)).click();
	}
	
	public static void clickBycssSelector(String cssPath) {
		SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).click();
	}

	public void getDataFromTableByXpath(String Xpath) {
		WebElement table = SeleniumFramework.driver.findElement(By.className("detailedsummary"));
		List<HashMap<String, Object>> orderTable = new ArrayList<HashMap<String, Object>>();
		List<WebElement> trCollectionThead = (table.findElements(By.xpath("className('detailedsummary')/thead/tr")));
		collectingData(trCollectionThead, orderTable);
		List<WebElement> trCollectionTbody = (table.findElements(By.xpath("className('detailedsummary')/tbody/tr")));
		collectingData(trCollectionTbody, orderTable);
		List<WebElement> trCollectionTfoot = (table.findElements(By.xpath("className('detailedsummary')/tfoot/tr")));
		collectingData(trCollectionTfoot, orderTable);

	}

	public static void collectingData(List<WebElement> tr_collection, List<HashMap<String, Object>> orderTable) {
		for (WebElement trElement : tr_collection) {
			HashMap<String, Object> orderDetails = new HashMap<String, Object>();
			List<WebElement> td_collection = trElement.findElements(By.tagName("td"));
			int col_num = 1;
			for (WebElement tdElement : td_collection) {
				if (col_num == 1)
					orderDetails = new HashMap<String, Object>();
				orderDetails.put("quantity", tdElement.getText());
				if (col_num == 2)
					orderDetails = new HashMap<String, Object>();
				orderDetails.put("item", tdElement.getText());
				if (col_num == 3)
					orderDetails = new HashMap<String, Object>();
				orderDetails.put("unit", tdElement.getText());
				if (col_num == 5)
					orderDetails = new HashMap<String, Object>();
				orderDetails.put("amount", tdElement.getText());
				orderTable.add(orderDetails);
			}
		}
	}
	
	/**
	 * This method will wait for the element to be present
	 * @param xpath
	 * @throws InterruptedException
	 */
	public static void waitForElementTobePresentByCssPath(String cssPath) throws InterruptedException {
		int i = 0;
		boolean elementPresent = true;
		while (elementPresent) {
			i = i + 1;
			try {
				SeleniumFramework.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				SeleniumFramework.driver.findElement(By.cssSelector(cssPath));
				SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				elementPresent = false;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
			if (i == 30)
				elementPresent = false;
		}
	}


	/**
	 * This method will wait for the element to be present
	 * @param xpath
	 * @throws InterruptedException
	 */
	public static void waitForElementTobePresentByXpath(String xpath) throws InterruptedException {
		int i = 0;
		boolean elementPresent = true;
		while (elementPresent) {
			i = i + 1;
			try {
				SeleniumFramework.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				SeleniumFramework.driver.findElement(By.xpath(xpath));
				SeleniumFramework.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				elementPresent = false;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
			if (i == 30)
				elementPresent = false;
		}
	}
	

	public static void clickByXpath(String xpath) throws InterruptedException {
		
		System.out.println("clickByXpath in SeleniumFun class");
		System.out.println("Xpath is:"+ xpath);
		System.out.println("Printed xpath");		
		waitForElementTobePresentByXpath(xpath);
		SeleniumFramework.driver.findElement(By.xpath(xpath)).click();
	}

	public static void clickByClassName(String className) {
		SeleniumFramework.driver.findElement(By.className(className)).click();
	}

	public static void clickByLinkText(String linkText) {
		SeleniumFramework.driver.findElement(By.linkText(linkText)).click();
	}

	public static void clickByName(String name) {
		SeleniumFramework.driver.findElement(By.name(name)).click();
	}

	public static void clickCheckBoxByName(String name) {
		SeleniumFramework.driver.findElement(By.name(name)).click();
	}

	public static void clickCheckBoxById(String id,String value) {
		if(value.equals("true")||value.equals("TRUE"))
		SeleniumFramework.driver.findElement(By.id(id)).click();
	}
	
	public static void clickCheckBoxByCss(String css,String value) {
		if(value.equals("true")||value.equals("TRUE"))
		SeleniumFramework.driver.findElement(By.cssSelector(css)).click();
	}

	public static void enterValueInTextBoxById(String id, String text) {
		SeleniumFramework.driver.findElement(By.id(id)).sendKeys(text);
	}
	
	public static void enterValueInTextBoxBycsspath(String cssPath, String text) throws InterruptedException {
		waitForElementTobePresentByCssPath(cssPath);
		SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).clear();
		SeleniumFramework.driver.findElement(By.cssSelector(cssPath)).sendKeys(text);
	}

	public static void enterValueInTextBoxByXpath(String xpath, String text) throws InterruptedException {
		waitForElementTobePresentByXpath(xpath);
		SeleniumFramework.driver.findElement(By.xpath(xpath)).clear();
		SeleniumFramework.driver.findElement(By.xpath(xpath)).sendKeys(text);
	}

	public static void selectValueInDropDownById(String id, String text) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.id(id)));
		select.selectByVisibleText(text.toString());
	}
	public static void selectValueInDropDownByCss(String id, String text) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.cssSelector(id)));
		select.selectByVisibleText(text.toString());
	}
	public static void selectValueInDropDownByXpath(String xpath, String text) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.xpath(xpath)));
		select.selectByVisibleText(text.toString());
	}

	public static void selectByIndexInDropDownById(String id, int index) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.id(id)));
		select.selectByIndex(index);
	}
	public static void selectByIndexInDropDownByCss(String id, int index) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.cssSelector(id)));
		select.selectByIndex(index);
	}
	public static void selectByIndexInDropDownByXpath(String xpath, int index) {
		Select select = new Select(SeleniumFramework.driver.findElement(By.xpath(xpath)));
		select.selectByIndex(index);
	}

	public static void enterValueInDateId(String id, String text) {
		SeleniumFramework.driver.findElement(By.id(id)).sendKeys(text);
	}

	public static void getDataByXpath(String xpath) {
		SeleniumFramework.driver.findElement(By.xpath(xpath)).getText();
	}
	public static void getDataByCss(String css) {
		SeleniumFramework.driver.findElement(By.cssSelector(css)).getText();
	}

	public static void getDataByClassName(String className) {
		SeleniumFramework.driver.findElement(By.id(className)).getText();
	}

	public static void getDataByUsingAttributeByXpath(String xpath, String attribute) {
		SeleniumFramework.driver.findElement(By.xpath(xpath)).getAttribute(attribute);
	}

	public static void getDataByUsingAttributeByClassName(String className, String attribute) {
		SeleniumFramework.driver.findElement(By.id(className)).getAttribute(attribute);
	}
}
