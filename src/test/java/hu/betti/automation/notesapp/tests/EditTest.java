package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.AddNoteModal;
import hu.betti.automation.notesapp.pages.EditNoteModal;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class EditTest extends BaseTest {

	@Test
	void editNote() {

		// ==================== Arrange ====================

		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();

		HomePage homePage = new HomePage(driver);
		homePage.open();

		RegisterPage registerPage = homePage.clickCreateAccount();

		registerPage.register(email, name, password);

		LoginPage loginPage = registerPage.clickLogin();

		NotesPage notesPage = loginPage.login(email, password);

		AddNoteModal addNoteModal = notesPage.clickAddNote();

		String title = "Note to edit";

		addNoteModal.selectCategory("Work");
		addNoteModal.enterTitle(title);
		addNoteModal.enterDescription("Original description");
		addNoteModal.clickCreate();

		notesPage.waitForNoteCount(1);

		// ==================== Act ====================

		EditNoteModal editNoteModal = notesPage.editNote(title);

		String editedTitle = "Edited note";
		String editedDescription = "Edited description";

		editNoteModal.enterTitle(editedTitle);
		editNoteModal.enterDescription(editedDescription);
		editNoteModal.clickSave();

		// ==================== Assert ====================

		assertTrue(notesPage.isNoteDisplayed(editedTitle));
	}
}