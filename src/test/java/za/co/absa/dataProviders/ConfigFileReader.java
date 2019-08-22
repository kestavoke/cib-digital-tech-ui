package za.co.absa.dataProviders;

import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    public static String url = null;
    public static Properties propObj;
    private static FileReader inputStreamObj = null;

    public ConfigFileReader() {
        propObj = new Properties();
        try {
            inputStreamObj = new FileReader(System.getProperty("user.dir") + "/src/test/resources/configuration.properties");
            try {
                propObj.load(inputStreamObj);
                url = propObj.getProperty("url");
                inputStreamObj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties not found at " + inputStreamObj);
        }
    }

    public Boolean getBrowserWindowSize() {
        String windowSize = propObj.getProperty("windowMaximize");
        if (windowSize != null) return Boolean.valueOf(windowSize);
        return true;
    }

    public long getImplicitlyWait() {
        String implicitlyWait = propObj.getProperty("implicitlyWait");
        if (implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the configuration.properties file.");
    }

    public String getApplicationUrl() {
        String url = propObj.getProperty("url");
        Assert.assertEquals("http://www.way2automation.com/angularjs-protractor/webtables/", url);
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the configuration.properties file.");
    }

}
