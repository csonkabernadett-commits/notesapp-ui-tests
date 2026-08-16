package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import hu.betti.automation.notesapp.base.BasePage;

public class LoginPage extends BasePage {

	// ==================== Fields ====================

	private final By emailField = By.cssSelector("[data-testid='login-email']");
	private final By passwordField = By.cssSelector("[data-testid='login-password']");
	private final By loginButton = By.cssSelector("[data-testid='login-submit']");
	private final By registerLink = By.cssSelector("[data-testid='register-view']");
	private final By forgotPasswordLink = By.id("forgotPasswordLink");

	// ==================== Constructor ====================

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// ==================== Interaction ====================

	public void enterEmail(String email) {
		type(emailField, email);
	}

	public void enterPassword(String password) {
		type(passwordField, password);
	}

	public NotesPage login(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		return clickLogin();
	}

	public NotesPage clickLogin() {
		click(loginButton);
		return new NotesPage(driver);
	}

	public RegisterPage clickRegister() {
		click(registerLink);
		return new RegisterPage(driver);
	}

	public ForgotPasswordPage clickForgotPassword() {
		click(forgotPasswordLink);
		return new ForgotPasswordPage(driver);
	}
}
