package com.omniwyse.mobiletest.test.cases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.omniwyse.mobiletest.data.DataManager;
import com.omniwyse.mobiletest.test.functions.AppFunction;
import com.omniwyse.mobiletest.test.functions.AppFunctionStep;
import com.omniwyse.mobiletest.test.param.Param;

public class TestCaseStep {	
	//final static Logger logger = Logger.getLogger(TestCasesStep.class.getClass());
	private String keyword;
	private Boolean expectedResult;
	private Boolean actualResult = false;
	private List<Param> params;

	public TestCaseStep(String repositoryPath, XSSFRow row, String testCaseName, String product) throws Exception {
		keyword = row.getCell(0).toString();
		
		expectedResult = DataManager.getBooleanValue(row);
		params = executeTestCaseStep(row, testCaseName, product);
	}

	private List<Param> executeTestCaseStep(XSSFRow row, String testCaseName, String product) throws Exception {
		System.out.println("test case step is executing...");
		List<Param> params = new ArrayList<Param>();
		int colIndex = 3;
		while (row.getCell(colIndex) != null && !"".equals(row.getCell(colIndex).toString())) {
			Param param = new Param(row.getCell(colIndex));
			params.add(param);
			colIndex++;
		}
		execute(params,testCaseName, product);
		return params;
	}
	
	private void execute(List<Param> parentParams, String testCaseName, String product) throws Exception {
		System.out.println("In Test Case Step Execute method with Params..");
		System.out.println("Keyword is : " + keyword);
		AppFunction appFunction = DataManager.appFunctions.get(keyword);
		System.out.println("Functions size " + appFunction.getFunctions().size());
		Iterator<AppFunctionStep> itr = appFunction.getFunctions().iterator();
		while (itr.hasNext()) {
			System.out.println("Inside Iterator App Step Execute..");
			AppFunctionStep appStep = itr.next();
			setActualResult(appStep.execute(parentParams, product));
			boolean flag=false;
			if(getActualResult().equals(getExpectedResult()))
				flag=true;
			String result=flag?"PASS":"FAIL";
			//logger.info(" : "+testCaseName+" : "+appFunction.getName()+" : "+appStep.getAction()+" : "+result+"  **********************************************");
			System.out.println("******************************" +new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss,SSS").format(Calendar.getInstance().getTime())+" : "+testCaseName+" : "+appFunction.getName()+" : "+appStep.getAction()+" : "+result+"  **********************************************");
		}
	}

	public Boolean getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(Boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Param> getParams() {
		return params;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public Boolean getActualResult() {
		return actualResult;
	}

	public void setActualResult(Boolean actualResult) {
		this.actualResult = actualResult;
	}

}
