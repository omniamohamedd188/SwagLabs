package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;
    int i=1;
    @BeforeSuite
    public void setup() {
        System.out.println("Runs before all test in this class");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
    }

    @DataProvider(name = "login")
    public Object[][] getlogin() {
        return new Object[][]{
                {"standard_user", "secret_sauce"}   // username wrong
        };
    }


    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                File directory = new File("./screenshots/");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                String screenshotPath = "./screenshots/" + result.getName() + "_" + i + ".png";
                FileHandler.copy(src, new File(screenshotPath));

                System.out.println(" Screenshot saved: " + screenshotPath);
                i++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @DataProvider(name = "invalidLogin")
    public Object[][] getinvalidLogin() {
        return new Object[][]{
                {"", "1111"} ,
                {"", "secret_sauce"} ,
                {"standard_user", "star"} ,
                {"1111", "secret_sauce"} ,
                {"standard_user", " "} ,
        };
    }
    @DataProvider(name = "validCheckout")
    public Object[][] getvalidData() {
        return new Object[][]{
                {"omnia", "mohamed", "1111"}   // username wrong
        };
    }

    @DataProvider(name = "invalidCheckout")
    public Object[][] getInvalidData() {
        return new Object[][]{
                {"omnia", "mohamed", ""},    // username wrong
                {"", "mohamed", "1111"},   // password wrong
                {"omnia", "", "1111"},      // two wrong
                {"1111", "mohamed", "1111"},              // username empty
                {"omnia", "1111", "1111"},             // password empty
                {"star", "mohamed", "1111"},                       // two empty
                {"omnia", "star", "1111"},                 //special charcter
                {"omnia", "mohamed", "star"}
        };



    }
    @AfterSuite
    public void teardown() {
       System.out.println("Runs after all test in this class");
       driver.close();

   }
}
