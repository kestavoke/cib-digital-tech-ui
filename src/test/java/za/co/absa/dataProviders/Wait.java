package za.co.absa.dataProviders;


import za.co.absa.managers.FileReaderManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Wait {

    static WebDriver driver;

    public static void untilJqueryIsDone(WebDriver driver){
        untilJqueryIsDone(driver, FileReaderManager.getInstance().getConfigFileReader().getImplicitlyWait());
    }

    private static void untilJqueryIsDone(WebDriver driver, Long timeoutInSeconds){
        until(driver, (d) ->
        {
            Boolean isJqueryCallDone = (Boolean)((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone) System.out.println("JQuery call is in Progress");
            return isJqueryCallDone;
        }, timeoutInSeconds);
    }

    public static void untilPageLoadComplete(WebDriver exDriver){
        untilPageLoadComplete(exDriver, FileReaderManager.getInstance().getConfigFileReader().getImplicitlyWait());
    }

    private static void untilPageLoadComplete(WebDriver exDriver, Long timeoutInSeconds){
        until(exDriver, (d) ->
        {
            Boolean isPageLoaded = ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded)System.out.println("Document is loading");
            return isPageLoaded;
        }, timeoutInSeconds);
    }

    public static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition){
        until(driver, waitCondition, FileReaderManager.getInstance().getConfigFileReader().getImplicitlyWait());
    }

    private static void until(WebDriver exDriver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds){
        WebDriverWait webDriverWait = new WebDriverWait(exDriver, timeoutInSeconds);
        webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
        try {
            webDriverWait.until(waitCondition);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
