package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import assignment.exceptionlib.service.ExceptionLibServiceImpl;
import assignment.exceptionlib.service.IExceptionLibService;

public class XMLReaderTests {

	Logger logger = Logger.getLogger("XML Tests");
	String file = "D:\\Users\\yichee\\Desktop\\Playground\\Assignment 3\\Testing.xml";
	
	@Test
	public void fileFoundForXMLReader() {		
		IExceptionLibService service = new ExceptionLibServiceImpl(file);
		
		try {
			service.init();
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	@Test (expected = java.io.IOException.class)
	public void fileNotFoundForXMLReader() throws IOException {
		String file2 = "C";
		
		IExceptionLibService service = new ExceptionLibServiceImpl(file2);
		
		service.init();
	}

	@Test
	public void incorrectXMLFileStructure() {		
		IExceptionLibService service = new ExceptionLibServiceImpl("D:\\Users\\yichee\\Desktop\\Playground\\Assignment 3\\Testing2.xml");
		
		try {
			service.init();
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		
		//Parsing fails so no projectinfos and actions
		assertEquals(0, service.getStorageEntrySize());
		assertEquals(0, service.getStorageActionSize());
	}

}
