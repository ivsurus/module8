package service;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import bo.Email;
import log.CustomLogger;
import page.DraftEmailPage;
import page.DraftsListPage;
import page.InboxListPage;
import util.RandomDataHelper;

public class EmailService {

	private WebDriver driver;
	private FolderService folderService;

	public EmailService(WebDriver driver) {
		super();
		this.driver = driver;
		folderService = new FolderService(driver);
	}

	public List<Email> createANumberOfEmailsWithRandomDataAndSendThem(int numberOfEmails){
		CustomLogger.getLogger().info(String.format("Create %s email(s) with random data", numberOfEmails));
		InboxListPage inboxListPage = new InboxListPage(driver);
		List<Email> emails = new ArrayList<>();
		for (int i=0; i<numberOfEmails; i++){
			Email email = new Email(RandomDataHelper.getRandomEmailAdress(), RandomDataHelper.getRandomEmailSubject(),
					RandomDataHelper.getRandomEmailBody());
			inboxListPage.toolbarComponent.createNewEmail().fillInEmailAdress(email.getAdress()).
			fillInEmailSubject(email.getSubject()).fillInEmailBody(email.getBody()).sendEmail();
			emails.add(email);
		}
		CustomLogger.getLogger().info(emails.toString());
		return emails;
	}

	public Email createEmailAndSaveToDrafts(){
		int numberEmailsInDrafts = folderService.getnumberEmailsInDrafts();
		CustomLogger.getLogger().info("Create email with random data");
		Email email = new Email(RandomDataHelper.getRandomEmailAdress(), RandomDataHelper.getRandomEmailSubject(),
				RandomDataHelper.getRandomEmailBody());
		new InboxListPage(driver).toolbarComponent.createNewEmail().
		fillInEmailAdress(email.getAdress()).fillInEmailSubject(email.getSubject()).
		fillInEmailBody(email.getBody()).closeEmailAndSaveToDraft().toolbarComponent.openDraftsFolder();
		CustomLogger.getLogger().info(email.toString());
		new DraftsListPage(driver).toolbarComponent.waitForChangeOfNumberOfEmailsInFolder(numberEmailsInDrafts+1);
		return email;
	}

	public String getActualEmailAdress(){
		String actualEmailAdress = new DraftEmailPage(driver).getEmailAdress();
		CustomLogger.getLogger().info(String.format("Actual email adress is: %s", actualEmailAdress));
		return actualEmailAdress;
	}

	public String getActualEmailSubject(){
		String actualEmailSubject = new DraftEmailPage(driver).getSubject();
		CustomLogger.getLogger().info(String.format("Actual email subject is: %s", actualEmailSubject));
		return actualEmailSubject;
	}

	public String getActualEmailBody(){
		String actualEmailBody = new DraftEmailPage(driver).getBody();
		CustomLogger.getLogger().info(String.format("Actual email body is: %s", actualEmailBody));
		return actualEmailBody;
	}


}
