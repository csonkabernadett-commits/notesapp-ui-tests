package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.AddNoteModal;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class CreateManyNotesTest extends BaseTest {

	@Test
	void createManyNotes() {

		// ==================== Arrange ====================

		int numberOfNotes = 5;

		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();

		HomePage homePage = new HomePage(driver);
		homePage.open();

		RegisterPage registerPage = homePage.clickCreateAccount();

		registerPage.register(email, name, password);

		assertTrue(registerPage.getSuccessMessage().contains("User account created successfully"));

		LoginPage loginPage = registerPage.clickLogin();

		NotesPage notesPage = loginPage.login(email, password);

		// ==================== Act ====================

		for (int i = 1; i <= numberOfNotes; i++) {

			AddNoteModal addNoteModal = notesPage.clickAddNote();

			String title = RandomDataGenerator.generateNoteTitle();

			String description = "Test note number " + i;

			addNoteModal.selectCategory("Work");
			addNoteModal.enterTitle(title);
			addNoteModal.enterDescription(description);
			addNoteModal.clickCreate();
		}

		// ==================== Assert ====================

		assertTrue(notesPage.isLoaded());

		notesPage.waitForNoteCount(numberOfNotes);

		assertEquals(numberOfNotes, notesPage.getNoteCount());
	}
}