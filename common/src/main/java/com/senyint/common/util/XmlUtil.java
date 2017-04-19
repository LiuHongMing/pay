/**
 * Copyright 2012 José Martínez
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.senyint.common.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * XML DOM utilities.
 */
public final class XmlUtil {

    private static final DocumentBuilder DOCUMENT_BUILDER;

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    static {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        try {
            DOCUMENT_BUILDER = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new InternalError("Error creating Document Builder");
        }
    }

    /**
     * Returns a new Document from the current DocumentBuidler.
     *
     * @return a new Document
     */
    public static final Document newDocument() {
        return DOCUMENT_BUILDER.newDocument();
    }

    /**
     * Parses a String into a new Element.
     *
     * @param element the String to parse
     * @return the parsed Element
     */
    public static final Element fromString(final String element) {
        try {
            final Document doc = newDocument();
            Transformer transform = TRANSFORMER_FACTORY.newTransformer();
            // 设置字符编码为UTF-8
            transform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transform.transform(new StreamSource(new StringReader(element)), new DOMResult(doc));

            return doc.getDocumentElement();
        } catch (TransformerException e) {
            throw new InternalError("Transformer error");
        }
    }

    /**
     * Returns the String representation of an Element.
     *
     * @param node the Element to convert
     * @return the String representation of a Element
     */
    public static final String toString(final Node node) {
        final StringWriter writer = new StringWriter();
        try {
            Transformer transform = TRANSFORMER_FACTORY.newTransformer();
            transform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            // 是否缩进
            transform.setOutputProperty(OutputKeys.INDENT, "no");
            // UTF-8编码
            transform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transform.transform(new DOMSource(node), new StreamResult(writer));

            return writer.toString();
        } catch (TransformerException e) {
            throw new InternalError("Transformer error");
        } finally {
            writer.flush();
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
