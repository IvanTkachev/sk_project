package com.ivantk.skproj.jaxb;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;
import com.ivantk.skproj.entities.Stores;
import com.ivantk.skproj.javaFX.view.MainController;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class JaxbServiceImpl extends UnicastRemoteObject implements XMLService {

    private JAXBContext context;
    private Unmarshaller unmarshaller;
    private DocumentBuilder documentBuilder;

    public JaxbServiceImpl() throws ParserConfigurationException, SAXException, IOException  {
        super();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = Class.class.getResource(schemaLocation);
        Schema schema = factory.newSchema(url);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setSchema(schema);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(productsFile);
        validator.validate(source);
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    @Override
    public void addProduct(Product product, String storeName) throws RemoteException {
        try {
            Document document = documentBuilder.parse(productsFile);
            Element root = null;
            NodeList nodes = document.getElementsByTagName("store");
            for (int i =0; i < nodes.getLength(); i++){
                if(storeName.equals(nodes.item(i).getAttributes().getNamedItem("name_store").getNodeValue())){
                    root = (Element) document.getElementsByTagName("store").item(i);
                }
            }
            Element productXML = document.createElement("product");
            productXML.setAttribute("id_product", String.valueOf(product.getId()));

            Element nameNode = document.createElement("name");
            nameNode.appendChild(document.createTextNode(product.getName()));

            Element countNode = document.createElement("count");
            countNode.appendChild(document.createTextNode(String.valueOf(product.getCount())));

            productXML.appendChild(nameNode);
            productXML.appendChild(countNode);
            root.appendChild(productXML);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(productsFile));

        } catch (IOException | SAXException | TransformerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts(String storeName) throws RemoteException {
        Stores stores = new Stores();
        try {
            context = JAXBContext.newInstance(Stores.class);
            unmarshaller = context.createUnmarshaller();
            stores = (Stores) unmarshaller.unmarshal(productsFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Stores.Store currentStore = null;
        for (Stores.Store store : new ArrayList<>(stores.getStore())) {
            if (store.getNameStore().equals(storeName))
                currentStore = store;
        }
        List<Product> products = new ArrayList<>();
        for (Stores.Store.Product product : currentStore.getProduct()) {
            products.add(new Product(product.getIdProduct(), ((ElementNSImpl) product.getName()).getFirstChild().getNodeValue(),
                    Integer.parseInt(((ElementNSImpl) product.getCount()).getFirstChild().getNodeValue())));
        }
        return products;
    }


    @Override
    public void deleteProduct(String productName, String storeName) throws RemoteException {
        try {
            Document document = documentBuilder.parse(productsFile);
            Element root = null;
            NodeList nodes = document.getElementsByTagName("store");
            for (int i =0; i < nodes.getLength(); i++){
                if(storeName.equals(nodes.item(i).getAttributes().getNamedItem("name_store").getNodeValue())){
                    root = (Element) document.getElementsByTagName("store").item(i);
                }
            }
            NodeList rootNodes = root.getElementsByTagName("product");
            for (int i =0; i < rootNodes.getLength(); i++){
                NodeList childes = rootNodes.item(i).getChildNodes();
                for (int j =0; j < childes.getLength(); j++){
                    if(childes.item(j).getFirstChild() != null
                            && productName.equals(childes.item(j).getFirstChild().getNodeValue())){
                        root.removeChild(rootNodes.item(i));
                    }
                }
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(productsFile));

        } catch (IOException | SAXException | TransformerException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Product findProduct(String productName, String storeName) throws RemoteException {
        Product product = null;
        String currentStore = null;
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
                    Attribute nameStore = startElement.getAttributeByName(new QName("name_store"));
                    if(nameStore != null){
                        currentStore = nameStore.getValue();
                    }
                    if(storeName.equals(currentStore)){
                        switch (startElement.getName().getLocalPart()) {
                            case "product":
                                product = new Product();
                                // Получаем атрибут id для каждого элемента Student
                                Attribute idProduct = startElement.getAttributeByName(new QName("id_product"));
                                if (idProduct != null) {
                                    product.setId(Integer.parseInt(idProduct.getValue()));
                                }
                                break;
                            case "name":
                                xmlEvent = reader.nextEvent();
                                product.setName(String.valueOf(xmlEvent.asCharacters().getData()));
                                break;
                            case "count":
                                xmlEvent = reader.nextEvent();
                                product.setCount(Integer.parseInt(xmlEvent.asCharacters().getData()));
                                break;
                        }
                    }

                }
                // если цикл дошел до закрывающего элемента Student,
                // то добавляем считанного из файла студента в список
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("product") && storeName.equals(currentStore)
                            && product != null && productName.equals(product.getName())) {
                        return product;
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Store> getAllStores() throws RemoteException {
        Stores stores = new Stores();
        try {
            context = JAXBContext.newInstance(Stores.class);
            unmarshaller = context.createUnmarshaller();
            stores = (Stores) unmarshaller.unmarshal(productsFile);
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        List<Store> resultList = new ArrayList<>();
        for (Stores.Store store : new ArrayList<>(stores.getStore())){
            resultList.add(new Store(store.getIdStore(), store.getNameStore()));
        }
        return resultList;
    }

    public static void main(String[] args) {
        try {
            JaxbServiceImpl service = new JaxbServiceImpl();
            List<Product> list = service.getAllProducts(MainController.nameStore);
            for (Product store: list) {
                System.out.println(store.getId());
                System.out.println(store.getName());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
