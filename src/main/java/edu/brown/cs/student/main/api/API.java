package edu.brown.cs.student.main.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import edu.brown.cs.student.main.kdtree.Student;
import edu.brown.cs.student.main.kdtree.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.List;

import java.time.Duration;
import java.time.Instant;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class API {
    private static int _max_iter;
    private static List<String> _baseurls;
    Gson gs;
    Duration _max_time;
    Set<User> _data = new HashSet<>();
    Set<User> _aggregator;
    String myDataSet;

    /**
     * Constructs an API, allows user to specify desired priortization of accuracy,
     * iterations, or time
     * 
     * @param base_url the base url of the api
     */
    public API(List<String> base_url, String dataSet) {
        _baseurls = base_url;
        _max_iter = 10; // these numbers will change as testing happens
        _max_time = Duration.ZERO;
        _max_time = _max_time.plusSeconds(20);
        this.gs = new Gson();
        this.myDataSet = dataSet;
        _aggregator = new HashSet<>();
    }

    /**
     * sends out api requests to the given urls stored in _baseurls
     */
    public void getIntroGetRequest() throws IOException {
        // get all urls
        List<String> url_list = get_url();
        // randomize the url_list
        Collections.shuffle(url_list);

        for (int i = 0; i < url_list.size() && i <= get_max_iter() && !get_max_time().isNegative(); i++) {
            // if we still have space and time, but have gone through the list
            String curr_url = url_list.get(i);
            System.out.println("Current url is " + curr_url);
            String reqUri = curr_url + "?auth=kyoshi&key=56i3x5fRAS";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(reqUri)).build();
            // need to deserialize request
            ApiClient client = new ApiClient();
            // gets the time before the call
            Instant inst1 = Instant.now();
            // makes the call
            HttpResponse<String> response = client.makeRequest(request);
            // gets the time after the call
            Instant inst2 = Instant.now();
            Duration time_run = Duration.between(inst1, inst2);
            // updates time to reflect time left
            set_time(get_max_time().minus(time_run));
            System.out.println("Time remaining: " + get_max_time().toString());
            System.out.println("Status " + response.statusCode());

            if (199 < response.statusCode() && response.statusCode() < 300) {
                // checks to see if status code starts with a 2
                // makes a set from the api calls
                if (this.legitDataSet(response)) {
                    String line = "";
                    line = response.body();
                    ObjectMapper mapper = new ObjectMapper();
                    User[] myObjects = mapper.readValue(line, User[].class);
                    ArrayList<User> temp = new ArrayList<>(Arrays.asList(myObjects));
                    // ArrayList<User> loopTemp = temp;
                    for (User usr : myObjects) {
                        for (User user : _aggregator) {
                            if (usr.equals(user)) { // compare the hashcode of the User
                                // System.out.println("removed sadly " + usr.user_id);
                                temp.remove(usr);
                            }
                        }
                    }
                    _aggregator.addAll(temp);
                    // update data to have all the new pulls
                }
            }
        }
        System.out.println(_aggregator.size() + " new instances of User created!");
    }

    /**
     * On successful execution, this method will return an array of Student class
     * loaded from the API
     */
    public Student[] getSecuredAPI() throws IOException, InterruptedException {

        String url = "https://runwayapi.herokuapp.com/integration";

        String apiKey = ClientAuth.getApiKey();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("x-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString("{\"auth\":\"" + "kyoshi" + "\"}")).build(); // making POST
                                                                                                       // request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (199 < response.statusCode() && response.statusCode() < 300) {
            // checks to see if status code starts with a 200
            // makes a set from the api calls
            System.out.println("API call success!");
            String line = "";
            line = response.body();
            ObjectMapper mapper = new ObjectMapper();
            Student[] myObjects = mapper.readValue(line, Student[].class);
            return myObjects;
        } else {
            String temp = "Unknow error has occured";
            System.out.println(temp);
            return null;
        }
    }

    /**
     * @return boolean check if the dataset is the one we desire
     */
    public boolean legitDataSet(HttpResponse<String> response) {

        String line;
        line = response.body();
        ObjectMapper mapper = new ObjectMapper();

        try {
            User[] myObjects = mapper.readValue(line, User[].class); // try mapping
        } catch (UnrecognizedPropertyException UPE) { // if unable to map
            return false; // return false
        } catch (JsonMappingException e) {
            return false;
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }

    // public boolean compFields(List<User> L1, List<User> L2){
    // if(L1.get(0).user_id != null && L2.g)
    // }

    public static List<String> get_url() {
        return _baseurls;
    }

    public static int get_max_iter() {
        return _max_iter;
    }

    public Duration get_max_time() {
        return _max_time;
    }

    public void set_time(Duration time) {
        _max_time = time;
    }

    public Set<User> get_data() {
        return _aggregator;
    }

    public void set_data(Set<User> response_gson) {
        _aggregator = response_gson;
    }

}
