package Tests;

import PageObjects.BasePage;
import PageObjects.HomePage;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrintAd extends BaseTest {


    @Test
    public void test01getAdvertisement () throws Exception {
        driver.get("https://easy.co.il/list/Auto-Parts?region=773&order=1");
homePage.clickOnPosts();


}



        }
