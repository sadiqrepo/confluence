package util;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by sadiq on 13/04/19.
 */


public class WaitCustom {

    private WebDriver driver;

    public WaitCustom(WebDriver driver)
    {
        this.driver = driver;
    }

    public static int getExplicitTimeout()
    {
        //Gets the predefined explicit wait from the config.reader & then returns the same in integer format
        return Integer.parseInt(ConfigReader.get("explicit.wait"));
        //return Long.parseLong(ConfigReader.get("explicit.wait")); //for Long format
    }

    //Is Element visible after performing an Explicit Wait
    public Boolean isElementVisibleAfterExplicitWait(WebElement element, final long timeout) {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch (ElementNotVisibleException e)
        {
            LogUtil.error("\n"+element+ "not found on the page. Here are the exception details: ");
            LogUtil.error(String.valueOf(e));
        }
        return false;
    }

    //FluentWait
    public WebElement fluentWait(final WebElement element, final long timeout, final long polltime)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, SECONDS)
                .pollingEvery(polltime, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(WebDriverException.class)
                .ignoring(ElementNotInteractableException.class);

        WebElement elmnt = wait.until(new Function<WebDriver, WebElement>()
        {
            public WebElement apply(WebDriver driver)
            {
                return element;
            }
        });

        return elmnt;
    }


    //fluentWait
    public WebElement fluentWait(final By locator,final long timeout, final long polltime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(polltime, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return element;
    }


    public static void sleep() {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException ignored)
        {
            LogUtil.info("[INFO] Exception seen while using Thread.sleep()");
            LogUtil.info(String.valueOf(ignored));
        }
    }

    //Is alert present
    public Alert isAlertPresent(final long timeout){
        Alert element = null;

        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.alertIsPresent());
        }
        catch(ElementNotVisibleException e)
        {
            LogUtil.error("\n[Error] Alert is not present. Here's the exception's stack trace");
            LogUtil.error(String.valueOf(e));
        }
        return element;
    }


    //StubbornWait (FluentWait With extra staleElement exception handling capability)
    public WebElement stubbornWaitWithXpath(WebDriver driver, final String xpath)
    {
        /*
        Waiting 30 seconds for an element to be present on the page, checking
        for its presence once every 3 seconds.
        */
        Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(3, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement foo = stubbornWait.until(new Function<WebDriver, WebElement>()
        {
            public WebElement apply(WebDriver driver)
            {
                WebElement elem2 = driver.findElement(By.xpath(xpath));
                return elem2;
            }
        });

        return foo;
    }


    //StubbornWait (FluentWait With extra staleElement exception handling capability)
    public WebElement stubbornWaitWithLinkText(WebDriver driver, final String linkText)
    {
        /*
        Waiting 30 seconds for an element to be present on the page, checking
        for its presence once every 5 seconds.
        */
        Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(3, SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement foo = stubbornWait.until(new Function<WebDriver, WebElement>()
        {
            public WebElement apply(WebDriver driver)
            {
                WebElement elem2 = driver.findElement(By.linkText(linkText));
                return elem2;
            }
        });

        return foo;
    }

    public void waitForInvisibilityOfElement(WebElement element, final long timeout)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.invisibilityOf(element));
        }
        catch(ElementNotVisibleException e)
        {
            LogUtil.error(String.valueOf(e));
        }

    }

    public static void waitForInvisibilityOfElement(WebDriver driver, WebElement element, final long timeout)
    {
        //Overloaded method (static). Need to pass driver instance
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.invisibilityOf(element));

        }catch(ElementNotVisibleException e)
        {
            LogUtil.error(String.valueOf(e));
        }

    }

    public WebElement isElementClickable(WebElement webElement, final long timeout)
    {
        WebElement element = null;

        try
        {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        }
        catch(ElementNotVisibleException e)
        {
            LogUtil.error(String.valueOf(e));
        }

        return element;
    }

    public static void setSleepTimeOut(final long sleepWait) {
        try {
            Thread.sleep(sleepWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void sleep(Integer timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException ignored) {
        }
    }


    public void setImplicitTimeOut(final long implicitWait){
        try{
            //System.out.println(implicitWait);
            driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        } catch(NullPointerException e){
            //e.printStackTrace();

        }
    }



    //Explicit Wait
    public WebElement isElementVisible(By locator, final long timeout){
        WebElement element = null;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

    public WebElement isElementPresent(By locator, final long timeout){
        WebElement element = null;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(ElementNotVisibleException e){
            LogUtil.info("WebElement '"+locator+ "' is not clickable");
            e.printStackTrace();
        }

        return element;
    }




    public boolean isElementSelectable(WebElement locator, final long timeout){
        boolean element = false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeSelected(locator));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

    public boolean isElementInVisible(By locator, final long timeout)
    {
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

    public boolean isElementEnabled(By locator, final long timeout){
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isEnabled();
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }




        public boolean isElementDisplayed (By locator,final long timeout){

            boolean element = false;

            try {
                WebDriverWait wait = new WebDriverWait(driver, timeout);
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            } catch (ElementNotVisibleException e) {
                e.printStackTrace();
            }

            return element;
        }




    public boolean waitForInvisibilityOfElement(By locator, final long timeout, final String strText){
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.invisibilityOfElementWithText(locator,strText));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

    public boolean isTextPresentLocated(By locator, final long timeout, final String strText){
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,strText));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

    public boolean isTextPresentValue(By locator, final long timeout, final String strText){
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.textToBePresentInElementValue(locator,strText));
        }catch(ElementNotVisibleException e){
            e.printStackTrace();
        }

        return element;
    }

}
