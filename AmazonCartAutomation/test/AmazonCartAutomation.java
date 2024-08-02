import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import pageObjects.AmazonPage;

import java.util.logging.Logger;
import java.util.logging.Level;

public class AmazonCartAutomation {

    private static final Logger logger = Logger.getLogger(AmazonCartAutomation.class.getName());
    private WebDriver driver;

    @Test
    public void test() {
        driver = new ChromeDriver();
        AmazonPage amazonPage = new AmazonPage(driver);

        try {
            amazonPage.navigateToAmazon();
            amazonPage.searchForItem("laptop");
            amazonPage.addItemToCart("1");
            int cartValue = amazonPage.getCartValue();
            assert cartValue == 1 : "Cart value is not correct. Expected 1 but found " + cartValue;

            amazonPage.searchForItem("dog");
            amazonPage.addItemToCart("2");
            cartValue = amazonPage.getCartValue();
            assert cartValue == 2 : "Cart value is not correct. Expected 2 but found " + cartValue;

            amazonPage.changeQuantity(1);
            cartValue = amazonPage.getCartValue();
            assert cartValue == 1 : "Cart value is not correct. Expected 1 but found " + cartValue;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Test failed", e);
        } finally {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}
