package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
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

        Object toolbarInfo = js.executeScript("""
                function findElement(root, selector) {

                    const direct = root.querySelector(selector);

                    if (direct) {
                        return direct;
                    }

                    const elements = root.querySelectorAll('*');

                    for (const el of elements) {
                        if (el.shadowRoot) {
                            const found = findElement(el.shadowRoot, selector);

                            if (found) {
                                return found;
                            }
                        }
                    }

                    return null;
                }

                const toolbar = findElement(document, '#ft-floating-toolbar');

                if (!toolbar) {
                    return 'NOT FOUND';
                }

                return {
                    tagName: toolbar.tagName,
                    id: toolbar.id,
                    text: toolbar.innerText || '',
                    html: toolbar.outerHTML.substring(0, 2000)
                };
            """);

            System.out.println("=== PRIVACY TOOLBAR SEARCH ===");
            System.out.println(toolbarInfo);

        
        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}