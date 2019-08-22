package za.co.absa.pages.adduser;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import za.co.absa.dataProviders.Wait;
import za.co.absa.managers.FileReaderManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddUser extends PageObject {

    @Managed
    private WebDriver driver;

    public AddUser(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//button[contains(text(), 'Add User')]")
    private WebElementFacade addUserButton;

    @FindBy(xpath = "/html/body/div[3]/div[2]")
    private WebElementFacade addUserForm;

    @FindBy(name = "FirstName")
    private WebElementFacade firstNameTextField;

    @FindBy(name = "LastName")
    private WebElementFacade lastNameTextField;

    @FindBy(name = "UserName")
    private WebElementFacade userNameTextField;

    @FindBy(name = "Password")
    private WebElementFacade passwordTextField;

    @FindBy(xpath = "/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[1]")
    private WebElementFacade companyAAARadio;

    @FindBy(xpath = "/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[2]")
    private WebElementFacade companyBBBRadio;

    @FindBy(name = "RoleId")
    private WebElementFacade roleDropdwmOption;

    @FindBy(name = "Email")
    private WebElementFacade emailTextField;

    @FindBy(name = "Mobilephone")
    private WebElementFacade mobilePhoneTextField;

    @FindBy(name = "IsLocked")
    private WebElementFacade lockedCheckBox;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElementFacade saveButton;

    @FindBy(css = "smart-table table table-striped")
    private WebElementFacade userRecordsTB;

    //Element Highlighter
    public void highLighterBorderMethod(WebElementFacade facade) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid green'", facade);
    }

    //Navigate to the User List Table
    @Step
    public void NavigateToUserListTable() {
        //Get the driver instance. It is needed when PageObject class is extended
        driver = getDriver();
        driver.get(FileReaderManager.getInstance().getConfigFileReader().getApplicationUrl());
        if (FileReaderManager.getInstance().getConfigFileReader().getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigFileReader().getImplicitlyWait(), TimeUnit.SECONDS);
        //Set the driver instance
        setDriver(driver);
    }

    //Validate that you are on the User List Table
    @Step
    public void checkThatUserListTableIsVisible() {
        if (addUserButton.isPresent()) {
            System.out.println("User List Table is displayed");
        } else {
            Assert.assertTrue("User list table not displayed! ", addUserButton.isPresent());
        }
    }

    //Click add user option
    @Step
    public void clickAddUserButton() {
        addUserButton.waitUntilClickable().click();
    }

    @Step
    public void checkThatAddUserFormIsVisible() {
        //Switch to add user modal popup form and validate it's displayed
        Assert.assertTrue("Add User not displayed! ", driver.switchTo().activeElement().isEnabled());
    }

    // Add new user information
    @Step
    public void addUserDetails(String firstName, String lastName, String userName, String password, String customer, String role, String email, String cell) {
        firstNameTextField.sendKeys(firstName);
        lastNameTextField.sendKeys(lastName);
        userNameTextField.sendKeys(userName);
        passwordTextField.sendKeys(password);

        //Customer checkbox algorithm
        if ("Company AAA".equalsIgnoreCase(customer)) {
            companyAAARadio.click();
        } else if ("Company BBB".equalsIgnoreCase(customer)) {
            companyBBBRadio.click();
        } else {
            Assert.fail("Invalid option selected");
        }

        //Loop through the list of dropdown existing options, before selecting the role type value
        int count = 0;
        String[] exp = {role};

        Select values = new Select(roleDropdwmOption);
        List<WebElement> options = values.getOptions();
        for (WebElement we : options) {
            for (int i = 0; i < exp.length; i++) {
                if (we.getText().equalsIgnoreCase(exp[i])) {
                    count++;
                }
            }
        }
        if (count == exp.length) {
            values.selectByVisibleText(role);
        } else {
            System.out.println(role + "Invalid option selected");
        }

        emailTextField.sendKeys(email);
        mobilePhoneTextField.sendKeys(cell);

        //Select the hidden Locked check box opttion
        lockedCheckBox.click();
    }

    @Step
    public void saveUserDetails(){
        //Save user details
        highLighterBorderMethod(saveButton);
        saveButton.click();
        Wait.untilPageLoadComplete(driver);
    }

    @Step
    public void checkThatTheUserIsAdded(String userName){
        WebElementFacade dataTB = userRecordsTB;
        List<WebElementFacade> lstRows = dataTB.find(By.tagName("tbody")).thenFindAll(By.tagName("tr"));

        for (WebElementFacade eleRow : lstRows) {

            List<WebElementFacade> lstColumns = eleRow.thenFindAll(By.tagName("td"));

            for (WebElementFacade eleColumns : lstColumns) {
                if (eleColumns.getText().equalsIgnoreCase(userName)){
                    String username = eleColumns.getText();
                    System.out.println(username);
                }
                else {
                    Assert.assertTrue(userName, eleColumns.isPresent());

                    break;
                }
            }
        }

    }
}
