package com.rogovig.calcdistance.service;

public interface CrowfligthInterface {

    public default Double calculateCrowfligth(double lat1, double long1, double lat2, double long2 ) {

        //радиус земли в км
        final int RADIUS = 6371;

        //переводим в радианы
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double radLong1 = Math.toRadians(long1);
        double radLong2 = Math.toRadians(long2);

        //косинусы и синусы
        double cosl1 = Math.cos(radLat1);
        double cosl2 = Math.cos(radLat2);
        double sinl1 = Math.sin(radLong1);
        double sinl2 = Math.sin(radLong2);
        double delta = radLong2 - radLong1;
        double cosDelta = Math.cos(delta);
        double sinDelta = Math.sin(delta);

        //вычисление длины большого круга
        double x = Math.sqrt(Math.pow(cosl2 * sinDelta, 2) +
                             Math.pow(cosl1 * sinl2 - sinl1 * cosl2 * cosDelta,2 ));

        double y = sinl1 * sinl2 + cosl1 * cosl2 * cosDelta;

        double ad = Math.atan2(x, y);

        double result = ad * RADIUS;

        return result;
    }







}
