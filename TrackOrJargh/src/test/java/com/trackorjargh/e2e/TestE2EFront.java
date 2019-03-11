package com.trackorjargh.e2e;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class TestE2EFront extends TestE2E{
	
	private Browser browser;
	
	final static Logger log = getLogger(lookup().lookupClass());
	
    public TestE2EFront(ChromeDriver driver) {
    	this.browser = new Browser(driver, "http://localhost:4200/");
    }
    
    @Test
    public void checkCreateList() {
    	//Login
    	this.browser.goToPage("login");
    	this.loginUser(this.browser, "oscar", "1234");
 
    	//Go to profile
    	this.browser.goToPage("perfil");
    	
    	//Select menu my lists
    	WebElement menuProfileBar = this.browser.getDriver().findElement(By.id("v-pills-tab"));
    	List<WebElement> elementsProfileBar = menuProfileBar.findElements(By.tagName("a"));
    	WebElement myLists = elementsProfileBar.get(1);
    	
    	//Move to my lists
    	myLists.click();
    	
    	//Add list
    	WebElement buttonAdd = this.browser.getDriver().findElement(By.cssSelector("[data-target='#showAddList']"));
    	buttonAdd.click();
    	
    	//Show add lists
    	browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("showAddList")), "No show add list", 1);
    	
    	//Create new list
    	WebElement listName = this.browser.getDriver().findElement(By.name("listName"));
    	listName.sendKeys("nueva-lista");
    	WebElement createList = this.browser.getDriver().findElement(By.name("list-submit"));
    	createList.click();
    	
    	//Check correct create
    	browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("CreatedLists")), "Failed creating list", 1);
    	List<WebElement> createLists = this.browser.getDriver().findElements(By.id("CreatedLists"));
    	String nameListCreated = createLists.get(createLists.size()-1).getText();
    	assertThat(nameListCreated).isEqualToIgnoringCase("nueva-lista");
    	log.info("List created correctly");
    	
    	//Remove list
    	browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.name("deleteList")), "Failed removing list", 1);
    	List<WebElement> deleteListsButtons = this.browser.getDriver().findElements(By.name("deleteList"));
    	deleteListsButtons.get(deleteListsButtons.size()-1).click();
    	log.info("List removed correctly");
    	
    	//Logout
    	this.logout(this.browser);
    }
}
