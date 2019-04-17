package util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by sadiq on 13/04/19.
 */
public class AlertUtil {


    private WebDriver driver;
    private WaitCustom newWaitCustom;
    private long explicitTimeOut;


    public AlertUtil(WebDriver driver)
    {
        this.driver = driver;
        newWaitCustom = new WaitCustom(driver);
        explicitTimeOut = Long.parseLong(ConfigReader.get("explicit.wait"));
    }

    public boolean isAlertPresent()
    {

        try {
            LogUtil.info("Waiting for the alert to be visible");
            newWaitCustom.isAlertPresent(explicitTimeOut);

            LogUtil.info("Switching to the alert");
            Alert alert = driver.switchTo().alert();

            //Capturing alert message.
            String alertMessage = alert.getText();
            LogUtil.info("Message on the alert is: "+alertMessage);
            return true;

        } catch (NoAlertPresentException ex)
        {
            LogUtil.info("[DEBUG] NoAlertPresentException exception seen after waiting for Alert's presence.");
            LogUtil.info(ex.getMessage());
            ex.printStackTrace();
            return false;

        }
    }

    public boolean isAlertPresent(boolean disableLogs)
    {
        boolean result;
        if(disableLogs)
        {
            try
            {
                final String mainWindowHandle = driver.getWindowHandle();

                newWaitCustom.isAlertPresent(5);
                driver.switchTo().alert();
                driver.switchTo().window(mainWindowHandle);  //Switch back to main content

                result = true;
            }
            catch (Exception e)
            {
                result = false;
            }
        }
        else
        {
            result = isAlertPresent();
        }

        return result;
    }

    public void acceptAlertOnHeadlessBrowser(String browserName)
    {
        // Switching to Alert & ACCEPTING - only for Headless browser
        LogUtil.debug("Current browser is a headless browser ("+browserName+"). Will try to accept the alert, now");

        if (browserName.equalsIgnoreCase("PhantomJS"))
        {
            LogUtil.info("No support for PhantomJS after Selenium 3.7.1");
/*            PhantomJSDriver phantom = (PhantomJSDriver) driver;
            phantom.executeScript("window.alert = function(){}");
            phantom.executeScript("window.confirm = function(){return true;}");*/
        }
        else if (browserName.equalsIgnoreCase("HeadlessChrome"))
        {
            ChromeDriver chroma = (ChromeDriver) driver;
            chroma.executeScript("window.alert = function(){}");
            chroma.executeScript("window.confirm = function(){return true;}");
        }
        else if (browserName.equalsIgnoreCase("firefox"))
        {
            FirefoxDriver ffhless = (FirefoxDriver) driver;
            //chroma.executeScript("window.alert = function(){}");
            ffhless.executeScript("window.confirm = function(){return true;}");
        }
        LogUtil.debug("Accepted the headless browser ("+browserName+") alert.");
    }

    public void acceptTheAlert()
    {
        newWaitCustom.isAlertPresent(WaitCustom.getExplicitTimeout());

        Alert alert = driver.switchTo().alert();
        LogUtil.info("Message on Alert:\n\t"+alert.getText());
        LogUtil.debug("Accepting the alert by pressing OK");
        alert.accept();

        newWaitCustom = null;
    }


    public void alertAccept(Alert alert){


        if(isAlertPresent()){

            //Accepting alert
            alert.accept();
        }
    }

    public void alertDismiss(Alert alert){

        if(isAlertPresent()){
            alert.dismiss();

        }
    }

    public void confirmAlert()
    {
        Alert javascriptAlert = driver.switchTo().alert();
        javascriptAlert.accept();
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void alertPrompt(String promptString, Alert alert){

        if(isAlertPresent()){


            //Will pass the text to the prompt popup
            driver.switchTo().alert().sendKeys(promptString);


            // Capturing alert message.
            String alertMessage=driver.switchTo().alert().getText();
            System.out.println(alertMessage);

            // Accepting alert
            alert.accept();
        }
    }
}
