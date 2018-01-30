package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import service.EmailService;
import service.FolderService;
import service.UserService;

public abstract class BaseTest {

	private final static String URL = "http://yandex.com";

	private WebDriver driver;
	protected UserService userService;
	protected EmailService emailService;
	protected FolderService folderService;


	@BeforeMethod
	protected void setUp(){
		System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chrome-driver-2.35.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(11, TimeUnit.SECONDS);
		userService = new UserService(driver);
		emailService = new EmailService(driver);
		folderService = new FolderService(driver);
		driver.get(URL);
		userService.logIn();
	}

	@AfterMethod
	protected void cleanUp(){
		userService.logOut();
		driver.quit();
	}

}

