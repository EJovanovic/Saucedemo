package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndividualProductPage extends BaseTest {
    public IndividualProductPage(){
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "shopping_cart_container")
    public WebElement cartButton;

    @FindBy(className = "inventory_details_price")
    public WebElement productPrice;

    @FindBy(css = ".inventory_details_desc.large_size")
    public WebElement productDescription;

    @FindBy(className = "inventory_details_img")
    public WebElement productPicture;

    @FindBy(id = "add-to-cart")
    public WebElement addToCartButton;

    @FindBy(id = "remove")
    public WebElement removeButton;

    @FindBy(id = "back-to-products")
    public WebElement backToProductsArrow;

    public void clickOnAddToCart(){
        addToCartButton.click();
    }
    public void clickOnRemoveButton(){
        removeButton.click();
    }
    public void clickOnCartButton(){
        cartButton.click();
    }
    public void clickOnBackToProduct(){
        backToProductsArrow.click();
    }

    }

