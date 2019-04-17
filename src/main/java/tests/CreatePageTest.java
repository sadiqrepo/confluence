package tests;

import builder.BlankPageBuilder;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.CreatePage;
import pageobjects.FillBlankPage;
import pageobjects.LoginPage;
import pageobjects.SetRestrictionsPage;
import util.*;

import java.io.File;
import java.util.List;

/**
 * Created by sadiq on 15/04/19.
 */
public class CreatePageTest extends BaseTest {

    private static Logger _log = LoggerFactory.getLogger(LoginTest.class);



    String technicalSpaceUrl = ConfigReader.get("technicalSpaceURL");
    String sTestCaseName=this.getClass().getSimpleName().trim();


    HelperUtil helperUtil;
    CreatePage createPage;
    public FillBlankPage fillBlankPage;
    public SetRestrictionsPage setRestrictionsPage;
    LoginPage loginPage;
    WaitCustom waitCustom;

    private CSVReader readCsv = new CSVReader();




    @BeforeClass
    public void init(){

        LogUtil.info("Before Login");
        LogUtil.startTestCase(sTestCaseName);
        createPage=PageFactory.initElements(driver, CreatePage.class);
        fillBlankPage = PageFactory.initElements(driver,FillBlankPage.class);
        setRestrictionsPage = PageFactory.initElements(driver,SetRestrictionsPage.class);

        helperUtil = new HelperUtil(driver);
        super.init();


    }


    @DataProvider(name = "createBlankPageData")
    public Object[][] createBlankPages() {
        Object[][] rawData;
        List<String[]> createBlankPages = readCsv.parseFile(new File(ConfigReader.get("BLANKPAGECSVPATH")).getAbsolutePath());
        rawData = new Object[createBlankPages.size()][1];
        for (int i = 0; i < createBlankPages.size(); i++) {
            for (String[] createBlankPage : createBlankPages) {
                rawData[i++][0] = new BlankPageBuilder(createBlankPage);
            }
        }
        return rawData;
    }

    /**
     * Creates a Blank page and restricts edit access for the page created.
     * Validation for the page access is not included but can be extended as member username and password has been
     * provided in the config.properties filegit
     * @param blankPageBuilder
     */

    @Test(description = "create blank page & set edit restriction",dataProvider = "createBlankPageData", priority = 0)
     public void createBlankPage(BlankPageBuilder blankPageBuilder){

        //Create a Blank page
        WaitCustom.sleep(4000);
        helperUtil.navigatePage(technicalSpaceUrl);
        WaitCustom.sleep(6000);
        setRestrictionsPage.verifyMenuLinkIsDisplayed();
        createPage.clickOnCreatePageButton();
        WaitCustom.sleep(4000);
        createPage.checkCreatePageWindowHasOpened();
        WaitCustom.sleep(2000);
        createPage.clickOnCreateButton();
        WaitCustom.sleep(6000);
        fillBlankPage.typeContentTitle(blankPageBuilder.title);
        WaitCustom.sleep(3000);
        fillBlankPage.typePageContent(blankPageBuilder.content);
        fillBlankPage.clickPublishButton();
        WaitCustom.sleep(2000);

        // Find the page created using title name and change the edit restriction
        helperUtil.clickElementWithText(blankPageBuilder.title,"//div[@id='confluence-left-nav']//div");
        setRestrictionsPage.clickOnMenuLink();
        setRestrictionsPage.clickOnRestrictionsLink();
        setRestrictionsPage.verifyPermissionsDialogDisplayed();
        setRestrictionsPage.restrictPageEdit();
        setRestrictionsPage.applySelectedRestrictionOnPage();

    }

    /**
     * Setting restrictions was created as separate test but later merged with the first test. As the user would
     * create the page and modify the permissions if he is working on something which needs to be access
     * protected
     */


 /*   @Test(description = "set Edit restriction on page",dataProvider = "createBlankPageData", priority = 1)
    public void setEditRestrictionsOnPage(BlankPageBuilder blankPageBuilder){
        WaitCustom.sleep(4000);
        helperUtil.navigatePage(technicalSpaceUrl);
        WaitCustom.sleep(6000);
        helperUtil.clickElementWithText(blankPageBuilder.title,"//div[@id='confluence-left-nav']//div");
        WaitCustom.sleep(6000);
        setRestrictionsPage.clickOnMenuLink();
        WaitCustom.sleep(3000);
        setRestrictionsPage.clickOnRestrictionsLink();
        WaitCustom.sleep(3000);
        setRestrictionsPage.verifyPermissionsDialogDisplayed();
        WaitCustom.sleep(3000);
        setRestrictionsPage.restrictPageEdit();
        WaitCustom.sleep(3000);
        setRestrictionsPage.applySelectedRestrictionOnPage();
        WaitCustom.sleep(3000);

    }*/




}
