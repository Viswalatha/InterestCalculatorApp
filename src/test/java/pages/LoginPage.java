package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    public void enterLoginDetails() {
        enterInputValue(getDriver(), By.id("UserName"), "srivellamk@gmail.com", 10);
        enterInputValue(getDriver(), By.id("Password"), "Grud@431", 10);
        clickOn(getDriver(), By.id("login-submit"), 10);
    }

}
