package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;


public class RepoTests {
    SeleniumUtils utils = new SeleniumUtils();

    String popularRepoName = "linux";

    String unstarButton = "//div[1]/div/ul/li[2]/div/form[1]/button";

    String repoName = "test";

    void unstar(WebDriver driver) {
        driver.findElement(By.xpath(unstarButton)).click();
    }

    By plusImgXpath = By.xpath("//div[2]/div[2]/ul/li[2]/details/summary");

    By newRepoXpath = By.xpath("//div[2]/div[2]/ul/li[2]/details/ul/a[1]");

    By repoNameInput = By.cssSelector("#repository_name");

    By description = By.cssSelector("#repository_description");

    By createRepoButton = By.cssSelector("button.btn:nth-child(12)");

    By settings = By.xpath("/html/body/div[4]/div/div/div[1]/nav/a[4]");

    By deleteRepoButton = By.xpath("//div[2]/div[1]/div/div[2]/div/div[10]/ul/li[4]/button");

    By deleteVerificationInput = By.xpath("//div[2]/div/div[10]/ul/li[4]/div/div/div/div/div[2]/form/p/input");

    By confirm = By.xpath("//div/div[2]/div/div[10]/ul/li[4]/div/div/div/div/div[2]/form/button");

    By deleteConfirmMessage = By.xpath("//html/body/div[3]/div/div[contains(text(), '')]");

    @BeforeEach
    void auth() {
        utils.auth();
    }

    @Test
    void findStaredRepo() {
        for (WebDriver driver : utils.getWebDrivers()) {
            driver.findElement(By.xpath("//ul/li[3]/details/summary/img")).click();
            driver.findElement(By.xpath("//div[2]/ul/li[3]/details/ul/li[4]/a")).click();
            driver.findElement(By.xpath("//div/div[2]/div[4]/div[1]/form/input[2]")).sendKeys("akka");
            driver.findElement(By.xpath("//div/div[2]/div[4]/div[1]/form/input[2]")).sendKeys(Keys.ENTER);
            assertTrue(driver.findElement(By.xpath("//span[contains(text(), 'akka')]")).getText().contains("akka"));
        }
    }

    @Test
    void starringRepo() {
        for (WebDriver driver : utils.getWebDrivers()) {
            By xpathOfSearch = By.xpath("//div[2]/div[1]/div/div/form/label/input[1]");
            driver.findElement(xpathOfSearch).click();
            driver.findElement(xpathOfSearch).sendKeys(popularRepoName);
            driver.findElement(xpathOfSearch).sendKeys(Keys.ENTER);
            driver.findElement(By.partialLinkText("torvalds")).click();
//            driver.findElement(By.xpath("//a/em[text()='linux']/parent::a[contains(text(), 'torvalds')]")).click();
            driver.findElement(By.xpath("//div[1]/div/ul/li[2]/div/form[2]/button")).click();
            assertTrue(driver.findElement(By.xpath(unstarButton)).isEnabled());
            unstar(driver);
        }
    }

    @Test
    void createRepo() {
        for(WebDriver driver: utils.getWebDrivers()) {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            driver.findElement(plusImgXpath).click();
            driver.findElement(newRepoXpath).click();
            driver.findElement(repoNameInput).sendKeys(repoName);
            driver.findElement(description).sendKeys("descr");
            wait.until(ExpectedConditions.elementToBeClickable(createRepoButton));
            driver.findElement(createRepoButton).click();
            assertTrue(driver.findElement(By.xpath("//strong[contains(text(), 'Quick setup')]")).getText().contains("Quick setup"));
            deleteRepoAfterCreation(driver);
        }
    }

    @Test
    void deleteRepo() {
        for(WebDriver driver: utils.getWebDrivers()) {
            createBeforeDeleting(driver);
            driver.findElement(settings).click();
            driver.findElement(deleteRepoButton).click();
            driver.findElement(deleteVerificationInput).click();
            driver.findElement(deleteVerificationInput).sendKeys("test");
            ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"btn btn-block btn-danger\")[2].disabled = null");
            driver.findElement(confirm).click();
            assertTrue(driver.findElement(deleteConfirmMessage).getText().contains("was successfully deleted"));
        }
    }

    private void createBeforeDeleting(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.findElement(plusImgXpath).click();
        driver.findElement(newRepoXpath).click();
        driver.findElement(repoNameInput).sendKeys(repoName);
        driver.findElement(description).sendKeys("descr");
        wait.until(ExpectedConditions.elementToBeClickable(createRepoButton));
        driver.findElement(createRepoButton).click();
    }

    private void deleteRepoAfterCreation(WebDriver driver) {
        driver.findElement(settings).click();
        driver.findElement(deleteRepoButton).click();
        driver.findElement(deleteVerificationInput).click();
        driver.findElement(deleteVerificationInput).sendKeys("test");
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"btn btn-block btn-danger\")[2].disabled = null");
        driver.findElement(confirm).click();
    }
}
