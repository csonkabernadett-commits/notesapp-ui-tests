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

		    // CI környezetben headless mód
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


	@AfterEach
	void tearDown() {

	    if (driver != null) {

	        try {
	            if (driver instanceof org.openqa.selenium.TakesScreenshot) {

	                java.io.File screenshot =
	                        ((org.openqa.selenium.TakesScreenshot) driver)
	                                .getScreenshotAs(
	                                        org.openqa.selenium.OutputType.FILE);

	                java.nio.file.Path screenshotPath =
	                        java.nio.file.Paths.get(
	                                "failure-screenshots",
	                                "PrivacyTest.png");

	                java.nio.file.Files.createDirectories(
	                        screenshotPath.getParent());

	                java.nio.file.Files.copy(
	                        screenshot.toPath(),
	                        screenshotPath,
	                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	            }

	            String html = driver.getPageSource();

	            java.nio.file.Path htmlPath =
	                    java.nio.file.Paths.get(
	                            "failure-screenshots",
	                            "PrivacyTest.html");

	            java.nio.file.Files.writeString(
	                    htmlPath,
	                    html,
	                    java.nio.charset.StandardCharsets.UTF_8);

	        } catch (Exception e) {
	            System.out.println(
	                    "Could not save PrivacyTest diagnostics: "
	                            + e.getMessage());
	        }

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
