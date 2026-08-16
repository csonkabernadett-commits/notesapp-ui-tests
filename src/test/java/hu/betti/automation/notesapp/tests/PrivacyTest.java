package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.RepeatedTest;
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
		//options.addArguments("--start-maximized");

		// No ad blocking: this test uses the normal page environment.
		driver = new ChromeDriver(options);

		//CI környezetben headless mód 
		 options.addArguments("--headless=new");
		  
		 // Full HD felbontás 
		 options.addArguments("--window-size=1920,1080");
		 
		 // CI környezethez 
		 options.addArguments("--disable-gpu");
		 options.addArguments("--no-sandbox");
		 options.addArguments("--disable-dev-shm-usage");
		 

		ChromeDriver chromeDriver = new ChromeDriver(options);

		driver = chromeDriver;
	}
	
	JavascriptExecutor js = (JavascriptExecutor) driver;

	Object result = js.executeScript("""
	    const result = [];

	    function inspect(root, level) {
	        const elements = root.querySelectorAll('*');

	        for (const el of elements) {
	            if (el.id === 'ft-floating-toolbar') {
	                result.push(
	                    'FOUND toolbar: ' +
	                    el.tagName +
	                    ' id=' +
	                    el.id +
	                    ' level=' +
	                    level
	                );
	            }

	            if (el.shadowRoot) {
	                result.push(
	                    'SHADOW ROOT: ' +
	                    el.tagName +
	                    ' id=' +
	                    (el.id || '')
	                );

	                inspect(el.shadowRoot, level + 1);
	            }
	        }
	    }

	    result.push(
	        'DIRECT: ' +
	        (document.querySelector('#ft-floating-toolbar') !== null)
	    );

	    inspect(document, 0);

	    return result;
	""");

	System.out.println("=== PRIVACY DOM DIAGNOSTICS ===");
	System.out.println(result);

	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	//@RepeatedTest(10)
	@Test
	void privacySettingsTest() {

		HomePage homePage = new HomePage(driver);

		homePage.open();
		homePage.scrollToPrivacy();
		homePage.clickPrivacyIcon();

		assertTrue(homePage.isPrivacySettingsDisplayed());
	}
}
