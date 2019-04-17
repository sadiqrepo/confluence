package util;

import org.testng.*;
import org.apache.log4j.Logger;


/**
 * Created by sadiq on 11/04/19.
 */

public class LogUtil extends TestListenerAdapter implements ITestListener {

    //Initialize Log4j logs
    private static Logger log = Logger.getLogger(ConfigReader.get("logger.file"));;




    // Need to create these methods, so that they can be called

    public static void info(String message) {
        log.info(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void fatal(String message) {
        log.fatal(message);
    }

    public static void debug(String message) {
        log.debug(message);
    }


    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
    public static void startTestCase(String sTestCaseName){

        log.info("\n****************************************************************************************");

        log.info("****************************************************************************************");

        log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");

        log.info("****************************************************************************************");

        log.info("****************************************************************************************");

    }

    //This is to print log for the ending of the test case

    public static void endTestCase(String sTestCaseName){

        log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");

        log.info("X");

        log.info("X");

        log.info("X\n");


    }


    /**
     * Class TestListenerAdapter
     * Implemented methods:	onFinish,onStart,onTestFailedButWithinSuccessPercentage,onTestFailure,onTestSkipped,onTestStart,onTestSuccess
     * UnImplmented methods:beforeConfiguration,getAllTestMethods,getConfigurationFailures,getConfigurationSkips,getFailedButWithinSuccessPercentageTests
     * getFailedTests,getPassedTests,getSkippedTests,getTestContexts,onConfigurationFailure,onConfigurationSkip,onConfigurationSuccess,setAllTestMethods
     * setFailedButWithinSuccessPercentageTests, setFailedTests,setPassedTests,setSkippedTests
     *
     * Interface ITestResult
     * Implemented:getMethod(),getTestClass(),getThrowable(),getName()
     * Unimplemented:getStatus(),getStartMillis(),getEndMillis(),isSuccess(),getHost(),getInstance(),getTestName(),
     * getInstanceName(),getTestContext()
     */

    @Override
    public void onStart(ITestContext context) {
        log.info(context.getName()+ "--Test start\n");
    }

    @Override
    public void onTestStart(ITestResult result) {

        log.info(result.getTestClass()+":"+result.getName()+ "--Test Started->");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        log.info("Test '" + result.getName() + "'  PASSED  ");

        // This will print the class name in which the method is present
        //log.info(""+result.getTestClass());

        // This will print the priority of the method.
        // If the priority is not defined it will print the default priority as
        // 'o'

        log.info("Priority of this method is " + result.getMethod().getPriority()+"\n\n");

        log.info("Priority of this method is " + result.getMethod().getPriority());
        log.info("### ### ### ### ### ### ### ### ### ### ### ### ###\n");


    }

    @Override
    public void onFinish(ITestContext context) {
        log.info(context.getName()+ "--Test Finish->\n\n");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.error(result.getName() + "--Test Skipped");
        log.warn("Test '" + result.getName() + "' SKIPPED\n");
        System.out.println(".....");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        log.error("Test '" + result.getName() + "' FAILED");
        log.error(Reporter.getCurrentTestResult());
        log.error(result.getThrowable());

        log.error("Priority of this method is " + result.getMethod().getPriority() + "\n\n");

        log.error("Priority of this method is " + result.getMethod().getPriority());
        log.info("###### ###### ###### ###### ###### ######\n");



    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.error(result.getName() + "--Test Failed with success percentage");
        log.error("Priority of this method is " + result.getMethod().getPriority() + "\n");
    }

    /**	private void log(String methodName) {
     System.out.println(methodName);
     }

     private void log(IClass testClass) {
     System.out.println(testClass);
     }*/



}
