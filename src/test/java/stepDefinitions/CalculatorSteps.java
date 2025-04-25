package stepDefinitions;

import util.ConfigReader;
import pages.CalculatorPage;
import pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;


public class CalculatorSteps {

    @Steps
    LoginPage loginPage;
    @Steps
    CalculatorPage calculatorPage;

    @Given("the user is on the login page")
    public void user_is_on_login_page() {
        String loginUrl = ConfigReader.getProperty("defaultURL");
        System.out.println("Opening URL: " + loginUrl);
        calculatorPage.openAt(loginUrl);
    }

    @When("the user successfully logs in with a valid username and password")
    public void userEnterValidUsernameAndPassword() {
        loginPage.enterLoginDetails();
    }

    @When("the user selects all required fields")
    public void userSelectsTheAnd( ) {
        calculatorPage.calculateInterest();
    }

    @Then("user should see the expectedInterestAmount and totalAmount")
    public void userShouldSeeTheAnd() {
    calculatorPage.verifyInterestAmount();
    }

    @Then("an alert message should be displayed with the error")
    public void userShouldSeeAlertMessage() {
        calculatorPage.verifyAlert();
    }

    @When("the user leaves a {string} missing")
    public void theUserLeavesAMissing(String field) {
        calculatorPage.calculateInterestWithAMissingField(field);
    }

    @And("the user selects specific fields")
    public void theUserSelectsSpecificFields() {
       calculatorPage.calculateInterestForFixedFieldValues();
    }

    @Then("user should see expected overall calculation")
    public void userShouldSeeExpectedOverallCalculation() {
        calculatorPage.verifyInterestAmountForFixedFieldValues();
    }
}
