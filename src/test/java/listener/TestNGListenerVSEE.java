package listener;

import com.VSEE.log.LogHelper;
import com.VSEE.keywords.WebKeywords;
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
        logger.info("Before Test");

    }

    @AfterSuite
    public void afterTest() {
        action.closeBrowser();
    }

}
