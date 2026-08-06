package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class LoginTest extends BaseTest {
	
	@Test
	void successfulLogin() {
		// Arrange - Tesztadatok létrehozása
		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();
		
		HomePage homePage = new HomePage(driver);
		homePage.open();
		
		// Act - 
		// 1. Regisztráció
		RegisterPage registerPage = homePage.clickCreateAccount();
		registerPage.register(email, name, password);
		
		// 2. Bejelentkezés
		LoginPage loginPage = registerPage.clickLogin();
		
		NotesPage notesPage = loginPage.login(email, password);
		
		// Assert
		assertTrue(notesPage.isLoaded());
	}
}
