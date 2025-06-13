package listener;

import com.VSEE.log.LogHelper;
import com.VSEE.keywords.WebKeywords;
import com.VSEE.pagetests.providers.Providers;
import com.VSEE.pagetests.robin1.RobinsInterview1;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.ITestNGListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestNGListenerVSEE implements ITestNGListener {

    public WebKeywords action;
    public static Logger logger = LogHelper.getLogger();

    public TestNGListenerVSEE() {
        action = new WebKeywords();
    }

    public TestNGListenerVSEE(WebKeywords action) {
        this.action = action;

    }

    @BeforeSuite
    public void beforeTest() throws Throwable {
        action.openBrowser();
        action.maximizeWindow();
    }

}
