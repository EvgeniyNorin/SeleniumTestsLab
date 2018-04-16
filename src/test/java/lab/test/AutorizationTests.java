package lab.test;

import lab.utils.SeleniumUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

class AutorizationTests {

    SeleniumUtils utils = new SeleniumUtils();

    @Test
    void tryWithWrongLoginAndPass() {
        utils.auth(utils.getValidLogin(), utils.getValidPassword());
    }

    @Test
    void tryWithRightLoginAndPass() {
        utils.auth("any", "path");
        String str = utils.getChromeDriver().findElement(By.xpath("/html/body/div[3]/div[1]/div/form/div[2]/div/div")).getText();
        assertEquals(str.trim(), "Incorrect username or password.");
    }

}
