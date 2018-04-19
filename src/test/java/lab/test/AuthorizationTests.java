package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

class AuthorizationTests {

    SeleniumUtils utils = new SeleniumUtils();

    @Test
    void tryWithWrongLoginAndPass() {
        utils.auth(utils.getValidLogin(), utils.getValidPassword());
        for (WebDriver driver: utils.getWebDrivers()) {
            String text = driver.findElement(By.xpath("//div[4]/div[1]/div/div/div[2]/div[2]/div/h3")).getText();
            assertTrue(text.contains("Your repositories"));
        }
    }

    @Test
    void tryWithRightLoginAndPass() {
        utils.auth("any", "path");
        for (WebDriver driver: utils.getWebDrivers()) {
            String text = driver.findElement(By.xpath("//div[3]/div[1]/div/form/div[2]/div/div")).getText();
            assertTrue(text.contains("Incorrect username or password."));
        }
    }

    @Test
    @DisplayName("we are logging out and try to do any auth-user action")
    void logout() {
        String imgButton = "//div/div[2]/div[2]/ul/li[3]/details/summary";
        String signOut = "//div/div[2]/div[2]/ul/li[3]/details/ul/li[9]/form/button";
        String signIn = "//div/div[2]/div/span/div/a[1]";
        utils.auth();
        for (WebDriver driver : utils.getWebDrivers()) {
            driver.findElement(By.xpath(imgButton)).click();
            driver.findElement(By.xpath(signOut)).click();
            assertTrue(driver.findElement(By.xpath(signIn)).getText().contains("Sign in"));
        }

    }

    @AfterEach
    void close() {
        utils.closeAll();
    }
}
