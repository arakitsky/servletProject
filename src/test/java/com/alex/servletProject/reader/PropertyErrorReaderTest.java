package com.alex.servletProject.reader;

import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.PropertyMessageReader;
import org.testng.annotations.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * .
 * Date: 12/12/12
 *
 * @author Alex Rakitsky
 */
public class PropertyErrorReaderTest {

    @Test
    public void correctFile() throws SystemException {
        String path = getClass().getResource(File.separator + "correct.properties").toString();
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader(path);

        String result = propertyErrorReader.readMessage("1");

        assertEquals("Test message property 1", result);
    }

    @Test(expectedExceptions = SystemException.class)
    public void propertyNotFound() throws SystemException {
        String path = getClass().getResource(File.separator + "correct.properties").toString();
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader(path);

        propertyErrorReader.readMessage("123");
    }

    @Test(expectedExceptions = SystemException.class)
    public void incorrectFile() throws SystemException {
        PropertyMessageReader propertyErrorReader = new PropertyMessageReader("blah");

        propertyErrorReader.readMessage("123");
    }
}
