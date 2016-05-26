package com.omniwyse.mobiletest.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.omniwyse.mobiletest.data.DataManager;
import com.omniwyse.mobiletest.driver.OmniDriver;
import com.omniwyse.mobiletest.test.suites.TestSuite;
import com.omniwyse.mobiletest.test.cases.TestCases;
import com.omniwyse.mobiletest.test.cases.TestCaseStep;

public class Reporter {

	private static final String resultPlaceholder = "<!-- INSERT_RESULTS -->";
	private static final String currentSuiteName = "Test results";
	private static final String currentDateandTime = "Date&Time";
	private static final String templatePath = "D:\\oWyseTestBase\\Results\\" + "TestPlan_Report.html";

	public static void writeReport(List<TestSuite> testSuites, String currentDate) throws Exception {
		String reportIn = new String(Files.readAllBytes(Paths.get(templatePath)));
		reportIn = reportIn.replaceFirst(currentSuiteName, "Report of TestPlan");
		reportIn = reportIn.replaceFirst(currentDateandTime, currentDate);

		for (int i = 0; i < testSuites.size(); i++) {
			if (testSuites.get(i).getName().equals("Not Applicable")) {
				reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#cc99ff'>" + testSuites.get(i).getProduct() + "</td><td bgcolor='#0066ff'>"
						+ testSuites.get(i).getName() + "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffff99'><b>Not Applicable</b></font></td></tr>"
						+ resultPlaceholder);
			} else {
				if (testSuites.get(i).getResult() == true)
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#cc99ff'>" + testSuites.get(i).getProduct() + "</td><td bgcolor='#0066ff'>"
							+ testSuites.get(i).getName() + "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>"
							+ resultPlaceholder);
				else
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#cc99ff'>" + testSuites.get(i).getProduct() + "</td><td bgcolor='#0066ff'>"
							+ testSuites.get(i).getName() + "</td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>"
							+ resultPlaceholder);
			}

			for (int j = 0; j < testSuites.get(i).getTestCases().size(); j++) {
				TestCases testCase = testSuites.get(i).getTestCases().get(j);
				if (testCase.getResult() == true)
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ff9900'>" + testCase.getId()
							+ "</td><td bgcolor='#ffffff'></td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>" + resultPlaceholder);
				else
					reportIn = reportIn.replaceFirst(resultPlaceholder, "<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ff9900'>" + testCase.getId()
							+ "</td><td bgcolor='#ffffff'></td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>" + resultPlaceholder);

				for (int k = 0; k < testCase.getTestCaseSteps().size(); k++) {
					TestCaseStep testStep = testCase.getTestCaseSteps().get(k);
					System.out.println("Actual TS result :: " + testStep.getActualResult());
					System.out.println("Expected TS result ::  " + testStep.getExpectedResult());
					if (testStep.getActualResult().equals(testStep.getExpectedResult()))
						reportIn = reportIn.replaceFirst(resultPlaceholder,
								"<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ccccff'>" + testStep.getKeyword()
										+ "</td><td><font color='#00cc00'><b>PASSED</b></font></td></tr>" + resultPlaceholder);
					else
						reportIn = reportIn.replaceFirst(resultPlaceholder,
								"<tr><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='#ffffff'></td><td bgcolor='ccccff'>" + testStep.getKeyword()
										+ "</td><td><font color='#cc3300'><b>FAILED</b></font></td></tr>" + resultPlaceholder);
				}

			}

		}
		String reportPath = "D:\\oWyseTestBase\\Results\\" + DataManager.currentDate + "\\Report.html";
		Files.write(Paths.get(reportPath), reportIn.getBytes(), StandardOpenOption.CREATE);
	}

	public static void captureScreenShot(String directoryName) throws IOException {
		String fileNameFormat;
		String fileName;
		fileNameFormat = new SimpleDateFormat("HHmmss.SS").format(new Date());
		fileName = directoryName + "\\" + fileNameFormat + "_" + UUID.randomUUID().toString() + ".png";
		System.out.println("FileName:" + fileName);
		WebDriver augmentedDriver = new Augmenter().augment(OmniDriver.driver);
		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(fileName), true);
	}
}
