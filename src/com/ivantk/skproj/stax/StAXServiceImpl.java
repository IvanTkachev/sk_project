package com.ivantk.skproj.stax;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class StAXServiceImpl extends UnicastRemoteObject implements XMLService {

    private XMLInputFactory xmlInputFactory;
    private XMLEventFactory xmlEventFactory;
    private DocumentBuilder documentBuilder;
    private XMLOutputFactory xmlOutputFactory;
    private XMLEventWriter xmlEventWriter;
    private XMLEventReader xmlEventReader;

    protected StAXServiceImpl() throws RemoteException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaLocation);
            xmlInputFactory = XMLInputFactory.newFactory();
            xmlEventFactory = XMLEventFactory.newFactory();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setSchema(schema);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(productFile);
            validator.validate(source);

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(Product product, String storeName) throws RemoteException {

    }

    @Override
    public List<Product> getAllProducts(String storeName) throws RemoteException {
        return null;
    }

    @Override
    public void updateProduct(Product oldProduct, Product newProduct, String oldStoreName, String newStoreName) throws RemoteException {

    }

    @Override
    public void deleteProduct(String productName, String storeName) throws RemoteException {

    }

    @Override
    public Product findProduct(String productName, String storeName) throws RemoteException {
        return null;
    }

    @Override
    public List<Store> getAllStores() throws RemoteException {
        return null;
    }

    @Override
    public void addStore(String name) throws RemoteException {

    }

    @Override
    public Store findStore(String name) throws RemoteException {
        return null;
    }

    @Override
    public void deleteStore(String name) throws RemoteException {

    }

    @Override
    public void updateStore(String oldStore, String newStore) throws RemoteException {

    }
}
