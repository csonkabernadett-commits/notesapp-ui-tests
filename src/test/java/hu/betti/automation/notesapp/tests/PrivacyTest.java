package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        // Hide the automation flag from the browser.
        options.setExperimentalOption(
                "excludeSwitches",
                Collections.singletonList("enable-automation")
        );

        driver = new ChromeDriver(options);

        new HomePage(driver).open();
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

        homePage.scrollToPrivacy();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object iframeInfo = js.executeScript("""
            const result = [];

            document.querySelectorAll('iframe').forEach((iframe, index) => {
                result.push(
                    'IFRAME ' + index +
                    ' src=' + (iframe.src || '') +
                    ' title=' + (iframe.title || '') +
                    ' id=' + (iframe.id || '') +
                    ' name=' + (iframe.name || '')
                );
            });

            result.push('IFRAME COUNT: ' + document.querySelectorAll('iframe').length);

            document.querySelectorAll('*').forEach((el, index) => {
                if (el.shadowRoot) {
                    result.push(
                        'SHADOW HOST ' + index +
                        ' tag=' + el.tagName +
                        ' id=' + (el.id || '') +
                        ' class=' + (el.className || '')
                    );
                }
            });

            return result;
        """);

        System.out.println("=== IFRAME / SHADOW DOM DIAGNOSTICS ===");
        System.out.println(iframeInfo);
        
        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}