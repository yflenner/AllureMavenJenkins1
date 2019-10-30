package Sanity;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class example1
{
    static WebDriver driver;

    @BeforeClass
    public void openBrowser()
    {
        initBrowser(System.getenv("browserName"));
        //initBrowser("chrome");
        driver.manage().window().maximize();
        driver.get("http://atidcollege.co.il/Xamples/bmi");
    }

    public void initBrowser(String browserName)
    {
        if(browserName.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("ie"))
        {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
    }

    @Test(description = "Test BMI Results")
    @Description("BMI Results Check")
    public void Test1_BMItest()
    {
        try
        {
            update(driver.findElement(By.id("weight")), "147");
            update(driver.findElement(By.id("hight")), "216");
            click(driver.findElement(By.id("calculate_data")));
            String ExpectedResult = "32";
            String ActualResult = getAttribute(driver.findElement(By.id("bmi_result")));
            verityEquals(ActualResult,ExpectedResult);
        }
        catch(Exception e)
        {
            saveScreenshot();
            fail("Test Failed: " + e);
        }
        catch(AssertionError e)
        {
            saveScreenshot();
            fail("Test Failed: " + e);
        }
    }

    @AfterClass
    public void closeBrowser()
    {
        driver.quit();
    }

    @Step("Update Text Field")
    public void update(WebElement elem, String value)
    {
        elem.sendKeys(value);
    }

    @Step("Click on Element")
    public void click(WebElement elem)
    {
        elem.click();
    }

    @Step("Get Text from input Element")
    public String getAttribute(WebElement elem)
    {
        return elem.getAttribute("value");
    }

    @Step("Verify Results")
    public void verityEquals(String actual, String expected)
    {
        assertEquals(actual, expected);
    }

    @Attachment(value = "Page Screen-Shot", type = "image/png")
    public byte[] saveScreenshot()
    {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
