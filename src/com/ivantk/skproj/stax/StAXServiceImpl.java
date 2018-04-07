package com.ivantk.skproj.stax;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class StAXServiceImpl extends UnicastRemoteObject implements XMLService {

    private XMLInputFactory xmlInputFactory;
    private XMLEventFactory xmlEventFactory;
    private DocumentBuilder documentBuilder;
    private XMLOutputFactory xmlOutputFactory;
    private XMLEventWriter xmlEventWriter;
    private XMLEventReader xmlEventReader;

    public StAXServiceImpl() throws RemoteException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = factory.newSchema(schemaLocation);
            xmlInputFactory = XMLInputFactory.newFactory();
            xmlEventFactory = XMLEventFactory.newFactory();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            documentBuilderFactory.setSchema(schema);
//            Validator validator = schema.newValidator();
//            Source source = new StreamSource(productFile);
//            validator.validate(source);

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
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
    public void deleteProduct(String productName, String storeName) throws RemoteException {

    }

    @Override
    public Product findProduct(String productName, String storeName) throws RemoteException {
        return null;
    }

    @Override
    public List<Store> getAllStores() throws RemoteException {
        List<Store> stores = new ArrayList<>();
        Store store = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            // инициализируем reader и скармливаем ему xml файл
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(productsFile));
            // проходим по всем элементам xml файла
            while (reader.hasNext()) {
                // получаем событие (элемент) и разбираем его по атрибутам
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("store")) {
                        store = new Store();
                        // Получаем атрибут id для каждого элемента Student
                        Attribute idStore = startElement.getAttributeByName(new QName("id_store"));
                        Attribute nameStore = startElement.getAttributeByName(new QName("name_store"));
                        if (idStore != null) {
                            store.setId(Integer.parseInt(idStore.getValue()));
                        }
                        if(nameStore != null){
                            store.setName(String.valueOf(nameStore.getValue()));
                        }
                    }
                }
                // если цикл дошел до закрывающего элемента Student,
                // то добавляем считанного из файла студента в список
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("store")) {
                        stores.add(store);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException exc) {
            exc.printStackTrace();
        }
        return stores;
    }

    public static void main(String[] args) {
        try {
            StAXServiceImpl stAXService = new StAXServiceImpl();
            List<Store> stores = new ArrayList<>();
            stores = stAXService.getAllStores();
            System.out.println(stores.get(0).getName());
            System.out.println(stores.get(0).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
