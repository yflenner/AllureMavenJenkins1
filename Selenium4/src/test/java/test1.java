import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class test1
{
    WebDriver driver;

    @BeforeClass
    public void openBrowser()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://wordtohtml.net/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void Test1_Arik() throws InterruptedException
    {
        driver.switchTo().frame(driver.findElement(By.className("fr-iframe")));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.className("fr-view"))).sendKeys("s").build().perform();
        driver.switchTo().parentFrame();
        Thread.sleep(1000);
        action.moveToElement(driver.findElement(By.cssSelector("span[class='cm-tag cm-bracket']"))).sendKeys("Hello").build().perform();
    }

    @AfterClass
    public void closeBrowser() throws InterruptedException
    {
        Thread.sleep(2500);
        driver.quit();
    }
}
