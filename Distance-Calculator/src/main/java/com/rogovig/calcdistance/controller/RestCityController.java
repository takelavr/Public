package com.rogovig.calcdistance.controller;

import com.rogovig.calcdistance.Constant;
import com.rogovig.calcdistance.data.CityRepository;
import com.rogovig.calcdistance.model.City;
import com.rogovig.calcdistance.service.DistanceCalculate;
import com.rogovig.calcdistance.xml.XmlService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class RestCityController {

    private CityRepository cityRepo;

    RestTemplate rest = new RestTemplate();

    public RestCityController(CityRepository cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //cities - возвращает список всех городов
    @GetMapping(path = "/cities")
    public ResponseEntity<List<City>> allCities() {
        final List<City> cities = (List<City>) cityRepo.findAll();
        return cities != null && !cities.isEmpty() ?
                new ResponseEntity<>(cities, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //вариация
//    public Iterable<City> allCities() {
//        return cityRepo.findAll();
//    }
    @GetMapping(path="/city")
    public Optional<City> getCity(@Param("id") long id) {
        return cityRepo.findById(id);

    }

    @GetMapping(path = "/calculate", produces = "text/plain")
    public String calculate(@Param("method") String method,
                                      @Param("cityFrom") long cityFrom,
                                      @Param("cityTo") long cityTo) {

        String answer;

        long cityFromId = cityFrom;
        long cityToId = cityTo;

        if (cityFromId == cityToId) {
            answer = "distance 0";
        } else {
            switch (method) {
                case ("crowfligth"): {

                    return new DistanceCalculate().crowfligth(cityRepo.findById(cityFromId).get(),
                                                            cityRepo.findById(cityToId).get());

                }
                case ("matrix"): {
                    return  new DistanceCalculate().calculateMatrix(cityRepo.findById(cityFromId).get(),
                                                                   cityRepo.findById(cityToId).get());
                }
                case ("all"): {
                    return  "Matrix " + new DistanceCalculate().calculateMatrix(cityRepo.findById(cityFromId).get(),
                                                                                                    cityRepo.findById(cityToId).get())
                                           +" + Crowfligth " + new DistanceCalculate().crowfligth(cityRepo.findById(cityFromId).get(),
                                                                                              cityRepo.findById(cityToId).get());

                }

            }
        }
        return "Unsupport method";

    }

    @GetMapping(path = "/parse", produces = "text/plain")
    public String parseXML() throws ParserConfigurationException, IOException, SAXException {
        try {
            File file = new File(Constant.PATHOFFILE);
            new XmlService().xmlSaveInDB(cityRepo, new XmlService().parseXml(new XmlService().findXML(file, Constant.NAMEXMLFILE)));

            return "upload complete successful!";

        } catch (Exception e) {
            return "something was wrong";
        }

    }



}
