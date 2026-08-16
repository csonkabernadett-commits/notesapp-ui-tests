package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class RegisterTest extends BaseTest {

	@Test
	void successfulRegistration() {

		// ==================== Arrange ====================

		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();

		HomePage homePage = new HomePage(driver);
		homePage.open();

		RegisterPage registerPage = homePage.clickCreateAccount();

		// ==================== Act ====================

		registerPage.register(email, name, password);

		// ==================== Assert ====================

		assertEquals("User account created successfully", registerPage.getSuccessMessage());
	}
}