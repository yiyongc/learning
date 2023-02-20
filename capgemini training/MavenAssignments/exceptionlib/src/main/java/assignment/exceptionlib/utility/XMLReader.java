package assignment.exceptionlib.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

import assignment.exceptionlib.beans.ActionInfo;
import assignment.exceptionlib.beans.ProjectInfo;
import assignment.exceptionlib.storage.IExceptionHandlerStorage;

public class XMLReader {
	
	Document dom;
	IExceptionHandlerStorage storage;
	
	Logger logger = Logger.getLogger("XMLReader");
	
	public XMLReader(String file, IExceptionHandlerStorage storage) throws IOException {
		this.storage = storage;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		//Using factory get an instance of document builder
		DocumentBuilder db = null;
		
		try {
			db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			if (db != null)
				dom = db.parse(file);
		} catch (SAXException | ParserConfigurationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	
	public void parseDocument() {
		Element docEle = dom.getDocumentElement();

		NodeList projectList = docEle.getElementsByTagName("project");

		if (projectList != null && projectList.getLength() > 0) {
			for(int i = 0 ; i < projectList.getLength();i++) {

				//get the project element
				Element project = (Element)projectList.item(i);
				
				processProject(project);

			}
		}
	}


	private void processProject(Element project) {
		//get its modules list
		NodeList moduleList = project.getElementsByTagName("module");

		if (moduleList != null && moduleList.getLength() > 0) {
			for(int j = 0 ; j < moduleList.getLength(); j++) {
				//get the module element 
				Element module = (Element)moduleList.item(j);
				
				processModule(module, project.getAttribute("name"));
			}
		}
	}


	private void processModule(Element module, String projName) {
		//get its exception list
		NodeList exceptionList = module.getElementsByTagName("exception");
		if (exceptionList != null && exceptionList.getLength() > 0) {
			for(int k = 0 ; k < exceptionList.getLength(); k++) {
				//get the exception element
				Element exception = (Element) exceptionList.item(k);
				String type = exception.getAttribute("type");

				/* Create pojo with projName, modName, type */
				ProjectInfo pInfo = new ProjectInfo(projName, module.getAttribute("name"), type);
				storage.addProjectInfo(pInfo);
				
				Set<ActionInfo> actions = storage.getActions(pInfo);
				
				processException(exception, actions);
			}
			
		}
	}


	private void processException(Element exception, Set<ActionInfo> actions) {
		//get its action list
		NodeList actionList = exception.getElementsByTagName("action");
		if (actionList != null && actionList.getLength() > 0) {

			for(int l = 0 ; l < actionList.getLength(); l++) {
				//get the action element
				Element action = (Element) actionList.item(l);

				processAction(action, actions);

			}
		}
	}


	private void processAction(Element action, Set<ActionInfo> actions) {
		for (int m = 0; m < action.getChildNodes().getLength(); m ++) {
			Node child = action.getChildNodes().item(m);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String actionName = child.getNodeName();
				Map<String, String> attributes = new HashMap<>();
				
				
				for (int n = 0; n < child.getAttributes().getLength(); n++) {
					String attributeName = child.getAttributes().item(n).getNodeName();
					String attributeValue = child.getAttributes().item(n).getNodeValue();
					
					attributes.put(attributeName, attributeValue);
				}
				
				/* create action object, add to hashset */
				ActionInfo actionToAdd = new ActionInfo(actionName, attributes);
				actions.add(actionToAdd);
			}			
		}
	}




}


	
	
