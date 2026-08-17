package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
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

        options.setExperimentalOption(
                "excludeSwitches",
                Collections.singletonList("enable-automation")
        );

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void privacySettingsTest() throws InterruptedException {

        HomePage homePage = new HomePage(driver);

        homePage.open();

        // Várunk, hogy az aszinkron Google CMP teljesen inicializálódjon.
        Thread.sleep(15000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Boolean toolbarExists = (Boolean) js.executeScript("""
            function findToolbar(root) {

                const elements = root.querySelectorAll('*');

                for (const element of elements) {

                    if (element.shadowRoot) {

                        if (element.shadowRoot.querySelector('#ft-floating-toolbar')) {
                            return true;
                        }

                        if (findToolbar(element.shadowRoot)) {
                            return true;
                        }
                    }
                }

                return false;
            }

            return findToolbar(document);
        """);

        System.out.println("=== PRIVACY TOOLBAR EXISTS AFTER 15 SEC ===");
        System.out.println(toolbarExists);

        Object cmpInfo = js.executeScript("""
            return [...document.querySelectorAll('iframe')].map(frame => ({
                id: frame.id,
                name: frame.name,
                src: frame.src
            }));
        """);

        System.out.println("=== CMP IFRAMES AFTER 15 SEC ===");
        System.out.println(cmpInfo);

        /*
        homePage.scrollToPrivacy();
        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
        */
    }
}