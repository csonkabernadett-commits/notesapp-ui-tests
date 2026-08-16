package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.AddNoteModal;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class AddNoteTest extends BaseTest {

	@Test
	void createNewNote() {

		// ==================== Arrange ====================

		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();
		String title = RandomDataGenerator.generateNoteTitle();
		String description = "This note was created by Selenium.";

		HomePage homePage = new HomePage(driver);
		homePage.open();

		RegisterPage registerPage = homePage.clickCreateAccount();

		registerPage.register(email, name, password);

		assertTrue(registerPage.getSuccessMessage().contains("User account created successfully"));

		// ==================== Act ====================

		LoginPage loginPage = registerPage.clickLogin();

		NotesPage notesPage = loginPage.login(email, password);

		AddNoteModal addNoteModal = notesPage.clickAddNote();

		addNoteModal.selectCategory("Work");
		addNoteModal.enterTitle(title);
		addNoteModal.enterDescription(description);

		addNoteModal.clickCreate();

		// ==================== Assert ====================

		assertTrue(notesPage.isLoaded());
		assertTrue(notesPage.isNoteDisplayed(title));

	}
}