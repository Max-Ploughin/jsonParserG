import com.google.gson.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


/*
Class that describes json file "Tickets".
It contains method for getting the average time of flight from all flights and method for percentile.
 */

public class Ticket {
    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String carrier;
    private Integer stops;
    private Integer price;


    public String getOrigin() {
        return origin;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public String getDestination() {
        return destination;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getCarrier() {
        return carrier;
    }

    public Integer getStops() {
        return stops;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDifference() {
        return new String();
    }


    private static List jsonListOfElements(JsonArray jsonArray) {
        /*
        Getting List from JsonArray
         */

        Gson gson = new Gson();
        List array = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            Ticket ticket = gson.fromJson(jsonElement, Ticket.class);
            array.add(ticket);

        }
        return array;
    }

    private static Date convertingStringsToDate (String date, String time) {
        /*
        Method for getting from 2 strings (date and time) - 1 string and then converting it to Date format
         */

        try{

            DateFormat formatter = new SimpleDateFormat("dd.mm.yy HH:mm");
            String combineDate = date + " " + time;
            Date finalDate = (Date)formatter.parse(combineDate);

            return finalDate;

        } catch (ParseException e){
            System.out.println("Date Parsing exception");
        }

        return null;

    }

    public int getFlightTime(){

        /*
        Method for getting time of flight
         */

        Date arrival = convertingStringsToDate(arrival_date, arrival_time);
        Date departure = convertingStringsToDate(departure_date, departure_time);
        long diff = arrival.getTime() - departure.getTime();

        return ((int)diff/60000 + calculateTimeDiff());

    }

    private int calculateTimeDiff(){

        /*
        Method for getting minutes of difference between time zones
         */

        JsonArray timeZonesJson = ParseJson.ParseInArray("timeZones.json");
        String originZone = TimeZoneJSON.getTimeZone(timeZonesJson, getOrigin_name());
        String destinationZone = TimeZoneJSON.getTimeZone(timeZonesJson, getDestination_name());

        ZoneId idOfOrigin = ZoneId.of(originZone);
        ZoneId idOfDestination = ZoneId.of(destinationZone);

        LocalDateTime localDateTimeOrigin = LocalDateTime.now(idOfOrigin);
        LocalDateTime localDateTimeDestination = LocalDateTime.now(idOfDestination);

        long differenceOfZones = Duration.between(localDateTimeDestination, localDateTimeOrigin).getSeconds();
        int minutes = (int)differenceOfZones/60 + (int)differenceOfZones % 60;

        return minutes;
    }

    public static int getAverageFlightTime(JsonArray jsonArray){
        /*
        Method which count the average time of flight from all flights
         */

        List<Integer> array = getArrayOfFlightTime(jsonArray);

        OptionalDouble result = array.stream().mapToInt(e -> e).average();
        double value = result.getAsDouble();

        return (int)value;

    }

    private static List<Integer> getArrayOfFlightTime(JsonArray jsonArray){
        /*
        Method for getting List of flight times
         */

        List<Integer> result = new ArrayList<>();
        List<Ticket> tickets = jsonListOfElements(jsonArray);

        for (Ticket ticket : tickets){
            Integer flightTime = Integer.valueOf(ticket.getFlightTime());
            result.add(flightTime);
        }

        return result;
    }

    private static int percentile(List<Integer> latencies, int percentile) {
        /*
        Method for percentile
         */

        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index - 1);
    }

    public static int getPercentile (JsonArray jsonArray, int percentileValue){
        /*
        Method for getting percentile from list of flights
         */

        List<Integer> list = getArrayOfFlightTime(jsonArray);
        Collections.sort(list);

        return percentile(list, percentileValue);
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "origin='" + origin + '\'' +
                ", origin_name='" + origin_name + '\'' +
                ", destination='" + destination + '\'' +
                ", destination_name='" + destination_name + '\'' +
                ", departure_date=" + departure_date +
                ", departure_time=" + departure_time +
                ", arrival_date=" + arrival_date +
                ", arrival_time=" + arrival_time +
                ", carrier='" + carrier + '\'' +
                ", stops=" + stops +
                ", price=" + price +
                '}';
    }
}
