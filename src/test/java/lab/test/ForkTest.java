package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class ForkTest {

    SeleniumUtils utils = new SeleniumUtils();

    By xpathOfSearch = By.xpath("//div[2]/div[1]/div/div/form/label/input[1]");

    By fork = By.cssSelector("button.btn-with-count:nth-child(3)");

    By options = By.cssSelector(".avatar");

    By profile = By.xpath("//header/div/div[2]/div[2]/ul/li[3]/details/ul/li[3]/a");

    By linuxLink = By.xpath("//span[contains(text(), 'linux')]");

    By settings = By.cssSelector("a.js-selected-navigation-item:nth-child(5)");

    By deleteRepoButton = By.xpath("//div[2]/div[1]/div/div[2]/div/div[10]/ul/li[4]/button");

    By deleteVerificationInput = By.xpath("//div[2]/div/div[10]/ul/li[4]/div/div/div/div/div[2]/form/p/input");

    By confirm = By.xpath("//div/div[2]/div/div[10]/ul/li[4]/div/div/div/div/div[2]/form/button");

    String popularRepoName = "linux";

    @BeforeEach
    void auth() {
        utils.auth();
    }

    @Test
    void doFork() {
        for (WebDriver driver : utils.getWebDrivers()) {
            driver.findElement(xpathOfSearch).click();
            driver.findElement(xpathOfSearch).sendKeys(popularRepoName);
            driver.findElement(xpathOfSearch).sendKeys(Keys.ENTER);
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("torvalds")));
            driver.findElement(By.partialLinkText("torvalds")).click();
            driver.findElement(fork).click();
            driver.findElement(options).click();
            driver.findElement(profile).click();
            assertTrue(driver.findElement(linuxLink).getText().contains(popularRepoName));
            deleteRepo(driver);
        }
    }

    private void deleteRepo(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.findElement(linuxLink).click();
        wait.until(ExpectedConditions.elementToBeClickable(settings));
        driver.findElement(settings).click();
        driver.findElement(deleteRepoButton).click();
        driver.findElement(deleteVerificationInput).click();
        driver.findElement(deleteVerificationInput).sendKeys(popularRepoName);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"btn btn-block btn-danger\")[2].disabled = null");
        driver.findElement(confirm).click();
    }

    @AfterEach
    void close() {
        utils.closeAll();
    }
}
