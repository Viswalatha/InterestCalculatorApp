package stepDefinitions;

import Pages.CalculatorPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;


public class CalculatorSteps {

    @Steps
    CalculatorPage calculatorPage;

    public CalculatorSteps() {
        calculatorPage = new CalculatorPage();
    }

    @Given("the user is on the login page")
    public void user_is_on_login_page() {
        calculatorPage.openAt("https://ten10techtest-dnd6bgfzcqdggver.uksouth-01.azurewebsites.net/Account/Login");
    }

    @When("the user successfully logs in with a valid username and password")
    public void userEnterValidUsernameAndPassword() {
        calculatorPage.enterLoginDetails();
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
}
