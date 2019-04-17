package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.HelperUtil;
import util.LogUtil;
import util.WaitCustom;

/**
 * Created by sadiq on 16/04/19.
 */
public class FillBlankPage {

    @FindBy(id = "content-title")
    public WebElement contentTitle;
    //@FindBy(xpath="//body[@id='com-atlassian-confluence']/div")
    //@FindBy(xpath="//body[@id='tinymce']/p")
    @FindBy(id = "tinymce")
    public WebElement pageContent;
    @FindBy(id = "rte-button-publish")
    public WebElement publishButton;


    //WebElements of FillBlank Page
    @FindBy(id = "rte-button-cancel")
    public WebElement closeButton;
    private WebDriver driver;
    private WaitCustom waitCustom;
    private HelperUtil helperUtil;


    /**
     * Methods for Create Page
     */


    public FillBlankPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitCustom = new WaitCustom(driver);
    }


    public void typeContentTitle(String value) {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(contentTitle, 30);
            contentTitle.clear();
            contentTitle.sendKeys(value);
            //driver.switchTo().defaultContent();
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void typePageContent(String value) {
        try {


            driver.switchTo().frame("wysiwygTextarea_ifr");
            pageContent.click();
            pageContent.clear();
            pageContent.sendKeys(new String[]{value});
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void clickPublishButton() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(publishButton, 30);
            publishButton.click();
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void closeBlankPage() {
        try {
            waitCustom.isElementVisibleAfterExplicitWait(closeButton, 30);
            closeButton.click();
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }


}
