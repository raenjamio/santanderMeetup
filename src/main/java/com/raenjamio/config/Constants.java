package com.raenjamio.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String X_RAPIDAPI_HOST = "x-rapidapi-host";
    public static final String X_RAPIDAPI_KEY = "x-rapidapi-key";
    public static final String OPEN_WEATHER_RAPIDAPI_COM = "community-open-weather-map.p.rapidapi.com";
    public static final String RAPIDAPI_KEY = "81aa57eeb2msh38c9b2dcd28e238p1d7fcejsnefcc305f9c5d";
    public static final String URL_OPEN_WEATHER = "https://community-open-weather-map.p.rapidapi.com/weather?callback=test&id=2172797&units=%2522metric%2522%20or%20%2522imperial%2522&mode=xml%252C%20html&q=London%252Cuk";
    public static final String PARAMETERS = "parameters";

    private Constants() {
    }
}
