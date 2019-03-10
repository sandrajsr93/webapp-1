package com.trackorjargh;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class TestE2EFront extends TestE2E{
	
	private ChromeDriver driver;
	private String baseurl;
	
    public TestE2EFront(ChromeDriver driver) {
        this.driver = driver;
        this.baseurl = "http://localhost:4200";
    }
    
    @Test
    public void checkCreateList() {
    	//Login
    	driver.get(baseurl + "/login");
    	this.loginUser(driver, "oscar", "1234");
 
    	//Go to profile
    	driver.get("http://localhost:4200/perfil");
    	
    	WebDriverWait wait = new WebDriverWait(driver, 2);
    	//Select menu my lists
    	WebElement menuProfileBar = driver.findElement(By.id("v-pills-tab"));
    	List<WebElement> elementsProfileBar = menuProfileBar.findElements(By.tagName("a"));
    	WebElement myLists = elementsProfileBar.get(1);
    	
    	//Move to my lists
    	myLists.click();
    	
    	//Add list
    	WebElement buttonAdd = driver.findElement(By.cssSelector("[data-target='#showAddList']"));
    	buttonAdd.click();
    	
    	//Show add lists
    	wait.withTimeout(1, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.id("showAddList")));
    	
    	//Create new list
    	WebElement listName = driver.findElement(By.name("listName"));
    	listName.sendKeys("nueva-lista");
    	WebElement createList = driver.findElement(By.name("list-submit"));
    	createList.click();
    	
    	//Check correct create
    	wait.withTimeout(3, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.id("CreatedLists")));
    	List<WebElement> createLists = driver.findElements(By.id("CreatedLists"));
    	String nameListCreated = createLists.get(createLists.size()-1).getText();
    	assertThat(nameListCreated).isEqualToIgnoringCase("nueva-lista");
    	
    	//Remove list
    	wait.withTimeout(3, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.name("deleteList")));
    	List<WebElement> deleteListsButtons = driver.findElements(By.name("deleteList"));
    	deleteListsButtons.get(deleteListsButtons.size()-1).click();
    	
    	//Logout
    	this.logout(driver);
    }
}
