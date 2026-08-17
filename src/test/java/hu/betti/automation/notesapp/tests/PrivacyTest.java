package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void privacySettingsTest() {

        HomePage homePage = new HomePage(driver);

        homePage.open();
        
        WebElement shadowHost = driver.findElement(
                By.cssSelector("body > div:nth-child(19)")
        );

        SearchContext shadowRoot = shadowHost.getShadowRoot();

        WebElement privacyToolbar = shadowRoot.findElement(
                By.id("ft-floating-toolbar")
        );

        System.out.println("=== PRIVACY TOOLBAR FOUND ===");
        System.out.println(privacyToolbar);

        homePage.scrollToPrivacy();

        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}