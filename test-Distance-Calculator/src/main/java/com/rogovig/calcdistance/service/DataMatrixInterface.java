package com.rogovig.calcdistance.service;



public interface DataMatrixInterface {

    public default Double calculateDataMatrix (double lat1, double long1, double lat2, double long2) {

        final double kmMeridian = 11.1;
        final double kmParallel = 11.3;

        double deltaLat = lat2 - lat1;
        double deltaLatInKm = deltaLat * kmMeridian;

        double cosLat1 = Math.cos(lat1);
        double cosLat2 = Math.cos(lat2);

        double deltaLong = long2 - long1;
        double deltaLongInKm = deltaLong * kmParallel;

        double gipo = Math.sqrt(Math.pow(deltaLongInKm,2) - Math.pow(0.5 * (deltaLongInKm - deltaLatInKm),2));
        double result = Math.sqrt(Math.pow(gipo,2)-Math.pow(deltaLongInKm - (0.5*(deltaLongInKm-deltaLatInKm)),2));


        return result;
    }

}
