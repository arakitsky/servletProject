package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
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
        PropertyErrorReader propertyErrorReader = new PropertyErrorReader(PATH_CORRECT_FILE);

        String result = propertyErrorReader.readError("1");

        assertEquals("Test message property 1",result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void propertyNotFound() throws SystemException {
        PropertyErrorReader propertyErrorReader = new PropertyErrorReader(PATH_CORRECT_FILE);

        propertyErrorReader.readError("123");
    }

    @Test(expectedExceptions = SystemException.class)
    public void incorrectFile() throws SystemException {
        PropertyErrorReader propertyErrorReader = new PropertyErrorReader("blah");

        propertyErrorReader.readError("123");
    }
}
