package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutOverviewPage extends BaseTest {
    public CheckoutOverviewPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".summary_value_label")
    public List<WebElement> summaryLabels;

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(id = "continue")
    public WebElement continueButton;

    @FindBy(id = "cancel")
    public WebElement cancelButton;

    @FindBy(xpath = "/html/body/div/div/div/div[2]/div/div[2]/div[6]")
    public WebElement itemTotal;

    public String getPaymentInformation() {
        return summaryLabels.get(0).getText();
    }

    public String getShippingInformation() {
        return summaryLabels.get(1).getText();
    }
    public String getItemPrice() {
        return itemTotal.getText();
    }

    public void clickOnFinishButton() {
        finishButton.click();
    }
    public void clickOnCancelButton(){
        cancelButton.click();



    }

}
