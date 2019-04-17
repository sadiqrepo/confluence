package pageobjects;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.HelperUtil;
import util.LogUtil;
import util.WaitCustom;

/**
 * Created by sadiq on 16/04/19.
 */
public class SetRestrictionsPage {

    private WebDriver driver;
    private WaitCustom waitCustom;
    private HelperUtil helperUtil;


    //WebElements of Set Restrictions Page

    @FindBy(id="action-menu-link")
    public WebElement menuLink;

    @FindBy(xpath="//span[contains(text(),'Restrictions')]")
    public WebElement pagePermissionsLink;


    @FindBy(xpath="//div[@data-test-id='restrictions-dialog-modal']")
    public WebElement pageRestrictionsDialog;


    @FindBy(xpath="//div[@data-test-id='restrictions-dialog.content-mode-select']")
    public WebElement pageRestrictionsDialogDropDown;


    @FindBy(xpath="//div[@data-test-id='restrictions-dialog.content-mode-select']//div//span[contains(text(),'Editing restricted']")
    public WebElement restrictEdit;



    @FindBy(id = "//button//span[contains(text(),'Apply')]")
    public WebElement applySelectedRestriction;

    @FindBy(id = "//button//span[contains(text(),'Cancel')]")
    public WebElement closeRestrictionsDialog;


    // Delete page elements


    @FindBy(id="action-remove-content-link")
    public WebElement removeContentLink;


    @FindBy(id="confirm")
    public WebElement confirmPageDelete;

    @FindBy(id="cancel")
    public WebElement cancelPageDelete;




    /**
     * Methods for Restrictions Page
     */


    public SetRestrictionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitCustom = new WaitCustom(driver);
        helperUtil = new HelperUtil(driver);
    }


    public void clickOnMenuLink(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(menuLink, 30);
            menuLink.click();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void verifyMenuLinkIsDisplayed(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(menuLink,30);
            menuLink.isDisplayed();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void clickOnRestrictionsLink(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(pagePermissionsLink,30);
            pagePermissionsLink.click();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void verifyPermissionsDialogDisplayed(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(pageRestrictionsDialog,30);
            pageRestrictionsDialog.isDisplayed();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void disablePageRestrictions(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(pageRestrictionsDialogDropDown,30);
            pageRestrictionsDialogDropDown.isDisplayed();
            pageRestrictionsDialogDropDown.click();
            helperUtil.clickElementWithText("No restrictions","//div[@data-test-id='restrictions-dialog.content-mode-select']//div//span");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void restrictPageEdit(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(pageRestrictionsDialogDropDown, 30);
            pageRestrictionsDialogDropDown.isDisplayed();
            pageRestrictionsDialogDropDown.click();
            helperUtil.clickElementWithText("Editing restricted","//div[@data-test-id='restrictions-dialog.content-mode-select']//div//span");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }

    public void restrictPageViewAndEdit(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(pageRestrictionsDialogDropDown, 30);
            pageRestrictionsDialogDropDown.isDisplayed();
            pageRestrictionsDialogDropDown.click();
            helperUtil.clickElementWithText("Viewing and editing restricted","//div[@data-test-id='restrictions-dialog.content-mode-select']//div//span");
        } catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void applySelectedRestrictionOnPage(){
        try{
            helperUtil.clickElementWithText("Apply","//button//span");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    public void cancelAndCloseRestrictionDialog(){
        try{
            waitCustom.isElementVisibleAfterExplicitWait(closeRestrictionsDialog,30);
            closeRestrictionsDialog.isDisplayed();
            closeRestrictionsDialog.click();
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }



}
