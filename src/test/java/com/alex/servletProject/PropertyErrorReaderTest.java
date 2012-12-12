package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.PropertyMessageReader;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * .
 * Date: 12/12/12
 *
 * @author Alex Rakitsky
 */
public class PropertyErrorReaderTest {

    public static final String PATH_CORRECT_FILE = "./src/test/resourses/correct.properties";

    @Test
    public void correctFile() throws SystemException {
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader(PATH_CORRECT_FILE);

        String result = propertyErrorReader.readMessage("1");

        assertEquals("Test message property 1",result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void propertyNotFound() throws SystemException {
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader(PATH_CORRECT_FILE);

        propertyErrorReader.readMessage("123");
    }

    @Test(expectedExceptions = SystemException.class)
    public void incorrectFile() throws SystemException {
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader("blah");

        propertyErrorReader.readMessage("123");
    }
}
