package com.qa.ExtentReportListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sun.deploy.util.SessionState.init;

public class ExtentIReporterSuiteListenerAdapter implements IReporter {
    private static final Calendar CALENDAR = Calendar.getInstance();
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        init(outputDirectory);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {

                ITestContext context = r.getTestContext();
                ExtentTest suiteTest = extent.createTest(r.getTestContext().getCurrentXmlTest().getName());

                buildTestNodes(suiteTest, context.getPassedTests(), Status.PASS);
                buildTestNodes(suiteTest, context.getFailedTests(), Status.FAIL);
                buildTestNodes(suiteTest, context.getSkippedTests(), Status.SKIP);


            }
        }
        //To print the TestRunner Logs
        for (String s : Reporter.getOutput()) {
            extent.setTestRunnerOutput(s);

        }

        extent.flush();

    }

    private void buildTestNodes(ExtentTest suiteTest, IResultMap tests, Status status) {
        ExtentTest test;

        if (tests.size() > 0) {

            for (ITestResult result : tests.getAllResults()) {

                test = suiteTest.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription());
                test.getModel().setDescription("Test Description: " + result.getMethod().getDescription());
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    if (result.getThrowable() instanceof java.lang.AssertionError) {
                        test.log(status, result.getThrowable().getMessage());
                        //Attach screenshot
                        try {
                            test.log(status,
                                    "Exception/Assertion screenshot:" + test.addScreenCaptureFromPath(".\\screenshots\\" + result.getMethod().getXmlTest().getName() + result.getMethod().getMethodName() + ".png", "ExceptionScreenshot"));
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        if (result.getMethod().getTestClass().getAfterTestMethods().length > 0)
                            test.log(status, result.getMethod().getTestClass().getAfterTestMethods()[0].getDescription());

                    } else {
                        //node.log(status, "Test " + status.toString().toLowerCase() + "ed");
                        test.log(status, "Test " + status.toString().toLowerCase() + "ed" + ": Due to Webdriver exception. Please re-run again\n"
                                .concat("\n<a href='.\\index.html' target='_blank'><span class='label info'>DetailedException</span></a>"));
                    }

                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));


            }
        }
    }

    private Date getTime(long millis) {

        CALENDAR.setTimeInMillis(millis);
        return CALENDAR.getTime();
    }
}

