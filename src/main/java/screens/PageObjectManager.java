package screens;

import base.BasePage;
import io.appium.java_client.AppiumDriver;

public class PageObjectManager extends BasePage {

    public static AppiumDriver<?> ad;
    private AmazonLoginPage AmazonLoginPage;
    private AmazonProductsPage AmazonProductsPage;

    private PageObjectManager(AppiumDriver driver)
    {
        super(driver);
        this.ad = super.getDriver() ;
    }


    public AmazonLoginPage getAmazonLoginPage() {
        return (AmazonLoginPage == null)
                ? AmazonLoginPage = launchPage(AmazonLoginPage.class) : AmazonLoginPage;
    }


    public AmazonProductsPage getAmazonProductsPage() {

        return (AmazonProductsPage == null)
                ? AmazonProductsPage = launchPage(AmazonProductsPage.class) : AmazonProductsPage;

    }


}
