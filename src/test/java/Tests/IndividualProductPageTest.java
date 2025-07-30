package Tests;

import Base.BaseTest;
import Pages.*;
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



public class IndividualProductPageTest extends BaseTest {
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
        wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage();
        homePage = new HomePage();
        checkoutPage = new CheckoutPage();
        cartPage = new CartPage();
        individualProductPage=new IndividualProductPage();

        driver.navigate().to("https://www.saucedemo.com/");
        loginPage.loginAs("standard_user", "secret_sauce");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void userCanSeeDetailsAboutProduct(){
        homePage.clickOnProduct("Sauce Labs Onesie");
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory-item.html?id=2");
        Assert.assertTrue(individualProductPage.productPrice.isDisplayed());
        Assert.assertTrue(individualProductPage.addToCartButton.isDisplayed());
        Assert.assertTrue(individualProductPage.productDescription.getText().contains("Rib snap infant onesie for the junior"));
        Assert.assertTrue(individualProductPage.productPicture.isDisplayed());

    }
    @Test
    public void userCanAddProductToTheCartFromProductPage() {
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());
        driver.navigate().back();
        homePage.clickOnProduct("Sauce Labs Onesie");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=2");
        Assert.assertTrue(individualProductPage.productDescription.getText().contains("Rib snap infant onesie for the junior"));
        wait.until(ExpectedConditions.elementToBeClickable(individualProductPage.addToCartButton));
        individualProductPage.clickOnAddToCart();
        individualProductPage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Onesie"));
        Assert.assertTrue(cartPage.productDescription.getText().contains("Rib snap infant onesie for the junior"));

    }
    @Test
    public void userCanOpenProductCartAndThenClickOnBackToProduct(){
        homePage.clickOnProduct("Sauce Labs Fleece Jacket");
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory-item.html?id=5");
        individualProductPage.clickOnBackToProduct();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(homePage.allProductsNamesAndImagesAreDisplayed());


    }
    @Test
    public void userCanRemoveProductFromCart() {
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());
        driver.navigate().back();
        homePage.clickOnProduct("Sauce Labs Onesie");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=2");
        Assert.assertTrue(individualProductPage.productDescription.getText().contains("Rib snap infant onesie for the junior"));
        individualProductPage.clickOnAddToCart();
        individualProductPage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Onesie"));
        driver.navigate().back();
        individualProductPage.clickOnRemoveButton();
        individualProductPage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());


    }
    @Test
    public void userCannotAddSameProductTwice(){
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());
        driver.navigate().back();
        homePage.clickOnProduct("Sauce Labs Backpack");
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory-item.html?id=4");
        Assert.assertEquals(individualProductPage.addToCartButton.getText(), "Add to cart");
        wait.until(ExpectedConditions.elementToBeClickable(individualProductPage.addToCartButton));
        individualProductPage.clickOnAddToCart();
        Assert.assertEquals(individualProductPage.removeButton.getText(), "Remove");
        individualProductPage.clickOnCartButton();
        Assert.assertEquals(cartPage.getNumberOfProductsInTheCart(),1);


    }

    }

