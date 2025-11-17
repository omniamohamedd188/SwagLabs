package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class loginpage {

    private WebDriver driver;
    private By UserName = By.id("user-name");
    private By PassWord = By.id("password");
    private By HomeTitle = By.className("product_label");

    public loginpage(WebDriver driver) {
        this.driver = driver;

    }

    public void enterCredential(String user, String pass) throws InterruptedException {
        driver.findElement(UserName).clear();
        driver.findElement(UserName).sendKeys(user);
        driver.findElement(PassWord).clear();
        driver.findElement(PassWord).sendKeys(pass);
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        Thread.sleep(2000);
    }

    public void assertOnCREDENTIAL(String title) {
        Assert.assertEquals(driver.findElement(HomeTitle).getText(), title);

    }


}





