package tests.VSEE.Robin1;

import com.VSEE.constants.VseeConstants;
import com.VSEE.pagetests.providers.Providers;
import com.VSEE.pagetests.robin1.RobinsInterview1;
import listener.TestNGListenerVSEE;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RobinsInterview1Test extends TestNGListenerVSEE {

    RobinsInterview1 robinsInterview1;
    Providers providers;

    @Test(description = "Verified before call")
    public void scheduleCheck() {
        providers = new Providers(action);
        providers.openBrowser();
        providers.login(VseeConstants.email, VseeConstants.password);
        Assert.assertTrue(providers.NoOneOnline());
    }

    @Test(description = "Enter waiting room")
    public void enterRoom() {
       robinsInterview1 = new RobinsInterview1(action);
       action.switchWindow(robinsInterview1.robin_interview1);
       robinsInterview1.login(VseeConstants.email);
       robinsInterview1.makeACall();
    }

//    @Test(description = "Enter waiting room")
//    public void endCall() {
//        providers = new Providers(action);
//        providers.login(VseeConstants.email, VseeConstants.password);
//        Assert.
//    }
}
