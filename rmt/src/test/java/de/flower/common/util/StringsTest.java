package de.flower.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class StringsTest {

    @Test
    public void testCamelCaseToHyphen() {
        // code copied from junit, thats why actual and expected args are switched.
        assertEquals("lowercase", Strings.camelCaseToHyphen("lowercase"));
        assertEquals("Class", Strings.camelCaseToHyphen("Class"));
        assertEquals("My-Class", Strings.camelCaseToHyphen("MyClass"));
        assertEquals("HTML", Strings.camelCaseToHyphen("HTML"));
        assertEquals("PDF-Loader", Strings.camelCaseToHyphen("PDFLoader"));
        assertEquals("A-String", Strings.camelCaseToHyphen("AString"));
        assertEquals("Simple-XML-Parser", Strings.camelCaseToHyphen("SimpleXMLParser"));
        assertEquals("GL-11-Version", Strings.camelCaseToHyphen("GL11Version"));
    }

    @Test
    public void testUnCapitalize() {
        // code copied from junit, thats why actual and expected args are switched.
        assertEquals("lowercase", Strings.uncapitalize("lowercase"));
        assertEquals("class", Strings.uncapitalize("Class"));
        assertEquals("myClass", Strings.uncapitalize("MyClass"));
        assertEquals("html", Strings.uncapitalize("HTML"));
        assertEquals("pdfLoader", Strings.uncapitalize("PDFLoader"));
        assertEquals("aString", Strings.uncapitalize("AString"));
        assertEquals("simpleXmlParser", Strings.uncapitalize("SimpleXMLParser"));
        assertEquals("gl11Version", Strings.uncapitalize("GL11Version"));
    }
}
