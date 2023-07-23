package PageObjects;

import Tests.BaseTest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import com.mongodb.client.result.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.List;

import static com.mongodb.client.model.Aggregates.skip;
import static java.lang.Thread.*;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    String city= "אשדוד";  // The desired city for the search

    // Locators for login functionality
    By loginToUserButton = By.cssSelector("span[class=\"user-drop-container_circleArea__p2Zt2\"]");
    By emailField = By.cssSelector("input[type='email']");
    By passwordField = By.cssSelector("input[type='password']");
    By submitButton = By.cssSelector("button[type='submit']");

    // Locator for real estate button
    By realEstateButton = By.cssSelector("[alt='נדל״ן']");

    // Locators for search functionality
    By cityField = By.cssSelector("[name='topArea,area,city,neighborhood,street,x_g,y_g']");
    By searchButton = By.cssSelector("button[data-test-id=\"searchButton\"]");
    By groupTitleElements = By.cssSelector("[data-test-id=\"searchAutoComplete_options_address\"]");

    // Locators for selecting apartment type
    By TypeOfApartmentDropdown = By.cssSelector("[class='y2_dropdown field_dropdown right']");
    By allCheckBox = By.cssSelector("[class=\"cb_text\"]");

    // Locators for navigating through posts
    By MaxPage = By.cssSelector("button.page-num");
    By nextButton = By.cssSelector("[class=\"y2i_back forward-icon\"]");

    // Locators for handling pop-up ads
    By PopUp = By.cssSelector("[class=\"y2-dialog-content\"]");
    By closePopUpButton = By.cssSelector("button[class=\"close-btn\"]");

    // Locators for retrieving post information
    By allPosts = By.cssSelector("[class^=\"color_container\"]");
    By emptyResults = By.cssSelector("[class=\"empty_results_notification\"]");

    public void loginToUser()  {
        Waitviseblity(loginToUserButton);  // Wait for the login button to be visible
        click(loginToUserButton);  // Click the login button
        sendKeys(emailField,"jennyjenkins742@gmail.com");  // Enter the email
        sendKeys(passwordField,"Qwerty123");  // Enter the password
        click(submitButton);  // Submit the login form
    }

    public void clickonrealestate(){
        waitForElement(realEstateButton);  // Wait for the real estate button to be visible
        click(realEstateButton);  // Click the real estate button
    }

    public void searchForCity(String city1) throws InterruptedException {
        waitForElement(cityField);  // Wait for the city field to be visible
        click(cityField);  // Click the city field
        sendKeys(cityField, city1);  // Enter the desired city
        click(cityField);  // Click the city field again to trigger the search
        waitForElement(groupTitleElements);  // Wait for the search results to be displayed
        List<WebElement> list = driver.findElements(groupTitleElements);  // Get the list of search results
        for (int i =0; i < list.size(); i++) {
            String name = list.get(i).getText();
            if (name.contains(city1) && name.contains("עיר")||name.contains("ישוב") ) {  // Check if the result matches the desired city
                (list.get(i)).click();  // Click the matching search result
                clickOnApartmentCheckbox();  // Select the apartment checkbox
                click(searchButton);  // Initiate the search
                break;
            }
        }
    }

    public void clickOnApartmentCheckbox() {
        click(TypeOfApartmentDropdown);  // Click the dropdown for selecting apartment type
        List<WebElement> list = driver.findElements(allCheckBox);  // Get the list of checkboxes
        for (int i =0; i < list.size(); i++) {
            String name = list.get(i).getText();
            if (name.contains("דירות")) {  // Check if the checkbox represents apartments
                (list.get(i)).click();  // Click the checkbox
                break;
            }
        }
    }
    public void jsonReader() throws InterruptedException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        clickonrealestate();
        Object obj = parser.parse(new FileReader("C:\\Users\\liav\\IdeaProjects\\Yad2\\src\\Data\\israel_cities.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray cities = (JSONArray) jsonObject.get("city");

        for (Object cityObj : cities) {
            JSONObject city = (JSONObject) cityObj;
            JSONArray hebrewNameArray = (JSONArray) city.get("hebrew_name");
            String hebrewName1 = (String) hebrewNameArray.get(0);
            int endIndex = hebrewName1.indexOf(')');
            if (endIndex != -1) {
                String hebrewName = hebrewName1.substring(0, endIndex);
                searchForCity(hebrewName);
                waitForElement(emptyResults);
                WebElement empty = driver.findElement(emptyResults);
                if (empty.isDisplayed())
                {
                    continue;
                }
                OpenAllPosts();

            }
        }



    }





    public void BlockAds() {
        try {
            WebElement popUp = driver.findElement(PopUp);
            if (popUp.isDisplayed()) {  // Check if the ad pop-up is displayed
                waitForElement(closePopUpButton);  // Wait for the close button to be visible
                click(closePopUpButton);  // Close the ad pop-up
            }
        } catch (NoSuchElementException e) {
            // Ad pop-up not found, continue without  blocking ads
        }
    }

    public void OpenAllPosts() throws InterruptedException {
        Thread.sleep(5000);
        waitForElement(MaxPage);  // Wait for the max page indicator to be visible
        WebElement maxPage = driver.findElement(MaxPage);  // Get the max page element
        int maxPageText = Integer.parseInt(maxPage.getText());  // Get the max page number
        System.out.println(maxPageText);
        for (int i = 0; i <= maxPageText; i++) {
            System.out.println("page number: " + (i + 1));

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(2, document.body.scrollHeight);");
            Thread.sleep(5000);
            BlockAds();  // Block ads if they appear
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(2, 0);");  // Scroll all the way up
            GetAllPostInfo();  // Get information from all posts on the current page
            click(nextButton);  // Go to the next page
        }
    }


    public void GetAllPostInfo() throws InterruptedException {
        Thread.sleep(20000);
        List<WebElement> list = driver.findElements(allPosts);  // Get the list of all posts on the page
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            By postTitle = By.cssSelector("[id='feed_item_" + i + "_title']");
            waitForElement(postTitle);  // Wait for the post title element to be visible
            By subtitleSelector = By.cssSelector("[id='feed_item_" + i + "_title'] ~ .subtitle");
            WebElement postTitleElement = driver.findElement(postTitle);  // Get the post title element
            By postPrice = By.cssSelector("[id='feed_item_" + i + "_price']");
            waitForElement(postPrice);  // Wait for the post title element to be visible
            click(postTitle);
            Thread.sleep(5000);
            By postContent = By.cssSelector("[class=\"show-more-container\"]");
            By postImg = By.cssSelector("[class=\"feedImage\"]");
            waitForElement(postImg);
            List<WebElement> postImgList = driver.findElements(postImg);
            String img = postImgList.get(i).getAttribute("src");  // Get the post image
            waitForElement(postContent);  // Wait for the post content element to be visible
            List<WebElement> postContentList = driver.findElements(postContent);
            Thread.sleep(5000);

            String content = postContentList.get(i).getText();  // Get the post subtitle
            String title = postTitleElement.findElement(By.tagName("span")).getText();  // Get the post title
            //  title = title.replaceAll("מפה", "");  // Cut out the word "מפה" from the title
            List<WebElement> postPriceList = driver.findElements(postPrice);
            System.out.println(postPriceList.get(0).getText());
            String price = postPriceList.get(0).getText();
            // Get the post subtitle
            if (title.isEmpty()) {
                System.out.println("title EMPTY -> " + i);
                continue;
            } else {
                System.out.println("Post " + i + " Title: " + title);
            }

            try {
                WebElement subtitleElement = driver.findElement(subtitleSelector);
                String subtitle = subtitleElement.getText();  // Get the post subtitle

                if (subtitle.isEmpty()) {
                    System.out.println("subtitle EMPTY -> " + i);
                } else {
                    System.out.println("Post " + i + " Subtitle : " + subtitle);
                    System.out.println("post " + i + "info: "+ content);
                    System.out.println("post " + i + " image link : "+ img);
                    System.out.println("post "+i+"Price : "+ price);

                }
            } catch (NoSuchElementException e) {
                System.out.println("subtitle element not found for post " + i);

            }
        }
    }
}
