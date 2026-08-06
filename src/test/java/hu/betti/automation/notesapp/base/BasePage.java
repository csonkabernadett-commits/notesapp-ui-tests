package hu.betti.automation.notesapp.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	//Fields
	protected WebDriver driver;
	protected WebDriverWait wait;

	//Constructor
	public BasePage(WebDriver driver) {
		this.driver = driver;
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	//Find
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}
	
	//Wait
	protected WebElement waitVisible(By locator) {
	    return wait.until(
	        ExpectedConditions.visibilityOfElementLocated(locator));  
	}
	
	protected WebElement waitClickable(By locator) {
	    return wait.until(
	        ExpectedConditions.elementToBeClickable(locator));
	}
	
	//Interaction
	protected void click(By locator) {
	    waitClickable(locator).click();
	}

	protected void type(By locator, String text) {
		waitVisible(locator).sendKeys(text);
	}
	
	protected String getText(By locator) {
	    return waitVisible(locator).getText();
	}

	protected String getTitle() {
	    return driver.getTitle();
	}

}
