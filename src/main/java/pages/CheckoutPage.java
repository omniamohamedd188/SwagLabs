package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

public class CheckoutPage {

    private WebDriver driver;
    private By FirstName = By.id("first-name");
    private By LastWord = By.id("last-name");
    private By Postal = By.id("postal-code");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;

    }

    public void validCheckout(String firstname, String lastname, String postal) {
        driver.findElement(FirstName).clear();
        driver.findElement(LastWord).clear();
        driver.findElement(Postal).clear();
        driver.findElement(FirstName).sendKeys(firstname);
        driver.findElement(LastWord).sendKeys(lastname);
        driver.findElement(Postal).sendKeys(postal, Keys.ENTER);
    }


}