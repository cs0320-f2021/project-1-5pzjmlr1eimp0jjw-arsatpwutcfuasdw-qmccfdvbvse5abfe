package edu.brown.cs.student.main.kdtree;

import com.google.gson.Gson;
import edu.brown.cs.student.main.api.API;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JsonFileReader {
    private String _fileAddress;

    public JsonFileReader(String fileAddy) {
        _fileAddress = fileAddy;
    }

    public List<User> loadUsers() {
        List<User> strArr = new ArrayList<User>();
        String line;
        int counter = 0;
        Gson gson = new Gson();
        try {
            BufferedReader fileR = new BufferedReader(new FileReader(_fileAddress));
            while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
                line = line.trim();
                if (counter == 0) {
                    line = line.substring(0);
                } else if (line.split("/]").length == 2) {
                    line = line.split("/]")[1];
                }
                User currUser = gson.fromJson(line, User.class);
                counter++;
                strArr.add(currUser);
            }
            return strArr;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return new ArrayList<User>();
    }
}
