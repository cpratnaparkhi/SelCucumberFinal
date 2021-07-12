package StepDefinitions;

import Managers.PageObjectManager;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import Utilities.PropertiesReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class MyStepdefs {

    WebDriver webDriver;
    HomePage homePage;
    LoginPage loginPage;
    PageObjectManager pageObjectManager;
    PropertiesReader propertiesReader = new PropertiesReader();

    @Given("Open the browser with bhinneka home page")
    public void openTheBrowserWithBhinnekaHomePage() throws Exception {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1644,868");
        webDriver = new ChromeDriver(options);

        webDriver.manage().timeouts().implicitlyWait(propertiesReader.getTimeout(), TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(propertiesReader.getTimeout(), TimeUnit.SECONDS);

        pageObjectManager = new PageObjectManager(webDriver);
        homePage = pageObjectManager.getHomePage();

        webDriver.get(PropertiesReader.getValue("url"));
    }

    @Then("Quit the session")
    public void quitTheSession() {

        webDriver.quit();
    }

    private boolean loginPageIsDisplayed() {

        loginPage = pageObjectManager.getLoginPage();

        Assert.assertTrue(loginPage.emailLoginPageIsDisplayed());
        String actualLoginHeaderTitle = loginPage.getLoginHeaderTitle();
        Assert.assertEquals("Silakan masuk ke akun Anda", actualLoginHeaderTitle);
        String actualRegisterQuestionText = loginPage.getQuestionRegisterText();
        Assert.assertEquals("Belum punya akun Bhinneka? Daftar", actualRegisterQuestionText);
        return true;
    }

    @Given("Login form in login page")
    public void goToLoginPage() {

        Assert.assertTrue(homePage.defaultHomePageIsDisplayed());
        homePage.clickLoginButton();
        Assert.assertTrue(loginPageIsDisplayed());
    }

    @Given("Home page without authorizations")
    public void homePageWithoutAuthorizations() {

        Assert.assertTrue(homePage.defaultHomePageIsDisplayed());
    }

    @When("Input {string} as email, {string} as password, {string} as account type")
    public void inputAsEmailAsPasswordAsAccountType(String email, String password, String account) {
        System.out.println("---" + email);
        System.out.println("---" + password);
        System.out.println("---" + account);
    }
}
