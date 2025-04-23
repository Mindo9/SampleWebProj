package test.LoginPage;

import com.SWP.loginpage.LoginPage;
import listener.TestNGListener;
import org.testng.annotations.Test;

public class LoginPageTest extends TestNGListener {

    LoginPage loginPage;

    @Test
    public void login() throws InterruptedException {
        loginPage = new LoginPage(action);
        loginPage.login("rm_user4", "password");
    }
}
