package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class LogoutTest extends BaseTest {

    @Test
    void logout() {

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

        // Act

        notesPage.logout();

        // Assert

        assertEquals(
                "Welcome to Notes App",
                homePage.getWelcomeTitle()
        );
    }
}