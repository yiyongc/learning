package assignment.exhibitmonitor.utility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import assignment.exhibitmonitor.beans.CSVInputFile;
import assignment.exhibitmonitor.beans.CSVOutputFile;
import assignment.exhibitmonitor.beans.Field;
import assignment.exhibitmonitor.context.ApplicationContext;

public class XMLParser {
	
	private static Logger logger = Logger.getLogger("XML Parser");
	private static Document dom;
	
	private XMLParser() {}
	
	public static void parseXML() throws IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		//Using factory get an instance of document builder
		DocumentBuilder db = null;
	
		try {
			db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			if (db != null)
				dom = db.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.xml"));
			
			parseInputs();
			parseOutputs();
		} catch (SAXException | ParserConfigurationException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	private static void parseOutputs() throws ParseException {
		Element docEle = dom.getDocumentElement();

		NodeList outputFileList = docEle.getElementsByTagName("outputfile");
		
		if (outputFileList != null && outputFileList.getLength() > 0) {
			for(int i = 0 ; i < outputFileList.getLength(); i++) {

				//get the project element
				Element outputFile = (Element)outputFileList.item(i);
				
				String outputFileName = outputFile.getAttribute("name");
				String outputFileTime = outputFile.getAttribute("time");
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");				
				Date outputFileDate = sdf.parse(outputFileTime);
				
				CSVOutputFile output = new CSVOutputFile(outputFileName, outputFileDate);
				List<String> dependencies = new ArrayList<>();
				ApplicationContext.getOutputFiles().put(output, dependencies);
				
				addDependenciesForOutputFile(outputFile, dependencies);
				
			}
		}
	}

	private static void addDependenciesForOutputFile(Element outputFile, List<String> dependencies) {
		NodeList dependencyList = outputFile.getElementsByTagName("dependency");
		if (dependencyList != null && dependencyList.getLength() > 0) {
			for(int j = 0 ; j < dependencyList.getLength(); j++) {
				Element dependency = (Element) dependencyList.item(j);
				
				String nameOfDependency = dependency.getAttribute("file");
				if(!nameOfDependency.isEmpty())
					dependencies.add(nameOfDependency);
			}
		}
		
	}

	private static void parseInputs() throws ParseException {
		Element docEle = dom.getDocumentElement();

		NodeList inputFileList = docEle.getElementsByTagName("inputfile");
		
		if (inputFileList != null && inputFileList.getLength() > 0) {
			for(int i = 0 ; i < inputFileList.getLength();i++) {

				//get the project element
				Element inputFile = (Element)inputFileList.item(i);
				
				String inputFileName = inputFile.getAttribute("name");
				String inputFileTime = inputFile.getAttribute("time");
				String inputFileGrace = inputFile.getAttribute("grace-period");
				
				if(inputFileName.length() == 0 || inputFileTime.length() == 0 || inputFileGrace.length() == 0)
					continue;
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");				
				Date inputFileDate = sdf.parse(inputFileTime);
				
				CSVInputFile input = new CSVInputFile(inputFileName, inputFileDate, Byte.parseByte(inputFileGrace));
				List<Field> fileStructure = new ArrayList<>();
				ApplicationContext.getInputFiles().put(input, fileStructure);
				
				addStructureOfInputFile(inputFile, fileStructure);
			}
		}
	}

	private static void addStructureOfInputFile(Element inputFile, List<Field> fileStructure) {
		NodeList structure = inputFile.getElementsByTagName("structure");
		if (structure != null && structure.getLength() > 0) {
			for(int j = 0 ; j < structure.getLength(); j++) {
				NodeList fieldList = structure.item(j).getChildNodes();
				readFieldList(fieldList, fileStructure);
				
			}
		}
		
	}

	private static void readFieldList(NodeList fieldList, List<Field> fileStructure) {
		for (int l = 0; l < fieldList.getLength(); l++) {
			if (fieldList.item(l).getNodeType() == Node.ELEMENT_NODE) {
				Node field = fieldList.item(l);
				
				addFieldsToFileStructure(field, fileStructure);
				
			}
		}
	}

	private static void addFieldsToFileStructure(Node field, List<Field> fileStructure) {
		Field f;
		
		for (int n = 0; n < field.getAttributes().getLength(); n+=2) {
			String fieldName = field.getAttributes().item(n).getNodeValue();
			String fieldType = field.getAttributes().item(n+1).getNodeValue();
			
			if (!fieldName.isEmpty() && !fieldType.isEmpty()) {
				f = new Field(fieldName, fieldType);
				fileStructure.add(f);
			}
		}
		
	}
}
