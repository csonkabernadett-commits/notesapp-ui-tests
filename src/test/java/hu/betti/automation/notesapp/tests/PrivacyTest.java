package hu.betti.automation.notesapp.tests;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;

import java.util.Map;

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
        options.addArguments("--lang=hu-HU");
        options.addArguments("--timezone=Europe/Budapest");
        options.addArguments("--disable-blink-features=AutomationControlled");

        options.setExperimentalOption(
                "excludeSwitches",
                Collections.singletonList("enable-automation")
        );

        driver = new ChromeDriver(options);

        // Magyar locale kényszerítése JavaScript oldalon
        ((ChromeDriver) driver).executeCdpCommand(
                "Emulation.setTimezoneOverride",
                Map.of("timezoneId", "Europe/Budapest")
        );

        ((ChromeDriver) driver).executeCdpCommand(
                "Emulation.setLocaleOverride",
                Map.of("locale", "hu-HU")
        );

        ((ChromeDriver) driver).executeCdpCommand(
                "Page.addScriptToEvaluateOnNewDocument",
                Map.of(
                        "source",
                        """
                        Object.defineProperty(navigator, 'language', {
                            get: () => 'hu-HU'
                        });

                        Object.defineProperty(navigator, 'languages', {
                            get: () => ['hu-HU', 'hu', 'en-US', 'en']
                        });
                        """
                )
        );

        new HomePage(driver).open();
        
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

        // Várunk az aszinkron Google CMP inicializálására
        Thread.sleep(25000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =========================================================
        // 1. Privacy toolbar keresése a DOM / Shadow DOM alatt
        // =========================================================

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


        // =========================================================
        // 2. Google CMP iframe-ek listázása
        // =========================================================

        Object cmpInfo = js.executeScript("""
            return [...document.querySelectorAll('iframe')].map(frame => ({
                id: frame.id,
                name: frame.name,
                src: frame.src
            }));
        """);

        System.out.println("=== CMP IFRAMES AFTER 15 SEC ===");
        System.out.println(cmpInfo);


        // =========================================================
        // 3. Böngésző privacy / locale környezet
        // =========================================================

        Object privacyState = js.executeScript("""
            return {
                language: navigator.language,
                languages: navigator.languages,
                timezone: Intl.DateTimeFormat().resolvedOptions().timeZone,
                cookieEnabled: navigator.cookieEnabled,
                doNotTrack: navigator.doNotTrack,
                url: window.location.href
            };
        """);

        System.out.println("=== BROWSER PRIVACY ENVIRONMENT ===");
        System.out.println(privacyState);


        // =========================================================
        // Privacy kattintás szándékosan nincs még bekapcsolva.
        //
        // homePage.scrollToPrivacy();
        // homePage.clickPrivacyIcon();
        // assertTrue(homePage.isPrivacySettingsDisplayed());
        // =========================================================
    }
}