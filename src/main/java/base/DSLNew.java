package base;

/**
 * Created by P737084 on 5/11/2016.
 */

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class DSLNew
{
    public DSLNew() {}

    public WebDriver driver = null;
    private int attemptcount = 10;

    public final void initialise(WebDriver driver)
    {
        this.driver = driver;
    }

    public WebElement pollForWebElement(By locator, int timeInSeconds) throws Exception
    {

        WebElement r_currentElement = null;
        Wait<WebDriver> p_wait = null;

        try {
            // Waiting for specified no of seconds for an element to be present on the page
            p_wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(timeInSeconds))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);

            r_currentElement = p_wait.until(ExpectedConditions.elementToBeClickable(locator));
            return r_currentElement;
        } catch (Exception e) {
            System.out.println("Waiting for element to display on DOM (pollforwebelement)");
        }
        return r_currentElement;
    }

    public void clickElement(String elementLocator, String elementName) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = findLocator(elementLocator,15000);
                element.click();
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }


    }
    
   public void clickDoubleElement(String locator, String elementName) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = findLocator(locator,15000);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                } else {
                    Actions act = new Actions(driver);
                    act.doubleClick(element).build().perform();
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }

    public void clickLink(String locator, String messageOnError) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = findLocator(locator, 15000);
                if (element == null) {
                    throw new Exception("Unable to locate element " + messageOnError);
                } else {
                    element.sendKeys(Keys.ENTER);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }
    }

    public void actionBuilder(By by, String elementName)throws Exception
    {
        int attempts = 0;

        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 20);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                } else {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(element).click().perform();
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }

    public void moveToMenuItem(String fieldname, String elementName) throws Exception
    {
        moveToMenuItem(getByLocator(fieldname), elementName);
    }

    public void moveToMenuItem(By by, String elementName) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 20);
                if (element == null)
                {
                    throw new Exception("Unable to locate element " + elementName);
                }
                else
                    {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(element).perform();
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }
    }


    private List<WebElement> getMenuList() throws Exception
    {
        List<WebElement> menuItems = null;
        int attempts = 0;

        while (attempts <= attemptcount)
        {
            //locator of menu is always same in xplan
            By by = getByLocator("//*[@id=\"popupdiv-0\"]/div/ul");
            WebElement element = pollForWebElement(by, 180);

            try
            {
                if (element == null)
                {
                    throw new Exception("Unable to locate menu list");
                }
                else
                {
                    //finds all elements inside within ul tag(menu) with a tag of li (all list items)
                    menuItems = element.findElements(By.tagName("li"));
                }

            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");

                if(attempts == attemptcount)
                {
                    throw new Exception();
                }
            }

            attempts++;
        }

        return menuItems;
    }

    public List<String> getMenuListItemNames() throws Exception
    {
        List<WebElement> menuItems = getMenuList();
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < menuItems.size(); i++)
        {
            tmpList.add(menuItems.get(i).getText());
        }

        return tmpList;
    }

    //to check if characters of string is contained in other string
//    public static Set<Character> stringToCharacterSet(String s)
//    {
//        Set<Character> set = new HashSet<>();
//        for (char c : s.toCharArray()) {
//            set.add(c);
//        }
//        return set;
//    }



    public boolean SplitStringAndMatch(String s1, String s2)
    {
        return SplitStringAndMatch(s1, s2, false);
    }


    public boolean SplitStringAndMatch(String s1, String s2, boolean exactMatch)
    {
        if (exactMatch)
            return s2.contains(s1);

        if(!s1.contains(" ") || !s2.contains(" "))
        {
            return s2.contains(s1);
        }
        else
        {
            String[] string1 = s1.toLowerCase().split("\\s+");
            String[] string2 = s2.toLowerCase().split("\\s+");
            int countofmatches =0;
            for (String eachstring1 : string1)
            {
               for (String eachstring2 :string2)
               {
                  if(eachstring2.contains(eachstring1))
                  {
                      countofmatches++;
                      break;
                  }

               }

            }
            if(countofmatches==string1.length && countofmatches!=0)
                return true;

        }
        return false;
    }

    public Boolean CheckTableandaction(String tablelocator, String fieldlocator, String[] text, boolean exactMatch) throws Exception
    {

        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                By by = getByLocator(tablelocator);
                WebElement element = pollForWebElement(by, 180);
                if (element == null)
                {
                    throw new Exception("Unable to locate table element");
                }
                else
                {   //finds all elements inside within row tag
                    List<WebElement> tablerows = element.findElements(By.tagName("tr"));
                    //once u find the number of rows in table find the row which has matching text and then find the column to click
                    for (int i = 0; i < tablerows.size(); i++)

                    {
                        if (SplitStringAndMatch(text[0].toLowerCase(), tablerows.get(i).getText().toLowerCase(), exactMatch))
                        {
                            WebElement elementselected = tablerows.get(i).findElement(By.xpath(fieldlocator));
                            //to check for page is being loaded before it can find locator
                            WaitforPageLoad();
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementselected);
                            //call method to check the element type
                            String elementtype = TypeofElement(elementselected);
                            //determine the action based on element type
                            if (elementtype.equalsIgnoreCase("select"))
                            {
                                elementselected.click();
                                if (text[1] != null && !text[1].isEmpty())
                                {
                                    Select dropdown = new Select(elementselected);
                                    dropdown.selectByVisibleText(text[1]);
                                }
                            }
                            else if (elementtype.equalsIgnoreCase("checkbox"))
                            {
                                if (!elementselected.isSelected())
                                    elementselected.click();
                            }
                            else if (elementtype.equalsIgnoreCase("text"))
                            {
                                elementselected.click();

                                if (text[1] != null && !text[1].isEmpty())
                                {
                                    //clear causes the page to refresh and loses the reference of element
                                    // elementselected.clear();
                                    elementselected.sendKeys(Keys.CONTROL + "a");
                                    elementselected.sendKeys(text[1]);
                                }
                            }
                            else
                                // default is just click
                                elementselected.click();

                            return true;
                        }

                    }
                    //unable to find a matching text in the table or if no tows present
                    return false;
                }

            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }
        return false;
    }


    public List<List<String>> getTableRowsData(String fieldLocator) throws Exception
    {
        By by = getByLocator(fieldLocator);
        WebElement element = pollForWebElement(by, 180);

        if (element == null)
            throw new Exception("Unable to locate element - " + fieldLocator);

        List<List<String>> tableRowsData = new ArrayList<>();

        //finds all elements inside within row tag
        List<WebElement> tablerows = element.findElements(By.tagName("tr"));

        for (int i=0; i < tablerows.size(); i++)
        {
            List<WebElement> tableColumns = tablerows.get(i).findElements(By.tagName("td"));
            List<String> columnData = new ArrayList<>();

            for (WebElement webElement : tableColumns)
            {
                columnData.add(webElement.getText());
            }

            tableRowsData.add(columnData);
        }

        return tableRowsData;
    }

    //method to determine the type of element
    public String TypeofElement (WebElement element) throws Exception
    {

        if(element.getTagName().equalsIgnoreCase("select"))
            return "select";
        if(element.getTagName().equalsIgnoreCase("input") && element.getAttribute("type").equalsIgnoreCase("checkbox"))
            return "checkbox";
        if(element.getTagName().equalsIgnoreCase("input") && element.getAttribute("type").equalsIgnoreCase("text"))
            return "text";

//        //if(element.findElements(By.xpath(".//*[@class]")).size()!=0)
//        if((element.getTagName().equalsIgnoreCase("a")||element.getTagName().equalsIgnoreCase("button") )&& element.getAttribute("class").equalsIgnoreCase("button primary"))
//            return "button";
//
//        WebElement menupageelement;
//        if(driver.findElements(By.xpath("//a[text()='Pages']//following-sibling::ul/li[@class='slide-sel slide-item']//a")).size()>0)
//        {
//            menupageelement = driver.findElement(By.xpath("//a[text()='Pages']//following-sibling::ul/li[@class='slide-sel slide-item']//a"));
//            if (menupageelement.equals(element))
//                return "menupage";
//        }

        return "buttonorlink";
    }
//this function is to dertermine if javascriptclick needed or not based on button and menu as they done register the click always with click() method
    public Boolean JavascriptClickNeeded (WebElement element) throws Exception
    {

       //if element is button type
        if((element.getTagName().equalsIgnoreCase("a")|| element.getTagName().equalsIgnoreCase("button"))
                &&
           (element.getAttribute("class").contains("button primary")|| element.getAttribute("class").equalsIgnoreCase("button")||
            element.getAttribute("type").equalsIgnoreCase("button")))
            return true;

        List<WebElement> menupageelement;
        //if element is page menu type it will match with at least one item from list items in the page menu element
        if(driver.findElements(By.xpath("//a[text()='Pages']//following-sibling::ul/li[@class='slide-sel slide-item']//a")).size()>0)
        {
            menupageelement = driver.findElements(By.xpath("//a[text()='Pages']//following-sibling::ul/li[contains(@class,'slide-item')]//a"));
           for(WebElement each:menupageelement)
           {
               if (element.equals(each))
                   return true;
           }
        }

        return false;
    }
    //TODO work in progress
    public Boolean checkforclick(By by) throws Exception
    {
        List<WebElement> element = driver.findElements(by);
        List<WebElement> selectedpagemenu = driver.findElements(By.xpath("//a[text()='Pages']//following-sibling::ul/li[@class='slide-sel slide-item']//a"));
        WebElement clickedpage;
        List<WebElement> menupageelement;
        boolean menupage =false;
        //if element is page menu type
        if(selectedpagemenu.size()>0 && element.size()>0)
        {
            menupageelement = driver.findElements(By.xpath("//a[text()='Pages']//following-sibling::ul/li[contains(@class,'slide-item')]//a"));
            for(WebElement each:menupageelement)
            {
                if(each.equals(element.get(0)))
                {
                  menupage =true;
                  break;
                }
            }
           if(menupage)
           {
               clickedpage = driver.findElement(By.xpath("//a[text()='Pages']//following-sibling::ul/li[@class='slide-sel slide-item']//a"));
               if (element.get(0).equals(clickedpage))
                   return true;
               else
                   return false;

           }
        }
        return true;
    }

    private String mainWindow = null;
    private String childWindow = null;

    // array of window handles by drivers
    // first array item is always the mainwindow
    private static Map<WebDriver, List<String>> _windowHandles = new HashMap<>();

    public void getMainWindowHandle()
    {
//        mainWindow = ExecutionManager.getWindowHandle(driver);
//        System.out.println(mainWindow);
//
//        // new code replaces above
//        // where no driver, add mainwindow handle
//        if (! _windowHandles.containsKey(driver))
//        {
//            _windowHandles.put(driver, new ArrayList<>());
//            _windowHandles.get(driver).add(ExecutionManager.getWindowHandle(driver));
//        }
//
//        mainWindow = _windowHandles.get(driver).get(0);
//        System.out.println(_windowHandles.get(driver).get(0));
        // end new code
    }

    public boolean getChildWindowHandleAndSwitch()
    {
        Boolean switchWindowSucessful = false;
        Set<String> myWindowHandles = driver.getWindowHandles();

        for (String myWindowHandle : myWindowHandles) {

            if (!myWindowHandle.equals(mainWindow)) {
                System.out.println(myWindowHandle);
                driver.switchTo().window(myWindowHandle);
                switchWindowSucessful = true;

                childWindow = driver.getWindowHandle();
            }
        }
        if (switchWindowSucessful == false) {
            System.out.println("Did not switch windows");
            return false;
        }

        // new code replaces above
        // we should have the mainwindow by now
        // but check anyway
        if (! _windowHandles.containsKey(driver))
            getMainWindowHandle();

        // set child window
        if (_windowHandles.containsKey(driver))
        {
            List<String> tmpArrayList = _windowHandles.get(driver);
            Set<String> tmpWindowHandles = driver.getWindowHandles();

            String tmpWindowHandle = "";

            // where new window
            if (tmpArrayList.size() < tmpWindowHandles.size())
            {
                for (String thisWindowHandle : tmpWindowHandles)
                {
                    if (! tmpArrayList.contains(thisWindowHandle))
                    {
                        tmpArrayList.add(thisWindowHandle);
                        break;
                    }
                }
            }

            // where window removed
            if (tmpArrayList.size() < tmpWindowHandles.size())
            {
                for (String thisWindowHandle : tmpArrayList)
                {
                    if (! tmpWindowHandles.contains(thisWindowHandle))
                    {
                        tmpArrayList.remove(thisWindowHandle);
                        break;
                    }
                }
            }

            tmpWindowHandle = tmpArrayList.get(tmpArrayList.size() -1);
            System.out.println(tmpWindowHandle);
            driver.switchTo().window(tmpWindowHandle);
            switchWindowSucessful = true;

            childWindow = driver.getWindowHandle();

        }

        if (switchWindowSucessful == false) {
            System.out.println("Did not switch windows");
            return false;
        }
        // end new code

        return true;
    }

    public List<String> getTableColumnData(String table, int columnNumber) throws Exception
    {
        if (!(table != null && !table.isEmpty()) || columnNumber < 0)
            return null;

        int attempts = 0;

        List<String> columnDataList = new ArrayList<>();

        while (attempts <= attemptcount)
        {
            try
            {
                By by = getByLocator(table);
                WebElement element = pollForWebElement(by, 180);

                if (element == null)
                {
                    throw new Exception("Unable to locate table element");
                }
                else
                {
                    // finds all elements inside within row tag
                    List<WebElement> tablerows = element.findElements(By.tagName("tr"));

                    for (int i = 0; i < tablerows.size(); i++)
                    {
                        String reportName = tablerows.get(i).findElements(By.tagName("td")).get(1).getText();

                        columnDataList.add(reportName);
                    }

                    break;
                }
            }
            catch (StaleElementReferenceException e)
            {
                System.out.println("in some stale element");

                if (attempts == attemptcount)
                    throw new Exception();
            }

            attempts++;
        }

        return columnDataList;
    }

    public void switchToParentWindow()
    {
        driver.switchTo().window(mainWindow);
    }

    public void closeChildWindows()
    {
        Set<String> myWindowHandles = driver.getWindowHandles();

        int windows = myWindowHandles.size();

        for (String myWindowHandle : myWindowHandles)
        {
            if (!myWindowHandle.equals(mainWindow))
            {
                System.out.println(myWindowHandle);
                driver.switchTo().window(myWindowHandle);
                driver.close();

                windows -= 1;
            }
        }
        if (windows != 1)
        {
            System.out.println("Did not close all windows");
        }

        // new code replaces above
        List<String> tmpArrayList = _windowHandles.get(driver);
        if (tmpArrayList.size() > 1)
        {
            String windowHandle = tmpArrayList.get(tmpArrayList.size() - 1);
            tmpArrayList.remove(windowHandle);

            // uncomment when old code is replaced
//            driver.switchTo().window(windowHandle);
//            driver.close();
        }
        // end new code

        driver.switchTo().window(mainWindow);
    }


    public void javascriptClick(String locator, String messageOnError) throws Exception
    {
        WebElement element = findLocator(locator, 15000);
        if (element == null) {
            throw new Exception("Unable to locate element " + messageOnError);
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", element);

//            JavascriptExecutor js = (JavascriptExecutor)driver;
//            js.executeScript("arguments[0].click();", element);
        }
    }

    public void clearTextBox(String fieldname, String elementName) throws Exception
    {
        clearTextBox(getByLocator(fieldname), elementName);
    }

    public void clearTextBox(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 0);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else
        {
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            //element.clear();
        }
    }
    public void SwitchToFrame(String frameName,String message) throws Exception
    {
        WaitforPageLoad();
        sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getByLocator(frameName)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//body")));

    }

    public void frameWithoutLocator(By by, String elementName) throws Exception
    {
        WebElement Frame = pollForWebElement(by, 20);
        if (Frame == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            sleep(3000);
            driver.switchTo().frame(Frame);
        }
    }

    public void SwitchTodefaultContent() throws Exception
    {
        //Thread.currentThread().sleep(3000);
        driver.switchTo().defaultContent();
    }


    public String elementGetHref(String fieldname, String elementName) throws Exception
    {
        return elementGetHref(getByLocator(fieldname), elementName);
    }

    public String elementGetHref(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        String webElementText;
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            webElementText = element.getAttribute("href");
        }
        return webElementText;
    }

    public String elementGetText(String fieldname, String elementName) throws Exception
    {
        return elementGetText(getByLocator(fieldname), elementName);
    }

    public String elementGetText(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        String webElementText;

        if (element == null)
        {
            throw new Exception("Unable to locate element " + elementName);
        }
        else
        {
            webElementText = element.getText();

            // where blank, try alternate
            // this caters for input fields
            if ( ! (webElementText != null && ! webElementText.isEmpty()))
            {
                webElementText = element.getAttribute("value");
            }
        }

        return webElementText;
    }

    public String elementGetTextIgnoreFailure(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        String webElementText;
        if (element == null) {
            webElementText="NO Record Found";
        } else {
            webElementText = element.getText();
        }
        return webElementText;
    }

    public void keyboardEnter(String fieldname, String elementName) throws Exception
    {
        keyboardEnter(getByLocator(fieldname), elementName);
    }

    public void keyboardEnter(By by, String elementName) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 20);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                } else {
                    element.click();
                    element.sendKeys(Keys.ENTER);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }

    public void pressKeyboardEnter (By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.sendKeys(Keys.ENTER);
        }

    }

    public void enterInvestmentAmount(By by, String elementName, String textToBeEntered) throws Exception
    {
        WebElement element = pollForWebElement(by, 180);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(textToBeEntered);
        }
    }

    public void keyboardA(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.sendKeys(Keys.SEMICOLON);
        }
    }

    public void keyboardDown(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.sendKeys(Keys.DOWN);
        }
    }

    //added function to click tab only -manish (kevin style to use method which accepts string and gets bylocator to use in actual function)
    public final void keyboardTabOnly(String fieldname, String elementName) throws Exception
    {
        keyboardTabOnly(getByLocator(fieldname),elementName);
    }
    //added function to click tab only -manish
    public void keyboardTabOnly(By by, String elementName) throws Exception
    {
        int attempts = 0;

        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 20);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                } else {
                    element.sendKeys(Keys.TAB);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }

    public void keyboardTab(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.sendKeys(Keys.TAB);
        }
    }

    public String captureTodayDate() throws Exception
    {
        // Create object of SimpleDateFormat class and decide the format
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //get current date time with Date()
        Date date = new Date();

        // Now format the date
        String todayDate = dateFormat.format(date);

        return todayDate;
    }

    public String GetPastDate() throws Exception
    {
        String formatted = "";
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date today = Calendar.getInstance().getTime();
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            String reportDate = df.format(today);
            calendar.setTime(df.parse(reportDate));
            //calendar.
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            formatted = simpleFormat.format(calendar.getTime());
            //System.out.println(formatted);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
        return formatted;
    }

    public String getCurrentUrl()
    {
        return driver.getCurrentUrl();
    }

    public void navigateToURL(String url)
    {
        driver.navigate().to(url);
    }

    private WebElement findLocator(String locator, int timeToIgnoreErrorInMilliSeconds) throws Exception {
        long t = System.currentTimeMillis();
        long end = t + timeToIgnoreErrorInMilliSeconds;
        long previous_t = 0;

        while (System.currentTimeMillis() < end) {
            try {

                By byOfthisLocator = getByLocator(locator,timeToIgnoreErrorInMilliSeconds);
               if(byOfthisLocator==null)
               {
                   return null;
               }
                WebElement element = driver.findElement(byOfthisLocator);
                webPageScrolltoPageElement(element, "locator :" + locator + "");
                return element;

            } catch (Exception e)
            {
                if (System.currentTimeMillis() < end)
                {
                    long seg = System.currentTimeMillis();

                    if (seg - previous_t > 5000) {
                        System.out.println((char) 27 + "[31;42min catch - " + Long.toString(seg - previous_t) + (char) 27 + "[0m" + " locator - " + locator);
                        previous_t = seg;
                    }

                    continue;
                }

                throw new Exception("Cannot find locator More than 15 secs - ", e);
            }
        }


        return null;
    }
    public By getByLocator(String locator) throws Exception
    {
        return getByLocator(locator ,15000);
    }

    public By getByLocator(String locator, int timeToIgnoreErrorInMilliSeconds) throws Exception
    {
//        By byOfthisLocator =new ByAll(By.id(locator),
//                By.name(locator),
//                By.xpath(locator),
//                By.linkText(locator),
//                By.className(locator),
//                By.cssSelector(locator),
//                By.tagName(locator),
//                By.partialLinkText(locator));

        long t = System.currentTimeMillis();
        long end = t + timeToIgnoreErrorInMilliSeconds;
        long previous_t = 0;

        while (System.currentTimeMillis() < end)
        {
            try
            {
                // TODO : how to backup default timeout? (Not sure if kevin wants to add more - can remove this comment if not needed)

                if (!driver.findElements(By.linkText(locator)).isEmpty()){
                    return By.linkText(locator);
                }
                if(!locator.startsWith("//"))
                {
                    if (!driver.findElements(By.id(locator)).isEmpty() && !locator.startsWith("//")) {
                        return By.id(locator);
                    }

                    if (!driver.findElements(By.name(locator)).isEmpty()) {
                        return By.name(locator);
                    }
                }
                else
                {
                    if (!driver.findElements(By.xpath(locator)).isEmpty() && locator.startsWith("//")) {
                        return By.xpath(locator);
                    }

                }
                if (!driver.findElements(By.tagName(locator)).isEmpty()) {
                    return By.tagName(locator);
                }


                if (!driver.findElements(By.className(locator)).isEmpty()) {
                    return By.className(locator);
                }

                if (!driver.findElements(By.cssSelector(locator)).isEmpty() && !locator.startsWith("//")) {
                    return By.cssSelector(locator);
                }

                if (!driver.findElements(By.partialLinkText(locator)).isEmpty()) {
                    return By.partialLinkText(locator);
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < end)
                {
                    long seg = System.currentTimeMillis();

                    if (seg - previous_t > 5000) {
                        System.out.println((char) 27 + "[31;42min catch - " + Long.toString(seg - previous_t) + (char) 27 + "[0m" + " locator - " + locator);
                        previous_t = seg;
                    }

                    continue;
                }

                throw new Exception("Cannot find By More than 15 secs - ", e);
            }
        }
        return null;

    }

    public void enterValue(String locator,  String textToBeEntered,String messageOnError) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = findLocator(locator,15000);
                if (element == null) {
                    throw new Exception("Unable to locate element " + messageOnError);
                } else {
                    element.click();
                    element.clear();
                    element.sendKeys(textToBeEntered);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }


    public void enterRichValue(String locator, String textToBeEntered, String messageOnError) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = findLocator(locator, 15000);
                if (element == null)
                {
                    throw new Exception("Unable to locate element " + messageOnError);
                }
                // Switch to frame
                driver.switchTo().frame(element);
                element.sendKeys(Keys.CONTROL + "a");
                element.sendKeys(textToBeEntered);
                SwitchTodefaultContent();
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }



    }
    //Created by Ananth
    public String getRichValue(String fieldname, String elementName) throws Exception{
        return getRichValue(getByLocator(fieldname), elementName);
    }
    //Created by Ananth
    public String getRichValue(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 180);
        if (element == null)
            throw new Exception("Unable to locate element " + elementName);

        // Switch to frame
        driver.switchTo().frame(element);
        element = pollForWebElement(By.tagName("body"), 180);
        if (element == null)
            throw new Exception("Unable to locate element " + elementName);

        sleep(3000);
        element.click();
        // NOTE - important
        // Switch back out
        SwitchTodefaultContent();
        //return element.getTagName();
        return element.getText();
    }
    public void enterValueThenPressReturn(By by, String elementName, String text) throws Exception
    {
        WebElement element = pollForWebElement(by, 180);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            sleep(1000);
            element.click();
            element.clear();
            element.sendKeys(text);
            element.sendKeys(Keys.RETURN);
        }
    }
    //added function to entervalue without clear function -(kevin style to use method which accepts string and gets bylocator to use in actual function) - Manish
    public void enterValueWithoutClear(String fieldname, String elementName,String text) throws Exception
    {
        enterValueWithoutClear(getByLocator(fieldname),elementName ,text);
    }
    public void enterValueWithoutClear(By by, String elementName, String text) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 20);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                } else {
                    element.click();
                    sleep(1000);
                    element.sendKeys(text);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }

    }

    public void textBoxClearClear(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.clear();
        }
    }

    public boolean elementDisplayed(String locator, String message) throws Exception
    {
         By by = getByLocator(locator,5000);
       if(driver.findElements(by).size()>0){
           return true;
       }
       return false;

    }

    public boolean elementPresent(String locator, String messageOnError) throws Exception
    {
        try
        {
            if(driver.findElements(getByLocator(locator)).size() > 0)
                return true;
            else
                return false;

        }catch (Exception e)
        {
            return false;
        }
    }


    public void WaitUntilElementDisplayed(String elementname) throws Exception
    {
        WaitUntilElementDisplayed(By.xpath(elementname));
    }
    public void WaitUntilElementDisplayed(By elementname) throws Exception
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(InvalidSelectorException.class)
                    .ignoring(StaleElementReferenceException.class);
        try
        {
            ExpectedCondition<Boolean> elementDisplayed = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(elementname);
                    return (element!=null);
                }
            };
            System.out.println("element found : "+driver.findElements(elementname));
            wait.until(elementDisplayed);
            System.out.println("element found : "+driver.findElements(elementname));
        }
        catch (Exception e)
        {
            System.out.println("Waiting for element to become visible or display");
            throw e;
        }

    }

    public void WaitforElementVisibility(By elementname) throws Exception
    {
      try
      {
          Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(driver)
                  .withTimeout(Duration.ofSeconds(30))
                  .pollingEvery(Duration.ofSeconds(1))
                  .ignoring(NoSuchElementException.class)
                  .ignoring(StaleElementReferenceException.class);

          stubbornWait.until(ExpectedConditions.elementToBeClickable(elementname));
          stubbornWait.until(ExpectedConditions.visibilityOfElementLocated(elementname));

      }
      catch (Exception e)
      {
          System.out.println("Waiting for element to become visible or display");
          throw e;
      }

    }
    public void WaitUntilElementDisappear(String elementname) throws Exception
    {
        WaitUntilElementDisappear(By.xpath(elementname));
    }

    public void WaitUntilElementDisappear(By elementname) throws Exception
    {
        try
        {
            Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(30,TimeUnit.SECONDS)
                    .pollingEvery(1,TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class);

            stubbornWait.until(ExpectedConditions.invisibilityOfElementLocated(elementname));
            System.out.println("element invisible");
        }
        catch (Exception e)
        {
            System.out.println("Waiting for element to become invisible");
            throw e;
        }

    }


   public void WaitforPageLoad() throws Exception
   {
       //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
       JavascriptExecutor jse = (JavascriptExecutor) driver;
       WebDriverWait wait = new WebDriverWait(driver, 180); // was 30. Increased as timing out on mozilla?

       List<String> xpathnames = new ArrayList<String>(){{
           add("//input[@class='select2-input select2-focused select2-active']"); //wait for loading icon of portfolio to dissappear if exists (quick entry)
           add("//a[contains(@class,'button primary_btn_loading')]");//wait for next buttons to load
           add("//button[contains(@class,'ui-state-active')]"); //wait for ok button to be non active
           add("//td[contains(.,'Searching..')]");//wait for search result for clients to display in add beneficiary tab
           add("//div[contains(@id,'cover_request_tabs')]/div[contains(.,'Please wait while we fetch results')]"); //wait for RR Premium Estimates loading text to disappear
           add("//div[contains(@id,'cover_request_tabs')]//div[contains(.,'Loading')]"); //wait for RR Premium Estimates loading text to disappear
           add("//em[contains(.,'Processing')]");//wait for processing while importing xtools
           add("//em[contains(.,'Loading')]");//wait for load of investments in ips table
           add("//img[contains(@src,'tiny')]");//wait for load of investments in ips table
           add("//td[contains(.,'Saving...')]");//wait for save of insurances
           add("//div[@aria-busy='true']");//dynamic wait for beneficiary dropdown to load
           add("//input[@value='save']");//wait for dfs to save
          //to investigate for xtool  dynamic waits
           //below line waits for xtools form to be enabled with an exception when import popup displayed (boolean)
           //takes care of it) i.e check for form disabled when popup not displayed
           add("//body[not(boolean(//span[contains(.,'Import Wizard')]))]//input[@name='content'][@disabled]");//wait for xtools lifestyle page load
           add("//div[contains(@id,'xplan_eggtimer') and contains(@style,'display: block;')]");// wait for loading icon to disappear if it is displayed (page level - for ef asset page)
           add("//div[contains(@class,'xplan_eggtimer_modal') and contains(@style,'block')]");// wait for loading icon to disappear if it is displayed (subpage level - for eg asset items)
           add("//*[@id='xplan_eggtimer']");// wait for loading icon to disappear if it is displayed
           add("//div[contains(@id,'cover_body') and not(contains(@style,'overflow'))]");//added for wait till the block expands while adding insurance covers
           add("//button[contains(.,'Saving')][@disabled]");//file note saving
           add("//button[contains(.,'Save')][@disabled]");//general wait while saving
           add("//form[@name='docnote_summary']/input[@value='text/plain']");//wait till doc note has changed its state to html editable (For threads)
           add("//span[contains(@id, 'wait_msg') and contains(.,'Submit mapping')]"); // wait for commpay client match submission to complete
       }};
      // String xplanmenuxpath="//body/script";//preceding::span[contains(@id,'linked_guarantors__entity_selector')]/div/input[@disabled='']

      for(int i=0;i<xpathnames.size();i++)
      {
          wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathnames.get(i))));
//          try
//          {
//              wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathnames.get(i))));
//          }
//          catch (UnhandledAlertException e)
//          {
//              // do nothing
//              System.out.println("blah");
//          }
      }
       //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xplanmenuxpath)));

       //waiting for body css to load
       waitforbodyload();

       // wait for jQuery to load if exists on the page
       ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
           @Override
           public Boolean apply(WebDriver driver) {
             // return ((long)jse.executeScript("return jQuery.active" )== 0);
               boolean Jqueryloaded =(Boolean)jse.executeScript("return !!window.jQuery && window.jQuery.active == 0");
               System.out.println("Jqueryloaded :"+ Jqueryloaded);
               return Jqueryloaded;
           }
       };
       //wait for Javascript to load
       ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
           @Override
           public Boolean apply(WebDriver driver) {
               boolean Jsloaded =jse.executeScript("return document.readyState").equals("complete");
            //   System.out.println("jsloaded :"+ Jsloaded);
               return Jsloaded;
           }
       };
      // wait.until(jQueryLoad);
       wait.until(jsLoad);
   }

    //not sure if this function is needed but it checks for html body is loaded
    public void waitforbodyload() throws Exception
    {
       try
       {
           new FluentWait<WebDriver>(driver)
                   .withTimeout(30, TimeUnit.SECONDS)
                   .pollingEvery(2, TimeUnit.MILLISECONDS)
                   .ignoring(WebDriverException.class)
                   .ignoring(NoSuchElementException.class)
                   .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));

       } catch (Exception e)
       {
           System.out.println("Waiting for css selector body to display on DOM");
       }

    }
    //experimental method to wait for dropdowns to load
    public void waitfordropdownload(String locator) throws Exception
    {
        WebDriverWait wait = new WebDriverWait(driver, 180);
        final Select dropdownlist = new Select(driver.findElement(getByLocator(locator)));
        try
        {

            ExpectedCondition<Boolean> Dropdownlistpopulated = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    // return ((long)jse.executeScript("return jQuery.active" )== 0);
                    System.out.println("file note type populated with :"+ dropdownlist.getOptions().size());
                    return (dropdownlist.getOptions().size()>1);
                }
            };
            wait.until(Dropdownlistpopulated);

        } catch (Exception e)
        {
            System.out.println("Waiting for dropdownlist to display on DOM");
        }

    }
    public void WaitForFieldPopulation(String locator) throws Exception
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        By by =getByLocator(locator);
        pollForWebElement(by, 5);
        try
        {

            ExpectedCondition<Boolean> Fieldpopulated = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(by);
                    System.out.println("subject text : "+element.getAttribute("value")+ " subject empty :"+ element.getAttribute("value").isEmpty());
                    return (!element.getAttribute("value").isEmpty());
                }
            };
            wait.until(Fieldpopulated);


        } catch (Exception e)
        {
            System.out.println("Waiting for field population to display on DOM");
        }

    }
    public boolean elementNotDisplayedPositiveScenario(String locator, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(By.xpath(locator), 3);
        if (element == null) {
            return true;
        } else {
            return false;

        }
    }
    public boolean elementNotDisplayedPositiveScenario(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 10);
        if (element == null) {
            return true;
        } else {
            return false;

        }
    }

    public void webPageScrollDown() throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 250);");
    }

    public void webPageScrollUp() throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -250);");
    }

    public void webPageScrollBottom() throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
    }
    

    public void webPageScrolltoPageBottom() throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, document.body.scrollHeight);");
    }
    public void webPageScrolltoPageElement(WebElement element, String e) throws Exception {
        if (element == null) {
            throw new Exception("Unable to locate element " + e);
        } else {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }
    
    public void webPageScrollUp(int i) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -"+String.valueOf(i)+");");
    }
    
    public void webPageScrollDown(int i) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, "+String.valueOf(i)+");");
    }


    //added by Manish
    public void webPageScrolltoPageTop() throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0,0);");
    }

    public void elementGetRichText(String f,String e) throws Exception {
        WebElement webElement=pollForWebElement(getByLocator(f),20);
        if (webElement == null) {
            throw new Exception("Unable to locate element " + e);
        } else {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView(true);", webElement);
        }
    }

    public void selectVisibleText(String fieldname, String elementName, String value) throws Exception
    {
        selectVisibleText(getByLocator(fieldname), elementName, value);
    }

    public void selectVisibleText(By by, String elementName, String visibleText) throws Exception
    {
        int attempts = 0;
        waitforbodyload();

        while(attempts <=attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 180);
                if (element == null) {
                    throw new Exception("Unable to locate drop down text " + elementName);
                } else {
                    Select dropdown = new Select(element);

                        dropdown.selectByVisibleText(visibleText);

                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;
        }


    }

    public String getVisibleText(String fieldname, String elementName) throws Exception
    {
        return getVisibleText(getByLocator(fieldname), elementName);
    }

    public String getVisibleText(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 180);
        if (element == null) {
            throw new Exception("Unable to locate drop down text " + elementName);
        } else {
            Select dropdown = new Select(element);
            return dropdown.getFirstSelectedOption().getText();
        }
    }
    //added function to return list of options from dropdown -Manish

    public List<String> GetListOfDropDown(String elementname) throws Exception{

       try
       {
           WebElement Dropdown = driver.findElement(getByLocator(elementname));

           Select DropDownoptions = new Select(Dropdown);
           List<WebElement> optionslist = DropDownoptions.getOptions();
           List<String> DropDownList = new ArrayList<>();
           ;
           for (WebElement option : optionslist) {
               DropDownList.add(option.getText());
           }
           return DropDownList;
       }
       catch (Exception StaleElementReferenceException)
       {
           WebElement Dropdown = driver.findElement(getByLocator(elementname));

           Select DropDownoptions = new Select(Dropdown);
           List<WebElement> optionslist = DropDownoptions.getOptions();
           List<String> DropDownList = new ArrayList<>();
           ;
           for (WebElement option : optionslist) {
               DropDownList.add(option.getText());
           }
           return DropDownList;
       }

    }

    public void selectVisible(By by, String elementName, String visibleText) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate" + elementName);
        } else {
            element.click();
            element.sendKeys(visibleText);
        }
    }

    public void selectByValue(By by, String elementName, String value) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate drop down text " + elementName);
        } else {
            Select dropdown = new Select(element);
            dropdown.selectByValue(value);
        }
    }

    public void switchToAlert()
    {
        //driver.switchTo().alert();
        driver.switchTo().activeElement();
    }

    public void acceptAlert() throws Exception
    {
        driver.switchTo().alert().accept();
    }

    public String getAlertText()
    {
        return driver.switchTo().alert().getText();
    }

    public boolean hasElement(By by)
    {
        return !driver.findElements(by).isEmpty();
    }

    public void refreshPage()
    {
        driver.navigate().refresh();
    }

    public void browseBack()
    {
        driver.navigate().back();
    }

    public boolean IsRadioButtonSelected(By by, String elementName) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        boolean selectValue;
        if (element == null) {
            throw new Exception("Unable to locate drop down text " + elementName);
        } else {
            selectValue = element.isSelected();
        }
        return selectValue;
    }

    public String generateRandomNumber() throws Exception
    {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss");
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);
        return reportDate;
    }

    public String GetFutureDate(int noOfdays) throws Exception
    {
        String formatted = "";
        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date today = Calendar.getInstance().getTime();
            // Using DateFormat format method we can create a string
            // representation of a date with the defined format.
            String reportDate = df.format(today);
            calendar.setTime(df.parse(reportDate));
            //calendar.
            calendar.add(Calendar.DAY_OF_MONTH, noOfdays);
            formatted = simpleFormat.format(calendar.getTime());
            //System.out.println(formatted);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
        return formatted;
        //calendar.setTime(new Date());
        // calendar.add(Calendar.DATE, 3);
        //String Final_admitDT = df.format(calendar.getTime());
        //return Final_admitDT;
    }

    public void selectOptionInputRadioText(String fieldname, String radioSelection) throws Exception
    {
        // contains will always return true where "" so explicit check
        if(radioSelection.equals(""))
            return;

        List<WebElement> radios = driver.findElements(getByLocator(fieldname));

        Boolean failed = true;

        if (!radios.isEmpty()) {
            // may need to change this
            String tmp = radios.get(0).findElement(By.xpath("./..")).getText();
            List<String> tmpStrings = Arrays.asList(tmp.trim().split("\n"));

            //  List<String> tmpStrings = Arrays.asList(tmp.replace("\u00a0","").trim().split("\n")); //.replace("\u00a0","") will remove &nbsp characters as this will not be picked up by trim

            for (int i = 0; i < radios.size(); i++) {
                if (tmpStrings.get(i).toLowerCase().contains(radioSelection.toLowerCase())) {
                    // select if not already selected
                    if (!radios.get(i).isSelected())
                        radios.get(i).click();

                    failed = false;
                    break;
                }
            }
        }

        if (failed) {
            throw new Exception("Cannot set radio Option - " + radioSelection);
        }
    }

    public void selectCheckboxByLabel(String CheckboxGrouplocator,String Checkboxvalue) throws Exception
    {
        List<WebElement> CheckboxContainer = driver.findElements(getByLocator(CheckboxGrouplocator));
        if (CheckboxContainer.size()!=0)
        {
            WebElement CheckboxContainerElement = driver.findElement(getByLocator(CheckboxGrouplocator));
            List<WebElement> CheckboxElements = CheckboxContainerElement.findElements(By.xpath("//input[@type='checkbox']"));


            for(WebElement checkboxelement : CheckboxElements)
            {
               List <WebElement> ElementByName = checkboxelement.findElements(By.xpath("//label[contains(text(),'"+Checkboxvalue+"')]/preceding-sibling::input[@type='checkbox']"));
                if(ElementByName.size()!=0)
                {
                    WebElement Element = checkboxelement.findElement(By.xpath("//label[contains(text(),'"+Checkboxvalue+"')]/preceding-sibling::input[@type='checkbox']"));
                    if(!Element.isSelected())
                    {
                        Element.click();
                        return;
                    }
                }
                List <WebElement> ElementByName2 = checkboxelement.findElements(By.xpath("//*[contains(text(),'"+Checkboxvalue+"')]/preceding-sibling::*/input[@type='checkbox']"));
                if(ElementByName2.size()!=0)
                {
                    WebElement Element = checkboxelement.findElement(By.xpath("//*[contains(text(),'"+Checkboxvalue+"')]/preceding-sibling::*/input[@type='checkbox']"));
                    if(!Element.isSelected())
                    {
                        Element.click();
                        return;
                    }
                }

            }
            throw new NotFoundException("checkbox " + Checkboxvalue + " not found in Group" + CheckboxGrouplocator);
        }

    }

    public void selectOptionInputRadio(String strRadioGroupName, int option) throws Exception
    {
        List<WebElement> radios = driver.findElements(getByLocator(strRadioGroupName));
        if (option > 0 && option <= radios.size())
        {
            // select if not already selected
            if (!radios.get(option - 1).isSelected())
                radios.get(option - 1).click();
        }
        else
        {
            throw new NotFoundException("option " + option + " not found in Group" + strRadioGroupName);
        }
    }

    public boolean isAlertPresent() throws Exception
    {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public void compareTest(By by, String elementName, String TextToBeCompared) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        String webElementText;
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            String applicationText = element.getText();
            if (applicationText.equalsIgnoreCase(TextToBeCompared)) {
                System.out.println("Excepted Text Displayed" + TextToBeCompared);
            } else {
                throw new Exception("Excepted Text Not Displayed" + TextToBeCompared);
            }
        }
    }

    public int tableIterateClickCell(By by, String text) throws Exception
    {
        WebElement table = pollForWebElement(by, 20);
        WebElement previousCell = null;
        int flag = 0;
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        // And iterate over them, getting the cells
        outerloop:
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (WebElement cell : cells) {
                if (cell.getText().contains(text)) {

                    previousCell.click();
                    flag = flag + 1;
                    break outerloop;
                }
                previousCell = cell;
            }
        }
        return flag;
    }

    public void tableIterateVerifyText(By by, String text) throws Exception
    {
        WebElement table = pollForWebElement(by, 20);
        WebElement previousCell=null;
        int flag=0;
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        // And iterate over them, getting the cells
        outerloop:
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            for (WebElement cell : cells) {
                if (cell.getText().equalsIgnoreCase(text)) {

                    System.out.println("Expected Text Displayed" +" "+text);
                    flag=flag+1;
                    break outerloop;
                }
            }

        }
        if(flag==0)
        {
            throw new Exception("Excepted Text Not Displayed" + text);
        }
    }

    public void ClearTextBox(By by, String elementName, String text) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.clear();
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(String.valueOf(text));
        }
    }

    public void enterValueCTRL(String fieldname, String elementName,String text) throws Exception
    {
        WebElement element = pollForWebElement(getByLocator(fieldname), 20);
        if (element == null) {
            throw new Exception("Unable to locate element " + elementName);
        } else {
            element.click();
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(text);
        }
    }

    public void setAttribute(By by, String elementName, String text) throws Exception
    {
        WebElement element = pollForWebElement(by, 20);
        if(element==null){
            throw new Exception("Unable to locate element " + elementName);
        }else{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";
            js.executeScript(scriptSetAttrValue, element, "value", text);
        }
    }

    public void setAttribute(WebElement element, String elementName, String text) throws Exception
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";
        js.executeScript(scriptSetAttrValue, element, "value", text);
    }

    public void findParticularElementFromList(By by, int index) throws Exception
    {
        List<WebElement>  elements=driver.findElements(by);
        WebElement element=elements.get(index);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", element);
    }

    public int tableIterateVerifyTextReturnRow(By by, String text) throws Exception
    {
        WebElement table = pollForWebElement(by, 20);
        int rowCount=0;
        WebElement previousCell=null;
        int flag=0;
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        // And iterate over them, getting the cells
        outerloop:
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            rowCount= rowCount+1;
            for (WebElement cell : cells) {
                if (cell.getText().contains(text)) {

                    System.out.println("Expected Text Displayed" +" "+text);
                    flag=flag+1;

                    break outerloop;
                }
            }

        }
        if(flag==0)
        {
            throw new Exception("Excepted Text Not Displayed" + text);
        }
        return rowCount;
    }

    public String ReadNotepad(String PathOfFile) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(PathOfFile));
        try
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                //sb.append("");
                line = br.readLine();
            }
            return sb.toString();
        }
        finally
        {
            br.close();
        }
    }

    public void ClickTab (String tabName) throws Exception {
        clickElement("//li[@role='tab']/a[contains(.,'" + tabName + "')]", "Clicking on Tab: " + tabName);
    }

    public final String removeHashAndNumberCharacter(String fieldvalue)
    {
       //below commented code can be used generally to remove numbers and # from string (however if the we have number before # in a name that will also get replaced
       //return fieldvalue.replaceAll("\\d.#","");

       //using this code to just remove # and number after the surname and before comma for client and entity names
       String fieldvalue1="";
       String fieldvalue2="";
       if(fieldvalue.contains("#"))
            fieldvalue1 = fieldvalue.substring(0, fieldvalue.indexOf('#'));

       if(fieldvalue.contains(","))
            fieldvalue2=fieldvalue.substring(fieldvalue.lastIndexOf(','));

       return fieldvalue1+fieldvalue2;

    }

    public final void uploadFile(String fieldname, String elementName, String value) throws Exception
    {
        By by = getByLocator(fieldname);

        int attempts = 0;
        while(attempts <= attemptcount)
        {
            try
            {
                WebElement element = pollForWebElement(by, 180);
                if (element == null) {
                    throw new Exception("Unable to locate element " + elementName);
                }
                else
                {
                    element.sendKeys(value);
                }
                break;
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if (attempts == attemptcount)
                    throw new Exception();
            }
            attempts++;
        }
    }

    public final int counttabs(String tabcontainerlocator) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {
            try
            {
                List<WebElement> totaltabs = driver.findElements(By.xpath(tabcontainerlocator + "//li[@role='tab']"));
                return totaltabs.size();
            }
            catch (StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if (attempts == attemptcount)
                    throw new Exception();
            }
            attempts++;
        }
        return 6;
    }

//    public final void enterUserCredentials()
//    {
//        driver.switchTo().alert();
//        driver.switchTo().alert().accept();
//        driver.switchTo().defaultContent();
//    }


    public boolean pageContainsText(String text)
    {
        return driver.getPageSource().contains(text);
    }


    //TODO work in progress- for stale element reference exception

    private interface Trycatchforstale
    {
        void go(String a, String b, String c);
        void go(String a, String b);
    }





    // call back
//    public <T> void stackTraceCheck(T... eventParameters)
//    {
//        doWait = true;
//    }

//    public void monitorNewStrackTraceWindow(PageInterface inPage, String clientReference)
//    {
////        final Thread thisThread = Thread.currentThread(); // note
//
//        String className = inPage.getClass().toString();
//        className = className.substring(className.lastIndexOf(".") + 1);
//
//        System.out.println("monitor - " + className);
//
////        Thread t2 = new Thread(() ->
////        {
////            doWait = true;
////
////            System.out.println(doWait);
////
////            try
////            {
////                System.out.println("before wait");
////
////                long t = System.currentTimeMillis();
////                long previous_t = t;
////
////                while (true) // automation code execution
////                {
////                    long seg = System.currentTimeMillis();
////
////                    if (seg - previous_t > 30000)
////                    {
////                        break;
////                    }
////                }
////
////                System.out.println("after wait");
////                doWait = false;
////                synchronized (lock)
////                {
////                    System.out.println("                before lock notify");
////                    lock.notify();
////                    System.out.println("                after lock notify");
////                }
////
////                while (thisThread.isAlive())
////                {
////
////                }
////            }
////            catch (Exception e)
////            {
////
////            }
////            finally
////            {
////                System.out.println("thread closing");
////            }
////        });
//
//        Thread t2 = new Thread(() ->
//        {
//            int existingWindows;
//
//            try
//            {
//                DSLNew.monitoringThreadCount += 1;
//                System.out.println("monitor thread start - " + DSLNew.monitoringThreadCount);
//
//                // add dsl this.flag (in dsl)
//                // page instances (from factory) will set to false
//                // page lauch will not set flag
////                while (thisThread.isAlive())
//                while (keepMonitoring) // note
//                {
//                    existingWindows = _windowHandles.get(driver).size();
//
//                    if (! doWait)
//                    {
//                        if (existingWindows < driver.getWindowHandles().size())
//                        {
//                            // ignore monitoring for certain actions
//
//                            // download reports
//                            if (MergeDocuments.isRunning(driver))
//                                continue;
//
//                            // where flag set
//                            if (haltMonitoring)
//                                continue;
//
//
//                            // set below, so getByLocator method will trigger thread lock
//                            doWait = true;
//
//                            System.out.println("   start check stack trace");
//                            ReportWriter.checkStackTrace(clientReference);
//                            System.out.println("   end check stack trace");
//
//                            doWait = false;
//
//                            List<String> tmpArrayList = _windowHandles.get(driver);
//                            driver.switchTo().window(tmpArrayList.get(tmpArrayList.size() -1));
//
//                            synchronized (lock)
//                            {
//                                System.out.println("           before lock notify");
//                                lock.notify();
//                                System.out.println("           after lock notify");
//                            }
//                        }
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                System.out.println(e.toString());
//            }
//            finally
//            {
//                DSLNew.monitoringThreadCount -= 1;
//                System.out.println("monitor thread end - " + DSLNew.monitoringThreadCount);
//            }
//        });
//
//        t2.start(); // make sure start() and not run()
//    }

    public int GetNoOfelements(String locator) throws Exception
    {
        int attempts = 0;
        while(attempts <=attemptcount)
        {

            try
            {
                List<WebElement> TotalNoOfElements = driver.findElements(By.xpath(locator));
                return TotalNoOfElements.size();
            }
            catch(StaleElementReferenceException e)
            {
                System.out.println("in some stale element");
                if(attempts==attemptcount)
                    throw new Exception();
            }
            attempts++;

        }
        return 0;
    }

}