package com.rogovig.calcdistance.service;



public interface MatrixInterface {

    public default Double calculateMatrix(double lat1, double long1, double lat2, double long2) {

        final double KmInDegrees = 111.1348;  //количество км в одном градусе

        Double result = Math.sqrt(Math.pow((lat2 - lat1), 2) + Math.pow((long2 - long1), 2)) * KmInDegrees;

        return result;
    }

}
