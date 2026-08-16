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

	    options.addArguments("--incognito");

	    if (System.getProperty("CI") != null) {
	        options.addArguments("--headless=new");
	        options.addArguments("--window-size=1920,1080");
	        options.addArguments("--force-device-scale-factor=1");
	    }

	    ChromeDriver chromeDriver = new ChromeDriver(options);

	    if (System.getProperty("CI") != null) {
	        chromeDriver.executeCdpCommand(
	            "Emulation.setDeviceMetricsOverride",
	            Map.of(
	                "width", 1920,
	                "height", 1080,
	                "deviceScaleFactor", 1,
	                "mobile", false
	            )
	        );
	    } else {
	        chromeDriver.manage().window().maximize();
	    }
	    
	    
	    chromeDriver.executeCdpCommand("Network.enable", Map.of());

	    chromeDriver.executeCdpCommand(
	        "Network.setBlockedURLs",
	        Map.of("urls", List.of(
	            "*doubleclick.net/*",
	            "*googlesyndication.com/*",
	            "*googleadservices.com/*",
	            "*adservice.google.com/*"
	        ))
	    );

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