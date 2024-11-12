import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCartFromRecommended {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private String email = "shepel.taras@ukr.net";
    private String password = "0473951381S";

    @BeforeEach
    public void setup() {
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
    public void testRecommendedItemsAddToCart() {
        navigateToSite();

        loginToSite();

        scrollToBottom();

        verifyRecommendedItemsVisible();

        addToCartRecommendedItem();

        viewCart();

        verifyProductInCart();
    }

    @Step("Step 0: Navigate to site \"http://automationexercise.com\"")
    private void navigateToSite()
    {
        page.navigate("http://automationexercise.com");
    }

    @Step("Step 1: Login to the site with valid credentials")
    private void loginToSite() {
        page.click("a[href='/login']");
        assertTrue(page.locator("h2:has-text('Login to your account')").isVisible());
        page.fill("input[data-qa='login-email']", email);
        page.fill("input[data-qa='login-password']", password);
        page.click("button[data-qa='login-button']");
    }

    @Step("Step 2: Scroll to the bottom of the page")
    private void scrollToBottom() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Step("Step 3: Verify 'RECOMMENDED ITEMS' section is visible")
    private void verifyRecommendedItemsVisible() {
        assertTrue(page.locator("h2:has-text('recommended items')").isVisible());
    }

    @Step("Step 4: Click on 'Add To Cart' for the recommended product")
    private void addToCartRecommendedItem() {
        page.click("a[data-product-id='1']");
    }

    @Step("Step 5: Click on 'View Cart' button")
    private void viewCart() {
        page.click("u:has-text('View Cart')");
    }

    @Step("Step 6: Verify the product is displayed in the cart page")
    private void verifyProductInCart() {
        assertTrue(page.locator("tr[id='product-1']").isVisible());
    }
}
