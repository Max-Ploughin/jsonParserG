import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

/*
Class for pasrsing Json file in JsonArray
 */

public class ParseJson {

    public static JsonArray ParseInArray(String path, String jsonArrayName){

        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(path));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray jsonArrayOfJson = fileObject.get(jsonArrayName).getAsJsonArray();
            return jsonArrayOfJson;

        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }

        return null;
    }


}
