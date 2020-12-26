import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckLinks
{

    public static WebDriver driver;

    public static void HighLight(WebElement element)
    {
        JavascriptExecutor js=(JavascriptExecutor)driver;
        try {
            js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');", element);
            Thread.sleep(1000);
            js.executeScript("arguments[0].setAttribute('style','background:blue;border:2px solid cyan;');", element);
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String [] args) throws IOException
    {
        System.setProperty("webdriver.chrome.driver","D:\\Selenium Jars\\chromedriver.exe");

        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();


        driver.get("http://www.google.co.in");

        WebElement txtSearch=driver.findElement(By.name("q"));
        HighLight(txtSearch);

        txtSearch.sendKeys("Welcome to ScrumStart");
        txtSearch.sendKeys(Keys.ENTER);

        List<WebElement> noOfLinks=driver.findElements(By.xpath("//h3//ancestor::a"));
        System.out.println("No Of links found on page is :"+ noOfLinks.size());
        String linkURL;
        int responseCode;

        for(WebElement l: noOfLinks)
        {
            linkURL=l.getAttribute("href");
            HttpURLConnection huc;
            try {
                huc = (HttpURLConnection)(new URL(linkURL).openConnection());
                huc.connect();
                responseCode=huc.getResponseCode();
                if(responseCode>=400)
                {
                    System.out.println("These links are broken links: "+ linkURL +": as response code is :"+ responseCode);
                }
                else {
                    System.out.println("Links are healthy:"+ linkURL +": as response code is :"+ responseCode);
                }
            }

            catch (MalformedURLException m) {
                System.out.println(m.getMessage());
            }

        }

        }
    }

