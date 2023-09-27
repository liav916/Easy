package PageObjects;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }
    String City = "HOLON";
    By recaptcha = By.cssSelector("[class=\"page-error gradient-multiple-bg\"]");
    By nextButton = By.cssSelector(".next-page-button");
    By allPostsConteiner = By.cssSelector(".list-results");
    By BackButton = By.cssSelector(".back-icon");
    By ByStoreName = By.cssSelector(".biz-title");
    By ByStoreService = By.cssSelector("span.best-sub-cat");
    By ByStoreAddress = By.cssSelector(".biz-address-text");
    By ByStoreNumber = By.cssSelector("#action-phone-label");
    By BYTotal = By.cssSelector(".heading-box");


    public void Post(String storeName, String storeService, String storeAddress, String storeNumber) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        // Replace with the actual API endpoint
        String apiUrl = "https://script.google.com/macros/s/AKfycbw_MLiirmtNkNWpeOI-zHqDfca5pCnhuNn2ll_qRfvCKbYavvCnOQ4oYS8CvUwcd3eZzw/exec";
        // Replace with the desired JSON payload
        String jsonPayload = "{\n" +
                "  \"city\":\""+City+"\",\n" +
                "  \"storeName\":\"" + storeName + "\",\n" +
                "  \"storeService\":\"" + storeService + "\",\n" +
                "  \"address\":\"" + storeAddress + "\",\n" +
                "  \"phone\":\"" + storeNumber + "\"\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .header("Content-Type", "application/json") // Set the content type to JSON
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // Check the HTTP status code
        int statusCode = response.statusCode();
        if (statusCode == 302) { // 201 indicates a successful creation in many APIs
            String responseBody = response.body();
            System.out.println("status code: " + statusCode);
            System.out.println("Response Body:\n" + responseBody);
        } else {
            System.out.println("POST request failed with status code: " + statusCode);
            System.out.println(jsonPayload);
        }
    }

    public void recaptchapass() throws InterruptedException {
        boolean captchaPresent = isElementPresent(recaptcha);

        while (captchaPresent) {
            System.out.println("Captcha detected. Please manually pass the captcha.");
            Thread.sleep(20000); // Sleep for 5 seconds before checking again

            captchaPresent = isElementPresent(recaptcha);
        }

        System.out.println("Captcha passed or not detected.");
        Thread.sleep(10000);
    }
    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void clickOnMoreAds() throws InterruptedException {
    Thread.sleep(1000);
  try {
      WebElement nextButtonElement = driver.findElement(nextButton);
        waitForElement(nextButton);
        if (nextButtonElement.isDisplayed()){
            ScrollAndClick(nextButton);
        }
        clickOnMoreAds();}catch (NoSuchElementException e) {}  }
    public void ScrollAndClick (By by) {
        WebElement elementToClick = driver.findElement(by);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", elementToClick);
        // Click the element
        elementToClick.click();


}
    public void clickOnPosts() throws Exception {
        recaptchapass();
        WebElement allPostsElement = driver.findElement(allPostsConteiner);
        waitForElement(allPostsConteiner);
        List<WebElement> posts;
        clickOnMoreAds();
        Thread.sleep(3000);
        recaptchapass();
       // posts = allPostsElement.findElements(By.cssSelector("[class^='list-results-scroll'] li"));
        posts = allPostsElement.findElements(By.cssSelector(".list-results li"));

        System.out.println("post size is" + posts.size());
        for (int i = 0; i < posts.size(); i++) {
            recaptchapass();
            WebElement post = posts.get(i);
            try {
            //    Thread.sleep(1000);
                scrollToElementAndClick(post);
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element reference, retrying click...");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
                post = wait.until(ExpectedConditions.elementToBeClickable(posts.get(i)));
                post.click();
             //  post.click();

            }//
      //      Thread.sleep(1000);

            // Extract information
            String storeName = getStoreInfo(ByStoreName, i);
            String storeService = getStoreService(ByStoreService, i);
            String storeAddress = getStoreInfo(ByStoreAddress, i);
            String storeNumber = getStoreInfo(ByStoreNumber, i);
            storeAddress = storeAddress.replace("\"", "'");
System.out.println("storeName : "+storeName+"\n"+
                   "storeService : "+storeService+"\n"+
                   "storeAddress : "+storeAddress+"\n"+
                   "storeNumber : "+storeNumber+"\n"+
                   "post number: " + i+"\n"+"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");



            // Call the Post method with the extracted values
            Post(storeName, storeService, storeAddress, storeNumber);

            waitForElement(BackButton);
            Thread.sleep(2000);

//           String currentURL = driver.getCurrentUrl();
           try{ click(BackButton);} catch (ElementClickInterceptedException e){scrollTobYElementAndClick(BackButton);
           }
            //
//            Thread.sleep(1000);
//            String currentURL2 = driver.getCurrentUrl();
//if (currentURL.equals(currentURL2))
//{click(BackButton);}


            clickOnMoreAds();
            waitForElement(allPostsConteiner);
            recaptchapass();
        }
    }
    public String getStoreInfo(By by, int index)  {

        String StoreName;
       try{ waitForElement(by); // Make sure this method correctly waits for the element
        WebElement element = driver.findElement(by); // Assuming 'driver' is your WebDriver instance
        StoreName = element.getText();}
        catch (TimeoutException e){
            StoreName = "there is no phone number for the this buisnes";
        }

        // Remove the word "מנוהל" from StoreName
        StoreName = StoreName.replace("מנוהל", "").trim();
        StoreName = StoreName.replace("\"", "'");


       // System.out.println("Post Number: " + index+" "+ StoreName);
        return StoreName;
    }

    public String getStoreService(By by, int index)  {
        WebElement element = driver.findElement(BYTotal); // Assuming 'driver' is your WebDriver instance
        WebElement element1 = element.findElement(by); // Assuming 'driver' is your WebDriver instance

        String StoreName;
        waitForElement(by); // Make sure this method correctly waits for the element
        StoreName = element1.getText();
        // Remove the word "מנוהל" from StoreName


        if (StoreName.contains("חלקי"))
        {
            StoreName = "חנות";
        }
else
        {
            StoreName = "נותן שירות";
        }
       // System.out.println("Post Number: " + index+" :"+ StoreName);
        return StoreName;
    }
}








