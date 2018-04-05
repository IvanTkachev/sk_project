package stax;

import entities.City;
import entities.Person;
import org.xml.sax.SAXException;
import services.XmlPersonCityService;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class StAXDao implements XmlPersonCityService {

    private XMLOutputFactory xmlOutputFactory;
    private XMLInputFactory xmlInputFactory;
    private XMLEventFactory xmlEventFactory;
    private int lastInsertIdOfPerson = 0;
    private int lastInsertIdOfCity = 0;

    public StAXDao() {
        xmlOutputFactory = XMLOutputFactory.newFactory();
        xmlInputFactory = XMLInputFactory.newFactory();
        xmlEventFactory = XMLEventFactory.newFactory();
    }

    @Override
    public void writePeopleInFile(List<Person> people) {
        try {
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(peopleFile));
            xmlStreamWriter.writeStartDocument("1.0");
            xmlStreamWriter.writeStartElement("people");
            for (Person person : people) {
                xmlStreamWriter.writeStartElement("person");
                xmlStreamWriter.writeAttribute("id", String.valueOf(person.getId()));
                xmlStreamWriter.writeAttribute("name", person.getName());
                xmlStreamWriter.writeAttribute("street", person.getStreet());
                xmlStreamWriter.writeAttribute("number", person.getStringNumber());
                xmlStreamWriter.writeAttribute("city_id", String.valueOf(person.getCity().getId()));
                xmlStreamWriter.writeCharacters("Person" + person.getId());
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeCitiesInFile(List<City> cities) {
        try {
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(citiesFile));
            xmlStreamWriter.writeStartDocument("1.0");
            xmlStreamWriter.writeStartElement("cities");
            for (City city : cities) {
                xmlStreamWriter.writeStartElement("city");
                xmlStreamWriter.writeAttribute("id", String.valueOf(city.getId()));
                xmlStreamWriter.writeAttribute("name", city.getName());
                xmlStreamWriter.writeCharacters("City" + city.getId());
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        try {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(peopleFile));
            while (xmlStreamReader.hasNext()) {
                int eventType = xmlStreamReader.getEventType();
                switch (eventType) {
                    case XMLStreamReader.START_ELEMENT:
                        if (xmlStreamReader.getName().toString().equals("person")) {
                            people.add(getPerson(xmlStreamReader));
                        }
                        break;
                }
                xmlStreamReader.next();
            }
            xmlStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(people, new PersonComparator());
        lastInsertIdOfPerson = people.get(people.size() - 1).getId();
        return people;
    }

    private Person getPerson(XMLStreamReader xmlStreamReader) {
        int id = 0, number = 0, city_id = 0;
        String name = "", street = "";
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            switch (xmlStreamReader.getAttributeName(i).toString()) {
                case "id":
                    id = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                    break;
                case "number":
                    number = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                    break;
                case "city_id":
                    city_id = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                    break;
                case "name":
                    name = xmlStreamReader.getAttributeValue(i);
                    break;
                case "street":
                    street = xmlStreamReader.getAttributeValue(i);
                    break;
            }
        }
        return new Person(id, name, street, number, getCity(city_id));
    }

    private City getCity(XMLStreamReader xmlStreamReader) {
        int id = 0;
        String name = "";
        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            switch (xmlStreamReader.getAttributeName(i).toString()) {
                case "id":
                    id = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                    break;
                case "name":
                    name = xmlStreamReader.getAttributeValue(i);
                    break;
            }
        }
        return new City(id, name);
    }

    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        try {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(citiesFile));
            while (xmlStreamReader.hasNext()) {
                int eventType = xmlStreamReader.getEventType();
                switch (eventType) {
                    case XMLStreamReader.START_ELEMENT:
                        if (xmlStreamReader.getName().toString().equals("city")) {
                            cities.add(getCity(xmlStreamReader));
                        }
                        break;
                }
                xmlStreamReader.next();
            }
            xmlStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(cities, new CityComparator());
        lastInsertIdOfCity = cities.get(cities.size() - 1).getId();
        return cities;
    }

    @Override
    public City getCity(int id) {
        City city = new City();
        try {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(citiesFile));
            boolean check = false;
            while (xmlStreamReader.hasNext()) {
                if (xmlStreamReader.getEventType() == XMLStreamReader.START_ELEMENT && xmlStreamReader.getName().toString().equals("city")) {
                    int idOfCity;
                    String name;
                    for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                        switch (xmlStreamReader.getAttributeName(i).toString()) {
                            case "id":
                                idOfCity = Integer.parseInt(xmlStreamReader.getAttributeValue(i));
                                if (idOfCity == id) {
                                    city.setId(idOfCity);
                                    check = true;
                                }
                                break;
                            case "name":
                                if (check) {
                                    name = xmlStreamReader.getAttributeValue(i);
                                    city.setName(name);
                                    return city;
                                }
                                break;
                        }
                    }
                }
                xmlStreamReader.next();
            }
            xmlStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int getCountInTheCity(int id) {
        int count = 0;
        try {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(peopleFile));
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(xmlStreamReader);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    if (xmlStreamReader.getName().toString().equals("person"))
                        if (xmlStreamReader.getAttributeValue(4).equals(String.valueOf(id)))
                            count++;
                }
            }
            xmlEventReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void updatePerson(int id, String toUpdate, String type) {
        try {
            XMLEventReader in = xmlInputFactory.createXMLEventReader(new FileInputStream(peopleFile));
            List<XMLEvent> events = new ArrayList<>();
            while (in.hasNext()) {
                XMLEvent e = in.nextEvent();
                if (e.isStartElement() && ((StartElement) e).getName().getLocalPart().equalsIgnoreCase("person")) {
                    Attribute a = ((StartElement) e).getAttributeByName(new QName("id"));
                    if (a.getValue().equalsIgnoreCase(String.valueOf(id))) {
                        StartElement element = e.asStartElement();
                        Iterator iterator = element.getAttributes();
                        List<Attribute> attributes = new ArrayList<>();
                        while (iterator.hasNext()) {
                            Attribute attribute = (Attribute) iterator.next();
                            if (attribute.getName().toString().equals(type))
                                attributes.add(xmlEventFactory.createAttribute(attribute.getName(), toUpdate));
                            else
                                attributes.add(xmlEventFactory.createAttribute(attribute.getName(), attribute.getValue()));
                        }
                        events.add(xmlEventFactory.createStartElement(element.getName(), attributes.iterator(), element.getNamespaces()));
                        in.next();
                        in.next();
                        continue;
                    }
                }
                events.add(e);
            }
            in.close();
            XMLEventWriter out = xmlOutputFactory.createXMLEventWriter(new FileOutputStream(peopleFile));
            for (XMLEvent e : events)
                out.add(e);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerson(Person newPerson) {
        try {
            XMLEventReader in = xmlInputFactory.createXMLEventReader(new FileInputStream(peopleFile));
            List<XMLEvent> events = new ArrayList<>();
            while (in.hasNext()) {
                XMLEvent e = in.nextEvent();
                if (e.isStartElement() && ((StartElement) e).getName().getLocalPart().equalsIgnoreCase("people")) {
                    events.add(e);
                    events.add(xmlEventFactory.createStartElement(new QName("person"), null, ((StartElement) e).getNamespaces()));
                    lastInsertIdOfPerson++;
                    events.add(xmlEventFactory.createAttribute(new QName("id"), String.valueOf(lastInsertIdOfPerson)));
                    events.add(xmlEventFactory.createAttribute(new QName("name"), newPerson.getName()));
                    events.add(xmlEventFactory.createAttribute(new QName("street"), newPerson.getStreet()));
                    events.add(xmlEventFactory.createAttribute(new QName("number"), String.valueOf(newPerson.getNumber())));
                    events.add(xmlEventFactory.createAttribute(new QName("city_id"), String.valueOf(newPerson.getCity().getId())));
                    events.add(xmlEventFactory.createCharacters("Person" + lastInsertIdOfPerson));
                    events.add(xmlEventFactory.createEndElement(new QName("person"), ((StartElement) e).getNamespaces()));
                    continue;
                }
                events.add(e);
            }
            in.close();
            XMLEventWriter out = xmlOutputFactory.createXMLEventWriter(new FileOutputStream(peopleFile));
            for (XMLEvent e : events)
                out.add(e);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(int id) {
        try {
            XMLEventReader in = xmlInputFactory.createXMLEventReader(new FileInputStream(peopleFile));
            List<XMLEvent> events = new ArrayList<>();
            while (in.hasNext()) {
                XMLEvent e = in.nextEvent();
                if (e.isStartElement() && ((StartElement) e).getName().getLocalPart().equalsIgnoreCase("person")) {
                    Attribute a = ((StartElement) e).getAttributeByName(new QName("id"));
                    if (a.getValue().equalsIgnoreCase(String.valueOf(id))) {
                        in.next();
                        in.next();
                        continue;
                    }
                }
                events.add(e);
            }
            in.close();
            XMLEventWriter out = xmlOutputFactory.createXMLEventWriter(new FileOutputStream(peopleFile));
            for (XMLEvent e : events)
                out.add(e);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastInsertId(Class myClass) {
        if (myClass.equals(Person.class))
            return lastInsertIdOfPerson;
        else return lastInsertIdOfCity;
    }

    @Override
    public boolean isValid(Class myClass) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            if (myClass.equals(Person.class))
                schema = factory.newSchema(new File("src/main/resources/people.xsd"));
            else schema = factory.newSchema(new File("src/main/resources/cities.xsd"));
        } catch (SAXException e) {
            return false;
        }
        Validator validator = schema != null ? schema.newValidator() : null;
        XMLEventReader xmlEventReader;
        try {
            if (myClass.equals(Person.class))
                xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(peopleFile));
            else xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(citiesFile));
        } catch (XMLStreamException | FileNotFoundException e) {
            return false;
        }
        try {
            if (validator != null) validator.validate(new StAXSource(xmlEventReader));
            return true;
        } catch (SAXException | IOException | XMLStreamException e) {
            return false;
        }
    }
}
