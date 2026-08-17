package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import hu.betti.automation.notesapp.pages.HomePage;

public class PrivacyTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // 1. WebDriver és Chrome opciók beállítása a GitHub Actions-höz
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled"); 

        options.setExperimentalOption(
                "excludeSwitches",
                Collections.singletonList("enable-automation")
        );

        // 2. Böngésző inicializálása (Csak egyszer szabad!)
        driver = new ChromeDriver(options);

        // 3. Az oldal megnyitása
        new HomePage(driver).open();

        // 4. Sütiablak (Google CMP) kezelése Shadow DOM-on keresztül
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // A Google CMP hivatalos shadow-host eleme:
            WebElement shadowHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#consent-shadow-root, div.fc-consent-root, div[id*='consent']"))); 

            // Shadow Root lekérése
            SearchContext shadowRoot = shadowHost.getShadowRoot();

            // A "Minden elfogadása" vagy "Accept all" gomb megkeresése és kattintása a shadow root-on belül
            WebElement acceptButton = shadowRoot.findElement(By.cssSelector("button.fc-cta-consent, button.accept-all, .fc-button-label"));
            acceptButton.click();
            
            System.out.println("Sütiablak sikeresen elfogadva a Shadow DOM-ban.");
        } catch (Exception e) {
            // Ha GitHub-on pl. az amerikai IP miatt fel se jönne az ablak, a teszt nem bukik el azonnal, hanem megy tovább
            System.out.println("A sütiablak nem jelent meg vagy nem sikerült rákattintani: " + e.getMessage());
        }
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
        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}
