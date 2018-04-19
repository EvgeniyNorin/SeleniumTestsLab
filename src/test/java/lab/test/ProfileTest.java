package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class ProfileTest {

    SeleniumUtils utils = new SeleniumUtils();

    By options = By.cssSelector(".avatar");

    By settings = By.xpath("//div/div[2]/div[2]/ul/li[3]/details/ul/li[8]/a");

    By locationInput = By.cssSelector("#user_profile_location");

    By update = By.xpath("//div[4]/div[1]/div/div[2]/form[1]/div/p/button");

    By profile = By.xpath("//header/div/div[2]/div[2]/ul/li[3]/details/ul/li[3]/a");

    String location = "Saint-Petersburg";

    String userName = "torvalds";

    By xpathOfSearch = By.xpath("//div[2]/div[1]/div/div/form/label/input[1]");

    By userButtonSearch = By.cssSelector("a.menu-item:nth-child(7)");

    By followButton = By.xpath("//div[2]/div[1]/div[1]/div[1]/span/span[1]/form/button");

    By unfollowButton = By.xpath("//div/div[2]/div[1]/div[1]/div[1]/span/span[2]/form/button");

    @BeforeEach
    void auth() {
        utils.auth();
    }

    @Test
    void locationTest() {
        for (WebDriver driver : utils.getWebDrivers()) {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            driver.findElement(options).click();
            driver.findElement(settings).click();
            driver.findElement(locationInput).clear();
            driver.findElement(locationInput).sendKeys(location);
            wait.until(ExpectedConditions.elementToBeClickable(update));
            driver.findElement(update).click();
            driver.findElement(options).click();
            driver.findElement(profile).click();
            assertTrue(driver.findElement(By.xpath("//span[contains(text(), '" + location + "')]")).getText().contains(location));
            cleanProfile(driver);
        }
    }

    @Test
    void followersTest() {
        for (WebDriver driver : utils.getWebDrivers()) {
            driver.findElement(xpathOfSearch).click();
            driver.findElement(xpathOfSearch).sendKeys(userName);
            driver.findElement(xpathOfSearch).sendKeys(Keys.ENTER);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(userButtonSearch));
            driver.findElement(userButtonSearch).click();

            wait.until(ExpectedConditions.elementToBeClickable(followButton));
            driver.findElement(followButton).click();

            wait.until(ExpectedConditions.elementToBeClickable(unfollowButton));
            assertTrue(driver.findElement(unfollowButton).isEnabled());
            driver.findElement(unfollowButton).click();
        }
    }

    private void cleanProfile(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        driver.findElement(options).click();
        driver.findElement(settings).click();
        driver.findElement(locationInput).clear();
        driver.findElement(locationInput).sendKeys("");
        wait.until(ExpectedConditions.elementToBeClickable(update));
        driver.findElement(update).click();
        driver.findElement(options).click();
        driver.findElement(profile).click();
    }

    @AfterEach
    void close() {
        utils.closeAll();
    }
}
