package com.example.pk.metcast.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class WeatherParsingModel {

    private City city;

    @SerializedName("cod")
    private String cod;
    @SerializedName("message")
    private double message;
    @SerializedName("cnt")
    private int cnt;

    private ArrayList<ListItem> list;

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

    public ArrayList<ListItem> getList() {
        return list;
    }

    public void setList(ArrayList<ListItem> list) {
        this.list = list;
    }

    public class City {
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

        public class Coord {
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

        public class Sys {
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

    public class ListItem {
        @SerializedName("dt")
        private long listDt;

        private Main main;

        private ArrayList<ListItemRoot> weather;

        private Clouds clouds;

        private Wind wind;

        private Rain rain;

        private Sys sys;

        @SerializedName("dt_txt")
        private String listDtTxt;

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public long getListDt() {
            return listDt;
        }

        public void setListDt(long listDt) {
            this.listDt = listDt;
        }

        public ArrayList<ListItemRoot> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<ListItemRoot> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getListDtTxt() {
            return listDtTxt;
        }

        public void setListDtTxt(String listDtTxt) {
            this.listDtTxt = listDtTxt;
        }

        public class Main {
            @SerializedName("temp")
            private double listMainTemp;
            @SerializedName("temp_min")
            private double listMainTempMin;
            @SerializedName("temp_max")
            private double listMainTempMax;
            @SerializedName("pressure")
            private double listMainPressure;
            @SerializedName("sea_level")
            private double listMainSeaLevel;
            @SerializedName("grnd_level")
            private double listMainGrndLevel;
            @SerializedName("humidity")
            private int listMainHumidity;
            @SerializedName("temp_kf")
            private double listMainTempKf;

            public double getListMainTemp() {
                return listMainTemp;
            }

            public void setListMainTemp(double listMainTemp) {
                this.listMainTemp = listMainTemp;
            }

            public double getListMainTempMin() {
                return listMainTempMin;
            }

            public void setListMainTempMin(double listMainTempMin) {
                this.listMainTempMin = listMainTempMin;
            }

            public double getListMainTempMax() {
                return listMainTempMax;
            }

            public void setListMainTempMax(double listMainTempMax) {
                this.listMainTempMax = listMainTempMax;
            }

            public double getListMainPressure() {
                return listMainPressure;
            }

            public void setListMainPressure(double listMainPressure) {
                this.listMainPressure = listMainPressure;
            }

            public double getListMainSeaLevel() {
                return listMainSeaLevel;
            }

            public void setListMainSeaLevel(double listMainSeaLevel) {
                this.listMainSeaLevel = listMainSeaLevel;
            }

            public double getListMainGrndLevel() {
                return listMainGrndLevel;
            }

            public void setListMainGrndLevel(double listMainGrndLevel) {
                this.listMainGrndLevel = listMainGrndLevel;
            }

            public int getListMainHumidity() {
                return listMainHumidity;
            }

            public void setListMainHumidity(int listMainHumidity) {
                this.listMainHumidity = listMainHumidity;
            }

            public double getListMainTempKf() {
                return listMainTempKf;
            }

            public void setListMainTempKf(double listMainTempKf) {
                this.listMainTempKf = listMainTempKf;
            }
        }

        public class ListItemRoot {
            @SerializedName("id")
            private int listWeatherId;
            @SerializedName("main")
            private String listWeatherMain;
            @SerializedName("description")
            private String listWeatherDescription;
            @SerializedName("icon")
            private String listWeatherIcon;

            public int getListWeatherId() {
                return listWeatherId;
            }

            public void setListWeatherId(int listWeatherId) {
                this.listWeatherId = listWeatherId;
            }

            public String getListWeatherMain() {
                return listWeatherMain;
            }

            public void setListWeatherMain(String listWeatherMain) {
                this.listWeatherMain = listWeatherMain;
            }

            public String getListWeatherDescription() {
                return listWeatherDescription;
            }

            public void setListWeatherDescription(String listWeatherDescription) {
                this.listWeatherDescription = listWeatherDescription;
            }

            public String getListWeatherIcon() {
                return listWeatherIcon;
            }

            public void setListWeatherIcon(String listWeatherIcon) {
                this.listWeatherIcon = listWeatherIcon;
            }
        }

        public class Clouds {
            @SerializedName("all")
            private int listCloudsAll;

            public int getListCloudsAll() {
                return listCloudsAll;
            }

            public void setListCloudsAll(int listCloudsAll) {
                this.listCloudsAll = listCloudsAll;
            }
        }

        public class Wind {
            @SerializedName("speed")
            private double listWindSpeed;
            @SerializedName("deg")
            private double listWindDeg;

            public double getListWindSpeed() {
                return listWindSpeed;
            }

            public void setListWindSpeed(double listWindSpeed) {
                this.listWindSpeed = listWindSpeed;
            }

            public double getListWindDeg() {
                return listWindDeg;
            }

            public void setListWindDeg(double listWindDeg) {
                this.listWindDeg = listWindDeg;
            }
        }

        public class Rain {
            @SerializedName("3h")
            private double listRain3h;

            public double getListRain3h() {
                return listRain3h;
            }

            public void setListRain3h(double listRain3h) {
                this.listRain3h = listRain3h;
            }
        }

        public class Sys {
            @SerializedName("pod")
            private String listSysPod;

            public String getListSysPod() {
                return listSysPod;
            }

            public void setListSysPod(String listSysPod) {
                this.listSysPod = listSysPod;
            }
        }
    }
}
