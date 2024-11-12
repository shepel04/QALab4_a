import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupWithExistingEmail {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "shepel.taras@ukr.net";
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
    public void testSignupWithExistingEmail() {
        navigateToUrl();

        verifyHomePageVisible();

        clickSignupLoginButton();

        verifyNewUserSignupVisible();

        enterSignupDetails();

        clickSignupButton();

        verifyEmailExistsError();
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
    private void clickSignupLoginButton() {
        page.click("a[href='/login']");
    }

    @Step("Step 4: Verify 'New User Signup!' is visible")
    private void verifyNewUserSignupVisible() {
        assertTrue(page.locator("h2:has-text('New User Signup!')").isVisible());
    }

    @Step("Step 5: Enter name and already registered email address")
    private void enterSignupDetails() {
        page.fill("input[data-qa='signup-name']", username);
        page.fill("input[data-qa='signup-email']", email); // Use an already registered email
    }

    @Step("Step 6: Click 'Signup' button")
    private void clickSignupButton() {
        page.click("button[data-qa='signup-button']");
    }

    @Step("Step 7: Verify error 'Email Address already exist!' is visible")
    private void verifyEmailExistsError() {
        assertTrue(page.locator("p:has-text('Email Address already exist!')").isVisible());
    }
}
