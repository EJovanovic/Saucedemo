package Tests;

import Base.BaseTest;
import Base.ExcelReader;
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

import java.io.IOException;
import java.time.Duration;

public class CheckoutPageTest extends BaseTest {
    @BeforeMethod
    public void pageSetUp() throws IOException {
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage();
        homePage = new HomePage();
        checkoutPage = new CheckoutPage();
        cartPage = new CartPage();
        checkoutOverviewPage=new CheckoutOverviewPage();
        checkoutCompletePage=new CheckoutCompletePage();
        excelReader = new ExcelReader("C:\\Users\\Korisnik\\Downloads\\Checkout.xlsx");

        driver.navigate().to("https://www.saucedemo.com/");
        loginPage.loginAs("standard_user", "secret_sauce");
    }

     @AfterMethod
    public void tearDown() {
         driver.quit();

     }
    @Test
    public void userCanFinishCheckoutWithValidCredentials() {
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isCartEmpty());//first I am checking if the cart is empty
        homePage.addProductToCart("Sauce Labs Backpack");//adding a product
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));//I have to assert that the right product is in the cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        cartPage.clickOnCheckoutButton();

        Assert.assertTrue(checkoutPage.firstNameField.isDisplayed());//assert that checkout form is available
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
        //I used Excel Reader to fill in information
        String firstName = excelReader.getStringData("Sheet1", 1, 0);
        String lastName = excelReader.getStringData("Sheet1", 1, 1);
        String zipCode = excelReader.getStringData("Sheet1", 1, 2);
        checkoutPage.fillCheckoutForm(firstName, lastName, zipCode);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
        Assert.assertEquals(checkoutPage.firstNameField.getAttribute("value"), "Emilija");//confirm that all information are present
        Assert.assertEquals(checkoutPage.lastNameField.getAttribute("value"), "Jovanovic");
        Assert.assertEquals(checkoutPage.postalCodeField.getAttribute("value"), "11000");

        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutOverviewPage.getPaymentInformation().contains("SauceCard #31337"));
        Assert.assertTrue(checkoutOverviewPage.itemTotal.getText().contains("$29.99"));//check if the same products is on the checkout page

        checkoutOverviewPage.clickOnFinishButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
        Assert.assertTrue(checkoutCompletePage.backHomeButton.isDisplayed());
        Assert.assertTrue(checkoutCompletePage.title.getText().contains("Checkout: Complete!"));//confirm that checkout is complete


    }

    @Test
    public void userCannotFinishCheckoutWithEmptyUsernameField() {
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        cartPage.clickOnCheckoutButton();

        Assert.assertTrue(checkoutPage.firstNameField.isDisplayed());
        String firstName = excelReader.getStringData("Sheet1", 2, 0);
        String lastName = excelReader.getStringData("Sheet1", 2, 1);
        String zipCode = excelReader.getStringData("Sheet1", 2, 2);
        checkoutPage.fillCheckoutForm(firstName,lastName,zipCode);

        Assert.assertTrue(checkoutPage.firstNameField.getAttribute("value").isEmpty());
        Assert.assertEquals(checkoutPage.lastNameField.getAttribute("value"), "Jovanovic");
        Assert.assertEquals(checkoutPage.postalCodeField.getAttribute("value"), "11000");
        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertTrue(checkoutPage.errorMessage.getText().contains("First Name is required"));
    }
    @Test
    public void userCannotFinishCheckoutWithEmptyLastNameField(){
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        cartPage.clickOnCheckoutButton();

        Assert.assertTrue(checkoutPage.firstNameField.isDisplayed());
        String firstName = excelReader.getStringData("Sheet1", 3, 0);
        String lastName = excelReader.getStringData("Sheet1", 3, 1);
        String zipCode = excelReader.getStringData("Sheet1", 3, 2);
        checkoutPage.fillCheckoutForm(firstName,lastName,zipCode);

        Assert.assertEquals(checkoutPage.firstNameField.getAttribute("value"),"Emilija");
        Assert.assertTrue(checkoutPage.lastNameField.getAttribute("value").isEmpty());
        Assert.assertEquals(checkoutPage.postalCodeField.getAttribute("value"), "11000");
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertTrue(checkoutPage.errorMessage.getText().contains("Last Name is required"));
    }
    @Test
    public void userCannotFinishCheckoutWithEmptyZipCodeField(){
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        cartPage.clickOnCheckoutButton();

        Assert.assertTrue(checkoutPage.firstNameField.isDisplayed());
        String firstName = excelReader.getStringData("Sheet1", 4, 0);
        String lastName = excelReader.getStringData("Sheet1", 4, 1);
        String zipCode = excelReader.getStringData("Sheet1", 4, 2);
        checkoutPage.fillCheckoutForm(firstName,lastName,zipCode);

        Assert.assertEquals(checkoutPage.firstNameField.getAttribute("value"),"Emilija");
        Assert.assertEquals(checkoutPage.lastNameField.getAttribute("value"),"Jovanovic");
        Assert.assertTrue(checkoutPage.postalCodeField.getAttribute("value").isEmpty());
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutPage.errorMessage.isDisplayed());
        Assert.assertTrue(checkoutPage.errorMessage.getText().contains("Postal Code is required"));
    }


    @Test
    public void verifyThatBackHomeButtonRedirectsToHomepage(){
        homePage.addProductToCart("Sauce Labs Bike Light");
        homePage.clickOnCartButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        cartPage.clickOnCheckoutButton();
        checkoutPage.fillCheckoutForm("Emilija","Jovanovic","11000");
        checkoutPage.clickOnContinueButton();

        Assert.assertTrue(checkoutOverviewPage.getPaymentInformation().contains("SauceCard #31337"));
        Assert.assertTrue(checkoutOverviewPage.getShippingInformation().contains("Free Pony Express Delivery!"));
        checkoutOverviewPage.clickOnFinishButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-complete.html");
        checkoutCompletePage.clickOnBackHomeButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");


    }
    @Test
    public void verifyThatOrderInformationAreVisibleOnCheckoutPage(){
        homePage.addProductToCart("Sauce Labs Bike Light");
        homePage.clickOnCartButton();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));
        cartPage.clickOnCheckoutButton();
        checkoutPage.fillCheckoutForm("Emilija","Jovanovic","11000");
        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutOverviewPage.getPaymentInformation().contains("SauceCard #31337"));
        Assert.assertTrue(checkoutOverviewPage.getItemPrice().contains("$9.99"));

    }
    @Test
    public void userCanFinishCheckoutWithoutAddingProductToCart() {
        homePage.clickOnCartButton();
        cartPage.clickOnCheckoutButton();
        checkoutPage.fillCheckoutForm("Emilija", "Jovanovic", "11000");
        checkoutPage.clickOnContinueButton();
        Assert.assertTrue(checkoutOverviewPage.getItemPrice().contains("$0"));
        checkoutOverviewPage.clickOnFinishButton();
        Assert.assertTrue(checkoutCompletePage.title.getText().contains("Checkout: Complete!"));
    }
        @Test
    public void userCanChangeMindAndCancelCheckout(){
            homePage.addProductToCart("Sauce Labs Bike Light");
            homePage.clickOnCartButton();
            Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Bike Light"));
            cartPage.clickOnCheckoutButton();
            checkoutPage.fillCheckoutForm("Emilija", "Jovanovic", "11000");
            checkoutPage.clickOnContinueButton();
            Assert.assertTrue(checkoutOverviewPage.getItemPrice().contains("$9.99"));
            checkoutOverviewPage.clickOnCancelButton();
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");



    }

    }







