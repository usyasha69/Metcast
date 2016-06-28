package com.example.pk.metcast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class WeatherModel {

    private City city;

    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;

    private ArrayList<ListItem> list;
    private ListItem listItem;


//    @SerializedName("list_main_temp")
//    private double listMainTemp;
//    @SerializedName("list_main_temp_min")
//    private double listMainTempMin;
//    @SerializedName("list_main_temp_max")
//    private double listMainTempMax;
//    @SerializedName("list_main_pressure")
//    private double listMainPressure;
//    @SerializedName("list_main_sea_level")
//    private double listMainSeaLevel;
//    @SerializedName("list_main_grnd_level")
//    private double listMainGrndLevel;
//    @SerializedName("list_main_humidity")
//    private int listMainHumidity;
//    @SerializedName("list_main_temp_kf")
//    private double listMainTempKf;
//    @SerializedName("list_weather_id")
//    private int listWeatherId;
//    @SerializedName("list_weather_main")
//    private String listWeatherMain;
//    @SerializedName("list_weather_description")
//    private String listWeatherDescription;
//    @SerializedName("list_weather_icon")
//    private String listWeatherIcon;
//    @SerializedName("list_clouds_all")
//    private int listCloudsAll;
//    @SerializedName("list_wind_speed")
//    private double listWindSpeed;
//    @SerializedName("list_wind_deg")
//    private double listWindDeg;
//    @SerializedName("list_sys_pod")
//    private String listSysPod;
//    @SerializedName("list_rain_3h")
//    private double listRain3h;
//    @SerializedName("list_dt_txt")
//    private String listDtTxt;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public ListItem getListItem() {
        return listItem;
    }

    public void setListItem(ListItem listItem) {
        this.listItem = listItem;
    }

    class City {
        @SerializedName("id")
        private int cityId;
        @SerializedName("name")
        private String cityName;

        private Coord coord;

        @SerializedName("country")
        private String cityCountry;
        @SerializedName("population")
        private int cityPopulation;

        private Sys sys;

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityCountry() {
            return cityCountry;
        }

        public void setCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
        }

        public int getCityPopulation() {
            return cityPopulation;
        }

        public void setCityPopulation(int cityPopulation) {
            this.cityPopulation = cityPopulation;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        class Coord {
            @SerializedName("lon")
            private double cityLonCoord;
            @SerializedName("lat")
            private double cityLatCoord;

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
        }

        class Sys {
            @SerializedName("sys_population")
            private int citySysPopulation;

            public int getCitySysPopulation() {
                return citySysPopulation;
            }

            public void setCitySysPopulation(int citySysPopulation) {
                this.citySysPopulation = citySysPopulation;
            }
        }
    }

    class ListItem {
        @SerializedName("dt")
        private int listDt;

        public int getListDt() {
            return listDt;
        }

        public void setListDt(int listDt) {
            this.listDt = listDt;
        }
    }
}
