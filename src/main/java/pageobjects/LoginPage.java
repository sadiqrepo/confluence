package pageobjects;

import com.sun.deploy.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.ConfigReader;
import util.HelperUtil;
import util.LogUtil;
import util.WaitCustom;

/**
 * Created by sadiq on 13/04/19.
 */
public class LoginPage {

    @FindBy(id = "username")
    public WebElement emailIdInputBox;
    @FindBy(id = "password")
    public WebElement passwordInputBox;
    @FindBy(id = "login-submit")
    public WebElement submitButton;
    String baseUrl = ConfigReader.get("baseURL");


    //Web elements of Login Page
    private WebDriver driver;
    private HelperUtil helperUtil;
    private WaitCustom waitCustom;


    /**
     * Methods for Login Page
     */


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitCustom = new WaitCustom(driver);
        helperUtil = new HelperUtil(driver);
    }


    public void typeEmailId() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(emailIdInputBox, 30);
            waitCustom.isElementClickable(emailIdInputBox, 2000);
            emailIdInputBox.sendKeys(ConfigReader.get("adminUserName"));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void clickContinueButton() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(submitButton, 30);
            waitCustom.isElementClickable(submitButton, 2000);
            submitButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void typePassword() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(passwordInputBox, 30);
            waitCustom.isElementClickable(passwordInputBox, 2000);
            passwordInputBox.sendKeys(ConfigReader.get("adminPassword"));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void clickLogInButton() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(submitButton, 30);
            waitCustom.isElementClickable(submitButton, 2000);
            submitButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void login() {
        try {
            LogUtil.info("Entering values for regular sign up page");
            helperUtil.navigatePage(baseUrl);
            typeEmailId();
            clickContinueButton();
            typePassword();
            clickLogInButton();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


}
