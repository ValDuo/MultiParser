package ru.sfedu.dubina.api;

import org.w3c.dom.*;
import ru.sfedu.dubina.models.BigCSVCutter;
import ru.sfedu.dubina.models.CSVReader;
import ru.sfedu.dubina.utils.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProviderXML {

    private final Logger LOGGER = Logger.getLogger(DataProviderXML.class.getName());
    private final File xmlFile;

    public DataProviderXML() {
        this.xmlFile = new File(Constants.CUTTER_XML_PATH);
        ensureFileExists();
    }

    private void ensureFileExists() {
        if (!xmlFile.exists()) {
            try {
                if (xmlFile.createNewFile()) {
                    try (var writer = new java.io.BufferedWriter(new java.io.FileWriter(xmlFile))) {
                        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<data>\n</data>");
                    }
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Ошибка при создании XML файла", e);
            }
        }
    }

    private Document getDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlFile);
    }

    private void saveDocument(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : "";
    }

    private void appendChild(Document doc, Element parent, String tagName, String value) {
        Element child = doc.createElement(tagName);
        child.setTextContent(value);
        parent.appendChild(child);
    }

    private void updateChild(Element parent, String tagName, String value) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            nodeList.item(0).setTextContent(value);
        } else {
            appendChild(parent.getOwnerDocument(), parent, tagName, value);
        }
    }

    public void create(BigCSVCutter cutter) {
        try {
            Document doc = getDocument();
            Element root = doc.getDocumentElement();

            Element cutterElement = doc.createElement("BigCSVCutter");
            cutterElement.setAttribute("id", cutter.getId().toString());

            appendChild(doc, cutterElement, "countLine", cutter.getCountLine().toString());
            appendChild(doc, cutterElement, "countFile", cutter.getCountFile().toString());
            appendChild(doc, cutterElement, "date", cutter.getDate());
            appendChild(doc, cutterElement, "folder", cutter.getFolder());
            appendChild(doc, cutterElement, "created", cutter.getCreated().toString());

            root.appendChild(cutterElement);
            saveDocument(doc);

            LOGGER.log(Level.FINE, "BigCSVCutter создан: {0}", cutter);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при создании BigCSVCutter", e);
        }
    }

    public BigCSVCutter read(UUID id) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("BigCSVCutter");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    return new BigCSVCutter(
                            Integer.parseInt(getElementValue(element, "countLine")),
                            Integer.parseInt(getElementValue(element, "countFile")),
                            getElementValue(element, "date"),
                            getElementValue(element, "folder"),
                            Boolean.parseBoolean(getElementValue(element, "created"))
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при чтении BigCSVCutter", e);
        }
        return null;
    }

    public void update(UUID id, BigCSVCutter updatedCutter) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("BigCSVCutter");
            boolean isUpdated = false;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    element.getElementsByTagName("countLine").item(0).setTextContent(String.valueOf(updatedCutter.getCountLine()));
                    element.getElementsByTagName("countFile").item(0).setTextContent(String.valueOf(updatedCutter.getCountFile()));
                    element.getElementsByTagName("date").item(0).setTextContent(updatedCutter.getDate());
                    element.getElementsByTagName("folder").item(0).setTextContent(updatedCutter.getFolder());
                    element.getElementsByTagName("created").item(0).setTextContent(String.valueOf(updatedCutter.getCreated()));
                    isUpdated = true;
                    LOGGER.log(Level.FINE, "BigCSVCutter обновлен: {0}", updatedCutter);
                    break;
                }
            }
            if (isUpdated) {
                saveDocument(doc);
            } else {
                LOGGER.log(Level.WARNING, "BigCSVCutter с ID {0} не найден для обновления", id);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при обновлении BigCSVCutter", e);
        }
    }


    public void delete(UUID id) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("BigCSVCutter");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    element.getParentNode().removeChild(element);
                    saveDocument(doc);
                    LOGGER.log(Level.FINE, "BigCSVCutter удален: {0}", id);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при удалении BigCSVCutter", e);
        }
    }


// CSVReader CRUD

    public void createCSVReader(CSVReader reader) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            File file = new File(Constants.READER_XML_PATH);
            if (file.exists()) {
                doc = builder.parse(file);
                doc.getDocumentElement().normalize();
            } else {
                doc = builder.newDocument();
                Element root = doc.createElement("CSVReaders");
                doc.appendChild(root);
            }

            Element root = doc.getDocumentElement();

            Element readerElement = doc.createElement("CSVReader");

            readerElement.setAttribute("id", reader.getId().toString());
            appendChild(doc, readerElement, "pathName", reader.getPathName());
            appendChild(doc, readerElement, "csvFile", reader.getCsvFile());
            appendChild(doc, readerElement, "words", reader.getWords().toString());

            root.appendChild(readerElement);
            saveDocument(doc);

            LOGGER.log(Level.FINE, "CSVReader создан: {0}", reader);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка на этапе создания CSVReader", e);
        }
    }

    public CSVReader readCSVReader(UUID id) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("CSVReader");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    return new CSVReader(
                            getElementValue(element, "pathName"),
                            getElementValue(element, "csvFile"),
                            Integer.parseInt(getElementValue(element, "words"))
                    );
                }
            }
            LOGGER.log(Level.FINE, "CSVReader получен.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка на этапе чтения CSVReader", e);
        }
        return null;
    }

    public void updateCSVReader(UUID id, CSVReader updatedReader) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("CSVReader");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    updateChild(element, "pathName", updatedReader.getPathName());
                    updateChild(element, "csvFile", updatedReader.getCsvFile());
                    updateChild(element, "words", updatedReader.getWords().toString());
                    saveDocument(doc);
                    LOGGER.log(Level.FINE, "CSVReader обновлен: {0}", updatedReader);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка на этапе обновления CSVReader", e);
        }
    }

    public void deleteCSVReader(UUID id) {
        try {
            Document doc = getDocument();
            NodeList nodeList = doc.getElementsByTagName("CSVReader");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                if (element.getAttribute("id").equals(id.toString())) {
                    element.getParentNode().removeChild(element);
                    saveDocument(doc);
                    LOGGER.log(Level.FINE, "CSVReader удален: {0}", id);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка на этапе удаления CSVReader", e);
        }
    }

//    // Utility methods
//
//    private Document getDocument() throws Exception {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        return builder.parse(new File(Constants.CUTTER_XML_PATH));
//    }
//
//    private void saveDocument(Document doc) throws TransformerException {
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//
//
//    }
//
//    private void appendChild(Document doc, Element parent, String tagName, String value) {
//        Element element = doc.createElement(tagName);
//        element.appendChild(doc.createTextNode(value));
//        parent.appendChild(element);
//    }
//
//    private String getElementValue(Element parent, String tagName) {
//        return parent.getElementsByTagName(tagName).item(0).getTextContent();
//    }
//
//    private void updateChild(Element parent, String tagName, String newValue) {
//        parent.getElementsByTagName(tagName).item(0).setTextContent(newValue);
//    }
}