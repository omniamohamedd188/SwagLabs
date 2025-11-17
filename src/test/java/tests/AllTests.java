package tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CheckoutPage;
import pages.loginpage;
import utils.DataProviders;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class AllTests extends BaseTest {

    @Test(dataProvider = "loginCSV", dataProviderClass = DataProviders.class, priority = 1)
    public void validlogin(String US, String PA) throws InterruptedException {
        loginpage login = new loginpage(driver);
        login.enterCredential(US, PA);
        login.assertOnCREDENTIAL("Products");

        String[] targetProducts = {
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Bolt T-Shirt"
        };
        String[] expectedPrices = new String[targetProducts.length];
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

        for (WebElement product : products) {
            String productName = product.findElement(By.className("inventory_item_name")).getText();
            for (int i = 0; i < targetProducts.length; i++) {
                if (productName.equalsIgnoreCase(targetProducts[i])) {
                    String priceHome = product.findElement(By.className("inventory_item_price")).getText();
                    expectedPrices[i] = priceHome.replace("$", "");
                    product.findElement(By.tagName("button")).click();
                }
            }
        }
        driver.findElement(By.className("shopping_cart_link")).click();
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        for (int i = 0; i < targetProducts.length; i++) {
            for (WebElement item : cartItems) {
                String cartName = item.findElement(By.className("inventory_item_name")).getText();
                String cartPrice = item.findElement(By.className("inventory_item_price")).getText();
                if (cartName.equalsIgnoreCase(targetProducts[i])) {
                    Assert.assertEquals(cartPrice, expectedPrices[i]);
                }
            }
        }
    }


    @Test(dataProvider = "checkoutDataExcel", dataProviderClass = utils.DataProviders.class, priority = 2)
    public void checkoutdata(String FN, String LN, String PC) {
        CheckoutPage validCheck = new CheckoutPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement checkoutBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn_action checkout_button']")));
        checkoutBtn.click();

        validCheck.validCheckout(FN, LN, PC);
        driver.findElement(By.xpath("//a[@class='btn_action cart_button']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//h2[@class='complete-header']")).getText(),
                "THANK YOU FOR YOUR ORDER");
        driver.findElement(By.xpath("//button[text()='Open Menu']")).click();
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(500));
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutBtn.click();
    }


    @Test(dataProvider = "loginJSON", dataProviderClass = DataProviders.class, priority = 3)
    public void invalidLoginjason(String US, String PA) throws InterruptedException {
        loginpage login = new loginpage(driver);
        login.enterCredential(US, PA);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        System.out.println(errorMsg.getText());
        Assert.assertTrue(errorMsg.isDisplayed(), "Username and password do not match any user in this service");
    }






    @Test(priority = 4)
    public void validLoginUsingProperties() throws IOException, InterruptedException {

        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/valid login.properties");
        props.load(fis);

        String username = props.getProperty("username");
        String password = props.getProperty("password");

        loginpage login = new loginpage(driver);
        login.enterCredential(username, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='header_label']")));

        Assert.assertTrue(successMsg.isDisplayed(), "Login was not successful!");
    }



@Test(priority = 5)
public void invalidCheckoutUsingProperties() throws IOException {
    Properties props = new Properties();
    FileInputStream fis = new FileInputStream("src/test/resources/invalid checkout.properties");
    props.load(fis);

    String firstname = props.getProperty("firstname");
    String lastname = props.getProperty("lastname");
    String zipcode = props.getProperty("zipcode");

    driver.findElement(By.id("shopping_cart_container")).click();
    driver.findElement(By.id("checkout")).click();

    driver.findElement(By.id("first-name")).sendKeys(firstname);
    driver.findElement(By.id("last-name")).sendKeys(lastname);
    driver.findElement(By.id("postal-code")).sendKeys(zipcode);

    driver.findElement(By.id("continue")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));

    System.out.println("⚠️ Error message displayed: " + errorMsg.getText());
    Assert.assertTrue(errorMsg.isDisplayed(), "Error message was not displayed for invalid checkout!");
}


    @Test(dataProvider = "checkoutDataExcel", dataProviderClass = utils.DataProviders.class, priority = 6)

    public void ValidcheckoutWithNoproduct(String FN, String LN, String PC) {
        CheckoutPage validCheck = new CheckoutPage(driver);
        validCheck.validCheckout(FN, LN, PC);
        driver.findElement(By.xpath("//a[@class='btn_action cart_button']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//h2[@class='complete-header']")).getText(),
                "THANK YOU FOR YOUR ORDER");

        driver.findElement(By.xpath("//button[text()='Open Menu']")).click();

        WebDriverWait waitT = new WebDriverWait(driver, Duration.ofSeconds(500));
        WebElement logoutBtn = waitT.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutBtn.click();


    }
}


























