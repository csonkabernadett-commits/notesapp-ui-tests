package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.RepeatedTest;
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
		options.addArguments("--start-maximized");

		// No ad blocking: this test uses the normal page environment.
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// @RepeatedTest(10)
	@Test
	void privacySettingsTest() {

		HomePage homePage = new HomePage(driver);

		homePage.open();
		homePage.scrollToPrivacy();
		homePage.clickPrivacyIcon();

		assertTrue(homePage.isPrivacySettingsDisplayed());
	}

}
