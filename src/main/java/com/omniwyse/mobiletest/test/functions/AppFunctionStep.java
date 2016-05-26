package com.omniwyse.mobiletest.test.functions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.omniwyse.mobiletest.data.DataManager;
import com.omniwyse.mobiletest.driver.AppiumFramework;
import com.omniwyse.mobiletest.driver.SeleniumFramework;
import com.omniwyse.mobiletest.test.object.ObjectRepository;
import com.omniwyse.mobiletest.test.param.Param;

public class AppFunctionStep {
	private String action;
	private Boolean expectedResult;
	private String object;
	private List<Param> params;

	public Boolean getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(Boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Boolean execute(List<Param> parentParams, String product) throws Exception {
		System.out.println("App Step Execute");
		String xpath = null;
		if (object.equals("::Device") || object.equals("::Function")) {
			xpath = object;
		} else {
			ObjectRepository objRep;
			if (object.contains("$Param")) {
				int index = Integer.parseInt(object.split("m")[1]);
				System.out.println(index + "         " + parentParams.get(index - 1).getValue());
				objRep = DataManager.objRepositories.get(parentParams.get(index - 1).getValue());
			} else {
				objRep = DataManager.objRepositories.get(object);
			}
			System.out.println("current object is: " + object);
			xpath = objRep.buildXpath();
		}
		System.out.println("XPATH:::::" + xpath);
		// Object frameworkInstance=getFrameworkInstance(product);
		// AppiumFramework apf = new AppiumFramework();
		java.lang.reflect.Method method = getFrameworkInstance(product).getClass().getMethod(getAction(), String.class, List.class);
		System.out.println("Invoking Framework Methods");
		return (Boolean) method.invoke(getFrameworkInstance(product), xpath, extractParamsFromParent(parentParams));
	}

	private Object getFrameworkInstance(String product) {
		Object frameworkInstance = null;
		if ("Appium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			frameworkInstance = new AppiumFramework();
		} else if ("Selenium".equals(DataManager.configInfo.get(product).get("Framework"))) {
			frameworkInstance = new SeleniumFramework();
		} else if ("UIAutomater".equals(DataManager.configInfo.get(product).get("Framework"))) {
			frameworkInstance = null;// new UIAutomaterFramework();
		}

		return frameworkInstance;
	}

	public List<Param> extractParamsFromParent(List<Param> parentParams) {
		List<Param> newParams = new ArrayList<Param>();
		Iterator<Param> itr = getParams().iterator();
		while (itr.hasNext()) {
			Param param = itr.next();
			if (!param.getValue().contains("$Param")) {
				newParams.add(param);
			} else {
				Param newParam = new Param();
				int index = Integer.parseInt(param.getValue().split("m")[1]);
				newParam.setValue(parentParams.get(index - 1).getValue());
				newParams.add(newParam);
			}
		}
		return newParams;
	}
}
