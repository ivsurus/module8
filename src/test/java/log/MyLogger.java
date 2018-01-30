package log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import test.MailBoxTest;

public class MyLogger {

	private static  final Logger logger = LogManager.getLogger(MailBoxTest.class);

	private MyLogger() {
	}

	public static Logger getLogger() {
		return logger;
	}

}
