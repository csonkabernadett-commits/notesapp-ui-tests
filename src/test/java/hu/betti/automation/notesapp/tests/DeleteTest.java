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

public class DeleteTest extends BaseTest {

	@Test
	void deleteNote() {

	    // Arrange

	    String email = RandomDataGenerator.generateEmail();
	    String name = RandomDataGenerator.generateName();
	    String password = RandomDataGenerator.generatePassword();

	    HomePage homePage = new HomePage(driver);
	    homePage.open();

	    RegisterPage registerPage = homePage.clickCreateAccount();
	    registerPage.register(email, name, password);

	    LoginPage loginPage = registerPage.clickLogin();

	    loginPage.enterEmail(email);
	    loginPage.enterPassword(password);

	    NotesPage notesPage = loginPage.clickLogin();

	    AddNoteModal addNoteModal = notesPage.clickAddNote();

	    String title = "Note to delete";

	    addNoteModal.selectCategory("Work");
	    addNoteModal.enterTitle(title);
	    addNoteModal.enterDescription("This note will be deleted.");
	    addNoteModal.clickCreate();

	    notesPage.waitForNoteCount(1);

	    assertTrue(notesPage.isNoteDisplayed(title));

	    // Act

	    notesPage.deleteNote(title);
	    notesPage.confirmDelete();

	    // Assert

	    notesPage.waitForNoteCount(0);

	    assertEquals(0, notesPage.getNoteCount());
	}
	
	@Test
	void cancelDeleteNote() {

	    // Arrange

	    String email = RandomDataGenerator.generateEmail();
	    String name = RandomDataGenerator.generateName();
	    String password = RandomDataGenerator.generatePassword();

	    HomePage homePage = new HomePage(driver);
	    homePage.open();

	    RegisterPage registerPage = homePage.clickCreateAccount();
	    registerPage.register(email, name, password);

	    LoginPage loginPage = registerPage.clickLogin();

	    loginPage.enterEmail(email);
	    loginPage.enterPassword(password);

	    NotesPage notesPage = loginPage.clickLogin();

	    AddNoteModal addNoteModal = notesPage.clickAddNote();

	    String title = "Note to keep";

	    addNoteModal.selectCategory("Work");
	    addNoteModal.enterTitle(title);
	    addNoteModal.enterDescription("This note should not be deleted.");
	    addNoteModal.clickCreate();

	    notesPage.waitForNoteCount(1);

	    // Act

	    notesPage.deleteNote(title);
	    notesPage.cancelDelete();

	    // Assert

	    notesPage.waitForNoteCount(1);

	    assertEquals(1, notesPage.getNoteCount());
	    assertTrue(notesPage.isNoteDisplayed(title));
	}
	
	
	
}
