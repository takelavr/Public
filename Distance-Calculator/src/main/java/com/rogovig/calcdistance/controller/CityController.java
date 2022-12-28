package com.rogovig.calcdistance.controller;


import com.rogovig.calcdistance.data.CityRepository;
import com.rogovig.calcdistance.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CityController  {

    private CityRepository cityrepo;

    @Autowired
    public CityController (CityRepository cityrepo) {
        this.cityrepo = cityrepo;
    }

    public Iterable<City> getCity(CityRepository cityrepo) {
        return cityrepo.findAll();
    }





}
