package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    private WebElement searchBox;

    @FindBy(xpath = "//span[@data-component-type='s-search-results']")
    private WebElement searchResults;

    @FindBy(xpath = "//span[@id='nav-cart-count']")
    private WebElement cartButton;

    @FindBy(xpath = "//span[@data-a-class='quantity']")
    private WebElement quantityField;

    @FindBy(xpath = "//*[@id='sc-subtotal-amount-activecart']")
    private WebElement totalPrice;

    @FindBy(xpath = "//div[@data-quantity='2']//span[@class='a-dropdown-container']")
    private WebElement quantityDropdown;

    public AmazonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToAmazon() {
        driver.get("https://www.amazon.com");
    }

    public void searchForItem(String item) {
        searchBox.sendKeys(item);
        searchBox.submit();
    }

    public void addItemToCart(String index) {
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@id, 'autoid-" + index + "-')]"));
        addToCartButton.click();
    }

    public int getCartValue() {
        wait.until(ExpectedConditions.visibilityOf(cartButton));
        cartButton.click();
        wait.until(ExpectedConditions.visibilityOf(quantityField));
        return driver.findElements(By.name("quantity")).stream()
            .mapToInt(element -> Integer.parseInt(element.getAttribute("value"))).sum();
    }

    public void changeQuantity(int newQuantity) {
        wait.until(ExpectedConditions.visibilityOf(quantityDropdown));
        quantityDropdown.click();
        WebElement option = driver.findElement(By.xpath("//option[@value='" + newQuantity + "'][1]"));
        option.click();
    }
    
}
