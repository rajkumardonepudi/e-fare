package com.omniwyse.mobiletest.driver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;

import io.appium.java_client.android.AndroidKeyCode;

import com.omniwyse.mobiletest.data.DataManager;
import com.omniwyse.mobiletest.test.object.ObjectRepository;
import com.omniwyse.mobiletest.test.param.Param;

public class AppiumFramework extends OmniDriver {
	
	static int debugLevel = 2;
	static String dirName;
	static LinkedList<DeepViewCardsList> deepViewCardsPageListInfo = new LinkedList<DeepViewCardsList>();
	static LinkedList<SearchPageCardsList> searchPageCardsListInfo = new LinkedList<SearchPageCardsList>();
	static MatchingSearchPageAppInfo matchingSearchPageappDetails;
	static MatchingDeepViewCardsPageAppInfo matchingDeepViewCardsPageappDetails;
	
	//All these functions require "driver" as One Param it could be 
	//class Private Variable or it can be passed ...Based on Design
	// Either the Element as Parameter or xpath as Parameter is fine for this function
	// Now we will consider the "Object" as the Parameter Eg. Apps/Cards in Test Step 
	public static boolean click(String xpath, List<Param> params) throws InterruptedException, IOException {

		// If element is Param then following is Code Sample
		// click success then return true else false
		System.out.println("Processing 'click' KeyWord" + "With Params:" + xpath);

		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
		
		if (!driver.findElements(By.xpath(xpath)).isEmpty()) {
			Thread.sleep(3000);
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(3000);
			System.out.println("Return Value:TRUE");
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			System.out.println("Return Value:FALSE");
			return false;
		}

	}

//Takes Two Parameters ::Device and Code
	public static boolean press(String pressType, List<Param> params) throws IOException, InterruptedException {

		// System.out.println("Processing 'Press' KeyWord" + "With Params:" +
		// pressType + "-" + params.get(0).getValue());
		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));		
		if (pressType.equals("::Device")) {
			if (params.get(0).getValue().equals("Home"))
				System.out.println("In Press Home");
			driver.pressKeyCode(AndroidKeyCode.HOME);
			if (params.get(0).getValue().equals("Back"))
				System.out.println("In Press Back");
			driver.pressKeyCode(AndroidKeyCode.BACK);
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean check_Element_Text(String xpath, List<Param> params) throws IOException, InterruptedException {

		WebElement element;

		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));		
		if (!driver.findElements(By.xpath(xpath)).isEmpty()) {
			element = (WebElement) driver.findElements(By.xpath(xpath)).get(0);
			if (element.getText().equals(params.get(0).getValue())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean clean_DeepViewCardsPageCache(String objType, List<Param> params) {
		deepViewCardsPageListInfo.clear();
		return true;
	}
		
	public static boolean clean_SearchPageCardsCache(String objType, List<Param> params) {
		searchPageCardsListInfo.clear();
		return true;
	}
	
	
	public static boolean scrollTo_DeepViewCardsPage_AppHeader_ByText(String objType, List<Param> params) throws NoSuchFieldException, SecurityException, IOException, InterruptedException {

		List<WebElement> appFrameLayoutList = null;
		List<WebElement> appSectionElementsList = null;
		// List<WebElement> FunctionHeaders = null;
		List<WebElement> appSectionHeadersList = null;
		WebElement appHeaderElement = null;
		WebElement appCatogeryElement = null;
		WebElement presentSectionHeaderElement = null;
		boolean endOfScreen = false;
		String presentAppHeader = null;
		String presentAppCatogery = null;
		String appSectionHeader = null;
		WebElement presentSectionElement = null;
		int countSameScreen = 0;
		int swipeCount = 0;
		int screenHeadersCount = 0;
		int screenappsCount = 0;
		String prevappHeader = null;
		String prevappCatogery = null;
		boolean foundMatchingapp = false;
		int matchingappIndex = 0;
		int duplicateCount = 0;
		String quixeyAppORLogicalName;
		String quixeyAppHeaderName;
		String quixeyAppHeaderCatogery;
		String quixeySectionHeaderName;
		String appSectionHeaderFuncType;
		WebElement appSectionHeaderRelativeElement = null;
		String appSectionHeaderRelativeElementContentDesc = null;
		List<WebElement> deepViewCardsDefaultElementList;
		boolean deepViewCardsInitialScreen = false;
		int lastappFrameIndex;
		boolean isEndOfScreenReached;
		int overallappSectionHeadersIndex = 0;
		int appPosIndexRelativeToApp = 0;
		String prevappSectionHeaderRelativeElementContentDesc = null;
		int overallappPosIndex = 0;
		WebElement presentAppHeaderElement = null;		
		WebElement presentAppCatogeryElement = null;

		System.out.println("scrollTo_DeepViewCardsPage_AppHeader_ByText");
		quixeyAppORLogicalName = params.get(0).getValue();
		quixeyAppHeaderName = params.get(1).getValue();
		quixeyAppHeaderCatogery = params.get(2).getValue();
		quixeySectionHeaderName = params.get(3).getValue();
		
		do{
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			deepViewCardsDefaultElementList = driver.findElementsByName(("Deep View Cards™"));
			if(deepViewCardsDefaultElementList.size() == 1){
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
				deepViewCardsInitialScreen = true;
			}else{
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				driver.swipe(360, 450, 360, 1073,  5000);
			}
		}while(!deepViewCardsInitialScreen);
		
		do {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			expandAllMoreOrLessContainers();
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			appFrameLayoutList = quixeyOnScreenVisibleAppsList();
			
			lastappFrameIndex = appFrameLayoutList.size();
			isEndOfScreenReached = isEndOfScreenReached_DeepViewCardsPage(lastappFrameIndex, presentAppHeader, presentAppCatogery, appSectionHeader, appSectionHeaderRelativeElementContentDesc);
			if(isEndOfScreenReached == true){
				displayAllAppsOnCardsPage();				
				return true;
			}

			for (int onScreenvisibleappIndex = 1; onScreenvisibleappIndex <= appFrameLayoutList.size(); onScreenvisibleappIndex++) {

				appHeaderElement = getTextViewElementAppHeaderName(onScreenvisibleappIndex);
				appCatogeryElement = getTextViewElementAppCatogeryName(onScreenvisibleappIndex);
				appSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(onScreenvisibleappIndex);
				
				if ((appHeaderElement == null) && (appCatogeryElement == null)) {
					if((prevappHeader != null) && (prevappCatogery != null)){
						presentAppHeader = prevappHeader;
						presentAppCatogery = prevappCatogery;
					}
					if((prevappHeader == null) && (prevappCatogery == null)){
						return false;
					}
				}
				if ((appHeaderElement != null) && (appCatogeryElement != null)) {
					presentAppHeader = appHeaderElement.getText();
					presentAppCatogery = appCatogeryElement.getText();
				}

				duplicateCount = 0;
				for(int visibleappSectionHeaderIndex = 1; visibleappSectionHeaderIndex <= appSectionHeadersList.size(); visibleappSectionHeaderIndex++) {
					appSectionHeaderRelativeElementContentDesc = appSectionHeadersList.get(visibleappSectionHeaderIndex - 1).getAttribute("name");
					if(isEntireHeaderVisible(presentAppHeader, presentAppCatogery,onScreenvisibleappIndex, visibleappSectionHeaderIndex, appSectionHeaderRelativeElementContentDesc)) {
						presentSectionHeaderElement = getAppFuncHeaderNameElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeader = presentSectionHeaderElement.getText();
						appSectionElementsList = getAllChildElementsOfRelativeLayoutElementOfAppSectionHeaderByIndex(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeaderRelativeElement = getSectionHeaderRelativeLayoutElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						if(!duplicateIndeepViewCardsPageListInfo(screenappsCount, screenHeadersCount, presentAppHeader, presentAppCatogery, appSectionHeader,
								appSectionHeaderRelativeElementContentDesc)) {
							overallappSectionHeadersIndex++;
							if((presentAppHeader.equals(prevappHeader)) && 
									(presentAppCatogery.equals(prevappCatogery)) && 
									(appSectionHeaderRelativeElementContentDesc.equals(prevappSectionHeaderRelativeElementContentDesc))){
									appPosIndexRelativeToApp++;
							}else{
								overallappPosIndex++;
								appPosIndexRelativeToApp = 1;
							}
							DeepViewCardsList appDetails1 = new DeepViewCardsList(overallappPosIndex, overallappSectionHeadersIndex, appPosIndexRelativeToApp, presentAppHeader, presentAppCatogery,
									appSectionHeaderRelativeElementContentDesc, appSectionHeader, appSectionElementsList);
							deepViewCardsPageListInfo.add(appDetails1);
							prevappHeader = presentAppHeader;
							prevappCatogery = presentAppCatogery;
							prevappSectionHeaderRelativeElementContentDesc = appSectionHeaderRelativeElementContentDesc;
						} else {
							duplicateCount++;
							if ((duplicateCount == appSectionHeadersList.size()) && (visibleappSectionHeaderIndex == appSectionHeadersList.size())
									&& (onScreenvisibleappIndex == appFrameLayoutList.size())) {
								captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
								endOfScreen = true;
							}
						}
						if (presentAppHeader.equals(quixeyAppHeaderName) && presentAppCatogery.equals(quixeyAppHeaderCatogery) && appSectionHeader.equals(quixeySectionHeaderName)) {
							captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
//							matchingDeepViewCardsPageappDetails = new MatchingDeepViewCardsPageAppInfo(appHeaderElement, appCatogeryElement, presentSectionHeaderElement,appSectionElementsList);
							matchingDeepViewCardsPageappDetails = new MatchingDeepViewCardsPageAppInfo(presentAppHeader, presentAppCatogery, presentSectionHeaderElement, appSectionHeadersList);							
							foundMatchingapp = true;
							matchingappIndex = visibleappSectionHeaderIndex;
							endOfScreen = true;
						} else {
						}
					} else {
					}
				}
			}
			if (endOfScreen == false) {
  			swipeByElementSize(appSectionHeaderRelativeElement);				
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			}
		} while (!endOfScreen);

		displayAllAppsOnCardsPage();
		if (foundMatchingapp == true) {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean scrollTo_SearchPage_AppHeader_ByText(String objType, List<Param> params) throws NoSuchFieldException, SecurityException, IOException, InterruptedException {

		List<WebElement> appFrameLayoutList = null;
		List<WebElement> appSectionElementsList = null;
		List<WebElement> appSectionHeadersList = null;
		WebElement appHeaderElement = null;
		WebElement appCatogeryElement = null;
		WebElement presentSectionHeaderElement = null;
		boolean endOfScreen = false;
		String presentAppHeader = null;
		String presentAppCatogery = null;
		String appSectionHeader = null;
		WebElement presentSectionElement = null;
		int countSameScreen = 0;
		int swipeCount = 0;
		int screenHeadersCount = 0;
		int screenappsCount = 0;
		String prevappHeader = null;
		String prevappCatogery = null;
		boolean foundMatchingapp = false;
		int matchingappIndex = 0;
		int duplicateCount = 0;
		String quixeyAppORLogicalName;
		String quixeyAppHeaderName;
		String quixeyAppHeaderCatogery;
		String quixeySectionHeaderName;
		String appSectionHeaderFuncType;
		WebElement appSectionHeaderRelativeElement = null;
		String appSectionHeaderRelativeElementContentDesc = null;
		int lastappFrameIndex;
		boolean isEndOfScreenReached;
		int overallappSectionHeadersIndex = 0;
		String prevappSectionHeaderRelativeElementContentDesc = null;
		int appPosIndexRelativeToApp = 0;
		int overallappPosIndex = 0;
		WebElement appPrevHeaderElement = null;
		WebElement appPrevCatogeryElement = null;

		System.out.println("***************************************************************************");		
		System.out.println("In Method scrollTo_SearchPage_AppHeader_ByText");

		quixeyAppORLogicalName = params.get(0).getValue();
		quixeyAppHeaderName = params.get(1).getValue();
		quixeyAppHeaderCatogery = params.get(2).getValue();
		quixeySectionHeaderName = params.get(3).getValue();

		do {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			expandAllMoreOrLessContainers();
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			appFrameLayoutList = quixeyOnScreenVisibleAppsList();
			
			lastappFrameIndex = appFrameLayoutList.size();
			if(appFrameLayoutList.size() == 0){
				isEndOfScreenReached = true;
			}else{
				isEndOfScreenReached = isEndOfScreenReached_SearchPage(lastappFrameIndex, presentAppHeader, presentAppCatogery, appSectionHeader, appSectionHeaderRelativeElementContentDesc);
			}
			if(isEndOfScreenReached == true){
				displayAllAppsOnSearchPage();				
				return true;
			}

			for (int onScreenvisibleappIndex = 1; onScreenvisibleappIndex <= appFrameLayoutList.size(); onScreenvisibleappIndex++) {

				System.out.println("***************************************************************************");
				System.out.println("Processing onScreenvisibleapp Frame:" + onScreenvisibleappIndex);				
				
				appHeaderElement = getTextViewElementAppHeaderName(onScreenvisibleappIndex);
				appCatogeryElement = getTextViewElementAppCatogeryName(onScreenvisibleappIndex);

				if ((appHeaderElement == null) && (appCatogeryElement == null)) {
					if((prevappHeader != null) && (prevappCatogery != null)){
						presentAppHeader = prevappHeader;
						presentAppCatogery = prevappCatogery;
					}
					if((prevappHeader == null) && (prevappCatogery == null)){
						return false;
					}
				}
				if ((appHeaderElement != null) && (appCatogeryElement != null)) {
					presentAppHeader = appHeaderElement.getText();
					presentAppCatogery = appCatogeryElement.getText();
				}
				
				System.out.println("***************************************************************************");				

				appSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(onScreenvisibleappIndex);
				
				duplicateCount = 0;
				for(int visibleappSectionHeaderIndex = 1; visibleappSectionHeaderIndex <= appSectionHeadersList.size(); visibleappSectionHeaderIndex++) {
					System.out.println("Processing visibleappSectionHeader Headers:" + visibleappSectionHeaderIndex);					
					appSectionHeaderRelativeElementContentDesc = appSectionHeadersList.get(visibleappSectionHeaderIndex - 1).getAttribute("name");
					if(isEntireHeaderVisible(presentAppHeader, presentAppCatogery,onScreenvisibleappIndex, visibleappSectionHeaderIndex, appSectionHeaderRelativeElementContentDesc)) {
						presentSectionHeaderElement = getAppFuncHeaderNameElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeader = presentSectionHeaderElement.getText();
						appSectionElementsList = getAllChildElementsOfRelativeLayoutElementOfAppSectionHeaderByIndex(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeaderRelativeElement = getSectionHeaderRelativeLayoutElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						if(!duplicateInSearchPageCardsListInfo(screenappsCount, screenHeadersCount, presentAppHeader, presentAppCatogery, appSectionHeader,
								appSectionHeaderRelativeElementContentDesc)) {
							overallappSectionHeadersIndex++;
							if((presentAppHeader.equals(prevappHeader)) && 
									(presentAppCatogery.equals(prevappCatogery)) && 
									(appSectionHeaderRelativeElementContentDesc.equals(prevappSectionHeaderRelativeElementContentDesc))){
									appPosIndexRelativeToApp++;
							}else{
								overallappPosIndex++;
								appPosIndexRelativeToApp = 1;
							}
							SearchPageCardsList appDetails1 = new SearchPageCardsList(overallappPosIndex, overallappSectionHeadersIndex, appPosIndexRelativeToApp, presentAppHeader, presentAppCatogery,
									appSectionHeaderRelativeElementContentDesc, appSectionHeader, appSectionElementsList);
							searchPageCardsListInfo.add(appDetails1);
							prevappHeader = presentAppHeader;
							prevappCatogery = presentAppCatogery;
							prevappSectionHeaderRelativeElementContentDesc = appSectionHeaderRelativeElementContentDesc;
						} else {
							duplicateCount++;
							if ((duplicateCount == appSectionHeadersList.size()) && (visibleappSectionHeaderIndex == appSectionHeadersList.size())
									&& (onScreenvisibleappIndex == appFrameLayoutList.size())) {
								captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
								endOfScreen = true;
							}
						}
						if (presentAppHeader.equals(quixeyAppHeaderName) && presentAppCatogery.equals(quixeyAppHeaderCatogery) && appSectionHeader.equals(quixeySectionHeaderName)) {
							captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
							System.out.println("*******************************************************************************");
							System.out.println("App is Matched with Expected App Data");							
							System.out.println("*******************************************************************************");							
//							matchingSearchPageappDetails = new MatchingSearchPageAppInfo(appHeaderElement, appCatogeryElement, presentSectionHeaderElement, appSectionHeadersList);
							matchingSearchPageappDetails = new MatchingSearchPageAppInfo(presentAppHeader, presentAppCatogery, presentSectionHeaderElement, appSectionHeadersList);							
							foundMatchingapp = true;
							matchingappIndex = visibleappSectionHeaderIndex;
							endOfScreen = true;
						} else {
						}
					} else {
					}
				}
			}
			if (endOfScreen == false) {
  			swipeByElementSize(appSectionHeaderRelativeElement);				
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			}
		} while (!endOfScreen);

		displayAllAppsOnSearchPage();
		if (foundMatchingapp == true) {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			return false;
		}
	}
	
	public static String getObjectRepositoryElement(String objectRepositoryName) throws NoSuchFieldException, SecurityException {

		String objXpath = DataManager.objRepositories.get(objectRepositoryName).buildXpath();
		return objXpath;

	}
	
	public static boolean click_AppHeader_ByText(String objType, List<Param> params) throws InterruptedException, IOException {

		String quixeyAppORLogicalName;
		String quixeyAppHeaderName;
		String quixeyAppHeaderCatogery;
		String quixeySectionHeaderName;
		String matchingquixeyAppHeaderName = null;
		String matchingquixeyAppHeaderCatogery = null;
		String matchingquixeySectionHeaderName = null;
		String quixeyPage;
		WebElement matchingappSectionHeaderTextElement = null;

		System.out.println("In Method click_AppHeader_ByText");
		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));		

		quixeyPage = params.get(0).getValue();
		quixeyAppORLogicalName = params.get(1).getValue();
		quixeyAppHeaderName = params.get(2).getValue();
		quixeyAppHeaderCatogery = params.get(3).getValue();
		quixeySectionHeaderName = params.get(4).getValue();

		if (quixeyPage.equals("DeepViewCards Page")) {
			matchingquixeyAppHeaderName = matchingDeepViewCardsPageappDetails.appHeaderText;
			matchingquixeyAppHeaderCatogery = matchingDeepViewCardsPageappDetails.appCatogeryText;
			matchingquixeySectionHeaderName = matchingDeepViewCardsPageappDetails.appSectionHeaderTextElement.getText();
			matchingappSectionHeaderTextElement = matchingDeepViewCardsPageappDetails.appSectionHeaderTextElement;
		}
		if (quixeyPage.equals("Search Page")) {
			matchingquixeyAppHeaderName = matchingSearchPageappDetails.appHeaderText;
			matchingquixeyAppHeaderCatogery = matchingSearchPageappDetails.appCatogeryText;
			matchingquixeySectionHeaderName = matchingSearchPageappDetails.appSectionHeaderTextElement.getText();
			matchingappSectionHeaderTextElement = matchingSearchPageappDetails.appSectionHeaderTextElement;
		}

		if ((quixeyAppHeaderName.equals(matchingquixeyAppHeaderName)) && quixeyAppHeaderCatogery.equals(matchingquixeyAppHeaderCatogery)
				&& matchingquixeySectionHeaderName.equals(matchingquixeySectionHeaderName)) {
			Thread.sleep(5000);

			if (quixeyPage.equals("DeepViewCards Page")) {
				System.out.println("Clicking the Text Element in Matching APP in DeepViewCards Page");				
				matchingappSectionHeaderTextElement.click();
				matchingDeepViewCardsPageappDetails = null;
			}
			if (quixeyPage.equals("Search Page")) {
				System.out.println("Clicking the Text Element in Matching APP in Search Page");				
				matchingappSectionHeaderTextElement.click();				
				matchingSearchPageappDetails = null;
			}
			Thread.sleep(5000);
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean check_DeepLink_Element_Exists(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, IOException, InterruptedException {

		String appName;
		String appElementxpath;
		String appSectionHeaderText;
		String appElementORLogicalName;
		String appElementORLogicalNamexpath;

		System.out.println("In Method check_DeepLink_Element_Exists");
		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));		

		appName = params.get(0).getValue();
		appElementORLogicalName = params.get(1).getValue();
		appElementORLogicalNamexpath = getquixeyObjectRepositoryElement(appElementORLogicalName);
		System.out.println("appElementORLogicalName:appElementORLogicalNamexpath" + "::" + appElementORLogicalName + ":" + appElementORLogicalNamexpath);

		if (driver.findElements(By.xpath(getquixeyObjectRepositoryElement(appElementORLogicalName))).size() == 1) {
			System.out.println("FOUND ELEMENT" + ":" + "PASS");
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			return true;
		} else {
			System.out.println("FOUND ELEMENT MULTI OR NULL" + ":" + "FAIL");
			return false;
		}
	}
	
	public static boolean check_DeepLink_Text(String xpath, List<Param> params) throws NoSuchFieldException, SecurityException, IOException, InterruptedException {

		String appName;
		String appElementxpath;
		String appSectionHeaderText;
		String appElementORLogicalName;
		String appElementORLogicalNamexpath;

		System.out.println("In Method check_DeepLink_Text");
		captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));		

		appName = params.get(0).getValue();
		appElementORLogicalName = params.get(1).getValue();
		appSectionHeaderText = params.get(2).getValue();
		appElementORLogicalNamexpath = getquixeyObjectRepositoryElement(appElementORLogicalName);
		System.out.println("appElementORLogicalName:appElementORLogicalNamexpath" + "::" + appElementORLogicalName + appElementORLogicalNamexpath);

		if (driver.findElements(By.xpath(appElementORLogicalNamexpath)).size() == 1) {
			WebElement appTextElement = (WebElement) driver.findElements(By.xpath(getquixeyObjectRepositoryElement(appElementORLogicalName))).get(0);
			if (appName.equals("BookMyShow–Movie Tickets,Plays")) {
				appSectionHeaderText = appSectionHeaderText.toUpperCase();
			}
			if (appTextElement.getText().equals(appSectionHeaderText)) {
				System.out.println("*****************APP TEXT IS MATCHED WITH TESTCASE INPUT:PASS");
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				return true;
			} else {
				System.out.println("*****************APP TEXT IS NOT MATCHED WITH TESTCASE INPUT:FAIL");
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean cache_AllAppsInfo_DeepViewCardsPage(String objType, List<Param> params) throws IOException, NoSuchFieldException, SecurityException, InterruptedException {

		List<WebElement> appFrameLayoutList = null;
		List<WebElement> appSectionElementsList = null;
		List<WebElement> deepViewCardsDefaultElementList = null;
		boolean deepViewCardsInitialScreen = false;
		List<WebElement> appSectionHeadersList = null;
		WebElement appHeaderElement = null;
		WebElement appCatogeryElement = null;
		WebElement presentSectionHeaderElement = null;
		boolean endOfScreen = false;
		String presentAppHeader = null;
		String presentAppCatogery = null;
		String appSectionHeader = null;
		int screenHeadersCount = 0;
		int screenappsCount = 0;
		String prevappHeader = null;
		String prevappCatogery = null;
		boolean foundMatchingapp = false;
		int duplicateCount = 0;
		WebElement appSectionHeaderRelativeElement = null;
		String appSectionHeaderRelativeElementContentDesc = null;
		int lastappFrameIndex;
		boolean isEndOfScreenReached;
		int overallappSectionHeadersIndex = 0;
		int appPosIndexRelativeToApp = 0;
		int overallappPosIndex = 0;
		String prevappSectionHeaderRelativeElementContentDesc = null;
		
		System.out.println("In Method cache_AllAppsInfo_DeepViewCardsPage");

		do{
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));			
			deepViewCardsDefaultElementList = driver.findElementsByName(("Deep View Cards™"));
			if(deepViewCardsDefaultElementList.size() == 1){
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
				System.out.println("*********************default Screen in DEEP VIEW CARDS PAGE*********************");
				deepViewCardsInitialScreen = true;
			}else{
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				System.out.println("*********************Swiping to Get default Screen in DEEP VIEW CARDS PAGE*********************");				
				driver.swipe(360, 450, 360, 1073,  5000);
			}
		}while(!deepViewCardsInitialScreen);

		do {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			expandAllMoreOrLessContainers();
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			appFrameLayoutList = quixeyOnScreenVisibleAppsList();
			
			lastappFrameIndex = appFrameLayoutList.size();
			if(appFrameLayoutList.size() == 0){
				isEndOfScreenReached = true;
			}else{
				isEndOfScreenReached = isEndOfScreenReached_DeepViewCardsPage(lastappFrameIndex, presentAppHeader, presentAppCatogery, appSectionHeader, appSectionHeaderRelativeElementContentDesc);
			}
			if(isEndOfScreenReached == true){
				displayAllAppsOnCardsPage();				
				return true;
			}

			for (int onScreenvisibleappIndex = 1; onScreenvisibleappIndex <= appFrameLayoutList.size(); onScreenvisibleappIndex++) {

				appHeaderElement = getTextViewElementAppHeaderName(onScreenvisibleappIndex);
				appCatogeryElement = getTextViewElementAppCatogeryName(onScreenvisibleappIndex);
				appSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(onScreenvisibleappIndex);
				
				if ((appHeaderElement == null) && (appCatogeryElement == null)) {
					if((prevappHeader != null) && (prevappCatogery != null)){
						presentAppHeader = prevappHeader;
						presentAppCatogery = prevappCatogery;
					}
					if((prevappHeader == null) && (prevappCatogery == null)){
						return false;
					}
				}
				if ((appHeaderElement != null) && (appCatogeryElement != null)) {
					presentAppHeader = appHeaderElement.getText();
					presentAppCatogery = appCatogeryElement.getText();
				}

				duplicateCount = 0;
				
				for(int visibleappSectionHeaderIndex = 1; visibleappSectionHeaderIndex <= appSectionHeadersList.size(); visibleappSectionHeaderIndex++) {
					appSectionHeaderRelativeElementContentDesc = appSectionHeadersList.get(visibleappSectionHeaderIndex - 1).getAttribute("name");
					if(isEntireHeaderVisible(presentAppHeader, presentAppCatogery,onScreenvisibleappIndex, visibleappSectionHeaderIndex, appSectionHeaderRelativeElementContentDesc)) {
						presentSectionHeaderElement = getAppFuncHeaderNameElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeader = presentSectionHeaderElement.getText();
						appSectionElementsList = getAllChildElementsOfRelativeLayoutElementOfAppSectionHeaderByIndex(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeaderRelativeElement = getSectionHeaderRelativeLayoutElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						if(!duplicateIndeepViewCardsPageListInfo(screenappsCount, screenHeadersCount, presentAppHeader, presentAppCatogery, appSectionHeader,
								appSectionHeaderRelativeElementContentDesc)) {
							overallappSectionHeadersIndex++;
							if((presentAppHeader.equals(prevappHeader)) && 
									(presentAppCatogery.equals(prevappCatogery)) && 
									(appSectionHeaderRelativeElementContentDesc.equals(prevappSectionHeaderRelativeElementContentDesc))){
									appPosIndexRelativeToApp++;
							}else{
								overallappPosIndex++;
								appPosIndexRelativeToApp = 1;
							}
							DeepViewCardsList appDetails1 = new DeepViewCardsList(overallappPosIndex, overallappSectionHeadersIndex, appPosIndexRelativeToApp, presentAppHeader, presentAppCatogery,
									appSectionHeaderRelativeElementContentDesc, appSectionHeader, appSectionElementsList);
							deepViewCardsPageListInfo.add(appDetails1);
							prevappHeader = presentAppHeader;
							prevappCatogery = presentAppCatogery;
							prevappSectionHeaderRelativeElementContentDesc = appSectionHeaderRelativeElementContentDesc; 
						} else {
							duplicateCount++;
							if ((duplicateCount == appSectionHeadersList.size()) && (visibleappSectionHeaderIndex == appSectionHeadersList.size())
									&& (onScreenvisibleappIndex == appFrameLayoutList.size())) {
								captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
								endOfScreen = true;
							}
						}
					} else {
					}
				}
			}
			if (endOfScreen == false) {
  			swipeByElementSize(appSectionHeaderRelativeElement);				
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			}
		} while (!endOfScreen);

		displayAllAppsOnCardsPage();
		return true;
	}
	
	public static boolean check_appInfo_DeepViewCardsPageCache(String objType, List<Param> params) throws IOException, InterruptedException {

		String quixeyAppORLogicalName;
		String quixeyAppHeaderName;
		String quixeyAppHeaderCatogery;
		String quixeySectionHeaderName;
		String quixeySectionHeaderIndex;

		System.out.println("In Method check_appInfo_DeepViewCardsPageCache");
		quixeyAppORLogicalName = params.get(0).getValue();
		quixeyAppHeaderName = params.get(1).getValue();
		quixeyAppHeaderCatogery = params.get(2).getValue();
		quixeySectionHeaderName = params.get(3).getValue();
		quixeySectionHeaderIndex = params.get(4).getValue();

		for (int deepViewCardsPagelistIndex = 0; deepViewCardsPagelistIndex < deepViewCardsPageListInfo.size(); deepViewCardsPagelistIndex++) {
			if ((deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appName.equals(quixeyAppHeaderName))
					&& (deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appCatogery.equals(quixeyAppHeaderCatogery))
					&& (deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appHeaderName.equals(quixeySectionHeaderName))) {
				System.out.println("*********************APP IS MATCHED IN DEEP VIEW CARDS PAGE************************");
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				return true;
			}
		}
		return false;
	}

	public static boolean isEndOfScreenReached_SearchPage(int lastappFrameIndex, String appHeader, String appCatogery, String appSectionHeader, String appSectionHeaderContentDesc) throws NoSuchFieldException, SecurityException{

		WebElement appHeaderElement;
		WebElement appCatogeryElement;
		List<WebElement> appSectionHeadersList;
		String lastAppHeader;
		String lastAppCatogery;
		int applastSectionHeaderIndex = 0;
		String applastSectionHeaderFuncType = null;
		WebElement lastSectionHeaderElement;
		String applastSectionHeader;
		
		System.out.println("***************************************************************************");		
		System.out.println("In Function:isEndOfScreenReached_SearchPage");
		System.out.println("lastappFrameIndex:" + lastappFrameIndex);
		System.out.println("appHeader:" + appHeader);
		System.out.println("appCatogery:" + appCatogery);
		System.out.println("appSectionHeader:" + appSectionHeader);
		System.out.println("appSectionHeaderContentDesc:" + appSectionHeaderContentDesc);
		System.out.println("***************************************************************************");		
		
		if((appHeader != null) && (appCatogery != null) && (appSectionHeader != null) && (appSectionHeaderContentDesc != null)) {
			appHeaderElement = getTextViewElementAppHeaderName(lastappFrameIndex);
			appCatogeryElement = getTextViewElementAppCatogeryName(lastappFrameIndex);
			appSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(lastappFrameIndex);
			
			if ((appHeaderElement != null) && (appCatogeryElement != null)) {
				lastAppHeader = appHeaderElement.getText();
				lastAppCatogery = appCatogeryElement.getText();
			}else{
				lastAppHeader = appHeader;
				lastAppCatogery = appCatogery;				
			}
			if(appSectionHeadersList.size() > 0){
				applastSectionHeaderIndex = appSectionHeadersList.size() - 1;
				applastSectionHeaderFuncType = 	appSectionHeadersList.get(applastSectionHeaderIndex).getAttribute("name");
				applastSectionHeaderIndex++;
				if(isEntireHeaderVisible(lastAppHeader, lastAppCatogery, lastappFrameIndex, applastSectionHeaderIndex, applastSectionHeaderFuncType)) {
					lastSectionHeaderElement = getAppFuncHeaderNameElement(lastappFrameIndex, applastSectionHeaderIndex);
					applastSectionHeader = lastSectionHeaderElement.getText();
					if(duplicateInSearchPageCardsListInfo(0, 0, lastAppHeader, lastAppCatogery, applastSectionHeader,applastSectionHeaderFuncType)) {
						return true;
					} else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public static boolean isEndOfScreenReached_DeepViewCardsPage(int lastappFrameIndex, String appHeader, String appCatogery, String appSectionHeader, String appSectionHeaderContentDesc) throws NoSuchFieldException, SecurityException{

		WebElement aHeaderElement;
		WebElement aCatogeryElement;
		List<WebElement> aSectionHeadersList;
		String lastAppHeader;
		String lastAppCatogery;
		int applastSectionHeaderIndex = 0;
		String applastSectionHeaderFuncType = null;
		WebElement lastSectionHeaderElement;
		String applastSectionHeader;
		
		if((appHeader != null) && (appCatogery != null) && (appSectionHeader != null) && (appSectionHeaderContentDesc != null)) {
			aHeaderElement = getTextViewElementAppHeaderName(lastappFrameIndex);
			aCatogeryElement = getTextViewElementAppCatogeryName(lastappFrameIndex);
			aSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(lastappFrameIndex);
			
			if ((aHeaderElement != null) && (aCatogeryElement != null)) {
				lastAppHeader = aHeaderElement.getText();
				lastAppCatogery = aCatogeryElement.getText();
			}else{
				lastAppHeader = appHeader;
				lastAppCatogery = appCatogery;				
			}
			if(aSectionHeadersList.size() > 0){
				applastSectionHeaderIndex = aSectionHeadersList.size() - 1;
				applastSectionHeaderFuncType = 	aSectionHeadersList.get(applastSectionHeaderIndex).getAttribute("name");
				applastSectionHeaderIndex++;
				if(isEntireHeaderVisible(lastAppHeader, lastAppCatogery, lastappFrameIndex, applastSectionHeaderIndex, applastSectionHeaderFuncType)) {
					lastSectionHeaderElement = getAppFuncHeaderNameElement(lastappFrameIndex, applastSectionHeaderIndex);
					applastSectionHeader = lastSectionHeaderElement.getText();
					if(duplicateIndeepViewCardsPageListInfo(0, 0, lastAppHeader, lastAppCatogery, applastSectionHeader,applastSectionHeaderFuncType)) {
						return true;
					} else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}

	
	public static boolean cache_AllAppsInfo_SearchPage(String objType, List<Param> params) throws IOException, NoSuchFieldException, SecurityException, InterruptedException {

		List<WebElement> appFrameLayoutList = null;
		List<WebElement> appSectionElementsList = null;
		List<WebElement> appSectionHeadersList = null;
		WebElement appHeaderElement = null;
		WebElement appCatogeryElement = null;
		WebElement presentSectionHeaderElement = null;
		boolean endOfScreen = false;
		String presentAppHeader = null;
		String presentAppCatogery = null;
		String appSectionHeader = null;
		int screenHeadersCount = 0;
		int screenappsCount = 0;
		String prevappHeader = null;
		String prevappCatogery = null;
		int duplicateCount = 0;
		WebElement appSectionHeaderRelativeElement = null;
		String appSectionHeaderRelativeElementContentDesc = null;
		boolean isEndOfScreenReached = false;
		int lastappFrameIndex;
		int overallappSectionHeadersIndex = 0;
		int overallappPosIndex = 0;
		String prevappSectionHeaderRelativeElementContentDesc = null;
		int appPosIndexRelativeToApp = 0;

		System.out.println("In Method cache_AllAppsInfo_SearchPage");

		do {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			expandAllMoreOrLessContainers();
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			appFrameLayoutList = quixeyOnScreenVisibleAppsList();
			
			lastappFrameIndex = appFrameLayoutList.size();
			isEndOfScreenReached = isEndOfScreenReached_SearchPage(lastappFrameIndex, presentAppHeader, presentAppCatogery, appSectionHeader, appSectionHeaderRelativeElementContentDesc);
			if(isEndOfScreenReached == true){
				displayAllAppsOnSearchPage();				
				return true;
			}

			for (int onScreenvisibleappIndex = 1; onScreenvisibleappIndex <= appFrameLayoutList.size(); onScreenvisibleappIndex++) {

				appHeaderElement = getTextViewElementAppHeaderName(onScreenvisibleappIndex);
				appCatogeryElement = getTextViewElementAppCatogeryName(onScreenvisibleappIndex);
				appSectionHeadersList = getRelativeLayoutElementListAppSectionHeadersList(onScreenvisibleappIndex);
				
				if ((appHeaderElement == null) && (appCatogeryElement == null)) {
					if((prevappHeader != null) && (prevappCatogery != null)){
						presentAppHeader = prevappHeader;
						presentAppCatogery = prevappCatogery;
					}
					if((prevappHeader == null) && (prevappCatogery == null)){
						return false;
					}
				}
				if ((appHeaderElement != null) && (appCatogeryElement != null)) {
					presentAppHeader = appHeaderElement.getText();
					presentAppCatogery = appCatogeryElement.getText();
				}

				duplicateCount = 0;
				
				for(int visibleappSectionHeaderIndex = 1; visibleappSectionHeaderIndex <= appSectionHeadersList.size(); visibleappSectionHeaderIndex++) {
					appSectionHeaderRelativeElementContentDesc = appSectionHeadersList.get(visibleappSectionHeaderIndex - 1).getAttribute("name");
					if(isEntireHeaderVisible(presentAppHeader, presentAppCatogery,onScreenvisibleappIndex, visibleappSectionHeaderIndex, appSectionHeaderRelativeElementContentDesc)) {
						presentSectionHeaderElement = getAppFuncHeaderNameElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeader = presentSectionHeaderElement.getText();
						appSectionElementsList = getAllChildElementsOfRelativeLayoutElementOfAppSectionHeaderByIndex(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						appSectionHeaderRelativeElement = getSectionHeaderRelativeLayoutElement(onScreenvisibleappIndex, visibleappSectionHeaderIndex);
						if(!duplicateInSearchPageCardsListInfo(screenappsCount, screenHeadersCount, presentAppHeader, presentAppCatogery, appSectionHeader,
								appSectionHeaderRelativeElementContentDesc)) {
							overallappSectionHeadersIndex++;
							if((presentAppHeader.equals(prevappHeader)) && 
									(presentAppCatogery.equals(prevappCatogery)) && 
									(appSectionHeaderRelativeElementContentDesc.equals(prevappSectionHeaderRelativeElementContentDesc))){
									appPosIndexRelativeToApp++;
							}else{
								overallappPosIndex++;
								appPosIndexRelativeToApp = 1;
							}
							SearchPageCardsList appDetails1 = new SearchPageCardsList(overallappPosIndex, overallappSectionHeadersIndex, appPosIndexRelativeToApp, presentAppHeader, presentAppCatogery,
									appSectionHeaderRelativeElementContentDesc, appSectionHeader, appSectionElementsList);
							searchPageCardsListInfo.add(appDetails1);
							prevappHeader = presentAppHeader;
							prevappCatogery = presentAppCatogery;
							prevappSectionHeaderRelativeElementContentDesc = appSectionHeaderRelativeElementContentDesc; 
						} else {
							duplicateCount++;
							if ((duplicateCount == appSectionHeadersList.size()) && (visibleappSectionHeaderIndex == appSectionHeadersList.size())
									&& (onScreenvisibleappIndex == appFrameLayoutList.size())) {
								captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
								endOfScreen = true;
							}
						}
					} else {
					}
				}
			}
			if (endOfScreen == false) {
  			swipeByElementSize(appSectionHeaderRelativeElement);				
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			}
		} while (!endOfScreen);

		displayAllAppsOnSearchPage();
		return true;
	}
	
	public static boolean check_appInfo_SearchPageCache(String objType, List<Param> params) throws IOException, InterruptedException {

		String quixeyAppORLogicalName;
		String quixeyAppHeaderName;
		String quixeyAppHeaderCatogery;
		String quixeySectionHeaderName;
		String quixeySectionHeaderIndex;

		System.out.println("In Method check_appInfo_SearchPageCache");
		quixeyAppORLogicalName = params.get(0).getValue();
		quixeyAppHeaderName = params.get(1).getValue();
		quixeyAppHeaderCatogery = params.get(2).getValue();
		quixeySectionHeaderName = params.get(3).getValue();
		quixeySectionHeaderIndex = params.get(4).getValue();

		for (int searchPageCardslistIndex = 0; searchPageCardslistIndex < searchPageCardsListInfo.size(); searchPageCardslistIndex++) {
			if ((searchPageCardsListInfo.get(searchPageCardslistIndex).appName.equals(quixeyAppHeaderName))
					&& (searchPageCardsListInfo.get(searchPageCardslistIndex).appCatogery.equals(quixeyAppHeaderCatogery))
					&& (searchPageCardsListInfo.get(searchPageCardslistIndex).appHeaderName.equals(quixeySectionHeaderName))) {
				System.out.println("*********************APP IS MATCHED IN SEARCH PAGE CACHE************************");
				captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));				
				return true;
			}
		}
		return false;
	}
	
	public static boolean scrollTo_DeepViewCardsPage_AppHeader_ByIndex(String xpath, List<Param> params) {
		System.out.println("xPath is:" + xpath);
		for (int i = 0; i < params.size(); i++) {
			System.out.println("Params" + ":" + i + "::" + params.get(i).getValue());
		}

		System.out.println("In Method scrollTo_DeepViewCardsPage_AppHeader_ByIndex");
		return true;
	}
	
	public static boolean click_AppHeader_ByIndex(String xpath, List<Param> params) {
		System.out.println("xPath is:" + xpath);
		for (int i = 0; i < params.size(); i++) {
			System.out.println("Params" + ":" + i + "::" + params.get(i).getValue());
		}

		System.out.println("In Method click_AppHeader_ByIndex");
		return true;
	}
	
	public static boolean scrollTo_SearchPage_AppHeader_ByIndex(String xpath, List<Param> params) {
		System.out.println("xPath is:" + xpath);
		for (int i = 0; i < params.size(); i++) {
			System.out.println("Params" + ":" + i + "::" + params.get(i).getValue());
		}
		System.out.println("In Method scrollTo_SearchPage_AppHeader_ByIndex");
		return true;
	}	
	
	/* ************ Framework Library Functions ************* */
	public static void expandAllMoreOrLessContainers() throws IOException, InterruptedException {

		List<WebElement> MoreLessContainerList = null;
		boolean visiblemoreContainers = true;

		do {
			captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));
			MoreLessContainerList = driver.findElements(By.name("Show More"));
			if (MoreLessContainerList.size() > 0) {
				if (MoreLessContainerList.get(0).getText().equals("Show More")) {
					captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));					
					MoreLessContainerList.get(0).click();
					Thread.sleep(5000);
					captureScreenShot(DataManager.resultPath, trace(Thread.currentThread().getStackTrace()));					
				}
			} else {
				System.out.println("More or Less Containers List Size:" + MoreLessContainerList.size());
				visiblemoreContainers = false;
			}
		} while (visiblemoreContainers);
	}
	
	public static List<WebElement> quixeyOnScreenVisibleAppsList() {
		return driver.findElements(By.xpath("//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']"));
	}
	
	public static void clickShowMoreContainer(int appIndex) {

		List<WebElement> MoreLessContainerList = null;
		List<WebElement> textViewMoreOrLessElements = null;

		MoreLessContainerList = driver.findElements(By.xpath("//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]"
				+ "/android.widget.RelativeLayout" + "/android.widget.RelativeLayout[@resource-id = 'com.quixey.launch:id/function_more_less_container']"));
		if (debugLevel >= 3) {
			System.out.println("More or Less Containers List Size:" + MoreLessContainerList.size());
		}
		// MoreLessContainerList.get(0).click();

		if (MoreLessContainerList.size() == 1) {
			textViewMoreOrLessElements = driver.findElements(
					By.xpath("//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]" + "/android.widget.RelativeLayout"
							+ "/android.widget.RelativeLayout[@resource-id = 'com.quixey.launch:id/function_more_less_container']" + "/android.widget.TextView"));
			System.out.println("textview List Size:" + textViewMoreOrLessElements.size());
			if (textViewMoreOrLessElements.size() == 1) {
				if (debugLevel >= 3) {
					System.out.println("More or Less Containers Text :" + textViewMoreOrLessElements.get(0).getText());
				}
				if (textViewMoreOrLessElements.get(0).getText().equals("Show More")) {
					textViewMoreOrLessElements.get(0).click();
				}
			}
		}
	}

	public static void captureScreenShot(String directoryName, String functionName) throws IOException, InterruptedException {

		String fileNameFormat;
		String fileName;

		Thread.sleep(1000);
		fileNameFormat = new SimpleDateFormat("HHmmss").format(new Date());
		fileName = directoryName + "\\" + fileNameFormat + "_" + functionName + ".png";

		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(fileName), true);
	}
	
	public static WebElement getTextViewElementAppHeaderName(int appIndex) throws NoSuchFieldException, SecurityException {

		List<WebElement> HeaderElementList = null;
		WebElement HeaderElement = null;
		String appFrameLayoutxpathByIndex;
		String appNameRelxpathFromFrameLayout;
		String appNamexpath;

		System.out.println("***********************************************************************************");		
		System.out.println("In getTextViewElementAppHeaderName Function:");		
		appFrameLayoutxpathByIndex = getquixeyObjectRepositoryElement("appFrameLayout") + "[" + appIndex + "]";
		appNameRelxpathFromFrameLayout = getquixeyObjectRepositoryElement("appNameRelxpathFromFrameLayout");
		appNamexpath = appFrameLayoutxpathByIndex + appNameRelxpathFromFrameLayout;
		if (debugLevel <= 3) {
			System.out.println("appNamexpath:" + appNamexpath);
		}

		HeaderElementList = driver.findElements(By.xpath(appNamexpath));

		if (HeaderElementList.size() == 1) {
				System.out.println("App Header Text for App index of:" + appIndex + ":is:" + HeaderElementList.get(0).getText());
				HeaderElement = HeaderElementList.get(0);
		} else {
				//System.out.println("ERROR:Multiple App Header Text Elements or NULL value for AppHeaderElements for App index of:" + appIndex);
				System.out.println("Headers Size:" + HeaderElementList.size());
		}
		System.out.println("************************************************************************************");		
		return HeaderElement;
	}
	
	public static WebElement getTextViewElementAppCatogeryName(int appIndex) throws NoSuchFieldException, SecurityException {

		List<WebElement> CatogeryElementList = null;
		WebElement CatogeryElement = null;
		String appFrameLayoutxpathByIndex;
		String appCatogeryRelxpathFromFrameLayout;
		String appCatogeryxpath;

		System.out.println("***********************************************************************************");		
		System.out.println("In getTextViewElementAppCatogeryName Function:");		
		appFrameLayoutxpathByIndex = getquixeyObjectRepositoryElement("appFrameLayout") + "[" + appIndex + "]";
		appCatogeryRelxpathFromFrameLayout = getquixeyObjectRepositoryElement("appCatogeryRelxpathFromFrameLayout");
		appCatogeryxpath = appFrameLayoutxpathByIndex + appCatogeryRelxpathFromFrameLayout;
		if (debugLevel <= 3) {
			System.out.println("appCatogeryxpath:" + appCatogeryxpath);
		}
		CatogeryElementList = driver.findElements(By.xpath(appCatogeryxpath));

		if (CatogeryElementList.size() == 1) {
				System.out.println("App Catogery Text App index of:" + appIndex + ":is:" + CatogeryElementList.get(0).getText());
			CatogeryElement = CatogeryElementList.get(0);
		} else {
//			System.out.println("ERROR:Multiple App Catogery Text Elements or NULL value for AppCatogeryElements for App index of:" + appIndex);
				System.out.println("Headers Size:" + CatogeryElementList.size());
		}
		System.out.println("***********************************************************************************");		
		return CatogeryElement;
	}

	public static List<WebElement> getRelativeLayoutElementListAppSectionHeadersList(int appIndex) throws NoSuchFieldException, SecurityException {

		List<WebElement> appSectionHeadersList;
		String appFrameLayoutxpathByIndex;
		String appSectionHeaderRelxpathFromFrameLayout;
		String appSectionHeaderxpath;

		appFrameLayoutxpathByIndex = getquixeyObjectRepositoryElement("appFrameLayout") + "[" + appIndex + "]";
		appSectionHeaderRelxpathFromFrameLayout = getquixeyObjectRepositoryElement("appSectionHeaderRelxpathFromFrameLayout");
		appSectionHeaderxpath = appFrameLayoutxpathByIndex + appSectionHeaderRelxpathFromFrameLayout;
		if (debugLevel <= 3) {
			System.out.println("appSectionHeaderxpath:" + appSectionHeaderxpath);
		}
		appSectionHeadersList = driver.findElements(By.xpath(appSectionHeaderxpath));

/*		if (debugLevel <= 3) {
			System.out.println("App Function Headers Size of App index:" + appIndex + ":is:" + appSectionHeadersList.size());
			for (int relativeLayoutIndex = 0; relativeLayoutIndex < appSectionHeadersList.size(); relativeLayoutIndex++) {
				if (debugLevel <= 3) {
					System.out
							.println("content-desc of Visible appSectionHeader:" + relativeLayoutIndex + "is:" + appSectionHeadersList.get(relativeLayoutIndex).getAttribute("name"));
				}
			}
		}
*/		return appSectionHeadersList;
	}

	public static boolean isEntireHeaderVisible(String appName, String appHeaderName, int appIndex, int headerIndex, String aSectionHeaderFuncType)
			throws NoSuchFieldException, SecurityException {

		List<WebElement> appfirstElement = null;
		List<WebElement> applastElement = null;
		String firstElementxpath = null;
		String lastElementxpath = null;
		String elexpath = null;

		System.out.println("***********************************************************************************");		
		System.out.println("In isEntireHeaderVisible Function:");		
		System.out.println("AppName:" + appName );
		System.out.println("AppIndex" + appIndex );		
		System.out.println("AppHeaderIndex::" + headerIndex);		
	
		firstElementxpath = getAppSectionFirstElement(appName, appHeaderName, aSectionHeaderFuncType);
		if(firstElementxpath == null){
			return false;
		}
		firstElementxpath = firstElementxpath.replace("//", "/");

		if (debugLevel <= 3) {
			System.out.println("@firstElementxpath:" + firstElementxpath);
		}

		elexpath = "//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]" + "/android.widget.RelativeLayout"
				+ "/android.widget.LinearLayout[@resource-id='com.quixey.launch:id/function_deep_view_frame']" + "/android.widget.RelativeLayout" + "[" + headerIndex + "]"
				+ firstElementxpath;

		if (debugLevel <= 3) {
			System.out.println("Complete FirstElement Path:" + elexpath);
		}

		appfirstElement = driver.findElements(By.xpath(elexpath));
		if (debugLevel <= 3) {
			System.out.println("AppfirstElement Size is:" + appfirstElement.size());
		}

		lastElementxpath = getAppSectionLastElement(appName, appHeaderName, aSectionHeaderFuncType);
		if(lastElementxpath == null){
			return false;
		}
		
		lastElementxpath = lastElementxpath.replace("//", "/");
		if (debugLevel <= 3) {
			System.out.println("@lastElementxpath:" + lastElementxpath);
		}

		elexpath = "//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]" + "/android.widget.RelativeLayout"
				+ "/android.widget.LinearLayout[@resource-id='com.quixey.launch:id/function_deep_view_frame']" + "/android.widget.RelativeLayout" + "[" + headerIndex + "]"
				+ lastElementxpath;

		if (debugLevel <= 3) {
			System.out.println("Complete LastElement Path:" + elexpath);
		}

		applastElement = driver.findElements(By.xpath(elexpath));
		if (debugLevel <= 3) {
			System.out.println("AppLastElement Size is:" + applastElement.size());
		}

		System.out.println("***********************************************************************************");		
		if ((appfirstElement.size() == 1) && (applastElement.size() == 1)) {
			return true;
		}
		return false;
	}

	public static String getAppSectionFirstElement(String appName, String appHeaderName, String appFuncType) throws NoSuchFieldException, SecurityException {

		String firstElexpath = null;
		String appSectionFirstElementNameInAppMap;
		String appSectionFirstElementNameInOR;

		appSectionFirstElementNameInAppMap = appName + "|" + appHeaderName + "|" + appFuncType;
		System.out.println("App Section First ElementName In App Map:" + appSectionFirstElementNameInAppMap );		
		try{
			appSectionFirstElementNameInOR = DataManager.appElements.get(appSectionFirstElementNameInAppMap).get("AppSectionFirstElement");			
		}catch(Exception e){
/*			System.out.println("This Could be the End Of Screen Before Element");			
			System.out.println("This Could be the Wrong Reading");
			System.out.println("App Section First ElementName In OR:" + "NULL");			
*/			return null;
		}

		System.out.println("App Section First ElementName In OR:" + appSectionFirstElementNameInOR);
		firstElexpath = getquixeyObjectRepositoryElement(appSectionFirstElementNameInOR);
		System.out.println("App Section First ElementName OR xpath:" + firstElexpath);
		return firstElexpath;
	}

	public static String getAppSectionLastElement(String appName, String appHeaderName, String appFuncType) throws NoSuchFieldException, SecurityException {

		String lastElexpath = null;
		String appSectionLastElementNameInAppMap;
		String appSectionLastElementNameInOR;
		
		appSectionLastElementNameInAppMap = appName + "|" + appHeaderName + "|" + appFuncType;
		System.out.println("App Section Last ElementName In App Map:" + appSectionLastElementNameInAppMap);
		
		try{
			appSectionLastElementNameInOR = DataManager.appElements.get(appSectionLastElementNameInAppMap).get("AppSectionLastElement");			
		}catch(Exception e){
/*			System.out.println("This Could be the End Of Screen Before Element");			
			System.out.println("This Could be the Wrong Reading");
			System.out.println("App Section Last ElementName In OR:" + "NULL");			
*/			return null;
		}
		
		System.out.println("App Section Last ElementName In OR:" + appSectionLastElementNameInOR);		
		lastElexpath = getquixeyObjectRepositoryElement(appSectionLastElementNameInOR);
		System.out.println("App Section Last ElementName OR xpath:" + lastElexpath);		
		return lastElexpath;
	}

	public static WebElement getAppFuncHeaderNameElement(int appIndex, int headerIndex) {

		List<WebElement> FunctionHeaderNameList;

/*		FunctionHeaderNameList = driver.findElements(By.xpath("//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]"
				+ "/android.widget.RelativeLayout" + "/android.widget.LinearLayout[@resource-id='com.quixey.launch:id/function_deep_view_frame']"
				+ "/android.widget.RelativeLayout" + "[" + headerIndex + "]" + "/android.widget.LinearLayout" + "/android.widget.TextView[@index='0']"));
*/
		
		FunctionHeaderNameList = driver.findElements(By.xpath("//android.widget.FrameLayout[@resource-id='com.quixey.launch:id/function_cardview']" + "[" + appIndex + "]"
				+ "/android.widget.RelativeLayout" + "/android.widget.LinearLayout[@resource-id='com.quixey.launch:id/function_deep_view_frame']"
				+ "/android.widget.RelativeLayout" + "[" + headerIndex + "]" + "/android.widget.LinearLayout[@index = '0']" + "/android.widget.TextView[@index='0']"));
		
/*		if (debugLevel <= 3) {
			System.out.println("App Function Headers Size:" + FunctionHeaderNameList.size());
		}
*/		if (!FunctionHeaderNameList.isEmpty()) {
			if (debugLevel <= 3) {
				System.out.println("Visible Info of App>>AppIndex:HeaderIndex:HeaderText" + appIndex + ":" + headerIndex + ":" + FunctionHeaderNameList.get(0).getText());
			}
			return FunctionHeaderNameList.get(0);
		} else {
			return null;
		}
	}

	public static List<WebElement> getAllChildElementsOfRelativeLayoutElementOfAppSectionHeaderByIndex(int appIndex, int headerIndex)
			throws NoSuchFieldException, SecurityException {

		List<WebElement> allChildsElementsOfAppSectionHeaderByIndex;
		String appFrameLayoutxpathByIndex;
		String appSectionHeaderRelxpathFromFrameLayout;
		String appSectionHeaderxpathByIndex;
		String allChildsElementsOfAppSectionHeaderxpathByIndex;

		appFrameLayoutxpathByIndex = getquixeyObjectRepositoryElement("appFrameLayout") + "[" + appIndex + "]";
		appSectionHeaderRelxpathFromFrameLayout = getquixeyObjectRepositoryElement("appSectionHeaderRelxpathFromFrameLayout");
		appSectionHeaderxpathByIndex = appFrameLayoutxpathByIndex + appSectionHeaderRelxpathFromFrameLayout + "[" + headerIndex + "]";
		allChildsElementsOfAppSectionHeaderxpathByIndex = appSectionHeaderxpathByIndex + "//*";

		if (debugLevel <= 3) {
			System.out.println("appSectionHeaderxpathByIndex:" + appSectionHeaderxpathByIndex);
		}

		allChildsElementsOfAppSectionHeaderByIndex = driver.findElements(By.xpath(allChildsElementsOfAppSectionHeaderxpathByIndex));
		return allChildsElementsOfAppSectionHeaderByIndex;
	}
	
	
	public static boolean duplicateIndeepViewCardsPageListInfo(int inscreenappsCount, int inscreenHeadersCount, String inpresentAppHeader, String inpresentAppCatogery,
			String inappSectionHeader, String inappSectionHeaderRelativeElementContentDesc) {

		for (int deepViewCardsPagelistIndex = 0; deepViewCardsPagelistIndex < deepViewCardsPageListInfo.size(); deepViewCardsPagelistIndex++) {
			if ((deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appName.equals(inpresentAppHeader))
					&& (deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appCatogery.equals(inpresentAppCatogery))
					&& (deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appHeaderName.equals(inappSectionHeader))
					&& (deepViewCardsPageListInfo.get(deepViewCardsPagelistIndex).appContentDesc.equals(inappSectionHeaderRelativeElementContentDesc))) {
				return true;
			} else {
			}
		}
		return false;
	}

	public static boolean duplicateInSearchPageCardsListInfo(int inscreenappsCount, int inscreenHeadersCount, String inpresentAppHeader, String inpresentAppCatogery,
			String inappSectionHeader, String inappSectionHeaderRelativeElementContentDesc) {

		for (int searchPageCardslistIndex = 0; searchPageCardslistIndex < searchPageCardsListInfo.size(); searchPageCardslistIndex++) {

			if ((searchPageCardsListInfo.get(searchPageCardslistIndex).appName.equals(inpresentAppHeader))
					&& (searchPageCardsListInfo.get(searchPageCardslistIndex).appCatogery.equals(inpresentAppCatogery))
					&& (searchPageCardsListInfo.get(searchPageCardslistIndex).appHeaderName.equals(inappSectionHeader))
					&& (searchPageCardsListInfo.get(searchPageCardslistIndex).appContentDesc.equals(inappSectionHeaderRelativeElementContentDesc))) {
				return true;
			} else {
			}
		}
		return false;
	}
	
	public static String getquixeyObjectRepositoryElement(String objectRepositoryName) throws NoSuchFieldException, SecurityException {

		String objxpath;

		objxpath = DataManager.objRepositories.get(objectRepositoryName).buildXpath();
		System.out.println("ObjectORName:xPath::" + objectRepositoryName + ":" + objxpath);
		return objxpath;

	}
	
	public static WebElement getSectionHeaderRelativeLayoutElement(int appIndex, int headerIndex) throws NoSuchFieldException, SecurityException {
		List<WebElement> appSectionHeadersList;
		WebElement appSectionHeader = null;
		String appFrameLayoutxpathByIndex;
		String appSectionHeaderRelxpathFromFrameLayout;
		String appSectionHeaderxpathByIndex;

		appFrameLayoutxpathByIndex = getquixeyObjectRepositoryElement("appFrameLayout") + "[" + appIndex + "]";
		appSectionHeaderRelxpathFromFrameLayout = getquixeyObjectRepositoryElement("appSectionHeaderRelxpathFromFrameLayout");
		appSectionHeaderxpathByIndex = appFrameLayoutxpathByIndex + appSectionHeaderRelxpathFromFrameLayout + "[" + headerIndex + "]";

		if (debugLevel <= 3) {
			System.out.println("appSectionHeaderxpathByIndex:" + appSectionHeaderxpathByIndex);
		}

		appSectionHeadersList = driver.findElements(By.xpath(appSectionHeaderxpathByIndex));

		if (debugLevel <= 3) {
			System.out.println("App Section Headers Size:" + appSectionHeadersList.size());
		}

		if (!appSectionHeadersList.isEmpty()) {
			if (debugLevel >= 3) {
				System.out
						.println("Visible Info of App>>AppIndex:HeaderIndex:Content-Desc" + appIndex + ":" + headerIndex + ":" + appSectionHeadersList.get(0).getAttribute("name"));
			}
			appSectionHeader = appSectionHeadersList.get(0);
		} else {
//			System.out.println("ERROR:Either Multiple appSectionHeadersList or NULL:AppInfo>>AppIndex:HeaderIndex:Content-Desc" + appIndex + ":" + headerIndex + ":" + "NULL");
		}
		return appSectionHeader;
	}

	public static void displayAllAppsOnSearchPage() {

	  File searchPageFile;
		BufferedWriter searchPageoutput = null;
		String fileNameFormat;
		
		fileNameFormat = new SimpleDateFormat("HHmmss").format(new Date());

		try {
        searchPageFile = new File(DataManager.resultPath + "SearchPage_" + fileNameFormat + ".txt");
        searchPageoutput = new BufferedWriter(new FileWriter(searchPageFile.getAbsoluteFile(), true));
        searchPageoutput.write("***********************************SEARCH PAGE APP Information:***********************************");
    } catch ( IOException e ) {
        e.printStackTrace();
    }
		
		System.out.println("**********************************APP DETAILS IN SEARCH PAGE - START *********************************************");
		System.out.println("******************************************************************************************************************");		
		for (int appHeaderIndex = 0; appHeaderIndex < searchPageCardsListInfo.size(); appHeaderIndex++) {
			if (debugLevel <= 3) {
				System.out.println("*******************************************************************************************************");				
				System.out.println("AppName:" + searchPageCardsListInfo.get(appHeaderIndex).appName);
				System.out.println("AppCatogery:" + searchPageCardsListInfo.get(appHeaderIndex).appCatogery);
				System.out.println("AppHeaderName:" + searchPageCardsListInfo.get(appHeaderIndex).appHeaderName);
				System.out.println("AppHeaderName:" + searchPageCardsListInfo.get(appHeaderIndex).appContentDesc);
				System.out.println("AppPosIndexInEntirePage:" + searchPageCardsListInfo.get(appHeaderIndex).appPosIndexInEntirePage);				
				System.out.println("AppSectionHeaderPosIndexInEntirePage:" + searchPageCardsListInfo.get(appHeaderIndex).appSectionHeaderPosIndexInEntirePage);
				System.out.println("AppPosIndexRelativeToApp:" + searchPageCardsListInfo.get(appHeaderIndex).appPosIndexRelativeToApp);			
				System.out.println("*******************************************************************************************************");				
			}
			try {
				searchPageoutput.newLine();				
				searchPageoutput.write("**************************************************************************************");
				searchPageoutput.newLine();				
				searchPageoutput.write("appName:" + searchPageCardsListInfo.get(appHeaderIndex).appName);
				searchPageoutput.newLine();
				searchPageoutput.write("appCatogery:" + searchPageCardsListInfo.get(appHeaderIndex).appCatogery);
				searchPageoutput.newLine();				
				searchPageoutput.write("appHeaderName:" + searchPageCardsListInfo.get(appHeaderIndex).appHeaderName);
				searchPageoutput.newLine();				
				searchPageoutput.write("AppContentDesc:" + searchPageCardsListInfo.get(appHeaderIndex).appContentDesc);
				searchPageoutput.newLine();
				searchPageoutput.write("AppFrameLayoutListSize:" + searchPageCardsListInfo.get(appHeaderIndex).appFrameLayoutList.size());
				searchPageoutput.newLine();				
				searchPageoutput.write("appPosIndexInEntirePage:" + searchPageCardsListInfo.get(appHeaderIndex).appPosIndexInEntirePage);
				searchPageoutput.newLine();
				searchPageoutput.write("appPosIndexRelativeToApp:" + searchPageCardsListInfo.get(appHeaderIndex).appPosIndexRelativeToApp);				
				searchPageoutput.newLine();				
				searchPageoutput.write("appSectionHeaderPosIndexInEntirePage:" + searchPageCardsListInfo.get(appHeaderIndex).appSectionHeaderPosIndexInEntirePage);
				searchPageoutput.newLine();
				searchPageoutput.write("**************************************************************************************");
			} catch (IOException e) {
				System.out.println("**********************************EXCEPTION IN WRITING FILE*********************************************");
				e.printStackTrace();
			}
		}
		System.out.println("******************************************************************************************************************");		
		System.out.println("**********************************APP DETAILS IN SEARCH PAGE - END *********************************************");
		try {
			searchPageoutput.close();
		} catch (IOException e) {
			System.out.println("**********************************Exception IN Closing SEARCH PAGE File *********************************************");
			e.printStackTrace();
		}
		
	}

	public static void displayAllAppsOnCardsPage() {
		
	  File deepViewCardsPageFile;
		BufferedWriter deepViewCardsPageoutput = null;
		String fileNameFormat;
		
		fileNameFormat = new SimpleDateFormat("HHmmss").format(new Date());
		try {
        deepViewCardsPageFile = new File(DataManager.resultPath + "DeepViewCardsPage_" + fileNameFormat + ".txt");
        deepViewCardsPageoutput = new BufferedWriter(new FileWriter(deepViewCardsPageFile.getAbsoluteFile(), true));
  	    deepViewCardsPageoutput.write("***********************************DeepViewCards PAGE APP Information:***********************************");
    } catch ( IOException e ) {
        e.printStackTrace();
    }
		

		System.out.println("**********************************APP DETAILS IN DEEPVIEWCARDS PAGE - START *********************************************");
		System.out.println("******************************************************************************************************************");		

		for (int appHeaderIndex = 0; appHeaderIndex < deepViewCardsPageListInfo.size(); appHeaderIndex++) {
			if (debugLevel <= 3) {
				System.out.println("**************************************************************************************************************");				
				System.out.println("AppName:" + deepViewCardsPageListInfo.get(appHeaderIndex).appName);
				System.out.println("AppCatogery:" + deepViewCardsPageListInfo.get(appHeaderIndex).appCatogery);
				System.out.println("AppHeaderName:" + deepViewCardsPageListInfo.get(appHeaderIndex).appHeaderName);
				System.out.println("AppContentDesc:" + deepViewCardsPageListInfo.get(appHeaderIndex).appContentDesc);
				System.out.println("AppPosIndexInEntirePage:" + deepViewCardsPageListInfo.get(appHeaderIndex).appPosIndexInEntirePage);				
				System.out.println("AppSectionHeaderPosIndexInEntirePage:" + deepViewCardsPageListInfo.get(appHeaderIndex).appSectionHeaderPosIndexInEntirePage);
				System.out.println("AppPosIndexRelativeToApp:" + deepViewCardsPageListInfo.get(appHeaderIndex).appPosIndexRelativeToApp);
				System.out.println("************************************************************************************************************");				
			}
			try {
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("*****************************************************************************************************");
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("appName:" + deepViewCardsPageListInfo.get(appHeaderIndex).appName);
				deepViewCardsPageoutput.newLine();
				deepViewCardsPageoutput.write("appCatogery:" + deepViewCardsPageListInfo.get(appHeaderIndex).appCatogery);
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("appHeaderName:" + deepViewCardsPageListInfo.get(appHeaderIndex).appHeaderName);
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("AppContentDesc:" + deepViewCardsPageListInfo.get(appHeaderIndex).appContentDesc);
				deepViewCardsPageoutput.newLine();
				deepViewCardsPageoutput.write("AppFrameLayoutListSize:" + deepViewCardsPageListInfo.get(appHeaderIndex).appFrameLayoutList.size());
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("appPosIndexInEntirePage:" + deepViewCardsPageListInfo.get(appHeaderIndex).appPosIndexInEntirePage);
				deepViewCardsPageoutput.newLine();
				deepViewCardsPageoutput.write("appPosIndexRelativeToApp:" + deepViewCardsPageListInfo.get(appHeaderIndex).appPosIndexRelativeToApp);				
				deepViewCardsPageoutput.newLine();				
				deepViewCardsPageoutput.write("appSectionHeaderPosIndexInEntirePage:" + deepViewCardsPageListInfo.get(appHeaderIndex).appSectionHeaderPosIndexInEntirePage);
				deepViewCardsPageoutput.newLine();
				deepViewCardsPageoutput.write("***********************************************************************************************************");
			} catch (IOException e) {
				System.out.println("**********************************EXCEPTION IN WRITING FILE*********************************************");
				e.printStackTrace();
			}
		}
		System.out.println("***********************************************************************************************************************");
		System.out.println("**********************************APP DETAILS IN DEEPVIEWCARDS PAGE - END *********************************************");
		try {
			deepViewCardsPageoutput.close();
		} catch (IOException e) {
			System.out.println("**********************************Exception IN Closing DEEPVIEWCARDS PAGE File *********************************************");
			e.printStackTrace();
		}
	}
	
	public static String trace(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				return s.getMethodName();
			}
			doNext = s.getMethodName().equals("getStackTrace");
		}
		return null;
	}
	
public static void swipeByElementSize(WebElement swipeElement){
		
		Dimension size;	
		int x1;
		int y1;
		int x2;
		int y2;
		
		size = driver.manage().window().getSize();
		org.openqa.selenium.Dimension elementSize = swipeElement.getSize();
		
		System.out.println("*********************SWIPING THE SCREEN BY SECTION HEADER SIZE******************");		
		if(debugLevel <= 3){
			System.out.println("Device Screen Height:Width::" + size.height + ":" + size.width);			
			System.out.println("Card Section Header Element Height:Width::" + elementSize.height + ":" + elementSize.width);			
		}

		Point p = swipeElement.getLocation();
		if(debugLevel <= 3){
			System.out.println("Swiping Element Location => x:y::" + p.x + ":" + p.y);			
		}
		
		x1 = (p.x+elementSize.width)/2;
		y1 = (int) ((p.y+elementSize.height)*0.90);
		x2 = (p.x+elementSize.width)/2;
		y2 = (int) (size.height * 0.10);

		System.out.println("Swiping Screen => x1:y1::x2:y2::" + x1 + ":" + y1 + "::" + x2 + ":" + y2);
		System.out.println("*********************************************************************************");		
		driver.swipe(x1, y1, x2, y2, 8000);		
	}
}