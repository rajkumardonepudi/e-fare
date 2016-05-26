package com.omniwyse.mobiletest.driver;

import java.util.List;

import org.openqa.selenium.WebElement;

public class SearchPageCardsList {
	
	int appPosIndexInEntirePage;
	int appSectionHeaderPosIndexInEntirePage;
	int appPosIndexRelativeToApp;
	
	String appName;
	String appCatogery;
	String appContentDesc;	
	String appHeaderName;
	
	List<WebElement> appFrameLayoutList;
	
	
	public SearchPageCardsList(int aPosIndexInEntirePage, int aSectionHeaderPosIndexInEntirePage, int aPosIndexRelativeToApp, String aName, String aCatogery, String aContentDesc, String aHeaderName, List<WebElement> aFrameLayoutList){

		appPosIndexInEntirePage = aPosIndexInEntirePage;
		appSectionHeaderPosIndexInEntirePage = aSectionHeaderPosIndexInEntirePage;
		appPosIndexRelativeToApp = aPosIndexRelativeToApp;
		
		appName = aName;
		appCatogery = aCatogery;
		appContentDesc = aContentDesc;		
		appHeaderName = aHeaderName;
		
		appFrameLayoutList = aFrameLayoutList;
		System.out.println("*********In SearchPageCardsList Constructor**********");
		System.out.println("appName:" + aName);
		System.out.println("appCatogery:" + aCatogery);			
		System.out.println("appHeaderName:" + aHeaderName);
		System.out.println("appFrameLayoutListSize:" + aFrameLayoutList.size());			
		System.out.println("appPosIndexInEntirePage:" + aPosIndexInEntirePage);
		System.out.println("appPosIndexRelativeToApp:" + appPosIndexRelativeToApp);		
		System.out.println("appSectionHeaderPosIndexInEntirePage:" + aSectionHeaderPosIndexInEntirePage);
		System.out.println("****************************************************");		
	}
}
