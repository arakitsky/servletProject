package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.XmlMessageReader;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link com.alex.servletProject.reader.XmlMessageReader}.
 * Date: 12/11/12
 *
 * @author Alex Rakitsky
 */
public class XmlErrorReaderTest {

    public static final String FILE_CORRECT_PATH = "./src/test/resourses/correct.xml";
    public static final String FILE_INCORRECT_PATH = "./src/test/resourses/inCorrect.xml";

    @Test
    public void correctXmlParse() throws SystemException {
        String expected = "Test message 1";
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader(FILE_CORRECT_PATH);

        String result = xmlErrorReader.readMessage(id);

        assertEquals(expected, result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void inCorrectXmlParse() throws SystemException {
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader(FILE_INCORRECT_PATH);

        xmlErrorReader.readMessage(id);
    }

    @Test(expectedExceptions = SystemException.class)
    public void notFoundXmlParse() throws SystemException {
        String id = "1";
        XmlMessageReader xmlErrorReader = new XmlMessageReader("blah");

        xmlErrorReader.readMessage(id);
    }
}
