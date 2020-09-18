package screens;

import base.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class AmazonProductsPage extends BasePage {


        @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
        private MobileElement productsearchText;


        public AmazonProductsPage(AppiumDriver driver)
        {
            super(driver);
            log("Launching object of "+getClass().getSimpleName());
        }

        public void enterProductSearch(String productText)
        {
            waitforVisibility(productsearchText);
            sendKeys(productsearchText,productText);
            pressEnter();
        }

}
