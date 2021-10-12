package edu.brown.cs.student.main.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import edu.brown.cs.student.main.kdtree.Rent;
import edu.brown.cs.student.main.kdtree.Review;
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
         * Constructs an API, allows user to specify desired priortization of accuracy, iterations, or time
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
            //get all urls
            List<String> url_list = get_url();
            //randomize the url_list
            Collections.shuffle(url_list);

            for (int i = 0;  i < url_list.size() && i <= get_max_iter() && !get_max_time().isNegative(); i++){
                //if we still have space and time, but have gone through the list
                String curr_url = url_list.get(i);
                System.out.println("Current url is " + curr_url);
                String reqUri = curr_url + "?auth=hcunnin4&key=bi4w98vsP2";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(reqUri))
                        .build();
                //need to deserialize request
                ApiClient client = new ApiClient();
                //gets the time before the call
                Instant inst1 = Instant.now();
                //makes the call
                HttpResponse<String> response = client.makeRequest(request);
                //gets the time after the call
                Instant inst2 = Instant.now();
                Duration time_run = Duration.between(inst1, inst2);
                //updates time to reflect time left
                set_time(get_max_time().minus(time_run));
                System.out.println("Time remaining: " + get_max_time().toString());
                System.out.println("Status " + response.statusCode());

                if(199 < response.statusCode() && response.statusCode() < 300){
                    //checks to see if status code starts with a 2
                    //makes a set from the api calls
                    if(this.legitDataSet(response)) {
                        String line = "";
                        line = response.body();
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, User[]> mapUser = this.createUserMapping(mapper, line, false);
                        Map<String, Rent[]> mapRent = this.createRentMapping(mapper, line, false);
                        Map<String, Review[]> mapReview = this.createUserapping(mapper, line, false);
                        
                        ArrayList<User> temp = new ArrayList<>(Arrays.asList(myObjects));
                        // ArrayList<User> loopTemp = temp;
                        for(User usr : myObjects){
                                for(User user : _aggregator) {
                                    if(usr.equals(user)){  //compare the hashcode of the User
                                        //System.out.println("removed sadly " + usr.user_id);
                                        temp.remove(usr);
                                    }
                                }
                        }
                        _aggregator.addAll(temp);
                        //update data to have all the new pulls
                    }
                }
            }
            System.out.println(_aggregator.size() + " new instances of User created!");
        }

    /**
     * @return boolean check if the dataset is the one we desire
     * */
    public boolean legitDataSet(HttpResponse<String> response){


            String line;
            line = response.body();
            ObjectMapper mapper = new ObjectMapper();

            try {
                User[] myObjects = mapper.readValue(line, User[].class); //try mapping
            }
            catch(UnrecognizedPropertyException UPE) {  //if unable to map
                return false;  //return false
            } catch (JsonMappingException e) {
                return false;
            } catch (JsonProcessingException e) {
                return false;
            }
            return true;
    }

    public Map<String, User[]> createUserMapping(ObjectMapper obm, String line, boolean called)
        throws JsonProcessingException {
        Map<String, User[]> map = new HashMap<>();
        map.put("User", obm.readValue(line, User[].class));
        return map;
    }

    public Map<String, Rent[]> createRentMapping(ObjectMapper obm, String line, boolean called)
        throws JsonProcessingException {
        Map<String, Rent[]> map = new HashMap<>();
        map.put("Rent", obm.readValue(line, Rent[].class));
        return map;
    }
    public Map<String, Review[]> createReviewMapping(ObjectMapper obm, String line, boolean called)
        throws JsonProcessingException {
        Map<String, Review[]> map = new HashMap<>();
        map.put("Rent", obm.readValue(line, Review[].class));
        return map;
    }


    public static List<String> get_url(){return _baseurls;}
    public static int get_max_iter(){return _max_iter;}
    public Duration get_max_time(){return _max_time;}
    public void set_time(Duration time){_max_time = time;}
    public Set<User> get_data(){return _aggregator;}
    public void set_data(Set<User> response_gson) {_aggregator = response_gson;}

    }

