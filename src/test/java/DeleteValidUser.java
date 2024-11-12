import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteValidUser {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "shepel.taras@ukr.net";
    private String password = "0473951381S";
    private String username = "shepel61";

    @BeforeEach
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("http://automationexercise.com");
    }

    @AfterEach
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void testAccountDeletion() {
        navigateToUrl();

        verifyHomePageVisible();

        clickSignupLogin();

        verifyLoginVisible();

        enterCredentials();

        clickLogin();

        verifyLoggedIn();

        clickDeleteAccount();

        verifyAccountDeleted();
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
        assertTrue(page.locator("a:has-text('Logged in as " + username + "')").isVisible());
    }

    @Step("Step 8: Click 'Delete Account' button")
    private void clickDeleteAccount() {
        page.click("a[href='/delete_account']");
    }

    @Step("Step 9: Verify that 'ACCOUNT DELETED!' is visible")
    private void verifyAccountDeleted() {
        assertTrue(page.locator("h2:has-text('ACCOUNT DELETED!')").isVisible());
    }
}