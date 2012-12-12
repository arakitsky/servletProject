package com.alex.servletProject.reader;

import com.alex.servletProject.exceptions.SystemException;
import org.testng.annotations.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link com.alex.servletProject.reader.XmlMessageReader}.
 * Date: 12/11/12
 *
 * @author Alex Rakitsky
 */
public class XmlErrorReaderTest {

    @Test
    public void correctXmlParse() throws SystemException {

        String path = getClass().getResource(File.separator + "correct.xml").toString();
        String expected = "Test message 1";
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader(path);

        String result = xmlErrorReader.readMessage(id);

        assertEquals(expected, result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void inCorrectXmlParse() throws SystemException {
        String incorrectPath = XmlErrorReaderTest.class.getResource(File.separator + "inCorrect.xml").toString();
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader(incorrectPath);

        xmlErrorReader.readMessage(id);
    }

    @Test(expectedExceptions = SystemException.class)
    public void notFoundXmlParse() throws SystemException {
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader("blah");

        xmlErrorReader.readMessage(id);
    }
}
