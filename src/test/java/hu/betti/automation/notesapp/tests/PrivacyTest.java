package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import hu.betti.automation.notesapp.pages.HomePage;

public class PrivacyTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // CI környezetben headless mód
        options.addArguments("--headless=new");

        // Full HD felbontás
        options.addArguments("--window-size=1920,1080");

        // CI környezethez
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Nincs reklámblokkolás a PrivacyTest-ben.
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String name) {

        try {
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path target = Path.of(
                    "privacy-diagnostics",
                    name + ".png"
            );

            Files.createDirectories(target.getParent());

            Files.copy(
                    screenshot.toPath(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Screenshot saved: " + target);

        } catch (Exception e) {
            System.out.println(
                    "Could not save screenshot " + name + ": "
                            + e.getMessage()
            );
        }
    }

    @Test
    void privacySettingsTest() {

        HomePage homePage = new HomePage(driver);

        homePage.open();

        takeScreenshot("01-after-open");

        homePage.scrollToPrivacy();

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object scrollInfo = js.executeScript("""
            return {
                scrollY: window.scrollY,
                innerHeight: window.innerHeight,
                scrollHeight: document.documentElement.scrollHeight
            };
        """);

        System.out.println("=== SCROLL INFO ===");
        System.out.println(scrollInfo);

        takeScreenshot("02-after-scroll");

        homePage.clickPrivacyIcon();

        takeScreenshot("03-after-privacy-click");

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}