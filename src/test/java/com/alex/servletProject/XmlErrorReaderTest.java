package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link com.alex.servletProject.XmlErrorReader}.
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
        XmlErrorReader xmlErrorReader = new XmlErrorReader(FILE_CORRECT_PATH);

        String result = xmlErrorReader.readError(id);

        assertEquals(expected, result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void inCorrectXmlParse() throws SystemException {
        String id = "1";
        XmlErrorReader xmlErrorReader = new XmlErrorReader(FILE_INCORRECT_PATH);

        xmlErrorReader.readError(id);
    }

    @Test(expectedExceptions = SystemException.class)
    public void notFoundXmlParse() throws SystemException {
        String id = "1";
        XmlErrorReader xmlErrorReader = new XmlErrorReader("blah");

        xmlErrorReader.readError(id);
    }
}
