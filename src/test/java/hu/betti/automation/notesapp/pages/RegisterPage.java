package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import hu.betti.automation.notesapp.base.BasePage;

public class RegisterPage extends BasePage {

	// Locators
	private final By emailField = By.cssSelector("[data-testid='register-email']");

	private final By nameField = By.cssSelector("[data-testid='register-name']");

	private final By passwordField = By.cssSelector("[data-testid='register-password']");

	private final By confirmPasswordField = By.cssSelector("[data-testid='register-confirm-password']");

	private final By registerButton = By.cssSelector("[data-testid='register-submit']");

	private final By loginLink = By.cssSelector("[data-testid='login-view']");

	private final By successMessage = By.cssSelector(".alert-success b");

	// Constructor
	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	public void enterEmail(String email) {
		type(emailField, email);
	}

	public void enterName(String name) {
		type(nameField, name);
	}

	public void enterPassword(String password) {
		type(passwordField, password);
	}

	public void enterConfirmPassword(String password) {
		type(confirmPasswordField, password);
	}

	public void clickRegister() {
		scrollIntoView(registerButton);
		click(registerButton);
	}

	public void register(String email, String name, String password) {
		enterEmail(email);
		enterName(name);
		enterPassword(password);
		enterConfirmPassword(password);
		clickRegister();
	}

	public String getSuccessMessage() {
		return getText(successMessage);
	}

	public LoginPage clickLogin() {
		click(loginLink);
		return new LoginPage(driver);
	}

}
