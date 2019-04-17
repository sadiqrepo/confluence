package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.LogUtil;
import util.WaitCustom;

/**
 * Created by sadiq on 13/04/19.
 */
public class CreatePage {

    @FindBy(id = "create-page-button")
    public WebElement createPageButton;
    @FindBy(id = "create-dialog")
    public WebElement createPageWindow;


    //Webelements of CreatePage

    @FindBy(xpath = "//div[@id='create-dialog']/ol/li[1]")
    public WebElement blankPage;
    @FindBy(xpath = "//button[@data-test-id='create-dialog-create-button']")
    public WebElement createButton;
    @FindBy(xpath = "//div[@class='dialog-button-panel']//a[contains(text(),'Close')]")
    public WebElement closeCreatePageWindowButton;
    private WebDriver driver;
    private WaitCustom waitCustom;


    /**
     * Methods for Create Page
     */


    public CreatePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitCustom = new WaitCustom(driver);
    }


    public void clickOnCreatePageButton() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(createPageButton, 30);
            createPageButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void checkCreatePageWindowHasOpened() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(createPageWindow, 30);
            createPageWindow.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void selectBlankPage() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(blankPage, 30);
            blankPage.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void clickOnCreateButton() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(createButton, 30);
            createButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void closeCreatePageWindow() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(closeCreatePageWindowButton, 30);
            closeCreatePageWindowButton.click();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


}
