import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutValidUser {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "shepel.taras@ukr.net";
    private String password = "0473951381S";
    private String username = "shepel61";

    @BeforeEach
    public void setup() {
        // Step 1: Launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        // Close browser after each test
        browser.close();
        playwright.close();
    }

    @Test
    public void testLogout() {
        navigateToUrl();

        verifyHomePageVisible();

        clickSignupLogin();

        verifyLoginVisible();

        enterCredentials();

        clickLogin();

        verifyLoggedIn();

        clickLogout();

        verifyUserNavigatedToLoginPage();
    }

    @Step("Step 1: Navigate to the URL")
    private void navigateToUrl() {
        page.navigate("http://automationexercise.com");
    }

    @Step("Step 2: Verify that home page is visible successfully")
    private void verifyHomePageVisible() {
        assertTrue(page.title().contains("Automation Exercise"));
    }

    @Step("Step 3: Click on 'Signup / Login' button")
    private void clickSignupLogin() {
        page.click("a[href='/login']");
    }

    @Step("Step 4: Verify 'Login to your account' is visible")
    private void verifyLoginVisible() {
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());
    }

    @Step("Step 5: Enter correct email address and password")
    private void enterCredentials() {
        page.fill("input[data-qa='login-email']", email);
        page.fill("input[data-qa='login-password']", password);
    }

    @Step("Step 6: Click 'login' button")
    private void clickLogin() {
        page.click("button[data-qa='login-button']");
    }

    @Step("Step 7: Verify that 'Logged in as username' is visible")
    private void verifyLoggedIn() {
        assertFalse(page.locator("a:has-text('Logged in as " + username + "')").isVisible());
    }

    @Step("Step 8: Click 'Logout' button")
    private void clickLogout() {
        page.click("a[href='/logout']");
    }

    @Step("Step 9: Verify that user is navigated to login page")
    private void verifyUserNavigatedToLoginPage() {
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());
    }
}
