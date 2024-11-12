import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewProduct {
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
    public void testProductReview() {
        navigateToUrl();

        clickProductsButton();

        verifyAllProductsPage();

        clickViewProduct();

        verifyWriteYourReviewVisible();

        enterReviewDetails();

        clickSubmitReview();

        verifyReviewSuccessMessage();
    }

    @Step("Step 1: Navigate to the URL")
    private void navigateToUrl() {
        page.navigate("http://automationexercise.com");
    }

    @Step("Step 2: Click on 'Products' button")
    private void clickProductsButton() {
        page.click("a[href='/products']");
    }

    @Step("Step 3: Verify user is navigated to ALL PRODUCTS page successfully")
    private void verifyAllProductsPage() {
        assertTrue(page.title().contains("All Products"));
    }

    @Step("Step 4: Click on 'View Product' button")
    private void clickViewProduct() {
        page.click("a[href='/product_details/1']"); // Assuming it's the first product
    }

    @Step("Step 5: Verify 'Write Your Review' is visible")
    private void verifyWriteYourReviewVisible() {
        assertTrue(page.locator("a:has-text('Write Your Review')").isVisible());
    }

    @Step("Step 6: Enter name, email, and review")
    private void enterReviewDetails() {
        page.fill("input[id='name']", username);
        page.fill("input[id='email']", email);
        page.fill("textarea[id='review']", "This is a test review.");
    }

    @Step("Step 7: Click 'Submit' button")
    private void clickSubmitReview() {
        page.click("button[id='button-review']");
    }

    @Step("Step 8: Verify success message 'Thank you for your review.'")
    private void verifyReviewSuccessMessage() {
        assertTrue(page.locator("span:has-text('Thank you for your review.')").isVisible());
    }
}
