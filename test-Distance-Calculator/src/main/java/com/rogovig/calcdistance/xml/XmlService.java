package com.rogovig.calcdistance.xml;

import com.rogovig.calcdistance.Constant;
import com.rogovig.calcdistance.data.CityRepository;
import com.rogovig.calcdistance.model.City;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class XmlService {

    private CityRepository cityRepo;

    List<City> cities = new ArrayList<>();

    //сохраняет спарсенные данные в DB
    public void xmlSaveInDB(CityRepository cityRepo, List<City>cities) {
        for(City city: cities) {
            cityRepo.save(city);
        }
       log.info("Repo saved: " + cities);
    }

    //парсит XML в Мапу City - (данные)
    public List<City> parseXml(File file) throws ParserConfigurationException, IOException, SAXException {

        File xmlFile = new File(Constant.PATHOFFILE);

        return new XlmParcer().getCitiesFromXml();
    }

    //ищет файл XML в папке с проектом
    public File findXML(File dir, String name) {
        File result = null;

        File[] dirList = dir.listFiles();
        for (int i = 0; i < dirList.length; i++) {
            if (dirList[i].isDirectory()) {
                result = findXML(dirList[i], name);
                if (result != null) break;
            } else if (dirList[i].getName().matches(name)) {
                return dirList[i];
            }
        }
        return result; // вернет null  если файл не найдет
    }

}
