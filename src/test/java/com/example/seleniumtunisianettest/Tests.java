package com.example.seleniumtunisianettest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tests {
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait waitVar;

    @Before
    public void prepareDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));
        waitVar = new WebDriverWait(driver, 40);
        js = (JavascriptExecutor) driver;
    }
    public User generateUser(){
        String random = RandomStringUtils.random(7, true, false);
        User userAccount = new User(
                "F" + random ,
                "L" + random ,
                "M" + random + "@gmail.com",
                "P" + random ,
                new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1) * 365 * 23)
        );
        return userAccount;
    }
    public void register(User userAccount, WebElement userInfoDropdown) throws InterruptedException {

        userInfoDropdown.click();
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-down > li > a > span")));
        WebElement signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        // Create an account
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("no-account")));
        WebElement createAccountButton = driver.findElement(By.className("no-account"));
        createAccountButton.click();

        // Choose the female option
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("custom-radio")));
        List<WebElement> genderOptions = driver.findElements(By.className("custom-radio"));
        genderOptions.get(1).click();

        //Fill form
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.form-control")));
        List<WebElement> signupFormFields = driver.findElements(By.cssSelector("input.form-control"));
        signupFormFields.get(1).sendKeys(userAccount.firstName);
        signupFormFields.get(2).sendKeys(userAccount.lastName);
        signupFormFields.get(3).sendKeys(userAccount.email);
        signupFormFields.get(4).sendKeys(userAccount.password);
        signupFormFields.get(5).sendKeys(userAccount.dateBirthFormat());

        // Register
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control-submit")));
        WebElement signupButton = driver.findElement(By.className("form-control-submit"));
        signupButton.click();

    }
    public void logout(WebElement userInfoDropdown) throws InterruptedException {

        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#_desktop_user_info > div > div > svg")));
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();

        // logout
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("logout")));
        WebElement signoutButton = driver.findElement(By.className("logout"));
        signoutButton.click();
    }
    public void login(User userAccount, WebElement userInfoDropdown) throws InterruptedException {

        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#_desktop_user_info > div > div > svg")));
        userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));
        userInfoDropdown.click();
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-down > li > a > span")));
        WebElement signinButton = driver.findElement(By.cssSelector(".user-down > li > a > span"));
        signinButton.click();

        // Input email and password
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-group > div > input")));
        WebElement emailTextField = driver.findElement(By.cssSelector(".form-group > div > input"));
        emailTextField.sendKeys(userAccount.email);
        WebElement pwdTextField = driver.findElement(By.cssSelector(".form-group > div > div > input"));
        pwdTextField.sendKeys(userAccount.password);

        // Sign in
        Thread.sleep(1500);
        WebElement submitButton = driver.findElement(By.id("submit-login"));
        submitButton.click();
    }
    public void searchProduct(String productName) throws InterruptedException {
        // Search for a product
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("search_query")));
        WebElement searchBar = driver.findElement(By.className("search_query"));
        searchBar.sendKeys(productName);

        // Click on SearchButton
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sp-btn-search > button")));
        WebElement searchButton = driver.findElement(By.cssSelector("#sp-btn-search > button"));
        searchButton.click();

        // Click on the first product
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-title")));
        List<WebElement> productsTitle = driver.findElements(By.className("product-title"));
        productsTitle.get(0).click();
    }
    public void addAndOrder() throws InterruptedException {
        // Add product to cart
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("add-to-cart")));
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();

        // Confirm order
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-content-btn > a")));
        WebElement confirmOrderButton = driver.findElement(By.cssSelector(".cart-content-btn > a"));
        confirmOrderButton.click();

        // Click to order
        Thread.sleep(1500);
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".checkout > div > a")));
        WebElement orderButton = driver.findElement(By.cssSelector(".checkout > div > a"));
        orderButton.click();
    }
    public void screenshot() throws IOException {
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File("./src/main/resources/Screenshots/endTest.png"));
    }

    @Test
    public void authAndOrderTest() throws InterruptedException, IOException {

        // -------------- Test Scenario --------------
        // 1 - Connectez-vous sur TunisiaNet
        // 2 - Faites une authentification
        // 3 - Effectuez une recherche du laptop
        //        PC portable MacBook M1 13.3
        // 4 - Finalisez le process d’achat
        // -------------------------------------------

        driver.get("https://www.tunisianet.com.tn/");

        User userAccount = generateUser();
        WebElement userInfoDropdown = driver.findElement(By.cssSelector("#_desktop_user_info > div > div > svg"));

        Thread.sleep(1500);
        register(userAccount, userInfoDropdown);
        System.out.println("Register: success");
        Thread.sleep(1500);
        logout(userInfoDropdown);
        System.out.println("logout: success");
        Thread.sleep(1500);
        login(userAccount,userInfoDropdown);
        System.out.println("login: success");
        Thread.sleep(1500);
        searchProduct("PC portable MacBook M1 13.3");
        System.out.println("Search product: success");
        Thread.sleep(1500);
        addAndOrder();
        System.out.println("add and order: success");
        screenshot();
        System.out.println("screenshot Taken");
    }

    @After
    public void quitDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}