package lab.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SeleniumUtils {

    private String baseUrl = "http://github.com";

    private String validLogin = "tpouser";

    private String validPassword = "tpouser1";

    private WebDriver firefoxDriver;

    private WebDriver chromeDriver;

    private ArrayList<WebDriver> webDrivers = new ArrayList<WebDriver>();

    public SeleniumUtils() {
        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        firefoxDriver = new FirefoxDriver();
        chromeDriver = new ChromeDriver();
        webDrivers.add(chromeDriver);
        webDrivers.add(firefoxDriver);
        for (WebDriver webDriver : webDrivers) {
            webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            webDriver.get(baseUrl);
        }
    }

    public void auth(String login, String password) {
        for (WebDriver webDriver : webDrivers) {
            webDriver.findElement(By.xpath("/html/body/div[1]/header/div/div[2]/div/span/div/a[1]")).click();
            webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/form/div[3]//input[@name='login']")).sendKeys(login);
            webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/form/div[3]//input[@name='password']")).sendKeys(password);
            webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/form/div[3]/input[3]")).click();
        }
    }

    public void auth() {
        auth(getValidLogin(), getValidPassword());
    }

    public String getValidLogin() {
        return validLogin;
    }

    public String getValidPassword() {
        return validPassword;
    }

    public WebDriver getChromeDriver() {
        return chromeDriver;
    }

    public WebDriver getFirefoxDriver() {
        return firefoxDriver;
    }
}
