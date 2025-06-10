package tests.VSEE.Robin1;

import com.VSEE.constants.VseeConstants;
import com.VSEE.keywords.WebKeywords;
import com.VSEE.pagetests.providers.Providers;
import com.VSEE.pagetests.robin1.RobinsInterview1;
import io.qameta.allure.Allure;
import listener.TestNGListenerVSEE;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinsInterview1Test extends TestNGListenerVSEE {

    RobinsInterview1 robinsInterview1;
    Providers providers;

    @Test(description = "Verified a call")
    public void scheduleCheck() throws InterruptedException {
        robinsInterview1 = new RobinsInterview1(action);
        robinsInterview1.openBrowser();
        robinsInterview1.login("patientRobin");
        Thread.sleep(5000);
        String currentUrl = action.getCurrentUrl();
        String visitId = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
        action.browser = "EDGE";
        providers = new Providers(action);
        providers.openBrowser();
        providers.login(VseeConstants.email, VseeConstants.password);
        providers.startACall(visitId);
        Thread.sleep(60000);
    }
}
