package Sanity;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class example2
{
    static WebDriver driver;
    DesiredCapabilities cap = new DesiredCapabilities();;

    @BeforeClass
    public void openBrowser() throws MalformedURLException
    {
        //initBrowser(System.getenv("browserName"));
        initBrowser("chrome");
        driver.manage().window().maximize();
        driver.get("http://atidcollege.co.il/Xamples/bmi");
    }

    public void initBrowser(String browserName) throws MalformedURLException
    {
        if(browserName.equalsIgnoreCase("chrome"))
        {
            cap.setBrowserName(browserName);
            cap.setPlatform(Platform.LINUX);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("firefox"))
        {
            cap.setBrowserName(browserName);
            cap.setPlatform(Platform.LINUX);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);

//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
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
