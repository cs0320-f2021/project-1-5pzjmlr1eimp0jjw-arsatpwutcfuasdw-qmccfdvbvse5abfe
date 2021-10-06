package edu.brown.cs.student.main.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.brown.cs.student.main.kdtree.User;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
    private int _num_entries;
    Duration _max_time;
    Set<String> _data = new HashSet<String>();

        /**
         * Constructs an API, allows user to specify desired priortization of accuracy, iterations, or time
         * @param base_url the base url of the api
         * @param num_entries the number of entries for that specific base url
         * @param max_iter - maximum number of iterations
         * @param max_time - maximum time for api call (seconds)
         */
//        public API(List<String> base_url, int num_entries, int max_iter, Duration max_time){
//            _baseurls = base_url;
//            _num_entries = num_entries;
//            _max_iter = max_iter;
//            _max_time = max_time;
//        }

        /**
         * Constructs an API using the optimal accuracy, iterations, and max_time as found through testing
         * @param base_url the base url of the api
         */
        public API(List<String> base_url) {
            _baseurls = base_url;
            _max_iter = 10; // these numbers will change as testing happens
            _max_time = Duration.ZERO;
            _max_time = _max_time.plusSeconds(20);
        }

    /**
     * sends out api requests to the given urls stored in _baseurls
     */
        public void getIntroGetRequest() {
            //get all urls
            List<String> url_list = get_url();
            //randomize the url_list
            Collections.shuffle(url_list);
            //stop when max_iter has been reached or when all urls have been searched
            Gson gson = new Gson();
            //create an empty list of 500 status code urls
            List<String> check_again = new ArrayList<String>();

            for (int i = 0;  i <= url_list.size() && i <= get_max_iter() && !get_max_time().isNegative(); i++){
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
//                    System.out.println("response class " + response.getClass());
                    //makes a set from the api calls
                    Set<String> url_responses = gson.fromJson(response.body(), Set.class);
                    System.out.println("gson modified class "+url_responses.getClass());
                    url_responses.addAll(get_data());
                    //update data to have all the new pulls
                    set_data(url_responses);

                } else if (499 < response.statusCode() && response.statusCode() < 600){
                    check_again.add(curr_url);
                }

//                System.out.println("the current data is: " + _data);
                System.out.println("the length of the data is: " + _data.size());

            }
            Set<String> jsonString = get_data();
//            Set<User> as_users = new HashSet<User>();
            for (String i: jsonString){
                System.out.println(i);
            }

            // See https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.html and
            // https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.Builder.html
        }






    public static List<String> get_url(){return _baseurls;}
    public int get_num_entries(){return _num_entries;}
    public static int get_max_iter(){return _max_iter;}
    public Duration get_max_time(){return _max_time;}
    public void set_time(Duration time){_max_time = time;}
    public Set<String> get_data(){return _data;}
    public void set_data(Set<String> response_gson) {_data = response_gson;}

    }

