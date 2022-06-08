import com.google.gson.*;

import java.io.File;

//- среднее время полета между городами Владивосток и Тель-Авив
//- 90-й процентиль времени полета между городами  Владивосток и Тель-Авив


public class Main {
    public static  void main(String[] args)  {

        JsonArray jsonArrayOfTickets = ParseJson.ParseInArray("tickets.json", "tickets");
        System.out.println("The average time of flight is " + Ticket.getAverageFlightTime(jsonArrayOfTickets) + " minutes");
        System.out.println("90th percentile of time flights is "+Ticket.getPercentile(jsonArrayOfTickets, 90));

    }

}
