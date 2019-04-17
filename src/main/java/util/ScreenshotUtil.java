package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sadiq on 11/04/19.
 */


public class ScreenshotUtil extends TestListenerAdapter implements ITestListener {
    protected static WebDriver driver ;



    public static void captureScreenShot(ITestResult result, String status){
        //BaseTest baseTest = new BaseTest();
        driver = BaseTest.driver;
        WaitCustom waitCustom= new WaitCustom(driver);
        waitCustom.setImplicitTimeOut(Long.parseLong(ConfigReader.get("implict.wait")));
        waitCustom.setSleepTimeOut(Long.parseLong(ConfigReader.get("sleep.wait")));

        String destDir = "";
        //String testClassName = getTestClassName(result.getInstanceName()).trim();
        String testClassName=result.getMethod().getRealClass().getSimpleName().trim();
        //String testMethodName=result.getName().toString().trim();
        String testMethodName=result.getMethod().getMethodName().trim();

        String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
        System.out.println("passfailMethod"+passfailMethod);



        //To capture screenshot.
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mmaa");

        //If status = fail then set folder name "screenshots/Failures"
        if(status.equalsIgnoreCase("fail")){
            destDir = "screenshots/Failure"+"/"+testClassName+"_"+dateFormat.format(new Date())+"/"+testMethodName;
            System.out.println("Screenshot can be found : " + destDir);
        }

        //If status = pass then set folder name "screenshots/Success"
        else if (status.equalsIgnoreCase("pass")){
            destDir = "screenshots/Success/"+"/"+testClassName+"_"+dateFormat.format(new Date())+"/"+testMethodName;
            System.out.println("Screenshot can be found : " + destDir);
        }

        //To create folder to store screenshots

        File file=new File(destDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        //Set file name with combination of test class name + date time.
        String destFile = passfailMethod+".png";

        try {
            //Store file  at destination folder location
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
            LogUtil.info(destDir + "/" + destFile + "\n\n\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        if(ConfigReader.get("screenShotOnPass").trim().equalsIgnoreCase("yes"))
        {
            ScreenshotUtil.captureScreenShot(result, "pass");
        }

    }


    @Override
    public void onTestFailure(ITestResult result) {


        if(ConfigReader.get("screenShotOnFail").trim().equalsIgnoreCase("yes"))
        {
            ScreenshotUtil.captureScreenShot(result, "fail");
        }
    }
}
