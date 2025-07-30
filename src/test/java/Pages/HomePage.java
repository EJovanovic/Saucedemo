package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class HomePage extends BaseTest {


    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".app_logo")
    public WebElement title;

    @FindBy(css = ".shopping_cart_link")
    public WebElement shoppingCartLink;

    @FindBy(id = "shopping_cart_container")
    public WebElement cartButton;
    @FindBy(id = "react-burger-menu-btn")
    public WebElement burgerMenuButton;

    @FindBy(css = ".bm-item.menu-item")
    public List<WebElement> burgerMenuOptions;

    @FindBy(className = "product_sort_container")
    public WebElement sortingButton;

    @FindBy(className = "inventory_item")
    public List<WebElement> products;

    @FindBy(className = "inventory_item_img")
    public WebElement productPicture;

    @FindBy(className = "inventory_item_price")
    public WebElement productPrice;

    @FindBy(className = "inventory_item_desc")
    public WebElement productDescription;

    @FindBy(className = "inventory_item_name")
    public List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> productPrices;


    @FindBy(linkText = "Twitter")
    public WebElement twitterLogo;

    @FindBy(linkText = "Facebook")
    public WebElement fbLogo;

    @FindBy(linkText = "LinkedIn")
    public WebElement lnLogo;



    public void clickOnBurgerMenuLinks(String section) {
        burgerMenuButton.click();
        for (int i = 0; i < burgerMenuOptions.size(); i++) {
            if (burgerMenuOptions.get(i).getText().equals(section)) {
                burgerMenuOptions.get(i).click();
                break;
            }
        }

    }

    public void clickOnCartButton() {
        cartButton.click();
    }
//I used for-each loop to iterate through all products on homepage and find the product with given name
    public void clickOnProduct(String productName) {
        for (WebElement product : productNames) {
            if (product.getText().equalsIgnoreCase(productName)) {
                product.click();
                break;
            }
        }
    }
    //I used this method to add a product to the cart based on its name.
//  It iterates through the list of products, finds the one matching the given product name,
// and clicks  "Add to cart" button if it's available.
    public void addProductToCart(String productName) {
        for (WebElement product : products) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                WebElement button = product.findElement(By.cssSelector(".btn_inventory"));
                if (button.getText().equals("Add to cart")) {
                    button.click();
                    break;

                }
            }
        }
    }
    public void removeProductFromCart(String productName) {
        for (WebElement product : products) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                WebElement button = product.findElement(By.cssSelector(".btn_inventory"));
                if (button.getText().equals("Remove")) {
                    button.click();
                    break;
                }
            }
        }
    }

    public void addAllProductsToTheCart() {
        for (WebElement product : products) {
            WebElement button = product.findElement(By.cssSelector(".btn_inventory"));
            if (button.getText().equals("Add to cart")) {
                button.click();

            }

        }
    }

    public void removeAllProductsFromTheCart() {
        for (WebElement product : products) {
            WebElement button = product.findElement(By.cssSelector(".btn_inventory"));
            if (button.getText().equals("Remove")) {
                button.click();
            }

        }

    }

    public int allProducts() {
        return products.size();
    }

    public boolean allProductsNamesAndImagesAreDisplayed() {
        for (WebElement product : products) {
            WebElement image = product.findElement(By.className("inventory_item_img"));
            WebElement name = product.findElement(By.className("inventory_item_name"));
            if (!image.isDisplayed() || !name.isDisplayed() || name.getText().isEmpty()) {
                return false;
            }
        }
        return true;

    }

    public boolean allProductsPriceAndDescriptionAreDisplayed() {
        for (WebElement product : products) {
            WebElement price = product.findElement(By.className("inventory_item_price"));
            WebElement description = product.findElement(By.className("inventory_item_desc"));
            if (!price.isDisplayed() || !description.isDisplayed() || description.getText().isEmpty()) {
                return false;
            }
        }
        return true;

    }
    // Iterates through the list of product names and compares each item with the next one.
// If any item comes before the next one alphabetically, the list is not sorted descending, and the method returns false.
    public boolean areProductsSortedByNameDesc() {
        for (int i = 0; i < productNames.size() - 1; i++) {
            String currentName = productNames.get(i).getText().trim();
            String nextName = productNames.get(i + 1).getText().trim();
            if (currentName.compareToIgnoreCase(nextName) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean areProductsSortedByNameAsc() {
        for (int i = 0; i < productNames.size() - 1; i++) {
            String currentName = productNames.get(i).getText().trim();
            String nextName = productNames.get(i + 1).getText().trim();
            if (currentName.compareToIgnoreCase(nextName) > 0) {// if the current name comes after the next name alphabetically, then it’s not ascending
                return false;
            }
        }
        return true;
    }

    public void selectSortOption(String visibleText) {
        Select select = new Select(sortingButton);
        select.selectByVisibleText(visibleText);
    }

    public boolean areProductsSortedByPriceHighToLow() {
        for (int i = 0; i < productPrices.size() - 1; i++) {
            double currentPrice = Double.parseDouble(productPrices.get(i).getText().replace("$", "").trim());//I am converting String to double so I can compare it
            double nextPrice = Double.parseDouble(productPrices.get(i + 1).getText().replace("$", "").trim());
            if (currentPrice < nextPrice) {
                return false;
            }
        }
        return true;
    }

    public boolean areProductsSortedByPriceLowToHigh() {
        for (int i = 0; i < productPrices.size() - 1; i++) {
            double currentPrice = Double.parseDouble(productPrices.get(i).getText().replace("$", "").trim());
            double nextPrice = Double.parseDouble(productPrices.get(i+1).getText().replace("$", "").trim());
            if (currentPrice > nextPrice) {
                return false;
            }
        }
        return true;
    }
}
    

   
   

