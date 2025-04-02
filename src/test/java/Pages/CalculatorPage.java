package Pages;


import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@DefaultUrl("https://ten10techtest-dnd6bgfzcqdggver.uksouth-01.azurewebsites.net/Account/Login")
public class CalculatorPage extends PageObject {

    public void enterInputValue(WebDriver driver, By locator, String text, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    public void clickOn(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void clickUsingJavaScript(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } else {
            System.out.println("Element not found: " + locator);
        }
    }

    public void enterLoginDetails() {
        enterInputValue(getDriver(), By.id("UserName"), "srivellamk@gmail.com", 10);
        enterInputValue(getDriver(), By.id("Password"), "Grud@431", 10);
        clickOn(getDriver(), By.id("login-submit"), 10);
    }

    public void calculateInterest() {
        selectRandomPrincipalAmount();
        selectRandomInterestRate();
        Actions actions = new Actions(getDriver());
        actions.moveByOffset(10, 10).click().perform();
        selectDurationForInterest();
        clickUsingJavaScript(getDriver(), By.id("gridCheck1"), 10);
        clickUsingJavaScript(getDriver(), By.className("btn-primary"), 10);
    }

    public void selectRandomInterestRate() {
        clickOn(getDriver(), By.id("dropdownMenuButton"), 10);
        List<WebElement> checkboxes = getDriver().findElements(By.xpath("//input[@type='checkbox']"));

        if (checkboxes.isEmpty()) {
            System.out.println("No checkboxes found!");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(checkboxes.size());

            WebElement randomCheckbox = checkboxes.get(randomIndex);
            if (!randomCheckbox.isSelected()) {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", randomCheckbox);
                randomCheckbox.click();
            }

        }
    }

    public void selectRandomPrincipalAmount() {

        WebElement rangeSlider = getDriver().findElement(By.id("customRange1"));
        Random random = new Random();
        int randomValue = random.nextInt(151) * 100;
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].value=arguments[1]; arguments[0].dispatchEvent(new Event('input'))", rangeSlider, randomValue);
        System.out.println("Randomly selected range value: " + randomValue);
    }

    public void selectDurationForInterest() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement divElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("durationList")));
        List<WebElement> links = divElement.findElements(By.tagName("a"));

        if (links.isEmpty()) {
            System.out.println("No links found inside the div!");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(links.size());
            WebElement randomLink = links.get(randomIndex);
            System.out.println("Randomly selected link text: " + randomLink.getText());

            try {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", randomLink);
                wait.until(ExpectedConditions.elementToBeClickable(randomLink));
                randomLink.click();
            } catch (Exception e) {
                System.out.println("Click failed, trying JavaScript click...");
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", randomLink);
            }
        }

    }

    public void verifyInterestAmount() {
        WebElement interestAmountHeader = getDriver().findElement(By.id("interestAmount"));
        String actualInterestAmountText = interestAmountHeader.getText().trim();
        actualInterestAmountText = actualInterestAmountText.replaceAll("[^0-9.]", "");
        String decimalPattern = "^\\d+\\.\\d{2}$";
        Assert.assertTrue("Interest does not have exactly 2 decimal places!", actualInterestAmountText.matches(decimalPattern));
        double interestValue = Double.parseDouble(actualInterestAmountText);
        String formattedInterest = String.format("%.2f", interestValue);
        Assert.assertEquals("Interest format is incorrect!", formattedInterest, actualInterestAmountText);
        WebElement totalAmountHeader = getDriver().findElement(By.id("totalAmount"));
        String actualTotalAmountText = totalAmountHeader.getText().trim();
        actualTotalAmountText = actualTotalAmountText.replaceAll("[^0-9.]", "");
        String decimalPattern1 = "^\\d+\\.\\d{2}$";
        Assert.assertTrue("Interest does not have exactly 2 decimal places!", actualInterestAmountText.matches(decimalPattern1));
        double interestValue1 = Double.parseDouble(actualTotalAmountText);
        String formattedInterest1 = String.format("%.2f", interestValue1);
        Assert.assertEquals("Interest format is incorrect!", formattedInterest1, actualTotalAmountText);

    }

    public void calculateInterestWithAMissingField(String field) {
        selectRandomPrincipalAmount();
        if (field.equalsIgnoreCase("interest rate")) {
            clickUsingJavaScript(getDriver(), By.id("gridCheck1"), 10);
        } else if (field.equalsIgnoreCase("consent")) {
            selectRandomInterestRate();
            Actions actions = new Actions(getDriver());
            actions.moveByOffset(10, 10).click().perform();
        }
        selectDurationForInterest();
        clickUsingJavaScript(getDriver(), By.className("btn-primary"), 10);
    }

    public void verifyAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert();
        String actualAlertMessage = alert.getText();
        alert.accept();
        System.out.println("Alert accepted successfully.");
        Assert.assertEquals("Alert message does not match!", "Please fill in all fields.", actualAlertMessage);
    }

}

