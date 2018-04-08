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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        Product resultProduct = null;
        for (Stores.Store.Product product : currentStore.getProduct()) {
            if(productName.equals(((ElementNSImpl) product.getName()).getFirstChild().getNodeValue())){
                resultProduct = new Product(product.getIdProduct(), ((ElementNSImpl) product.getName()).getFirstChild().getNodeValue(),
                        Integer.parseInt(((ElementNSImpl) product.getCount()).getFirstChild().getNodeValue()));
            }
        }
        return resultProduct;
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
            Product product = service.findProduct("Product1", MainController.nameStore);
         System.out.println(product);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
