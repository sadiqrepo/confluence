package tests;

import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import util.ConfigReader;
import util.HelperUtil;
import util.LogUtil;

/**
 * Created by sadiq on 14/04/19.
 */
public class LoginTest extends BaseTest {

    private static Logger _log = LoggerFactory.getLogger(LoginTest.class);


    String baseUrl = ConfigReader.get("baseURL");

    String sTestCaseName=this.getClass().getSimpleName().trim();

    LoginPage loginPage;
    HelperUtil helperUtil;



    @BeforeClass
    public void init(){

        LogUtil.info("Before Login");
        LogUtil.startTestCase(sTestCaseName);
        loginPage=PageFactory.initElements(driver, LoginPage.class);
        helperUtil = new HelperUtil(driver);
    }

    @Test(description = "Regular sign-in",priority = 0)
    public void login(){
        LogUtil.info("Entering values for regular sign up page");
        helperUtil.navigatePage(baseUrl);
        loginPage.typeEmailId();
        loginPage.clickContinueButton();
        loginPage.typePassword();
        loginPage.clickLogInButton();
    }







    @AfterTest(description = "After test info")
    public void afterTest(){
        LogUtil.endTestCase(sTestCaseName);
    }

}
