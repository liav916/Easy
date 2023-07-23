package PageObjects;
import Tests.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public class BasePage
{ WebDriver driver;
    BaseTest BaseTest;
    public BasePage(WebDriver driver)
    {
        this.driver = driver;
        BaseTest=new BaseTest();
    }
    // click function
    public  void click(By elementLocation) {
        waitForElement(elementLocation);
        driver.findElement(elementLocation).click();
    }
    public void selectByText(By elementLoction, String text) {
        waitForElement(elementLoction);
        WebElement combo = driver.findElement(elementLoction);
        Select selectText = new Select(combo);
        selectText.selectByValue(text);

    }
    public void selectByIndex (By elementLoction, int index) {
        waitForElement(elementLoction);
        WebElement combo = driver.findElement(elementLoction);
        Select selectIndex = new Select(combo);
        selectIndex.selectByIndex(index);

    }
    //insert values in field function
    public void sendKeys(By elementLocation, String text) {

        driver.findElement(elementLocation).clear();
        driver.findElement(elementLocation).sendKeys(text);
    }
//    public static void jsonReader()
//    {
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("C:\\Users\\liav\\IdeaProjects\\Yad2\\src\\Data\\israel_cities.json"));
//            JSONObject jsonObject = (JSONObject)obj;
//            //   String name = (String)jsonObject.get("cities");
//            JSONArray subjects = (JSONArray)jsonObject.get("city");
//            System.out.println("json: " + subjects.get(0));
//            JSONArray cities = (JSONArray)subjects.get(1);
//            System.out.println(cities.get(1));






//            Object ob = new Object();
//            ob = subjects.get(0);
//System.out.println(ob);


//
//            for(Object x : subjects) {
//                Class<?> clazz = x.getClass();
//                Field field = clazz.getField("hebrew_name"); //Note, this can throw an exception if the field doesn't exist.
//                Object fieldValue = field.get(x);
//                System.out.println(fieldValue);


//            System.out.println("Subjects:");
//            Iterator iterator = subjects.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("/Users/User/Desktop/course.json"));
//            JSONObject jsonObject = (JSONObject)obj;
//            String name = (String)jsonObject.get("Name");
//            String course = (String)jsonObject.get("Course");
//            JSONArray subjects = (JSONArray)jsonObject.get("Subjects");
//            System.out.println("Name: " + name);
//            System.out.println("Course: " + course);
//            System.out.println("Subjects:");
//            Iterator iterator = subjects.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }
//        } catch(Exception e) {
//            e.printStackTrace();}}



//    public static void jsonReader1() {
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("C:\\Users\\liav\\IdeaProjects\\Yad2\\src\\Data\\israel_cities.json"));
//            JSONObject jsonObject = (JSONObject) obj;
//            JSONArray cityArray = (JSONArray) jsonObject.get("city");
//
//            if (cityArray != null && !cityArray.isEmpty()) {
//                JSONObject cityObj = (JSONObject) cityArray.get(0);
//                String hebrewName = (String) cityObj.get("hebrew_name");
//
//                // Use the Hebrew name as the value for the city variable
//                String city = hebrewName;
//
//                // Rest of your code here...
//                // Use the updated city variable in your code as needed
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public  void waitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public void closepopup(By by) throws InterruptedException {

        if (driver.findElement(by).isDisplayed())
        {click(by);}
    }

    public void Waitviseblity(By by) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException o) {
            o.printStackTrace();
        }

    }




}
