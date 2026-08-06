package hu.betti.automation.notesapp.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected WebDriver driver;

	@BeforeEach
	void setUp() {
	    WebDriverManager.chromedriver().setup();

	    ChromeOptions options = new ChromeOptions();

	    options.addArguments("--start-maximized");
	    options.addArguments("--disable-blink-features=AutomationControlled");

	    driver = new ChromeDriver(options);
	}

	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
