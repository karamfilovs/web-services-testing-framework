package inv.ui;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class LoginPage {
    private final String loginButtonSelector = "input.selenium-submit-button";
    private final String userPanelSelector = "div.userpanel-header";
    private Page page = null;


    public LoginPage(Page page){
        this.page = page;
    }


    public void login(){
        String companyNameSelector = "//div[@id='wellcome']/h2";
        String companyName = page.textContent(companyNameSelector);
        Assertions.assertEquals("QA Ground", companyName);
        //Enter email
        String emailSelector = "#loginusername";
        page.fill(emailSelector, "karamfilovs@gmail.com");
        //Enter password
        String passwordSelector = "#loginpassword";
        page.fill(passwordSelector, "123456");
        //Click Login button
        page.waitForNavigation(() -> {
            page.click(loginButtonSelector);
        });
    }
}
