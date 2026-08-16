package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.HomePage;

public class OpenHomePageTest extends BaseTest {

	@Test
	void openHomePage() {

		HomePage homePage = new HomePage(driver);

		homePage.open();

		assertEquals("Welcome to Notes App", homePage.getWelcomeTitle());
	}
}