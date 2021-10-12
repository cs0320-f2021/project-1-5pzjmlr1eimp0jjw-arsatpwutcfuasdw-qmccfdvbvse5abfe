package edu.brown.cs.student.main.kdtree;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;


public class JsonFileReader {
    private String _fileAddress;

    public JsonFileReader(String fileAddy) {
        _fileAddress = fileAddy;
    }

    public ArrayList<User> loadUsers() {
        ArrayList<User> strArr = new ArrayList<User>();
        String line;
        int counter = 0;
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();

        try {
            BufferedReader fileR = new BufferedReader(new FileReader(_fileAddress));
            while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
                line = line.trim();
                line = line.replaceAll("\\[", "");  //remove the square brackets
                line = line.replaceAll("]", "");
                line = line.substring(0, line.length() - 1);
                if(line.charAt(line.length() - 1) != '}'){  //last json object will not have comma so add back the bracket that you deleted
                    line = line.concat("}");
                }

                User currUser = gson.fromJson(line, User.class);
                counter++;
                strArr.add(currUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } return strArr;
    }
}
