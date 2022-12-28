package com.rogovig.calcdistance.data;

import com.rogovig.calcdistance.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    @Override
    Iterable<City> findAll();


}
