package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import bo.Email;
import bo.User;

public class MailBoxTest extends BaseTest {

	private final static int NUMBER_OF_EMAILS = 3;

	/**Login to the mail box -> assert, that the login is successful -> create a new mail (fill addressee, subject and body fields) ->
	   save the mail as a draft -> verify, that the mail presents in ‘Drafts’ folder -> verify the draft content
	   (addressee, subject and body – should be the same as in 3) -> send the mail -> verify, that the mail disappeared from ‘Drafts’ folder ->
		verify, that the mail is in ‘Sent’ folder -> Log off.*/
	@Test
	public void draftsAndSentFoldersTest() {
		User user = userService.getUser();
		assertEquals(userService.getActualUserName(), user.getLogin());
		int numberEmailsInSent = folderService.getnumberEmailsInSent();
		Email email = emailService.createEmailAndSaveToDrafts();
		assertTrue(folderService.isEmailPresentInDraftsList(email.getSubject()));
		folderService.openEmailBySubject(email.getSubject());
		assertEquals(emailService.getActualEmailAdress(), email.getAdress());
		assertEquals(emailService.getActualEmailSubject(), email.getSubject());
		assertEquals(emailService.getActualEmailBody(), email.getBody());
		folderService.sendEmailFromDraft(email.getSubject());
		assertFalse(folderService.isEmailPresentInDraftsList(email.getSubject()));
		folderService.openSentFolder(numberEmailsInSent+1);
		assertTrue(folderService.isEmailPresentInSentList(email.getSubject()));
	}

	/** Create a number of new emails and send them -> go to 'Sent' folder -> multi-select ALL emails (shift + LM)
	 -> drag and drop to trash -> highlight empty folder message and make screenshot -> check 'Sent' folder is empty
	 -> Log off.**/
 	@Test
 	public void advanceActionsTest(){
		emailService.createANumberOfEmailsWithRandomDataAndSendThem(NUMBER_OF_EMAILS);
		folderService.moveAllEmailsInSentIntoTrashAndHighlightSuccessMessage();
		assertTrue(folderService.isSentFolderEmpty());
	}

}
