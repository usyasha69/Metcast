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

    public DayWeatherModel fillInAllFields(String query) {

        WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(query);

        DayWeatherModel dayWeatherModel = new DayWeatherModel();

        //CityID
        dayWeatherModel.setCityID(weatherParsingModel.getCity().getCityId());
        //CityName
        dayWeatherModel.setCityName(weatherParsingModel.getCity().getCityName());
        //CityLonCoord
        dayWeatherModel.setCityLonCoord(weatherParsingModel.getCity().getCoord().getCityLonCoord());
        //CityLatCoord
        dayWeatherModel.setCityLatCoord(weatherParsingModel.getCity().getCoord().getCityLatCoord());
        //CityCountry
        dayWeatherModel.setCountry(weatherParsingModel.getCity().getCityCountry());
        //Cod
        dayWeatherModel.setCod(weatherParsingModel.getCod());
        //Message
        dayWeatherModel.setMessage(weatherParsingModel.getMessage());
        //Cnt
        dayWeatherModel.setCnt(weatherParsingModel.getCnt());

        //listDt
        long[] listDtArray = new long[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listDtArray[i] = weatherParsingModel.getList().get(i).getListDt();
        }
        dayWeatherModel.setListDt(listDtArray);

        //listMainTemp
        double[] listMainTempArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainTempArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainTemp();
        }
        dayWeatherModel.setListMainTemp(listMainTempArray);

        //listMainTempMin
        double[] listMainTempMinArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainTempMinArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainTempMin();
        }
        dayWeatherModel.setListMainTempMin(listMainTempMinArray);

        //listMainTempMax
        double[] listMainTempMaxArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainTempMaxArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainTempMax();
        }
        dayWeatherModel.setListMainTempMax(listMainTempMaxArray);

        //listMainPressure
        double[] listMainPressureArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainPressureArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainPressure();
        }
        dayWeatherModel.setListMainPressure(listMainPressureArray);

        //listMainSeaLevel
        double[] listMainSeaLevelArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainSeaLevelArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainSeaLevel();
        }
        dayWeatherModel.setListMainSeaLevel(listMainSeaLevelArray);

        //listMainGrndLevel
        double[] listMainGrndLevelArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainGrndLevelArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainGrndLevel();
        }
        dayWeatherModel.setListMainGrndLevel(listMainGrndLevelArray);

        //listMainHumidity
        double[] listMainHumidityArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainHumidityArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainHumidity();
        }
        dayWeatherModel.setListMainHumidity(listMainHumidityArray);

        //listMainTempKf
        double[] listMainTempKfArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainTempKfArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainTempKf();
        }
        dayWeatherModel.setListMainTempKf(listMainTempKfArray);

        //listWeatherID
        int[] listWeatherIdArray = new int[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherIdArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherId();
            }
        }
        dayWeatherModel.setListWeatherID(listWeatherIdArray);

        //listWeatherMain
        String[] listWeatherMainArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherMainArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherMain();
            }
        }
        dayWeatherModel.setListWeatherMain(listWeatherMainArray);

        //listWeatherDescription
        String[] listWeatherDescriptionArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherDescriptionArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription();
            }
        }
        dayWeatherModel.setListWeatherDescription(listWeatherDescriptionArray);

        //listWeatherIcon
        String[] listWeatherIconArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherIconArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherIcon();
            }
        }
        dayWeatherModel.setListWeatherIcon(listWeatherIconArray);

        //listCloudsAll
        double[] listCloudsAllArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listCloudsAllArray[i] = weatherParsingModel.getList().get(i).getClouds().getListCloudsAll();
        }
        dayWeatherModel.setListCloudsAll(listCloudsAllArray);

        //listWindSpeed
        double[] listWindSpeedArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listWindSpeedArray[i] = weatherParsingModel.getList().get(i).getWind().getListWindSpeed();
        }
        dayWeatherModel.setListWindSpeed(listWindSpeedArray);

        //listWindDeg
        double[] listWindDegArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listWindDegArray[i] = weatherParsingModel.getList().get(i).getWind().getListWindDeg();
        }
        dayWeatherModel.setListWindDeg(listWindDegArray);

        //listRain3h
        double[] listRain3hArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listRain3hArray[i] = weatherParsingModel.getList().get(i).getRain().getListRain3h();
        }
        dayWeatherModel.setListRain3h(listRain3hArray);

        //listSysPod
        String[] listSysPodArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listSysPodArray[i] = weatherParsingModel.getList().get(i).getSys().getListSysPod();
        }
        dayWeatherModel.setListSysPod(listSysPodArray);

        //listDtTxt
        String[] listDtTxtArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listDtTxtArray[i] = weatherParsingModel.getList().get(i).getListDtTxt();
        }
        dayWeatherModel.setListDtTxt(listDtTxtArray);

        return  dayWeatherModel;
    }

    public DayWeatherModel makeUpCurrentWeather(String query) {

        WeatherParsingModel weatherParsingModel = new WeatherParsing().parseQuery(query);

        DayWeatherModel dayWeatherModel = new DayWeatherModel();

        //listMainTemp
        double[] listMainTempArray = new double[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listMainTempArray[i] = weatherParsingModel.getList().get(i).getMain().getListMainTemp();
        }
        dayWeatherModel.setListMainTemp(listMainTempArray);

        //listWeatherMain
        String[] listWeatherMainArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherMainArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherMain();
            }
        }
        dayWeatherModel.setListWeatherMain(listWeatherMainArray);

        //listWeatherDescription
        String[] listWeatherDescriptionArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherDescriptionArray[i] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription();
            }
        }
        dayWeatherModel.setListWeatherDescription(listWeatherDescriptionArray);

        //listDtTxt
        String[] listDtTxtArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            listDtTxtArray[i] = weatherParsingModel.getList().get(i).getListDtTxt();
        }
        dayWeatherModel.setListDtTxt(listDtTxtArray);

        return dayWeatherModel;
    }

    public HashMap<String, Object[][]> groupingWeatherByDate(DayWeatherModel dayWeatherModel) {

        HashMap<String, Object[][]> dayWeatherMap;

        String[] dates = dayWeatherModel.getListDtTxt();
        double[] temperature = dayWeatherModel.getListMainTemp();
        String[] weatherMain = dayWeatherModel.getListWeatherMain();
        String[] weatherDescription = dayWeatherModel.getListWeatherDescription();

        ArrayList<Integer[]> daysMonthsYearsList = dateTextToInteger(dates);

        String[] daysOfWeek = dateToDayWeek(daysMonthsYearsList, dates.length);

        dayWeatherMap = formationResultMap(daysOfWeek,dates, temperature, weatherMain, weatherDescription);

        return dayWeatherMap;
    }

    public ArrayList<Integer[]> dateTextToInteger(String[] dates) {

        Integer[] days = new Integer[dates.length];
        Integer[] months = new Integer[dates.length];
        Integer[] years = new Integer[dates.length];

        ArrayList<Integer[]> daysMonthsYears = new ArrayList<>();

        for (int i = 0; i < dates.length; i++) {
            days[i] = Integer.parseInt(dates[i].substring(8, 10));
            months[i] = Integer.parseInt(dates[i].substring(5, 7));
            years[i] = Integer.parseInt(dates[i].substring(0, 4));
        }

        daysMonthsYears.add(days);
        daysMonthsYears.add(months);
        daysMonthsYears.add(years);

        return daysMonthsYears;
    }

    public String[] dateToDayWeek(ArrayList<Integer[]> dates, int size) {

        String[] daysOfWeek = new String[size];
        Integer[] days = dates.get(0);
        Integer[] months = dates.get(1);
        Integer[] years = dates.get(2);
        Calendar calendar = new GregorianCalendar();
        int dayOfWeek;

        for (int i = 0; i < daysOfWeek.length; i++) {
            calendar.set(years[i], months[i] - 1, days[i]);
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            switch (dayOfWeek) {
                case 1:
                    daysOfWeek[i] = "Sunday";
                    break;
                case 2:
                    daysOfWeek[i] = "Monday";
                    break;
                case 3:
                    daysOfWeek[i] = "Tuesday";
                    break;
                case 4:
                    daysOfWeek[i] = "Wednesday";
                    break;
                case 5:
                    daysOfWeek[i] = "Thursday";
                    break;
                case 6:
                    daysOfWeek[i] = "Friday";
                    break;
                case 7:
                    daysOfWeek[i] = "Saturday";
                    break;
                default: break;
            }
        }

        return daysOfWeek;
    }

    public HashMap<String, Object[][]> formationResultMap(String[] keys,String[] dates, double[] temperature, String[] weatherMain, String[] weatherDescription) {

        ArrayList<Object[][]> finalValues = new ArrayList<>();

        HashMap<String, Object[][]> finalMap = new HashMap<String, Object[][]>();

        ArrayList<String> finalKeys = new ArrayList<>();

        int mondayCount = 0;
        int tuesdayCount = 0;
        int wednesdayCount = 0;
        int thursdayCount = 0;
        int fridayCount = 0;
        int saturdayCount = 0;
        int sundayCount = 0;

        for (String key : keys) {
            if (key.equals("Monday")) mondayCount++;
            if (key.equals("Tuesday")) tuesdayCount++;
            if (key.equals("Wednesday")) wednesdayCount++;
            if (key.equals("Thursday")) thursdayCount++;
            if (key.equals("Friday")) fridayCount++;
            if (key.equals("Saturday")) saturdayCount++;
            if (key.equals("Sunday")) sundayCount++;
        }

        if (mondayCount != 0) {
            Object[][] mondayObjects = new Object[mondayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Monday")) {
                    mondayObjects[j][0] = dates[i];
                    mondayObjects[j][1] = temperature[i];
                    mondayObjects[j][2] = weatherMain[i];
                    mondayObjects[j][3] = weatherDescription[i];
                    j++;
                    }
            }
            finalValues.add(mondayObjects);
            finalKeys.add("Monday");
        }

        if (tuesdayCount != 0) {
            Object[][] tuesdayObjects = new Object[tuesdayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Tuesday")) {
                    tuesdayObjects[j][0] = dates[i];
                    tuesdayObjects[j][1] = temperature[i];
                    tuesdayObjects[j][2] = weatherMain[i];
                    tuesdayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(tuesdayObjects);
            finalKeys.add("Tuesday");
        }

        if (wednesdayCount != 0) {
            Object[][] wednesdayObjects = new Object[wednesdayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Wednesday")) {
                    wednesdayObjects[j][0] = dates[i];
                    wednesdayObjects[j][1] = temperature[i];
                    wednesdayObjects[j][2] = weatherMain[i];
                    wednesdayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(wednesdayObjects);
            finalKeys.add("Wednesday");
        }

        if (thursdayCount != 0) {
            Object[][] thursdayObjects = new Object[thursdayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Thursday")) {
                    thursdayObjects[j][0] = dates[i];
                    thursdayObjects[j][1] = temperature[i];
                    thursdayObjects[j][2] = weatherMain[i];
                    thursdayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(thursdayObjects);
            finalKeys.add("Thursday");
        }

        if (fridayCount != 0) {
            Object[][] fridayObjects = new Object[fridayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Friday")) {
                    fridayObjects[j][0] = dates[i];
                    fridayObjects[j][1] = temperature[i];
                    fridayObjects[j][2] = weatherMain[i];
                    fridayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(fridayObjects);
            finalKeys.add("Friday");
        }

        if (saturdayCount != 0) {
            Object[][] saturdayObjects = new Object[saturdayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Saturday")) {
                    saturdayObjects[j][0] = dates[i];
                    saturdayObjects[j][1] = temperature[i];
                    saturdayObjects[j][2] = weatherMain[i];
                    saturdayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(saturdayObjects);
            finalKeys.add("Saturday");


        }

        if (sundayCount != 0) {
            Object[][] sundayObjects = new Object[sundayCount][4];
            int j = 0;
            for (int i = 0; i < dates.length; i++) {
                if (keys[i].equals("Sunday")) {
                    sundayObjects[j][0] = dates[i];
                    sundayObjects[j][1] = temperature[i];
                    sundayObjects[j][2] = weatherMain[i];
                    sundayObjects[j][3] = weatherDescription[i];
                    j++;
                }
            }
            finalValues.add(sundayObjects);
            finalKeys.add("Sunday");
        }


        for (int i = 0; i < finalValues.size(); i++) {
            finalMap.put(finalKeys.get(i), finalValues.get(i));
        }

        return finalMap;
    }
}
