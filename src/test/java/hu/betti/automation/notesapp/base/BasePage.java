package hu.betti.automation.notesapp.base;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

	    for (int i = 0; i < 3; i++) {
	        try {
	            WebElement element = waitClickable(locator);
	            
	            scrollIntoView(element);
	            
	            element.click();
	            return;

	        } catch (StaleElementReferenceException e) {
	            // DOM újrarenderelődött, újra megkeressük az elemet
	        }
	    }

	    throw new StaleElementReferenceException(
	            "Element remained stale after 3 attempts: " + locator);
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

	// Scrolls the element into the center of the viewport
	protected void scrollIntoView(WebElement element) {
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block: 'center'});",
	            element);
	}
	
	//CreateManyNotesTest - UI-renderelési probléma miatt
	protected void waitForNumberOfElements(By locator, int number) {
	    wait.until(
	        ExpectedConditions.numberOfElementsToBe(locator, number)
	    );
	}
	
	protected void clear(By locator) {
	    waitVisible(locator).clear();
	}
	
	public void scrollToBottom() {
	    new Actions(driver)
	            .sendKeys(Keys.END)
	            .perform();
	}
}
	
