package com.rogovig.calcdistance.service;

import com.rogovig.calcdistance.data.CityRepository;
import com.rogovig.calcdistance.model.City;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class CityByIdConverter implements Converter<String, City> {

    private CityRepository cityRepo;

    public CityByIdConverter(CityRepository cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    public City convert(String id) {
        return cityRepo.findById(Long.valueOf(id)).orElse(null);
    }



}
