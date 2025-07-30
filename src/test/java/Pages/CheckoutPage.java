package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CheckoutPage extends BaseTest{

    public CheckoutPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    public WebElement title;

    @FindBy(id = "first-name")
    public WebElement firstNameField;

    @FindBy(id = "last-name")
    public WebElement lastNameField;

    @FindBy(id = "postal-code")
    public WebElement postalCodeField;

    @FindBy(id = "cancel")
    public WebElement cancelButton;

    @FindBy(id = "continue")
    public WebElement continueButton;


    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(css = ".error-message-container")
    public WebElement errorMessage;





    public void clickOnContinueButton(){
        continueButton.click();
    }


    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
    }


    public void clickOnFinishButton(){
        finishButton.click();
    }


    }







