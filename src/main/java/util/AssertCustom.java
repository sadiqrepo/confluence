package util;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Created by sadiq on 13/04/19.
 */
public class AssertCustom {



        private WebDriver driver;
        static ITestResult result;
        public static final String SOFTASSERT = ConfigReader.get("softassert");


        public AssertCustom(WebDriver driver)
        {
            this.driver = driver;
        }

    public static void assertTrueTextContains(String expect, String original)
    {
        assertTrueTextContains(expect,original,null);
    }


    public static String getPageSource() {
        return new BaseTest().driver.getPageSource();
    }

    public static boolean checkPageSourceContains(String text) {
        return getPageSource().contains(text);
    }



    public static void assertTruePageText(String text) {
        assertTruePageText(text, null);
    }

    public static void assertTruePageText(String text, String failMessage) {
        WaitCustom.sleep(500);
        assertTrue(checkPageSourceContains(text), failMessage);

    }

    public static  void assertTrueTextContains(String expect, String original, String failMessage)
    {
        assertTrue(original.contains(expect),failMessage);
    }

    public static  void assertTrueTextEquals(String expect, String original, String failMessage)
    {
        final String failMessageCustom = failMessage+"\nAssertion failed while performing string equality.\nExpected: "+expect+"\nActual: "+original+"\n";
        final String passMessageCustom = "Assertion PASSED while performing string equality.\nExpected: "+expect+"\nActual: "+original+"\n";

        assertTrue(original.equals(expect),failMessageCustom);
        LogUtil.info(passMessageCustom);
    }

    public static void assertFalseTextContains(String expect, String original)
    {
        assertFalseTextContains(expect,original,null);
    }

    public static  void assertFalseTextContains(String expect, String original, String failMessage)
    {
        assertFalse(original.contains(expect),failMessage);
    }


    public static void assertTrueValue(boolean value, String failMessage) {
        assertTrue(value, failMessage);
    }
    public static void assertTrueValue(boolean value) {
        assertTrue(value, null);
    }

    public static void assertFalseValue(boolean value, String failMessage) {
        assertFalse(value, failMessage);
    }

    public static void softAssertStringEquality(SoftAssert softAssert, String expected, String original)
    {
        //This method does a softAssertion between equality of 2 Strings (Original & expected).
        // On success/failure, logs a custom message.

        final String failMessageCustom = "SoftAssertion failed while performing string equality.\nExpected: "+expected+"\nActual: "+original+"\n";
        final String passMessageCustom = "SoftAssertion passed while performing string equality.\nExpected: "+expected+"\nActual: "+original+"\n";

        softAssert.assertTrue(expected.contentEquals(original), failMessageCustom);
        LogUtil.info(passMessageCustom);
    }

    public static void softAssertFalseValue(SoftAssert softAssert, boolean value, String reqPassMsgToLog)
    {
        //This method does a softAssertion & passes if the assertion is a False.
        final String failMessageCustom = "SoftAsserting of the element's display has failed. Element is still present on the page";
        //final String passMessageCustom = "SoftAsserting of the element's display has passed. Element is not present on the page";

        softAssert.assertFalse(value, failMessageCustom);
        LogUtil.info(reqPassMsgToLog);
        //LogUtil.info(passMessageCustom);
    }

    public static void softAssertTrueValue(SoftAssert softAssert, boolean value, String reqPassMsgToLog, String reqFailMsgToLog)
    {
        //This method does a softAssertion & passes if the assertion is a Boolean True.

        //final String failMessageCustom = "SoftAsserting of the element's display has failed. Element is still present on the page";
        //final String passMessageCustom = "SoftAsserting of the element's display has passed. Element is not present on the page";

        softAssert.assertTrue(value, reqFailMsgToLog);
        LogUtil.debug(reqPassMsgToLog);
    }

    public static void assertTrueHashMapEquality(Map expMap, Map actuMap, String failMsg)
    {
        if(HelperUtil.isEmpty(failMsg))
        {
            failMsg = "Expected and actual HashMap key/values did not match";
        }

        Assert.assertEquals(expMap, actuMap, failMsg);
    }

    public static void softAssertDifferentConditionsOnThePresentPage(boolean[] bArray)
    {
        SoftAssert softAssertLocal = new SoftAssert();

        for (int i = 0; i < bArray.length; i++)
        {
            boolean boo = bArray[i];
            softAssertTrueValue(softAssertLocal, boo, "", "");
            if (!boo)
            {
                LogUtil.debug("[DEBUG]: False value found while soft asserting, at the index: " + i);
            }
        }

        softAssertLocal.assertAll();
    }



    private static void addVerificationFailure(final Throwable e) {
        LogUtil.error("addVerificationFailure Method");
        result = Reporter.getCurrentTestResult();
        System.out.println(result);

        //Logger log = Logger.getLogger(AssertCustom.class);
        LogUtil.error("!!!FAILURE ALERT!!! - Soft Assertion Failure: " + e.getMessage());

    }

    private static void addVerificationSuccess() {
        LogUtil.info("addVerificationSuccess Method");
        result= Reporter.getCurrentTestResult();
        System.out.println(result);

        //Logger log = Logger.getLogger(AssertCustom.class);
        LogUtil.info("!!!Success !!! - Soft Assertion Pass: ");
    }

    // CustomAssertion Methods
    //assertEquals

    public static void assertEquals(final boolean actual, final boolean expected) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected);
        } else {

            Assert.assertEquals(actual, expected);
        }
    }

    public static void assertEquals(final boolean actual, final boolean expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final byte actual, final byte expected) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected);
        } else {
            Assert.assertEquals(actual, expected);
        }
    }
    public static void assertFail(String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertFail(message);
        } else {
            Assert.fail(message);
        }
    }


    public static void assertEquals(final byte actual, final byte expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final byte[] actual, final byte[] expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final char actual, final char expected) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected);
        } else {
            Assert.assertEquals(actual, expected);
        }
    }

    public static void assertEquals(final char actual, final char expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final Collection actual, final Collection expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final double actual, final double expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final float actual, final float expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final int actual, final int expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }
    public static void assertEquals(final int actual, final int expected) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected);
        } else {
            Assert.assertEquals(actual, expected);
        }
    }
    public static void assertEquals(final long actual, final long expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT))  {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final Object actual, final Object expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final Object[] actual, final Object[] expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final short actual, final short expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertEquals(final String actual, final String expected) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected);
        } else {
            Assert.assertEquals(actual, expected);
        }
    }

    public static void assertEquals(final String actual, final String expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertEquals(actual, expected, message);
        } else {
            Assert.assertEquals(actual, expected, message);
        }
    }

    public static void assertFalse(final boolean condition, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertFalse(condition, message);
        } else {
            Assert.assertFalse(condition, message);
        }
    }

    public static void assertNotNull(final Object object, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertNotNull(object, message);
        } else {
            Assert.assertNotNull(object, message);
        }
    }

    public static void assertNotSame(final Object actual, final Object expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertNotSame(actual, expected, message);
        } else {
            Assert.assertNotSame(actual, expected, message);
        }
    }

    public static void assertNull(final Object object, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertNull(object, message);
        } else {
            Assert.assertNull(object, message);
        }
    }

    public static void assertSame(final Object actual, final Object expected, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertSame(actual, expected, message);
        } else {
            Assert.assertSame(actual, expected, message);
        }
    }

    public static void assertTrue(final boolean condition, final String message) {
        if (Boolean.valueOf(SOFTASSERT)) {
            softassertTrue(condition, message);
        } else {
            //LogUtil.info("Hard Assert ->");
            Assert.assertTrue(condition, message);
        }
    }

    public static void fail(final String message) {
        Assert.fail(message);
    }

    public static List<Throwable> getVerificationFailures() {
        return (List<Throwable>) Reporter.getCurrentTestResult();
    }

    // Soft CustomAssertion Methods

    public static void softassertEquals(final boolean actual, final boolean expected) {

        try {
            Assert.assertEquals(actual, expected);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }



    public static void softassertEquals(final boolean actual, final boolean expected, final String message) {

        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final byte actual, final byte expected, final String message) {

        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final byte[] actual, final byte[] expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }

    }

    public static void softassertEquals(final char actual, final char expected) {
        try {
            Assert.assertEquals(actual, expected);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final char actual, final char expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final Collection actual, final Collection expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final double actual, final double expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final float actual, final float expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final int actual, final int expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final int actual, final int expected) {
        try {
            Assert.assertEquals(actual, expected);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }
    public static void softassertEquals(final long actual, final long expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {

            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final Object actual, final Object expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final Object[] actual, final Object[] expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final short actual, final short expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final String actual, final String expected) {
        try {
            Assert.assertEquals(actual, expected);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertEquals(final String actual, final String expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertFalse(final boolean condition, final String message) {
        try {
            Assert.assertFalse(condition, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertNotNull(final Object object, final String message) {
        try {
            Assert.assertNotNull(object, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertNotSame(final Object actual, final Object expected, final String message) {
        try {
            Assert.assertNotSame(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertNull(final Object object, final String message) {
        try {
            Assert.assertNull(object, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertSame(final Object actual, final Object expected, final String message) {
        try {
            Assert.assertSame(actual, expected, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertTrue(final boolean condition, final String message) {
        try {
            Assert.assertTrue(condition, message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }

    public static void softassertFail(String message) {
        try {
            Assert.fail(message);
            addVerificationSuccess();
        } catch (Throwable e) {
            addVerificationFailure(e);
        }
    }





}
