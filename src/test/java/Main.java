import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/maps");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,60);
        Actions action = new Actions(driver);

        driver.findElement(By.id("searchboxinput")).sendKeys("Madurai, Tamil Nadu");
        WebElement to = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.suggest-left-cell")));
        action.moveToElement(to).build().perform();
        to.click();

        WebElement directionIcon = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button.iRxY3GoUYUY__button.gm2-hairline-border.section-action-chip-button"))).get(0);
        action.moveToElement(directionIcon).build().perform();
        directionIcon.click();

        WebElement from = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#directions-searchbox-0 input.tactile-searchbox-input")));
        from.sendKeys("Tiruchirappalli, Tamil Nadu");
        from.sendKeys(Keys.ENTER);

        List<WebElement> distance = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.section-directions-trip-distance.section-directions-trip-secondary-text")));
        List<Integer> kmList = new ArrayList<>();
        for (WebElement dis:distance
             ) {
             String[] kmRange = dis.getText().split(" ");
            kmList.add(Integer.valueOf(kmRange[0]));
        }
        int totalKM = kmList.stream().reduce(0,(ans,i) -> ans+i);
        System.out.println("Total km : " + totalKM);
    }
}