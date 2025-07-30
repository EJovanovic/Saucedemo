package Base;

import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public LoginPage loginPage;
    public HomePage homePage;
    public CheckoutPage checkoutPage;
    public CartPage cartPage;
    public IndividualProductPage individualProductPage;
    public ExcelReader excelReader;
    public CheckoutOverviewPage checkoutOverviewPage;
    public CheckoutCompletePage checkoutCompletePage;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
    }

}



