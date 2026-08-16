package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // CI környezetben headless mód
        options.addArguments("--headless=new");

        // Full HD felbontás
        options.addArguments("--window-size=1920,1080");

        // CI környezethez
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        ChromeDriver chromeDriver = new ChromeDriver(options);

        // Nincs reklámblokkolás a PrivacyTest-ben.
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
        homePage.scrollToPrivacy();

        // Privacy DOM diagnosztika
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Object result = js.executeScript("""
            const result = [];

            result.push('BODY DIV COUNT: ' + document.querySelectorAll('body > div').length);

            document.querySelectorAll('body > div').forEach((el, index) => {
                result.push(
                    'DIV ' + index +
                    ' id=' + (el.id || '') +
                    ' class=' + (el.className || '') +
                    ' shadowRoot=' + (el.shadowRoot !== null)
                );
            });

            result.push(
                'DIRECT TOOLBAR: ' +
                (document.querySelector('#ft-floating-toolbar') !== null)
            );

            return result;
        """);
        
        Object privacySearch = js.executeScript("""
        	    const result = [];

        	    const html = document.documentElement.outerHTML;

        	    const terms = [
        	        'ft-floating-toolbar',
        	        'privacy',
        	        'Privacy',
        	        'legal',
        	        'cookie'
        	    ];

        	    for (const term of terms) {
        	        result.push(term + ': ' + html.includes(term));
        	    }

        	    return result;
        	""");

        	System.out.println("=== PRIVACY TEXT SEARCH ===");
        	System.out.println(privacySearch);

        System.out.println("=== PRIVACY DOM DIAGNOSTICS ===");
        System.out.println(result);

    
        homePage.clickPrivacyIcon();

        assertTrue(homePage.isPrivacySettingsDisplayed());
    }
}