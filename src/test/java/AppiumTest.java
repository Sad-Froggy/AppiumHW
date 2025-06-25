import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppiumTest {

    private static final String PACKAGE_NAME = "ru.netology.testing.uiautomator";
    private static final long TIMEOUT = 10000;

    private static final String TEXT_TO_SET = "Netology";

    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "emulator-5554");
        desiredCapabilities.setCapability("appium:appPackage", PACKAGE_NAME);
        desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:noReset", true);
        desiredCapabilities.setCapability("appium:app", "D:\\homeworks24-25\\mqa-homeworks-mqa-video\\2.2 UI Automator\\sample\\app\\build\\outputs\\apk\\debug\\app-debug.apk");
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);

    }

    @Test
    public void testEmptyInput() {
        WebElement textElement = driver.findElement(By.id("ru.netology.testing.uiautomator:id/textToBeChanged"));
        String initialText = textElement.getText();

        testInput("", initialText);

        testInput("   ", initialText);
    }

    private void testInput(String inputText, String expectedText) {
        WebElement inputField = driver.findElement(By
                .id("ru.netology.testing.uiautomator:id/userInput"));
        inputField.isDisplayed();
        inputField.sendKeys("");

        WebElement changeButton = driver.findElement(By
                .id("ru.netology.testing.uiautomator:id/buttonChange"));
        changeButton.isDisplayed();
        changeButton.click();

        String result = driver.findElement(By.id("ru.netology.testing.uiautomator:id/textToBeChanged"))
                .getText();
        assertEquals(expectedText, result);
    }

    @Test
    public void testOpenTextInNewActivity() {
        WebElement inputField = driver.findElement(By.id("ru.netology.testing.uiautomator:id/userInput"));
        inputField.isDisplayed();
        inputField.sendKeys(TEXT_TO_SET);

        WebElement openActivityButton = driver.findElement(By
                .id("ru.netology.testing.uiautomator:id/buttonActivity"));
        openActivityButton.isDisplayed();
        openActivityButton.click();

        WebElement resultText = new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("ru.netology.testing.uiautomator:id/text")));

        assertEquals(TEXT_TO_SET, resultText.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
