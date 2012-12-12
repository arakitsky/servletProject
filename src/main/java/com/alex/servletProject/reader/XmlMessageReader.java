package com.alex.servletProject.reader;

import com.alex.servletProject.exceptions.SystemException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Find the error message in the xml file.
 * If the message was not found or read error is thrown {@link SystemException}.
 * Date: 12/11/12
 *
 * @author Alex Rakitsky
 */
public class XmlMessageReader extends DefaultHandler implements IMessageReader {

    private String path;


    private String id;
    private String message;
    Map<String, String> resultMap = new HashMap<>();

    /**
     * @param path path to parsed xml file.
     */
    public XmlMessageReader(String path) {
        this.path = path;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("answer")) {
            id = attributes.getValue("name");
        }
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        message = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("answer")) {
            if (id != null) {
                resultMap.put(id, message);
            }
        }
    }

    /**
     * Read the error message from xml for the respective machine.
     *
     * @param idMachine unique id name of machine.
     * @return error message
     * @throws SystemException error with the database
     */
    @Override
    public String readMessage(String idMachine) throws SystemException {
        resultMap = new HashMap<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(path, this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new SystemException(e);
        }
        String message = resultMap.get(idMachine);
        if (message == null) {
            throw new SystemException("Not found in xml " + path + "error message");
        }
        return message;
    }
}
