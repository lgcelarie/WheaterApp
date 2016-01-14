package vandy.mooc.model.aidl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vandy.mooc.model.aidl.WeatherData.Main;
import vandy.mooc.model.aidl.WeatherData.Sys;
import vandy.mooc.model.aidl.WeatherData.Weather;
import vandy.mooc.model.aidl.WeatherData.Wind;
import android.util.JsonReader;
import android.util.JsonToken;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of WeatherData objects that contain this data.
 */
public class WeatherDataJsonParser {
    /**
     * Used for logging purposes.
     */
    private final String TAG =
        this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public List<WeatherData> parseJsonStream(InputStream inputStream)
        throws IOException {

        // TODO -- you fill in here.
        // Create a JsonReader for the inputStream.
        try (JsonReader reader =
                     new JsonReader(new InputStreamReader(inputStream,
                             "UTF-8"))) {
            // Log.d(TAG, "Parsing the results returned as an array");

            // Handle the array returned from the Acronym Service.
            return parseJsonWeatherDataArray(reader);
        }
    }

    /**
     * Parse a Json stream and convert it into a List of WeatherData
     * objects.
     */
    public List<WeatherData> parseJsonWeatherDataArray(JsonReader reader)
        throws IOException {
        // TODO -- you fill in here.
        List<WeatherData> weatherDatas = null;
        reader.beginObject();

        while (reader.hasNext()) {
            weatherDatas.add(parseJsonWeatherData(reader));
        }

        reader.endObject();
        return weatherDatas;
    }

    /**
     * Parse a Json stream and return a WeatherData object.
     */
    public WeatherData parseJsonWeatherData(JsonReader reader) 
        throws IOException {

        // TODO -- you fill in here.

        WeatherData theWeather = new WeatherData();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case WeatherData.cod_JSON:
                    theWeather.setCod(reader.nextLong());
                    break;

                case WeatherData.message_JSON:
                    theWeather.setMessage(reader.nextString());
                    break;

                case WeatherData.name_JSON:
                    theWeather.setName(reader.nextString());
                    break;

                case WeatherData.dt_JSON:
                    theWeather.setDate(reader.nextLong());
                    break;

                case WeatherData.sys_JSON:
                    if (reader.peek() == JsonToken.BEGIN_ARRAY)
                        theWeather.setSys(parseSys(reader));
                    break;

                case WeatherData.main_JSON:
                    if (reader.peek() == JsonToken.BEGIN_ARRAY)
                        theWeather.setMain(parseMain(reader));
                    break;

                case WeatherData.wind_JSON:
                    if (reader.peek() == JsonToken.BEGIN_ARRAY)
                        theWeather.setWind(parseWind(reader));
                    break;
                case WeatherData.weather_JSON:
                    if (reader.peek() == JsonToken.BEGIN_ARRAY)
                        theWeather.setWeathers(parseWeathers(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        return theWeather;
    }
    
    /**
     * Parse a Json stream and return a List of Weather objects.
     */
    public List<Weather> parseWeathers(JsonReader reader) throws IOException {
        // TODO -- you fill in here.
        List<Weather> weatherList = null;

        reader.beginArray();

        while(reader.hasNext())
        {
           weatherList.add(parseWeather(reader));
        }
        reader.endArray();
        return weatherList;

    }

    /**
     * Parse a Json stream and return a Weather object.
     */
    public Weather parseWeather(JsonReader reader) throws IOException {
        // TODO -- you fill in here.
        reader.beginObject();

        Weather theWeather = new Weather();
        while(reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case Weather.id_JSON:
                    theWeather.setId(reader.nextLong());
                    break;
                case Weather.main_JSON:
                    theWeather.setMain(reader.nextString());
                    break;
                case Weather.description_JSON:
                    theWeather.setDescription(reader.nextString());
                    break;
                case Weather.icon_JSON:
                    theWeather.setIcon(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return theWeather;
    }

    /**
     * Parse a Json stream and return a Main Object.
     */
    public Main parseMain(JsonReader reader) 
        throws IOException {
        // TODO -- you fill in here.
        reader.beginObject();
        Main theMain = new Main();

        while(reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case Main.temp_JSON:
                    theMain.setTemp(reader.nextDouble());
                    break;
                case Main.pressure_JSON:
                    theMain.setPressure(reader.nextDouble());
                    break;
                case Main.humidity_JSON:
                    theMain.setHumidity(reader.nextLong());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
        return theMain;
    }

    /**
     * Parse a Json stream and return a Wind Object.
     */
    public Wind parseWind(JsonReader reader) throws IOException {
        // TODO -- you fill in here.

        reader.beginObject();
        Wind theWind = new Wind();

        while(reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case Wind.speed_JSON:
                    theWind.setSpeed(reader.nextDouble());
                    break;
                case Wind.deg_JSON:
                    theWind.setDeg(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
        return theWind;
    }

    /**
     * Parse a Json stream and return a Sys Object.
     */
    public Sys parseSys(JsonReader reader)
        throws IOException {
        // TODO -- you fill in here.

        reader.beginObject();
        Sys theSys = new Sys();

        while(reader.hasNext())
        {
            String name = reader.nextName();
            switch (name)
            {
                case Sys.sunrise_JSON:
                    theSys.setSunrise(reader.nextLong());
                    break;
                case Sys.sunset_JSON:
                    theSys.setSunset(reader.nextLong());
                    break;
                case Sys.country_JSON:
                    theSys.setCountry(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();
        return theSys;
    }
}
