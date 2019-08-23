package za.co.absa.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import za.co.absa.pages.adduser.AddUser;

public class AddUser_steps {

    private AddUser addUser;

    @Given("^I navigate to the user list table$")
    public void iNavigateToTheUserListTable()
    {
        addUser.NavigateToUserListTable();
    }

    @Then("^I see the user list table$")
    public void iSeeTheUserListTable()
    {
       addUser.checkThatUserListTableIsVisible();
    }

    @When("^I click add user option$")
    public void iClickAddUserOption()
    {
        addUser.clickAddUserButton();
    }

    @Then("^I see add user form$")
    public void iSeeAddUserForm()
    {
        addUser.checkThatAddUserFormIsVisible();
    }

    @And("^I enter the new user details (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*)$")
    public void iEnterTheNewUserDetails(String firstName, String lastName, String userName, String password, String customer, String role, String email, String cell)
    {
        addUser.addUserDetails(firstName, lastName, userName, password, customer, role, email, cell, true);
    }

    @When("^I click save option$")
    public void iClickSaveOption() {
       addUser.saveUserDetails();
    }

    @Then("^I see the added users in the user list table (.*)$")
    public void iSeeTheAddedUsersInTheUserListTable(String userName) {
        addUser.checkThatTheUserIsAdded(userName);
    }
}
