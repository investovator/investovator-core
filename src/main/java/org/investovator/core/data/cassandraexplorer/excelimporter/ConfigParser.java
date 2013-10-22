package org.investovator.core.data.cassandraexplorer.excelimporter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/04/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigParser {

    private ArrayList<String> fields = null;
    private String configFilePath;

    public ConfigParser(String configFilePath){
            this.fields = new ArrayList<String>();
            this.configFilePath = configFilePath;
    }
    public ArrayList<String> getInputFields(){

            parseConfigFile();
            return fields;
    }

    private void parseConfigFile(){
        try {

            File fXmlFile = new File(configFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            String field;

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("field");

            for (int i = 0; i < nList.getLength(); i++) {

                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    field = eElement.getElementsByTagName("header").item(0).getTextContent();
                    fields.add(field);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

