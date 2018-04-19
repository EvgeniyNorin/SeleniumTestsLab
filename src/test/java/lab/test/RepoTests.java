package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RepoTests {
    SeleniumUtils utils = new SeleniumUtils();

    String popularRepoName = "linux";

    By unstarButton = By.xpath("//div[1]/div/ul/li[2]/div/form[1]/button");

    String repoName = "test";

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

    By xpathOfSearch = By.xpath("//div[2]/div[1]/div/div/form/label/input[1]");

    By starredRepoSearch = By.cssSelector("input.form-control:nth-child(2)");

    By starButton = By.cssSelector(".unstarred > button:nth-child(4)");

    @BeforeEach
    void auth() {
        utils.auth();
    }

    @Test
    void findStaredRepoTest() {
        for (WebDriver driver : utils.getWebDrivers()) {
            driver.findElement(By.xpath("//ul/li[3]/details/summary/img")).click();
            driver.findElement(By.xpath("//div[2]/ul/li[3]/details/ul/li[4]/a")).click();
            driver.findElement(starredRepoSearch).sendKeys("akka");
            driver.findElement(starredRepoSearch).sendKeys(Keys.ENTER);
            assertEquals(driver.findElement(By.xpath("//div/div[2]/div[3]/div[2]/div/strong[1]")).getText(), "1");
        }
    }

    @Test
    void starringRepoTest() {
        for (WebDriver driver : utils.getWebDrivers()) {
            starRepo(driver);
            assertTrue(driver.findElement(unstarButton).isEnabled());
            unstar(driver);
        }
    }

    @Test
    void createRepoTest() {
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
    void deleteRepoTest() {
        for(WebDriver driver: utils.getWebDrivers()) {
            createBeforeDeleting(driver);
            driver.findElement(settings).click();
            driver.findElement(deleteRepoButton).click();
            driver.findElement(deleteVerificationInput).click();
            driver.findElement(deleteVerificationInput).sendKeys("test");
            ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"btn btn-block btn-danger\")[2].disabled = null");
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirm);
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

    private void starRepo(WebDriver driver) {
        By linux = By.partialLinkText("torvalds");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.findElement(xpathOfSearch).click();
        driver.findElement(xpathOfSearch).sendKeys(popularRepoName);
        driver.findElement(xpathOfSearch).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(linux));
        driver.findElement(linux).click();
//            driver.findElement(By.xpath("//a/em[text()='linux']/parent::a[contains(text(), 'torvalds')]")).click();
        driver.findElement(starButton).click();
    }


    private void unstar(WebDriver driver) {
        driver.findElement(unstarButton).click();
    }

    @AfterEach
    void close() {
        utils.closeAll();
    }
}
