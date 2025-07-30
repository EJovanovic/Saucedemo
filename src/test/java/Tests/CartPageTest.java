package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CartPageTest extends BaseTest {
    @BeforeMethod
    public void pageSetUp() {
        ChromeOptions options = new ChromeOptions();
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
        checkoutPage = new CheckoutPage();
        cartPage = new CartPage();

        driver.navigate().to("https://www.saucedemo.com/");
        loginPage.loginAs("standard_user", "secret_sauce");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void cartIsEmptyAfterOpening() {
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 0);
    }

    @Test
    public void productIsVisibleInTheCartAfterAdding() {
        homePage.addProductToCart("Sauce Labs Backpack");
        wait.until(ExpectedConditions.elementToBeClickable(homePage.cartButton));
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cartPage.productDescription.getText().contains("carry.allTheThings() with the sleek, streamlined Sly Pack"));

    }

    @Test
    public void productIsNotVisibleInTheCartAfterRemoving() {
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        Assert.assertTrue(cartPage.productDescription.getText().contains("carry.allTheThings() with the sleek, streamlined Sly Pack"));
        cartPage.removeProductByName("Sauce Labs Backpack");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Backpack"));

    }

    @Test
    public void continueShoppingRedirectsToHomepage() {
        homePage.clickOnCartButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");
        cartPage.clickOnContinueButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(homePage.sortingButton.isDisplayed());

    }

    @Test
    public void checkoutRedirectToCheckoutPage() {
        homePage.clickOnCartButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");
        Assert.assertTrue(cartPage.checkoutButton.isDisplayed());
        cartPage.clickOnCheckoutButton();
        Assert.assertTrue(checkoutPage.title.getText().contains("Checkout: Your Information"));

    }

    @Test
    public void multipleProductsAreVisibleInCart() {
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());
        driver.navigate().back();
        homePage.addProductToCart("Sauce Labs Bike Light");
        homePage.addProductToCart("Sauce Labs Fleece Jacket");
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 3);


    }

    @Test
    public void productRemainInCartAfterUserLogsOutAndLogsBackIn() {
        Assert.assertTrue(cartPage.isCartEmpty());
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        homePage.clickOnBurgerMenuLinks("Logout");
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        loginPage.loginAs("standard_user", "secret_sauce");
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));

    }

    @Test
    public void userCanContinueShoppingAfterAddingFirstProduct() {
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());
        driver.navigate().back();
        homePage.addProductToCart("Sauce Labs Bike Light");
        wait.until(ExpectedConditions.elementToBeClickable(homePage.cartButton));
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
        cartPage.clickOnContinueButton();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(homePage.allProductsNamesAndImagesAreDisplayed());
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(),2);

    }
    @Test
    public void addingSameProductTwiceDoesNotDuplicateInCart() {
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(), 1);
    }
    @Test
    public void removeAllProductsFromTheCart(){
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.addProductToCart("Sauce Labs Bike Light");
        homePage.clickOnCartButton();
        cartPage.removeAllProductsFromTheCart();
        Assert.assertTrue(cartPage.isCartEmpty());
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Backpack"));
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Bike Light"));

    }
    }










