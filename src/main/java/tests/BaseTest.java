package tests;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pageobjects.LoginPage;
import util.*;

/**
 * Created by sadiq on 12/04/19.
 */
public class BaseTest
{
    public static WebDriver driver;
    public String browserSelected;
    public HelperUtil helperUtil;
    public WaitCustom waitCustom;
    public LoginPage loginPage;

    public static final String DEFAULT_USERNAME = ConfigReader.get("adminUserName");
    public static final String DEFAULT_PASSWORD = ConfigReader.get("adminPassword");

    private static Logger _log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    void Config()
    {
        ConfigWriter.setPropertyValue("logger.file", "webLogger", ConfigReader.get("config.path"));
    }


    /**
     * Methods to find OS type and Browser time to launch the webdriver
     * @param browserName
     */

    @Parameters({"browser"})
    @BeforeClass
    public void LaunchBrowser(String browserName){
      try
        {
        if (browserName.equalsIgnoreCase("Chrome"))
        {
            LogUtil.info("Selected browser is: "+browserName);
            String chromePath = ConfigReader.get("chromedriver.path");
            System.setProperty("webdriver.chrome.driver", chromePath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("disable-infobars");

            driver = new ChromeDriver(options);
            browserSelected = "chrome";
        }
        else if (browserName.equalsIgnoreCase("ie"))
        {
            LogUtil.info("Selected browser is: "+browserName);
            driver = new InternetExplorerDriver();
            browserSelected = "ie";
        }

        else if (browserName.equalsIgnoreCase("firefox"))
        {
            LogUtil.info("Selected browser is: "+browserName);

            String ffpath = getFirePath();
            System.setProperty("webdriver.gecko.driver", ffpath);

            driver = newFFWebDriver();

            FirefoxProfile ffp = new FirefoxProfile();
            ffp.setPreference("accessibility.blockautorefresh", true);
            browserSelected = "firefox";
        }
        else if (browserName.equalsIgnoreCase("chromeheadless")) {
            LogUtil.info("Selected browser is: " + browserName);
            String chromePath = ConfigReader.get("chromedriver.path");
            System.setProperty("webdriver.chrome.driver", chromePath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("window-size=1200x600"); //Never remove this line for chrome headless. Otherwise CI will fail
            options.addArguments("disable-notifications");
            options.addArguments("disable-gpu");

            driver = new ChromeDriver(options);
            browserSelected = "chromeHeadlessBrowser";
        }

            helperUtil = new HelperUtil(driver);
            waitCustom = new WaitCustom(driver);
            driver.manage().window().setPosition(new Point(0, 0));
            Dimension d = new Dimension(1300, 900);
            driver.manage().window().setSize(d);

        } catch (WebDriverException e) {
    System.out.println(e.getMessage());
}
    }


    public void init(){
        loginPage = new LoginPage(driver);
        waitCustom = new WaitCustom(driver);
        loginPage.login();
        helperUtil = new HelperUtil(driver);
    }

    @AfterClass
    public void tearDownBrowser()
    {
        driver.quit();
    }

    private boolean getFirefoxHeadlessStatus(String currPlatform)
    {
        if(currPlatform.contains("mac"))
        {
            return Boolean.parseBoolean(ConfigReader.get("set.firefox.headless.on.mac"));
        }
        else if(currPlatform.contains("linux"))
        {
            return Boolean.parseBoolean(ConfigReader.get("set.firefox.headless.on.linux"));
        }
        else
        {
            return true;
        }
    }

    private void setHeadlessStatus(String currentOS, FirefoxOptions FF_Options)
    {
        if(getFirefoxHeadlessStatus(currentOS))
        {
            FF_Options.addArguments("--headless");
        }
    }

    private DesiredCapabilities setFirefoxOptions()
    {
        FirefoxOptions options = new FirefoxOptions();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("moz:firefoxOptions", options);
        capabilities.setCapability("accessibility.blockautorefresh", true);
        setHeadlessStatus("mac", options);   //Enable this to make the firefox headless

        return capabilities;
    }

    private String getFirePath()
    {
        String firePath = null;
        if(SystemUtils.IS_OS_LINUX)
        {
            LogUtil.debug("\n[DEBUG] Detected os is LINUX.\n Geckodriver for Linux 64bit will be used");
            firePath = ConfigReader.get("geckodriver.path.linux");
        }
        else if (SystemUtils.IS_OS_MAC)
        {
            LogUtil.debug("\n[DEBUG] Detected os is Mac. \n Geckodriver for Mac OS will be used");
            firePath = ConfigReader.get("geckodriver.path.mac");
        }
        else
        {
            LogUtil.debug("\n\n[DEBUG] Unable to detect the current OS....!!!\nGeckodriver cannot be used\n\n");
        }

        return firePath;
    }

    private WebDriver newFFWebDriver() {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        if (SystemUtils.IS_OS_LINUX) {
            FirefoxOptions options = new FirefoxOptions();
            setHeadlessStatus("linux", options);

            return new FirefoxDriver(options);
        } else {
            FirefoxOptions opts = new FirefoxOptions(setFirefoxOptions());
            return new FirefoxDriver(opts);
        }
    }



}
