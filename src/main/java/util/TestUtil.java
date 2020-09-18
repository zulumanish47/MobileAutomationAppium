package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TestUtil {


	private static TestUtil utils = null;

	//private constructor - cant instantiate
	private TestUtil(){
		this.log().info("Thread id :"+ Thread.currentThread().getId()+" Launching constructor of "+this.getClass().getSimpleName()+"\n");
	}

	public static TestUtil getInstance()
	{
		if(utils==null)
		{
			utils= new TestUtil();
		}
		return utils;
	}

	// this method will parse xml and return hashmap of key value pairs
	public HashMap<String, String> parseStringXML(InputStream file) throws Exception{
		HashMap<String, String> stringMap = new HashMap<String, String>();
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Build Document
		Document document = builder.parse(file);

		//Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		//Here comes the root node
		Element root = document.getDocumentElement();

		//Get all elements
		NodeList nList = document.getElementsByTagName("string");

		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) node;
				// Store each element key value in map
				stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
			}
		}
		return stringMap;
	}

    //This method will return JSON object and accepts file name as parameter (assuming file is in src/main/resources)
	public JSONObject getJSONData(String dataFileName)
	{
		InputStream dataIS;
		JSONObject jsonObject;

		dataIS=getClass().getClassLoader().getResourceAsStream(dataFileName);
		JSONTokener jsonTokener = new JSONTokener(dataIS);
		return jsonObject = new JSONObject(jsonTokener);
	}


    //This method will return current DateTime in yyyy-MM-dd-HH-mm-sss format
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

    //log 4j logger
	public Logger log()
	{
		return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}


	//manual log with log4j framework
	public void log1(String txt) {

		String msg = Thread.currentThread().getId() + ":"
				+ GlobalParams.getInstance().getPlatform() + ":"
				+ GlobalParams.getInstance().getDeviceName() + ":"
				+ Thread.currentThread().getStackTrace()[2].getClassName() + ":"
				+ txt;

		System.out.println(msg);

		String strFile = "logs"
				+ File.separator
				+ GlobalParams.getInstance().getPlatform()
				+ "_"
				+ GlobalParams.getInstance().getDeviceName()
				+ File.separator
				+ getDateTime();

		File logFile = new File(strFile);

		if (!logFile.exists()) {
			logFile.mkdirs();
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(logFile + File.separator + "log.txt",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(msg);
		printWriter.close();
	}





	//public static String TESTDATA_SHEET_PATH = "C:/Users/manish meera/Documents/workspace_sais/PageObjectModel-master"
//			+ "/src/main/java/com/crm/qa/testdata/FreeCrmTestData.xlsx";
//
//	static Workbook book;
//	static Sheet sheet;
//	static JavascriptExecutor js;

//	public static void takeScreenshotAtEndOfTest() throws IOException {
//		File scrFile = ((TakesScreenshot) ).getScreenshotAs(OutputType.FILE);
//		String currentDir = System.getProperty("user.dir");
//		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
//	}




//	public static void runTimeInfo(String messageType, String message) throws InterruptedException {
//		js = (JavascriptExecutor) ad;
//		// Check for jQuery on the page, add it if need be
//		js.executeScript("if (!window.jQuery) {"
//				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
//				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
//				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
//		Thread.sleep(5000);
//
//		// Use jQuery to add jquery-growl to the page
//		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");
//
//		// Use jQuery to add jquery-growl styles to the page
//		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
//				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
//		Thread.sleep(5000);
//
//		// jquery-growl w/ no frills
//		js.executeScript("$.growl({ title: 'GET', message: '/' });");
////'"+color+"'"
//		if (messageType.equals("error")) {
//			js.executeScript("$.growl.error({ title: 'ERROR', message: '"+message+"' });");
//		}else if(messageType.equals("info")){
//			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
//		}else if(messageType.equals("warning")){
//			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
//		}else
//			System.out.println("no error message");
//		// jquery-growl w/ colorized output
////		js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
////		js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
////		js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
//		Thread.sleep(5000);
//	}
	//
//	public static Object[][] getTestData(String sheetName)
//	{
//		FileInputStream file = null;
//		try {
//			file = new FileInputStream(TESTDATA_SHEET_PATH);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			book = WorkbookFactory.create(file);
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		sheet = book.getSheet(sheetName);
//		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
//		// System.out.println(sheet.getLastRowNum() + "--------" +
//		// sheet.getRow(0).getLastCellNum());
//		for (int i = 0; i < sheet.getLastRowNum(); i++) {
//			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
//				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
//				// System.out.println(data[i][k]);
//			}
//		}
//		return data;
//	}

}
