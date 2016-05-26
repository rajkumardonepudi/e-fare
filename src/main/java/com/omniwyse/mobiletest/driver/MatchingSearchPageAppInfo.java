package com.omniwyse.mobiletest.driver;

import java.util.List;

import org.openqa.selenium.WebElement;

public class MatchingSearchPageAppInfo {
	
	String appHeaderText;
	String appCatogeryText;
	WebElement appSectionHeaderTextElement;

	List<WebElement> appSectionHeadersList;

	public MatchingSearchPageAppInfo(String aHeaderText, String aCatogeryText, WebElement aSectionHeaderTextElement, List<WebElement> aSectionHeadersList){

		appHeaderText = aHeaderText;
		appCatogeryText = aCatogeryText;
		appSectionHeaderTextElement = aSectionHeaderTextElement;
		appSectionHeadersList = aSectionHeadersList;
		
		System.out.println("*********In MatchingSearchPageAppInfo Constructor**********");
		System.out.println("appHeaderText:" + aHeaderText);
		System.out.println("appCatogeryText:" + aCatogeryText);		
		System.out.println("appSectionHeaderText:" + aSectionHeaderTextElement);
		System.out.println("appSectionHeadersList:" + appSectionHeadersList.size());
		System.out.println("***********************************************************");		
	}
	

}
