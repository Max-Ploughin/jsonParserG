import com.google.gson.*;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeZoneJSON {

    private String city;
    private String timeZone;

    public TimeZoneJSON(String city, String timeZone) {
        this.city = city;
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void addTimeZone(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Writer writer = Files.newBufferedWriter(Paths.get("timeZones.json"));
            Map<String, String> map = new HashMap<>();
            map.put("city", getCity());
            map.put("timeZone", getTimeZone());

            gson.toJson(map, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addTimeZone(List<TimeZoneJSON> list){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Writer writer = Files.newBufferedWriter(Paths.get("timeZones.json"));
            gson.toJson(list, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List getListOfTimeZones(JsonArray jsonArray){

        Gson gson = new Gson();
        List list = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            TimeZoneJSON timeZone = gson.fromJson(jsonElement, TimeZoneJSON.class);
            list.add(timeZone);

        }

        return list;
    }

    public static String getTimeZone(JsonArray jsonArrayOfZones, String city){

        List<TimeZoneJSON> tzList = getListOfTimeZones(jsonArrayOfZones);
        for (TimeZoneJSON elem : tzList){

            if(elem.getCity().equals(city)){
                return elem.getTimeZone();
            }

        }

        return null;
    }

}
