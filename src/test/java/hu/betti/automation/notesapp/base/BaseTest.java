package hu.betti.automation.notesapp.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import java.util.Map;

public class BaseTest {

	protected WebDriver driver;

	   @BeforeEach
	    void setUp() {

	        WebDriverManager.chromedriver().setup();

	        ChromeOptions options = new ChromeOptions();

	        options.addArguments("--start-maximized");
	        options.addArguments("--disable-blink-features=AutomationControlled");

	        ChromeDriver chromeDriver = new ChromeDriver(options);

	        // Ads blokkolása
	        chromeDriver.executeCdpCommand(
	                "Network.enable",
	                Map.of()
	        );

	        chromeDriver.executeCdpCommand(
	                "Network.setBlockedURLs",
	                Map.of(
	                        "urls",
	                        List.of(
	                                "*doubleclick.net/*",
	                                "*googlesyndication.com/*",
	                                "*googleadservices.com/*",
	                                "*adservice.google.com/*"
	                        )
	                )
	        );

	        driver = chromeDriver;
	    }


	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
 