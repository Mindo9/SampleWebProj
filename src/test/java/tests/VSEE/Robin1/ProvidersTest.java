package tests.VSEE.Robin1;

import com.VSEE.constants.VseeConstants;
import com.VSEE.pagetests.providers.Providers;
import com.VSEE.pagetests.robin1.RobinsInterview1;
import listener.TestNGListenerVSEE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

public class ProvidersTest extends TestNGListenerVSEE {

    Providers providers;

    @Test(description = "Verified a call")
    public void scheduleCheck() throws InterruptedException, AWTException {
        action.browser = "EDGE";
        action.openBrowser();
        action.maximizeWindow();
        providers = new Providers(action);
        action.navigateToUrl(providers.vss_meeting2);
        providers.login(VseeConstants.email, VseeConstants.password);
//        providers.startACall(VseeConstants.getVisitId());
//        providers.chatWithPatient();
        Thread.sleep(30000);
//        providers.endCall();
    }
}
