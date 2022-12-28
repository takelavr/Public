package com.rogovig.calcdistance.xml;

import com.rogovig.calcdistance.model.City;
import lombok.Data;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

@Data
public class AdvancedXMLHandler extends DefaultHandler {


//        //структура XML
//        <dataset>
//                <record><Город></Город><Широта></Широта><Долгота></Долгота></record>
//        </dataset>


    private String name, lastElementName;
    double latitude, longitude;
    private List<City> citiesFromXml;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        lastElementName = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String information = new String(ch, start, length);

        information = information.replace("\n", "").trim();

        if (!information.isEmpty()) {
            if (lastElementName.equals("Город"))
                name = information;
            if (lastElementName.equals("Широта"))
                latitude = Double.parseDouble(information);
            if (lastElementName.equals("Широта"))
                longitude = Double.parseDouble(information);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ( (name != null && !name.isEmpty()) && (latitude != 0 && longitude != 0) ) {
            citiesFromXml.add(new City(name, latitude, longitude));
            name = null;
            latitude = 0;
            longitude = 0;
        }
    }
}


