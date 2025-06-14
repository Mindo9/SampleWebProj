package tests.VSEE.Robin1;

import com.VSEE.constants.VseeConstants;
import com.VSEE.driver.DriverManager;
import com.VSEE.keywords.WebKeywords;
import com.VSEE.pagetests.providers.Providers;
import com.VSEE.pagetests.robin1.RobinsInterview1;
import io.qameta.allure.Allure;
import listener.TestNGListenerVSEE;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.sql.SQLOutput;

public class RobinsInterview1Test extends TestNGListenerVSEE {

    RobinsInterview1 robinsInterview1;
    Providers providers;

    @Test(description = "Verified a call")
    public void scheduleCheck() throws InterruptedException, AWTException {
        robinsInterview1 = new RobinsInterview1(action);
        robinsInterview1.openBrowser();
        WebDriver chromeDriver = action.getWebDriver();
        robinsInterview1.login("patientRobin");
        Thread.sleep(5000);
        String currentUrl = action.getCurrentUrl();
        String visitId = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
        action.browser = "EDGE";
        providers = new Providers(action);
        providers.openBrowser();
        WebDriver edgeDriver = action.getWebDriver();
        providers.login(VseeConstants.email, VseeConstants.password);
        providers.startACall(visitId);
        providers.chatWithPatient();
        action.altTab();
        chromeDriver.findElement(By.xpath("//h3[normalize-space()='Continue on this browser']")).click();
        Thread.sleep(5000);
        Assert.assertTrue(chromeDriver.findElement(By.xpath("//div[contains(text(),'Are U OK') and @class='webchat-message-bubble']")).isDisplayed());
        chromeDriver.findElement(By.xpath("//button[@class='close closebox']")).click();
        Thread.sleep(10000);
        chromeDriver.switchTo().frame(chromeDriver.findElement(By.id("jitsiConferenceFrame0")));
        chromeDriver.findElement(By.xpath("//div[@class='toolbox-icon   hangup-button']")).click();
        Thread.sleep(3000);
        action.altTab();
        providers.endCall();
        chromeDriver.quit();
        edgeDriver.quit();
    }

}
