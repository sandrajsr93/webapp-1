package com.trackorjargh;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class TestE2EFront extends TestE2E{
    ChromeDriver driver;

    public TestE2EFront(ChromeDriver driver) {
        this.driver = driver;
    }
    
    @Test
    public void checkCreateList() {
    	driver.get("http://localhost:4200/login");
    	this.loginUser(driver, "oscar", "1234");
    	
    	driver.get("http://localhost:4200/perfil");
    	
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Select menu my lists
    	WebElement menuProfileBar = driver.findElement(By.id("v-pills-tab"));
    	List<WebElement> elementsProfileBar = menuProfileBar.findElements(By.tagName("a"));
    	WebElement myLists = elementsProfileBar.get(1);
    	
    	//Move to my lists
    	myLists.click();
    	this.logout(driver);
    }
}
