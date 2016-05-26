package com.omniwyse.mobiletest.utils;


import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
/**
 * 
 * @author manohark
 *
 */

public class GmailUtility {
	private static String LINK_START_SENTINEL = "<a style=\"padding-left:10px;\" shape=\"rect\" href=\"";
	private static String LINK_END_SENTINEL = "\"";

	/**
	 * Gets the INBOX folder
	 * 
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public static Folder getInboxFolder(Store store) throws Exception {
		return getFolder("INBOX", store);
	}

	/**
	 * 
	 * @param folderName
	 * @param store
	 * @return
	 * @throws Exception
	 */
	public static Folder getFolder(String folderName, Store store) throws Exception {
		Folder folder = store.getFolder(folderName);
		folder.open(Folder.READ_WRITE);
		return folder;
	}

	/**
	 * Gets store
	 * 
	 * @param host
	 * @param emailAddress
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public static Store getEmailStore(String host, String emailAddress, String pass) throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		// Session session = Session.getDefaultInstance(props, props);
		Session session = Session.getInstance(props);
		Store store = session.getStore("imaps");
		store.connect(host, emailAddress, pass);
		return store;
	}

	/**
	 * Gets verification link from mail body
	 * 
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	public static String getVerificationLink(Folder folder) throws Exception {
		Message[] messages = folder.getMessages();
		Message message = null;
		System.out.println("Total messages : " + messages.length);
		for (int i = messages.length, n = 0; i > n; i--) {
			message = messages[i - 1];
			Address address = message.getFrom()[0];
			String subject = message.getSubject();
			System.out.println("---------------------------------");
			System.out.println("Email Number " + (i));
			System.out.println("Subject: " + message.getSubject());
			System.out.println("From: " + message.getFrom()[0]);
			System.out.println("Text: " + message.getContent().toString());
			System.out.println("Mail received from : " + address.toString());
			System.out.println("subject : " + subject);
			if ((address.toString().contains("no-reply@cdta.org") && subject.equals("User Signup")) || i == 100) {
				break;
			}
		}
		String emailContent = (String) ((javax.mail.internet.MimeMultipart) message.getDataHandler().getContent()).getBodyPart(0).getContent();// buffer.toString();
		System.out.println("Email content : " + emailContent);
		int startIndex = emailContent.indexOf(LINK_START_SENTINEL) + LINK_START_SENTINEL.length();
		int endIndex = emailContent.indexOf(LINK_END_SENTINEL, startIndex);
		return emailContent.substring(startIndex, endIndex);
	}

	/**
	 * Move the messages from folder to trash folder
	 * 
	 * @param folder
	 * @param trashFolder
	 * @throws Exception
	 */
	public static void purgeMessages(Folder folder, Folder trashFolder) throws Exception {
		Message[] messages = folder.getMessages();
		if (messages == null) {
			return;
		}
		folder.copyMessages(messages, trashFolder);
	}

	/**
	 * Will get the verification link from gmail inbox.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVerificationLinkFromGmail(String email, String password) throws Exception {

		String emailHost = "imap.gmail.com";
		System.out.println("Email:Password::" + email + ":" + password);
		Store emailStore = getEmailStore(emailHost, email, password);
		Folder testInbox = getInboxFolder(emailStore);

		// waitForEmailToGet(testInbox, 2, 60000l);
		String verificationLink = getVerificationLink(testInbox);
		System.out.println("Verification link from gmail : " + verificationLink);
		return verificationLink;
	}
}
