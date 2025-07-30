package Tests;

import Base.BaseTest;
import Pages.HomePage;
import Pages.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        loginPage = new LoginPage();
        homePage = new HomePage();

    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void loginStandardUser() {
        loginPage.loginAs("standard_user", "secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
        Assert.assertTrue(homePage.title.isDisplayed());
        Assert.assertEquals(homePage.title.getText(), "Swag Labs");
        Assert.assertTrue(homePage.cartButton.isDisplayed());

    }

    @Test
    public void loginLockedOutUser() {
        loginPage.loginAs("locked_out_user", "secret_sauce");
        String errorMessage = loginPage.errorMessage.getText();
        Assert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.");
        String expectedUrl = "https://www.saucedemo.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }

    @Test
    public void loginProblemUser() {
        loginPage.loginAs("problem_user", "secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
        Assert.assertEquals(homePage.title.getText(), "Swag Labs");
        Assert.assertTrue(homePage.cartButton.isDisplayed());


    }

    @Test
    public void loginPerformanceGlitchUser() {
        loginPage.loginAs("performance_glitch_user", "secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
        Assert.assertTrue(homePage.cartButton.isDisplayed());

    }

    @Test
    public void loginErrorUser() {
        loginPage.loginAs("error_user", "secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
        Assert.assertTrue(homePage.cartButton.isDisplayed());


    }

    @Test
    public void loginVisualUser() {
        loginPage.loginAs("visual_user", "secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
        Assert.assertEquals(homePage.title.getText(), "Swag Labs");
    }


    @Test
    public void loginWithBothEmptyFields() {
        loginPage.loginAs("", "");
        Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username is required");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }


    @Test
    public void loginWithValidPasswordAndEmptyUsername() {
        loginPage.loginAs("","secret_sauce");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username is required");
    }

    @Test
    public void loginWithValidUsernameAndEmptyPassword() {
        loginPage.loginAs("standard_user","");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Password is required");


    }
    @Test
    public void loginWithOnlyClickingLoginButton(){
        loginPage.clickOnLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertEquals(loginPage.errorMessage.getText(), "Epic sadface: Username is required");



    }
}
