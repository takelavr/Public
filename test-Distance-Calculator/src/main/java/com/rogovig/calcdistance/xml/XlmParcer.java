package com.rogovig.calcdistance.xml;


import com.rogovig.calcdistance.Constant;
import com.rogovig.calcdistance.model.City;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Data
public class XlmParcer {

//        //структура XML
//        <dataset>
//                <record><Город></Город><Широта></Широта><Долгота></Долгота></record>
//        </dataset>

    //  File xmlFile = findXML(new File("D:\\Рабочая\\programming\\test-Distance-Calculator\\xml\\"), "cities.xml");

    private String fileXml = Constant.NAMEXMLFILE;
    private List<City> citiesFromXml;

    private String getValue(NodeList fields, int index)
    {
        NodeList list = fields.item(index).getChildNodes();
        if (list.getLength() > 0) {
            return list.item(0).getNodeValue();
        } else {
            return "";
        }
    }
    private void readDataXML()
    {
        citiesFromXml = new ArrayList<City>();

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = null;

            FileInputStream fis = null;
                try {
                    fis = new FileInputStream(fileXml);
                    doc = db.parse(fis);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            doc.getDocumentElement().normalize();

            NodeList fieldsName   = null;
            NodeList fieldsLatitude   = null;
            NodeList fieldsLongitude   = null;
            NodeList nodeList = null;

            //в пределах тега record
            nodeList = doc.getElementsByTagName("record");
            for (int s = 0; s < nodeList.getLength(); s++) {
                Node node = nodeList.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    fieldsName = el.getElementsByTagName("Город");
                    fieldsLatitude = el.getElementsByTagName("Широта");
                    fieldsLongitude = el.getElementsByTagName("долгота");

                    City city = new City();
                    city.setName (getValue(fieldsName, 0));
                    city.setLatitude  (Double.parseDouble(getValue(fieldsLatitude,0)));
                    city.setLongitude (Double.parseDouble(getValue(fieldsLongitude, 0)));

                    citiesFromXml.add(city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
