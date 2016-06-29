package com.example.pk.metcast;


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

    public DayWeatherModel makeUpCurrentWeather(String query) {

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
        dayWeatherModel.setListMainHumidity(listMainGrndLevelArray);

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
                listWeatherIdArray[j] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherId();
            }
        }
        dayWeatherModel.setListWeatherID(listWeatherIdArray);

        //listWeatherID
        String[] listWeatherMainArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherMainArray[j] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherMain();
            }
        }
        dayWeatherModel.setListWeatherMain(listWeatherMainArray);

        //listWeatherDescription
        String[] listWeatherDescriptionArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherDescriptionArray[j] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherDescription();
            }
        }
        dayWeatherModel.setListWeatherDescription(listWeatherDescriptionArray);

        //listWeatherIcon
        String[] listWeatherIconArray = new String[weatherParsingModel.getList().size()];
        for (int i = 0; i < weatherParsingModel.getList().size(); i++) {
            for (int j = 0; j < weatherParsingModel.getList().get(i).getWeather().size(); j++) {
                listWeatherIconArray[j] = weatherParsingModel.getList().get(i).getWeather().get(j).getListWeatherIcon();
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
}
