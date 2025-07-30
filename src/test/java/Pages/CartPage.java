package Pages;

import Base.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;

public class CartPage extends BaseTest {

    public CartPage() {
        PageFactory.initElements(driver, this);
    }//I am using page factory to locate elements on page

    @FindBy(className = "shopping_cart_badge")
    public WebElement cartBadge;

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(className = "inventory_item")
    public List<WebElement> products;

    @FindBy(className = "inventory_item_name")
    public WebElement productName;

    @FindBy(className = "inventory_item_desc")
    public WebElement productDescription;

    @FindBy(css = ".cart_item")
    public List<WebElement> cartProducts;


    public void clickOnCheckoutButton() {
        checkoutButton.click();
    }
    public void clickOnContinueButton(){
        continueShoppingButton.click();
    }

    public boolean isCartEmpty() {//if cart badge is not present, then the cart is empty
        try {
            return !cartBadge.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

//Finding product by name in the cart
    public boolean isProductInCart(String productName) {
        for (WebElement product : cartProducts) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                return true;
            }
        }
        return false;
    }

//total of products on page
    public int getNumberOfProductsInTheCart() {
        return cartProducts.size();

    }
    //I used for-each loop to iterate through all products in the cart and remove the one that matches the given product name.
    // It should find the product by its name and then click the "Remove" button if it's present.

    public void removeProductByName(String productName) {
        for (WebElement product : cartProducts) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if (productName.equals(name)) {
                WebElement button=driver.findElement(By.cssSelector(".cart_button"));
                if(button.getText().contains("Remove")){
                    button.click();
                    break;
                }
            }

        }
    }
    public int allProductsInTheCart(){
        return cartProducts.size();
    }

    public void removeAllProductsFromTheCart() {
        for (WebElement product : cartProducts) {
            WebElement button = product.findElement(By.cssSelector(".cart_button"));
            if (button.getText().equals("Remove")) {
                button.click();
            }
        }
    }

}













