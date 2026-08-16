package hu.betti.automation.notesapp.base;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	// ==================== Fields ====================

	// Shared WebDriver used by all tests.
	protected WebDriver driver;

	// ==================== Setup ====================

	@BeforeEach
	void setUp() {

		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();

		// Start Chrome maximized.
		options.addArguments("--start-maximized");

		/*
		 * CI környezetben headless mód 
		 * options.addArguments("--headless=new");
		 * 
		 * // Full HD felbontás 
		 * options.addArguments("--window-size=1920,1080");
		 * 
		 * // CI környezethez 
		 * options.addArguments("--disable-gpu");
		 * options.addArguments("--no-sandbox");
		 * options.addArguments("--disable-dev-shm-usage");
		 */

		ChromeDriver chromeDriver = new ChromeDriver(options);

		// Enable network control through Chrome DevTools Protocol.
		chromeDriver.executeCdpCommand("Network.enable", Map.of());

		// Block selected advertising network requests.
		chromeDriver.executeCdpCommand("Network.setBlockedURLs", Map.of("urls", List.of("*doubleclick.net/*",
				"*googlesyndication.com/*", "*googleadservices.com/*", "*adservice.google.com/*")));

		  driver = chromeDriver;
	}

	// ==================== Teardown ====================

	@AfterEach
	void tearDown() {

		if (driver != null) {
			driver.quit();
		}
	}
}