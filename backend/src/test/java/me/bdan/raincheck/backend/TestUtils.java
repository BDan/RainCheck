package me.bdan.raincheck.backend;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class TestUtils {
public static 	Document parseXmlFile (String fName) throws Exception{
	File inputFile = new File(fName);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(inputFile);
	doc.getDocumentElement().normalize();
	return doc;

}
}
