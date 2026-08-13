package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import hu.betti.automation.notesapp.base.BasePage;
import org.openqa.selenium.JavascriptExecutor;

public class HomePage extends BasePage {

	private static final String URL = "https://practice.expandtesting.com/notes/app/";
	private final By loginButton = By.linkText("Login");
	private final By createAccountButton = By.cssSelector("[data-testid='open-register-view']");
	private final By welcomeTitle = By.tagName("h1");
	private final By privacySettingsButton =
	        By.cssSelector("#ft-floating-toolbar .ft-reg-message-button");
	
	// Constructor
	public HomePage(WebDriver driver) {
		super(driver);
	}

	
    public void open() {
        driver.get(URL);
    }

    public LoginPage clickLogin() {
        click(loginButton);
        return new LoginPage(driver);
    }

    public RegisterPage clickCreateAccount() {
        click(createAccountButton);
        return new RegisterPage(driver);
    }

    public String getWelcomeTitle() {
        return getText(welcomeTitle);
    }

    public void clickPrivacySettings() {
        click(privacySettingsButton);
    }
    
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }
}



