package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.HomePage;
import Pages.IndividualProductPage;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePageTest extends BaseTest {


    @BeforeMethod
    public void pageSetUp() {
        ChromeOptions options = new ChromeOptions();// this option will help me with pop up windows
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetectionEnabled");
        options.addArguments("--password-store=basic");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        loginPage = new LoginPage();
        homePage = new HomePage();
        cartPage = new CartPage();
        individualProductPage = new IndividualProductPage();

        driver.navigate().to("https://www.saucedemo.com/");
        loginPage.loginAs("standard_user", "secret_sauce");

    }

     @AfterMethod
    public void tearDown() {
         driver.quit();
     }
//Clicking on options from BurgerMenu
    @Test
    public void userCanClickOnOptionAboutFromBurgerMenu() {
        homePage.clickOnBurgerMenuLinks("About");
        wait.until(ExpectedConditions.urlToBe("https://saucelabs.com/"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/");

    }

    @Test
    public void userCanLogoutByClickingLogoutFromBurgerMenu() {
        homePage.clickOnBurgerMenuLinks("Logout");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertTrue(loginPage.loginButton.isDisplayed());

   // I verified visibility of product details on page
    }
    @Test
    public void verifyThatAllProductsAreVisibleOnHomepage(){
        Assert.assertEquals(homePage.allProducts(),6);
    }

    @Test
    public void verifyThatAllProductsPicturesAndNamesAreVisibleOnHomepage(){
        Assert.assertTrue(homePage.allProductsNamesAndImagesAreDisplayed());

    }
    @Test
    public void verifyThatAllProductHavePriceAndDescriptionOnHomepage() {
        Assert.assertTrue(homePage.allProductsPriceAndDescriptionAreDisplayed());

    }
//Opening product card
    @Test
    public void userCanOpenProductCard() {
        homePage.clickOnProduct("Sauce Labs Bike Light");
        wait.until(ExpectedConditions.urlContains("inventory-item.html"));
        Assert.assertTrue(individualProductPage.productPrice.isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=0");
    }
    //Adding product
        @Test
    public void userCanAddProductToTheCartFromHomepage() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
        }
        //Removing product
        @Test
    public void userCanRemoveProductFromTheCartFromHomepage() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));//I had to confirm that the same product that was added is in the cart
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
            driver.navigate().back();
            homePage.removeProductFromCart("Sauce Labs Bike Light");
            Assert.assertTrue(cartPage.isCartEmpty());
            Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Bike Light"));

        }
        //Adding multiple products
        @Test
    public void userCanAddMultipleProductsByNameToTheCart() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.addProductToCart("Sauce Labs Bolt T-Shirt");
            homePage.addProductToCart("Sauce Labs Fleece Jacket");
            homePage.clickOnCartButton();
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 3);
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Fleece Jacket"));
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bolt T-Shirt"));
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));
        }
        // Removing a product by clicking on burger menu option
        @Test
    public void userCanRemoveProductsFromTheCartByResetAppState() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.addProductToCart("Sauce Labs Bolt T-Shirt");
            homePage.addProductToCart("Sauce Labs Fleece Jacket");
            homePage.clickOnCartButton();
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 3);
            homePage.clickOnBurgerMenuLinks("Reset App State");
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
        }
        @Test
    public void userCanRemoveSomeProductsAfterAdding() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.addProductToCart("Sauce Labs Bolt T-Shirt");
            homePage.addProductToCart("Sauce Labs Fleece Jacket");
            homePage.clickOnCartButton();
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 3);
            cartPage.removeProductByName("Sauce Labs Bike Light");
            cartPage.removeProductByName("Sauce Labs Bolt T-Shirt");
            Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Fleece Jacket"));//It's best to assert that the same product that was added is still in the cart
        }
        //Adding all products

        @Test
    public void userCanAddAllProductsToTheCart() {
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addAllProductsToTheCart();
            homePage.clickOnCartButton();
            Assert.assertEquals(cartPage.allProductsInTheCart(), 6);//I confirmed in the beginning that 6 is the total of products on the page
        }
        //Removing all products
        @Test
    public void userCanRemoveAllProductsAfterAdding(){
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isCartEmpty());
            driver.navigate().back();
            homePage.addAllProductsToTheCart();
            homePage.clickOnCartButton();
            Assert.assertEquals(cartPage.allProductsInTheCart(), 6);
            driver.navigate().back();
            homePage.removeAllProductsFromTheCart();
            Assert.assertTrue(cartPage.isCartEmpty());
            Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Bike Light"));//I am checking if some of the product names from the homepage is in the cart
    }
    //Sorting from Z to A
    @Test
    public void userCanSortProductsByNameDesc() {
        homePage.selectSortOption("Name (Z to A)");
        Assert.assertTrue(homePage.areProductsSortedByNameDesc());
    }
//Sorting from A to Z
    @Test
    public void userCanSortProductsByNameAsc(){
        homePage.selectSortOption("Name (A to Z)");
        Assert.assertTrue(homePage.areProductsSortedByNameAsc());
    }
    //Sorting price from high to low
    @Test
    public void userCanSortProductsByPriceHighToLow() {
        homePage.selectSortOption("Price (high to low)");
        Assert.assertTrue(homePage.areProductsSortedByPriceHighToLow());
    }
    //Sorting price from low to high
    @Test
    public void userCanSortProductsByPriceLowToHigh(){
        homePage.selectSortOption("Price (low to high)");
        Assert.assertTrue(homePage.areProductsSortedByPriceLowToHigh());
    }


    }

