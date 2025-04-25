package pages;


import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CalculatorPage extends BasePage {

    public void calculateInterest() {
        selectRandomPrincipalAmount();
        selectRandomInterestRate();
        Actions actions = new Actions(getDriver());
        actions.moveByOffset(10, 10).click().perform();
        selectDurationForInterest();
        clickUsingJavaScript(getDriver(), By.id("gridCheck1"), 10);
        clickUsingJavaScript(getDriver(), By.className("btn-primary"), 10);
    }

    public void calculateInterestForFixedFieldValues() {
        clickOn(getDriver(), By.id("dropdownMenuButton"), 10);
        clickOn(getDriver(),By.id("rate-5%"),10);
        Actions actions = new Actions(getDriver());
        actions.moveByOffset(10, 10).click().perform();
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

    public void verifyInterestAmountForFixedFieldValues() {
        WebElement interestAmountHeader = getDriver().findElement(By.id("interestAmount"));
        String actualInterestAmountText = interestAmountHeader.getText().trim();
        String numericInterestAmountText = actualInterestAmountText.replaceAll("[^0-9.,]", "");
        double actualInterestAmount = Double.parseDouble(numericInterestAmountText.replace(",", ""));
        WebElement totalAmountHeader = getDriver().findElement(By.id("totalAmount"));
        String actualTotalAmountText = totalAmountHeader.getText().trim();
        String numericTotalAmountText = actualTotalAmountText.replaceAll("[^0-9.,]", "");
        double actualTotalAmount = Double.parseDouble(numericTotalAmountText.replace(",", ""));
        double principalAmount = 7500;
        double interestRate = 5;
        int timePeriod = 1; // 1 day
        double expectedInterestAmount = (principalAmount * interestRate * timePeriod) / (365 * 100);
        double expectedTotalAmount = principalAmount + expectedInterestAmount;
        Assert.assertEquals("Interest amount is incorrect!", expectedInterestAmount, actualInterestAmount, 0.01);
        Assert.assertEquals("Total amount is incorrect!", expectedTotalAmount, actualTotalAmount, 0.01);
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

