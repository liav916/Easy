package Tests;

import PageObjects.HomePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.util.Iterator;
//sdfsed

public class BaseTest {
    static HomePage homePage;
    static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        resetPage();
        driver.manage().window().maximize();
    }


    public static void resetPage()
    {
        homePage = new HomePage(driver);
    }


    @AfterClass
    public static void close() {
        //driver.close();
        //driver.quit();
    }}

