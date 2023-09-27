package Tests;
//
import PageObjects.HomePage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
;
import java.io.IOException;
import java.util.Collections;

public class BaseTest {
    static HomePage homePage;
    static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        resetPage();
        driver.manage().window().maximize();
    }

    public static void resetPage() {
        homePage = new HomePage(driver);
    }

//    @AfterClass
//    public static void close() throws IOException {
//        if (driver != null) {
//            driver.quit();
//        }


    }

