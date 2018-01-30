package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScreenshotHelper {

	public static void highlightElementAndMakeScreenshot(WebDriver driver, WebElement element) {
		String backgound = element.getCssValue("backgroundColor");
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);
		makeScreenshot(driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + backgound + "'", element);
	}

	public static void makeScreenshot(WebDriver driver) {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFileToDirectory(screenshot, new File("src\\test\\resources\\screenshots\\"));
		} catch (IOException e){
		}
	}
}
