import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.openqa.selenium.Alert.*;

public class WaitToBeGone {
    WebDriver driver;


    @BeforeMethod
    public void before() {
        driver = new ChromeDriver();
    }

    @Test
    public void addRemoveTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement checkBox = driver.findElement(By.cssSelector("#checkbox>input"));
        checkBox.click();

        WebElement removeButton = driver.findElement(By.cssSelector("#checkbox-example>button"));
        removeButton.click();

        WebElement isGone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Assert.assertEquals("It's gone!", isGone.getText());

    }

    @Test
    public void alertConfirm() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement alertConfirm = driver.findElement(By.cssSelector("button[onclick='jsConfirm()']"));
        alertConfirm.click();

        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    @Test
    public void alertPrompt() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement alertPrompt = driver.findElement(By.cssSelector("button[onclick='jsPrompt()']"));
        alertPrompt.click();

        String myName = "Georgi";
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(myName);
        alert.accept();

        WebElement checker = driver.findElement(By.cssSelector("#result"));
        Assert.assertEquals("You entered: " + myName, checker.getText());
    }

    @Test
    public void swotchTab() {
        driver.get("https://the-internet.herokuapp.com/windows");
        driver.manage().window().fullscreen();
        WebElement theLink = driver.findElement(By.cssSelector(".example>a"));
        theLink.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement checkHeader = driver.findElement(By.cssSelector("h3"));
        String headerText = checkHeader.getText();
        Assert.assertEquals("New Window", headerText);

        driver.close();
        driver.switchTo().window(tabs.get(0));

    }

    @Test
    public void nestedFrames() {
        driver.get("https://the-internet.herokuapp.com/frames");
        driver.findElement(By.linkText("Nested Frames")).click();

        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        WebElement leftElement = driver.findElement(By.tagName("body"));
        leftElement.getText();

        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    @Test
    public void iFrame() {
        driver.get("https://the-internet.herokuapp.com/frames");
        driver.findElement(By.linkText("iFrame")).click();

        driver.switchTo().frame("mce_0_ifr");
        WebElement textField = driver.findElement(By.cssSelector("#tinymce>p"));
        textField.clear();
        textField.sendKeys("IT WOOOORKS");
    }

    @Test
    public void tablesTest() {
        driver.get("https://the-internet.herokuapp.com/tables");
        WebElement editFrank = driver.findElement(By.xpath("//table[@id='table1']//td[contains(text(),'Frank')]/../td/a[contains(text(),'edit')]"));
    }

    @AfterMethod
    public void after() {
        // driver.quit();
    }
}
