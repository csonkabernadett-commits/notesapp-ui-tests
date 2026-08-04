package hu.betti.automation.nopcommerce.tests;

import org.junit.jupiter.api.Test;

import hu.betti.automation.nopcommerce.base.BaseTest;

public class OpenHomePageTest extends BaseTest {

	@Test
	void openHomePage() {
		
		driver.get("https://demo.nopcommerce.com");
		
	}
	
}
