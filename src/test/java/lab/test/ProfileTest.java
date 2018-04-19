package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
            assertTrue(driver.findElement(By.xpath("//span[contains(text(), '" + location +"')]")).getText().contains(location));
            cleanProfile(driver);
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


}
