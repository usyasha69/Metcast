package com.example.pk.metcast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DayWeatherModel {

    private int cityID;
    private String cityName;
    private double cityLonCoord;
    private double cityLatCoord;
    private String country;
    private String cod;
    private double message;
    private int cnt;
    private long[] listDt;
    private double[] listMainTemp;
    private double[] listMainTempMin;
    private double[] listMainTempMax;
    private double[] listMainPressure;
    private double[] listMainSeaLevel;
    private double[] listMainGrndLevel;
    private double[] listMainHumidity;
    private double[] listMainTempKf;
    private int[] listWeatherID;
    private String[] listWeatherMain;
    private String[] listWeatherDescription;
    private String[] listWeatherIcon;
    private double[] listCloudsAll;
    private double[] listWindSpeed;
    private double[] listWindDeg;
    private double[] listRain3h;
    private String[] listSysPod;
    private String[] listDtTxt;

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCityLonCoord() {
        return cityLonCoord;
    }

    public void setCityLonCoord(double cityLonCoord) {
        this.cityLonCoord = cityLonCoord;
    }

    public double getCityLatCoord() {
        return cityLatCoord;
    }

    public void setCityLatCoord(double cityLatCoord) {
        this.cityLatCoord = cityLatCoord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public long[] getListDt() {
        return listDt;
    }

    public void setListDt(long[] listDt) {
        this.listDt = listDt;
    }

    public double[] getListMainTemp() {
        return listMainTemp;
    }

    public void setListMainTemp(double[] listMainTemp) {
        this.listMainTemp = listMainTemp;
    }

    public double[] getListMainTempMin() {
        return listMainTempMin;
    }

    public void setListMainTempMin(double[] listMainTempMin) {
        this.listMainTempMin = listMainTempMin;
    }

    public double[] getListMainTempMax() {
        return listMainTempMax;
    }

    public void setListMainTempMax(double[] listMainTempMax) {
        this.listMainTempMax = listMainTempMax;
    }

    public double[] getListMainPressure() {
        return listMainPressure;
    }

    public void setListMainPressure(double[] listMainPressure) {
        this.listMainPressure = listMainPressure;
    }

    public double[] getListMainSeaLevel() {
        return listMainSeaLevel;
    }

    public void setListMainSeaLevel(double[] listMainSeaLevel) {
        this.listMainSeaLevel = listMainSeaLevel;
    }

    public double[] getListMainGrndLevel() {
        return listMainGrndLevel;
    }

    public void setListMainGrndLevel(double[] listMainGrndLevel) {
        this.listMainGrndLevel = listMainGrndLevel;
    }

    public double[] getListMainHumidity() {
        return listMainHumidity;
    }

    public void setListMainHumidity(double[] listMainHumidity) {
        this.listMainHumidity = listMainHumidity;
    }

    public double[] getListMainTempKf() {
        return listMainTempKf;
    }

    public void setListMainTempKf(double[] listMainTempKf) {
        this.listMainTempKf = listMainTempKf;
    }

    public int[] getListWeatherID() {
        return listWeatherID;
    }

    public void setListWeatherID(int[] listWeatherID) {
        this.listWeatherID = listWeatherID;
    }

    public String[] getListWeatherMain() {
        return listWeatherMain;
    }

    public void setListWeatherMain(String[] listWeatherMain) {
        this.listWeatherMain = listWeatherMain;
    }

    public String[] getListWeatherDescription() {
        return listWeatherDescription;
    }

    public void setListWeatherDescription(String[] listWeatherDescription) {
        this.listWeatherDescription = listWeatherDescription;
    }

    public String[] getListWeatherIcon() {
        return listWeatherIcon;
    }

    public void setListWeatherIcon(String[] listWeatherIcon) {
        this.listWeatherIcon = listWeatherIcon;
    }

    public double[] getListCloudsAll() {
        return listCloudsAll;
    }

    public void setListCloudsAll(double[] listCloudsAll) {
        this.listCloudsAll = listCloudsAll;
    }

    public double[] getListWindSpeed() {
        return listWindSpeed;
    }

    public void setListWindSpeed(double[] listWindSpeed) {
        this.listWindSpeed = listWindSpeed;
    }

    public double[] getListWindDeg() {
        return listWindDeg;
    }

    public void setListWindDeg(double[] listWindDeg) {
        this.listWindDeg = listWindDeg;
    }

    public double[] getListRain3h() {
        return listRain3h;
    }

    public void setListRain3h(double[] listRain3h) {
        this.listRain3h = listRain3h;
    }

    public String[] getListSysPod() {
        return listSysPod;
    }

    public void setListSysPod(String[] listSysPod) {
        this.listSysPod = listSysPod;
    }

    public String[] getListDtTxt() {
        return listDtTxt;
    }

    public void setListDtTxt(String[] listDtTxt) {
        this.listDtTxt = listDtTxt;
    }
}
