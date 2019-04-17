package util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by sadiq on 13/04/19.
 */
public class HelperUtil {


    private WebDriver driver;
    private final int explicitTimeOut;
    private WaitCustom newWaitCustom;

    public HelperUtil(WebDriver driver)
    {
        this.driver = driver;
        newWaitCustom = new WaitCustom(driver);
        explicitTimeOut = Integer.parseInt(ConfigReader.get("explicit.wait"));
    }

    public void maximizeBrowser()
    {
        try
        {
            driver.manage().window().maximize();
        }
        catch (WebDriverException wd)
        {
            LogUtil.debug("WebDriverException seen while trying to maximize the browser");
            driver.manage().window().setSize(new Dimension(1024,768));
        }
    }

    public void sendkeys(WebElement webElement, String keysToSend)
    {
        webElement.clear();

        String inputFieldName;
        //Below try/catch is for logging purpose
        try
        {
            inputFieldName = " : "+webElement.getAttribute("placeholder");
        }
        catch(Exception e)
        {
            try
            {
                inputFieldName = " "+webElement.getText();
            }
            catch(Exception e1)
            {
                inputFieldName = "";
            }
        }
        LogUtil.debug("[DEBUG] Sending the characters '"+keysToSend+"' to the Input field "+inputFieldName);
        webElement.sendKeys(keysToSend);
    }


    public WebElement find(By by) {
        int retries = 3;
        WebElement element = null;
        do {
            try
            {
                element = driver.findElement(by);
            } catch (NoSuchElementException e)
            {
                if (--retries < 0)
                {
                    throw e;
                }
                else
                {
                    WaitCustom.sleep(500);
                }
            }
        } while (element == null);
        return element;
    }

    public void selectByWebElementUsingValue(WebElement dropDownWebElement, String value) {
        //Select by value text on the dropDownWebElement
        LogUtil.debug("[DEBUG] Selecting the drop-down based on the value: '"+value+"', on the WebElement: "+dropDownWebElement);
        Select select = new Select(dropDownWebElement);
        select.selectByValue(value);
    }

    public void selectByWebElementUsingVisibleText(WebElement dropDownWebElement, String visibleText) {
        //Select by visible text on the dropDownWebElement
        LogUtil.debug("[DEBUG] Selecting the drop-down based on the visible text: '"+visibleText+"', on the WebElement: "+dropDownWebElement);
        Select select = new Select(dropDownWebElement);
        select.selectByVisibleText(visibleText);
    }

    public void selectByWebElementUsingIndex(WebElement dropDownWebElement, int index) {
        //Select by index on the dropDownWebElement
        LogUtil.debug("[DEBUG] Selecting the drop-down based on the index: '"+index+"', on the WebElement: "+dropDownWebElement);
        Select select = new Select(dropDownWebElement);
        select.selectByIndex(index);
    }

    public void selectByName(String name, String value) {
        //Select by Name
        WebElement element = find(By.name(name));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }




    public void selectByPath(String path, String value) {
        //Select using xpath
        WebElement element = find(By.xpath(path));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }


    public void clickByWebElement(WebElement element)
    {
        try
        {
            LogUtil.debug("[DEBUG] Trying to click the element:\n"+element);
            newWaitCustom.fluentWait(element, explicitTimeOut, 1).isDisplayed();
            element.click();
        }
        catch (NoSuchElementException e)
        {
            LogUtil.error("1-[ERROR!!!] "+element + " is not clickable. Following is the exception detail: ");
            LogUtil.error(String.valueOf(e));
        }
        catch(Exception ex)
        {
            try
            {
                LogUtil.debug("[DEBUG] 2-Trying to move to the element & click on the same. Element is: "+element);
                moveToElementAndClick(element);
            }
            catch (Exception ex2)
            {
                try
                {
                    LogUtil.info("3-Inside javascript executor's try block of actual catch block. " +
                            "Clicking on the element"+element);
                    JavascriptExecutor executor = (JavascriptExecutor)driver;
                    executor.executeScript("arguments[0].click();", element);
                }
                catch (Exception ultimateException)
                {
                    LogUtil.error("4-[ERROR!!!] "+element + " is NOT found. Following are the exception details: ");
                    LogUtil.error(String.valueOf(ultimateException));
                }
            }
        }
    }

    public void clickByWebElement(WebElement element, boolean javascriptClickRequired)
    {
        try
        {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);
        }
        catch (Exception e)
        {
            LogUtil.error("[ERROR] Exception seen while clicking on the element using javascript: "+element);
            LogUtil.error(String.valueOf(e));
        }
    }


/*    public static void scrollToElement(WebDriver driver, WebElement elem)
    {
        //Static method
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
    }*/

    public void scrollToAnElement(WebElement elem)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
    }

    public void scrollToBottomOfPage() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }


    public void moveToElementAndClick(WebElement element1, String xpath)
    {
        WebDriverWait wait = new WebDriverWait(driver, explicitTimeOut);
        Actions builder = new Actions(driver);
        builder.moveToElement(element1).click();
        WaitCustom.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        WebElement element2 = driver.findElement(By.xpath(xpath));
        scrollToAnElement(element2);
        builder.moveToElement(element2).click().perform();
    }

    public void hoverClickAndClickTheDropdown(WebElement elem1, WebElement elem2)
    {
    /* This method is for Clicking on the main WebElement & then also clicking on
    another WebElement which is available in the drop-down (hover-down) menu */
        Actions act = new Actions(driver);
        act.moveToElement(elem1).click();
        act.moveToElement(elem2).click();
        LogUtil.debug("[DEBUG] Performing the composite Actions");
        act.build().perform();
    }

    public void clickElementAndClickTheOptionOnDropdownMenu(WebElement elem1, WebElement elem2)
    {
    /* This method is for Clicking on the main WebElement & then also clicking on
    another WebElement which is available as the drop-down menu option */
        //DriverUtilities driverUtilities = new DriverUtilities(driver);
        //NewWaitCustom newWaitCustom = new NewWaitCustom(driver);
        final int timeOut = explicitTimeOut;

        try
        {
            newWaitCustom.isElementVisibleAfterExplicitWait(elem1, timeOut);
        }
        catch (TimeoutException tme)
        {
            boolean result;
            for(int i = 0; i<3; i++)
            {
                try
                {
                    driver.navigate().refresh();
                    result = newWaitCustom.isElementVisibleAfterExplicitWait(elem1, timeOut);
                    if(result)
                    {
                        break;
                    }
                }
                catch (TimeoutException tme1)
                {
                    LogUtil.debug("Retry #"+(i+1));
                }
            }
        }
        clickByWebElement(elem1);

        try
        {
            WaitCustom.sleep();
            newWaitCustom.isElementVisibleAfterExplicitWait(elem2, timeOut);

        }
        catch (TimeoutException tme2)
        {
            boolean dropdownResult;
            for(int i = 0; i<3; i++)
            {
                try
                {
                    clickByWebElement(elem1);
                    dropdownResult = newWaitCustom.isElementVisibleAfterExplicitWait(elem2, timeOut);
                    if(dropdownResult)
                    {
                        break;
                    }
                }
                catch (TimeoutException tme3)
                {
                    LogUtil.debug("Retry #"+(i+1));
                }
            }
        }
        WaitCustom.sleep();
        clickByWebElement(elem2);

        newWaitCustom = null;
    }

    public void selectDropDownValue(WebElement element1, String value) {
        //This method accepts a webElement of a dropdown & then clicks on it.
        // It clicks & disposes the dropdown for multiple times to make sure that the drop down loads all the options.
        //Then finally, as per the 'value' parameter, drop down would be selected
        //if (value.isEmpty())
        if (isEmpty(value))
        {
            LogUtil.info("Drop down selection related information is not provided in the CSV");
        } else {
            try {
                scrollToAnElement(element1);
                WaitCustom.sleep(1000);
                for (int i = 0; i < 3; i++) {
                    element1.click();
                    element1.sendKeys(Keys.ESCAPE);
                    WaitCustom.sleep(1000);
                    Select dropDown = new Select(element1);
                    LogUtil.info("Number of options available on the drop-down: " + String.valueOf(dropDown.getOptions().size()));
                    if (dropDown.getOptions().size() > 1) {
                        dropDown.selectByValue(value);
                        break;
                    }
                }

            } catch (Exception e) {
                LogUtil.error("ERROR!! Exception seen while clicking on the drop down@User creation page");
                LogUtil.error(String.valueOf(e));
            }

        }
    }

    public static boolean isEmpty(String value)
    {
        if (value == null || value.length()==0) {return true;}
        else {return false;}

    }

    public WebElement getWebElementByXpath(String xpath)
    {
        //finds & returns the WebElement using the Xpath
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, explicitTimeOut);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
            WebElement elmnt = driver.findElement(By.xpath(xpath));
            return elmnt;
        }
        catch (TimeoutException te)
        {
            LogUtil.error("ERROR! Unable to find the element using this xpath: " + xpath + ". Request timed out..");
            LogUtil.error(String.valueOf(te));
            return null;
        }
        catch (NoSuchElementException nse)
        {
            LogUtil.error("ERROR! Unable to find the element using this xpath: " + xpath);
            LogUtil.error(String.valueOf(nse));
            return null;
        }
    }

    public WebElement getWebElementByXpath(String xpath, Boolean fluentWaitRequired)
    {
        //finds & returns the WebElement using the Xpath (Uses fluentWait)
        //NewWaitCustom newWaitCustom = new NewWaitCustom(driver);

        WebElement elema = newWaitCustom.stubbornWaitWithXpath(driver, xpath);
        return elema;
    }


    public WebElement getWebElementByLinkText(String linkText, Boolean fluentWaitRequired)
    {
        //finds & returns the WebElement using the LinkText (Uses fluentWait)
        //NewWaitCustom newWaitCustom = new NewWaitCustom(driver);

        WebElement elema = newWaitCustom.stubbornWaitWithXpath(driver, linkText);
        return elema;
    }




    public Boolean waitForDropDownToLoad(WebElement webElement1, String xpath)
    {
        Boolean optionsLoaded = false;
        //NewWaitCustom newWaitCustom = new NewWaitCustom(driver);
        newWaitCustom.isElementVisibleAfterExplicitWait(webElement1, 15);

        webElement1.click(); //Open the drop down
        webElement1.click(); //Dispose the opened drop down

        for (int i = 0; i < 4; i++)
        {
            try
            {
                Select select = new Select(webElement1);
                if (select.getOptions().size() > 1)
                {
                    webElement1.click(); //Open the drop down
                    if(driver.findElement(By.xpath(xpath)).isDisplayed())
                    {
                        optionsLoaded = true;
                        webElement1.click(); //Dispose the opened drop down
                        break;
                    }
                }
                else
                {
                    LogUtil.error("Trying to load the options on the dropdown of WebElement: " + webElement1 + ". Try#" + i);
                    webElement1.click();
                    WaitCustom.sleep(1200);
                    webElement1.click();
                    WaitCustom.sleep(1200);
                    optionsLoaded = false;
                }
            }
            catch (Exception e)
            {
                {
                    LogUtil.error("Option/Element on the dropdown is not found. HEre's the exception details");
                    LogUtil.error(String.valueOf(e));
                }
                optionsLoaded = false;
            }
        }

        return optionsLoaded;
    }

    public void acceptAlert()
    {
        Alert alert=driver.switchTo().alert();

        // Capturing alert message.
        String alertMessage=driver.switchTo().alert().getText();
        LogUtil.info("Alert message:\n"+alertMessage);

        // Accepting alert
        alert.accept();
    }

    public String getBrowserUserAgent()
    {
        //Returns the browser user agent.
        String userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
        LogUtil.debug("\n\nBROWSER AGENT is: "+userAgent+"\n\n");
        return userAgent;

    }


    public void moveToElementAndClick(WebElement webElement)
    {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).click().perform();
        action = null;
    }

    public int[] getCoordinates(WebElement elem)
    {
        Point coordinates = elem.getLocation();
        int xcordi = coordinates.getX();
        int ycordi = coordinates.getY();

        return new int[] {xcordi, ycordi};
    }

    public String getFirstSelectedOption(WebElement element)
    {
        //Returns the first selected option on any DropDown having select tag
        Select select = new Select(element);
        WebElement elemOption = select.getFirstSelectedOption();
        return elemOption.getText();
    }

    public boolean checkPageSourceContains(String text) {
        return getPageSource().contains(text);
    }

    public boolean hitAndCheckPageSourceContains(String url, String text) {
        navigatePage(url);
        WaitCustom.sleep();
        return checkPageSourceContains(text);
    }

    public void navigatePage(String url) {
        driver.get(url);
    }

    public void hitRefresh(){
        driver.navigate().refresh();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    private String getText(WebElement element) {
        return element.getText();
    }

    public String getTextByClassName(String className) {
        return getText(find(By.className(className)));
    }

    public String getTextById(String id) {
        return getText(find(By.id(id)));
    }

    public boolean textBoxEnabled(String userid) {
        return find(By.name(userid)).isEnabled();
    }

    public void clickLink(String linkText) {
        try {
            find(By.linkText(linkText)).click();
        }catch (WebDriverException ex){
            LogUtil.error(ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void clickPartialLink(String partialLinkText) {
        find(By.partialLinkText(partialLinkText)).click();
    }

    public void clickByName(String name) {
        find(By.name(name)).click();
    }

    public void clickById(String id) {
        find(By.id(id)).click();
    }

    public void clickByXPath(String xPath) {
        WaitCustom.sleep(500);
        find(By.xpath(xPath)).click();
        WaitCustom.sleep();
    }

/*    public void clickByWebElement(WebElement element) {
        Utils.sleep(500);
        element.click();
        Utils.sleep();
    }*/

    public void clickElementWithText(String text, String basePath){
        try{
            WebElement element = find(By.xpath(basePath
                    + "[contains(text(),'" + text + "')]"));
            newWaitCustom.isElementVisibleAfterExplicitWait(element, 40);
            element.click();
        }catch (Exception e)
        {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }


    /*public void clickElementWithText(String text){
        clickElementWithText(text, "//td");
    }*/

    public void pageScrollUp(){

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(250, 0)");

    }

    public void typeInputByJSXpath(WebElement webElement, String value){
        JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
        myExecutor.executeScript("arguments[0].value='"+value+"';", webElement);
    }





    public void clickByClassName(String className) {
        find(By.className(className)).click();
    }

    public void typeInputByName(String name, String value) {
        find(By.name(name)).sendKeys(value);
    }

    public void clearAndTypeInputByName(String name, String value) {
        find(By.name(name)).clear();
        find(By.name(name)).sendKeys(value);
    }

    public void typeInputById(String id, String value) {
        find(By.id(id)).clear();
        find(By.id(id)).sendKeys(value);
    }

    public void typeInputByXPath(String path, String value) {
        find(By.xpath(path)).sendKeys(value);
    }

    public void clearAndTypeInputByXPath(String path, String value) {
        find(By.xpath(path)).clear();
        find(By.xpath(path)).sendKeys(value);
    }

    public void clickAndSelectByName(String name, String value) {
        clickByName(name);
        WaitCustom.sleep();
        selectByName(name, value);
    }

    public void clickAndSelectByXPath(String path, String value) {
        clickByXPath(path);
        WaitCustom.sleep();
        selectByPath(path, value);
    }


    public void clickAndSelectByXPathAndOptionValue(String path, String value) {
        clickByXPath(path);
        WaitCustom.sleep();
        selectByPathAndOptionValue(path, value);
    }

    public void fillByName(String name, String text) {
        fillByName(name, text, false);
    }

    public void fillByName(String name, String text, boolean isAppend) {
        WebElement searchBox = find(By.name(name));
        if(!isAppend){
            searchBox.clear();
        }
        searchBox.sendKeys(text);
    }
    public void fillTypeAheadByXpath(String path,String value){
        typeInputByXPath(path,value);
        WaitCustom.sleep(3000);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

    }


    public void selectById(String id, String value) {
        WebElement element = find(By.id(id));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }


    public void selectByPathAndOptionValue(String path, String value) {
        WebElement element = find(By.xpath(path));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectByIdAndOptionValue(String id, String value) {
        WebElement element = find(By.id(id));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public boolean checkElementSelected(String name) {
        WebElement element = find(By.name(name));
        return element.isSelected();
    }

    public boolean checkElementisDisplayed(String name) {
        try {
            newWaitCustom.fluentWait(By.name(name), explicitTimeOut, 2);
            WebElement element = find(By.name(name));
            return element.isDisplayed();
        } catch (TimeoutException e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public boolean checkElementEnabled(String name) {
        try {
            newWaitCustom.fluentWait(By.name(name), explicitTimeOut, 2);
            WebElement element = find(By.name(name));
            return element.isEnabled();
        } catch (TimeoutException e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkElementEnabledById(String Id) {
        try {
            newWaitCustom.fluentWait(By.id(Id), explicitTimeOut, 2);
            WebElement element = find(By.id(Id));
            return element.isEnabled();
        } catch (TimeoutException e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



    public boolean isElementVisiblebyXpath(String xpath) {
        //isElementVisiblebyXpath method is overloaded
        try {
            WaitCustom.sleep(200);
            WebElement element = driver.findElement(By.xpath(xpath));
            return element.isDisplayed();
        } catch (NoSuchElementException e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean isElementVisiblebyXpath(WebElement xpath) {
        //isElementVisiblebyXpath method is overloaded
        try
        {
            WaitCustom.sleep(200);
            return xpath.isDisplayed();
        } catch (NoSuchElementException e)
        {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public void clearElementByName(String name) {
        find(By.name(name)).clear();
    }

    public void clearElementByPath(String path) {
        find(By.xpath(path)).clear();
    }

    public void clearElementById(String id) {
        find(By.id(id)).clear();
    }

    public void sendKeyByName(String name, String value) {
        find(By.name(name)).sendKeys(value);
    }

    public void sendKeyByPath(String path, String value) {
        WebElement element = find(By.xpath(path));
        element.sendKeys(value);
    }

    public void sendKeyById(String id, String value) {
        WebElement element = find(By.id(id));
        element.sendKeys(value);
    }

    public String getAttributeByLinkText(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        return link.getAttribute("href");
    }

    public void confirmAlert() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, 30);
            Alert javascriptAlert = driver.switchTo().alert();
            LogUtil.info(driver.switchTo().alert().getText());
            javascriptAlert.accept();
            WaitCustom.sleep(1000);
        } catch (NoAlertPresentException e) {
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void clickByJavascriptExecutor(WebElement weblement)
    {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", weblement);
    }

    public void confirmAlert(boolean withoutStackTrace) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, 5);
            Alert javascriptAlert = driver.switchTo().alert();
            LogUtil.info(driver.switchTo().alert().getText());
            javascriptAlert.accept();
            WaitCustom.sleep(500);
        }
        catch (NoAlertPresentException e)
        {
            //Ignore
        }
    }


    public String webDriverWaitByXpath(String name){
        return webDriverWaitByXpath(name, 30);
    }

    public String webDriverWaitByXpath(String name, int seconds){
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(name)));
        return getText(find(By.xpath(name)));
    }

    public boolean isElementDisplayed(String locator){
        return isElementDisplayed(locator, 10);
    }

    public boolean isElementDisplayed(String locator, final long timeout){
        boolean element=false;

        try{
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))).isDisplayed();
        }catch(ElementNotVisibleException e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }

        return element;
    }

    public List<WebElement> findMultiple(By by) {
        int retries=3;
        List<WebElement> el = null;
        do{
            boolean failed = false;
            try{
                el = driver.findElements(by);
                if(el.isEmpty()){
                    failed = true;
                }
            }catch(NoSuchElementException e){
                failed = true;
            }
            if(failed){
                if(--retries<0){
                    throw new NoSuchElementException("Element not found "+by);
                }else{
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                        LogUtil.error(ignored.getMessage());
                        ignored.printStackTrace();
                    }
                }
            }

        }while(el==null || el.isEmpty());
        return el;
    }




    public void sendKeyWithTab(String value) {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(value);
    }

    public String getTextByXpath(String xPath){
        return find(By.xpath(xPath)).getText();
    }


    public String getValueInColWithRowText(String text, int colNumber, String basePath) {
        WebElement element = find(By.xpath(basePath + "[contains(text(),'" + text + "')]"));
        WebElement parentRow = getParentTableRow(element);
        return parentRow.findElement(By.xpath(".//td[" + colNumber + "]")).getText();
    }

    public WebElement getParentTableRow(WebElement element) {
        while (!element.getTagName().toLowerCase().equals("tr")) {
            try {
                element = element.findElement(By.xpath("..")); // Returns the
                // parent
            } catch (Exception e) {
                return null;
            }
        }
        return element;
    }

    public void disableReadOnly(String className){
        removeAttribute(className, "readonly");
    }

    public void setAttribute(String id,String attribute,String value){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + id + "').setAttribute('" + attribute + "', '" + value + "')");
    }

    public void removeAttribute(String className, String attribute){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('" + className + "').removeAttribute('" + attribute + "')");
    }

    public void resetAttribute(String id,String attribute, String value){
        removeAttribute(id, attribute);
        setAttribute(id, attribute, value);
    }


    public void  disableElementByXpath(String path, String input, String date, boolean flag, String attribute) {
        clickByXPath(path);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(input + ".removeAttribute('" + attribute + "')");
        js.executeScript(input + ".removeAttribute('ng-" + attribute + "')");
        //js.executeScript(input + ".removeAttribute('readonly')");
        if (!flag) {
            js.executeScript(input + ".value='" + date + "'");
            WaitCustom.sleep();
            clickByXPath(path);
        } else {
            WaitCustom.sleep();
            sendKeyByPath(path, date);
        }
        // Space is used for updating the date element
        WaitCustom.sleep(2000);
        driver.switchTo().activeElement().sendKeys(Keys.SPACE);
    }


    public void  disableWebElement(WebElement element, String input, String date, boolean flag, String attribute) {
        WaitCustom.sleep(500);
        clickByWebElement(element);
        WaitCustom.sleep(500);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(input + ".removeAttribute('" + attribute + "')");
        js.executeScript(input + ".removeAttribute('ng-" + attribute + "')");
        if (!flag) {
            js.executeScript(input + ".value='" + date + "'");
            WaitCustom.sleep();
            clickByWebElement(element);
        } else {
            WaitCustom.sleep();
            sendKeysByWebElement(element, date);
        }
        // Space is used for updating the date element
        WaitCustom.sleep(2000);
        driver.switchTo().activeElement().sendKeys(Keys.SPACE);
        WaitCustom.sleep(500);
    }


    public void getDropDownValuesByXpath(String xpath, String option){
        Select select = new Select(driver.findElement(By.xpath(xpath)));
        select.selectByVisibleText(option);
    }

    public void switchToOtherTab(String materialId) {
//        String handle = driver.getWindowHandle();
//        System.out.println(driver.getTitle()+handle);
//        int i = 0;
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
            try {
//                System.out.println("Trying window " + (++i));
                driver.findElement(By.name("check" + materialId));
//                System.out.println("Window has content id:" + i);
                break;
            } catch (Exception e) {
//                System.out.println("Wrong window.. try next");
                LogUtil.error(e.getMessage());
                e.printStackTrace();
            }
        }
        //driver.switchTo().activeElement();
    }

    /*public void clickCheckBoxwithRowText(String text, String checkBoxName,
                                         String basePath) {
        WebElement element = find(By.xpath(basePath
                + "[contains(text(),'" + text + "')]"));
        WebElement parentRow = getParentTableRow(element);
        parentRow.findElement(By.name(checkBoxName)).click();
    }*/

    public void clickCheckBoxwithRowTextAndXpath(String text, String xpath,
                                                 String basePath) {
        WebElement element = find(By.xpath(basePath + "[contains(text(),'" + text + "')]"));
        WaitCustom.sleep(2000);
        WebElement parentRow = getParentTableRow(element);
        WaitCustom.sleep(2000);
        parentRow.findElement(By.xpath(xpath)).click();
    }

    public void fillTypeAheadById(String id, String value, boolean isEdit, boolean isFollowing, boolean wait) {
        // search by xpath.. because of hidden input element issue..
        // should be the position of typeahead in the page.
        List<WebElement> lists = null;
        if (isFollowing) {
            lists = findMultiple(By.xpath("//div[@id='" + id + "']//li"));
        } else {
            lists = findMultiple(By.xpath("//div[@id='" + id + "']//li/input"));
        }

        int length = lists.size() - 1;
        WebElement element = driver.findElement(By.xpath("//div[@id='" + id + "']//li/input"));
        if (isEdit) {
            for (int i = 0; i < length; i++) {
                element.sendKeys("\b\b");
            }
        }
        String[] splits = value.split(",");
        for (String split : splits) {
            element.sendKeys(split);
            if (wait) {
                //Loop to ensure results are fetched/displayed.
                boolean isResultDisplayed = false;
                int loops = 0;
                do {
                    try {
                        WebElement telement = find(By.xpath("//input[@id='" + id
                                + "']/following-sibling::div//ul[@class='textboxlist-autocomplete-results']")); //No Auto complete results
                        if (telement.isDisplayed()) {
                            isResultDisplayed = true;
                        }
                    } catch (Exception ignored) {
                    }
                    if (!isResultDisplayed) {
                        // Required for server side
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException ignored) {
                        }
                    }
                } while (!isResultDisplayed && ++loops < 5);
            }
            element.sendKeys(Keys.SPACE);
            WaitCustom.sleep(500);
            element.sendKeys("\n");
        }
    }

    public void clickAndPushAcross(String sourceElement, String pushRightButton, String targetElement) {
        try{
            clickByXPath(sourceElement);
            clickByXPath(targetElement);
            clickByXPath(pushRightButton);
        }catch(Exception e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }



    public void sendKeysByWebElement(WebElement element, String value) {
        try {
            WaitCustom.sleep(500);
            element.isDisplayed();
            element.clear();
            element.click();
            element.sendKeys(value);
            WaitCustom.sleep(500);
        } catch (NullPointerException e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        } catch (InvalidSelectorException e){
            LogUtil.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void  verifyTabTitle(String text){
        AssertCustom.assertTruePageText(text);
    }



    public Boolean isElementDisplayedOnThePage(WebElement element)
    {
        try
        {
            return element.isDisplayed();
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
