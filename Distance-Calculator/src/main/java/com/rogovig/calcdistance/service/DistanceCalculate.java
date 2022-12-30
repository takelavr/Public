package com.rogovig.calcdistance.service;

import com.rogovig.calcdistance.model.City;
import lombok.Data;

import java.text.DecimalFormat;

@Data
public class DistanceCalculate implements CrowfligthInterface, MatrixInterface {

    //вычисление расстояния по сфере
    public String crowfligth(City fromCity, City toCity) {

        return "Result: " + (new DecimalFormat("#0.00").format(calculateCrowfligth(fromCity.getLatitude(),
                                                                                           fromCity.getLongitude(),
                                                                                           toCity.getLatitude(),
                                                                                           toCity.getLongitude())))
                + " Km";

    }

    //вычисление расстояния по прямой
    public String calculateMatrix(City fromCity, City toCity) {
        return "Result: " + new DecimalFormat("#0.00").format(calculateMatrix(fromCity.getLatitude(),
                                                                                          fromCity.getLongitude(),
                                                                                          toCity.getLatitude(),
                                                                                          toCity.getLongitude()))
                + " Km";
    }

}
