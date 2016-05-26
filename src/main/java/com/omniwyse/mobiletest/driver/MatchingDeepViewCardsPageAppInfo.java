package com.omniwyse.mobiletest.driver;

import java.util.List;

import org.openqa.selenium.WebElement;

public class MatchingDeepViewCardsPageAppInfo {
	
	String appHeaderText;
	String appCatogeryText;
	WebElement appSectionHeaderTextElement;

	List<WebElement> appSectionHeadersList;

	public MatchingDeepViewCardsPageAppInfo(String aHeaderText, String aCatogeryText, WebElement aSectionHeaderTextElement, List<WebElement> aSectionHeadersList){		

		appHeaderText = aHeaderText;
		appCatogeryText = aCatogeryText;
		appSectionHeaderTextElement = aSectionHeaderTextElement;
		appSectionHeadersList = aSectionHeadersList;
		
		System.out.println("*********In MatchingDeepViewCardsPageAppInfo Constructor**********");
		System.out.println("appHeaderTextElement:" + aHeaderText);
		System.out.println("appCatogeryTextElement:" + aCatogeryText);		
		System.out.println("appSectionHeaderTextElement:" + appSectionHeaderTextElement);
		System.out.println("appSectionHeadersList:" + appSectionHeadersList.size());
		System.out.println("******************************************************************");		
	}

}
