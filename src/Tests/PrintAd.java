package Tests;

import PageObjects.BasePage;
import PageObjects.HomePage;
import org.json.simple.parser.ParseException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrintAd extends BaseTest {


    @Test
    public void test01getAdvertisement () throws InterruptedException, IOException, ParseException {
        driver.get("https://www.yad2.co.il");
        homePage.jsonReader();
//          homePage.OpenAllPosts();


    }}