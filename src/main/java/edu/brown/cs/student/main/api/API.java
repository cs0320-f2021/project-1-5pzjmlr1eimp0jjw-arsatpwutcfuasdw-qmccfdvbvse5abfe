package edu.brown.cs.student.main.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class API {
    private static int _max_iter;
    private static List<String> _baseurls;
    private int _num_entries;
    double _max_time;
    Set<String> _data = new HashSet<String>();

        /**
         * Constructs an API, allows user to specify desired priortization of accuracy, iterations, or time
         * @param base_url the base url of the api
         * @param num_entries the number of entries for that specific base url
         * @param max_iter - maximum number of iterations
         * @param max_time - maximum time for api call (seconds)
         */
        public API(List<String> base_url, int num_entries, int max_iter, double max_time){
            _baseurls = base_url;
            _num_entries = num_entries;
            _max_iter = max_iter;
            _max_time = max_time;
        }

        /**
         * Constructs an API using the optimal accuracy, iterations, and max_time as found through testing
         * @param base_url the base url of the api
         */
        public API(List<String> base_url) {
            _baseurls = base_url;
            _max_iter = base_url.size() % 2; // these numbers will change as testing happens
            _max_time = 20;
        }

        /**
         *
         */
        public void load_data(){
            //steps we want to first get a list of each specific url we can use
            //grab max_iter randomly from list
            //go through these and ping each (probably outsource pinging to seperate api_call method)
            //take what they have and update data accordingly with any new information
            //stop pinging if max time occurs (potentially stop pining a website if that specific one over does the individual site time
            //when either all have been iterated through, or time has been reached

            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                double ans = 0.0;
                while ((input = br.readLine()) != null) {
                    try {
                        input = input.trim();
                    } catch (Exception e) {
                        // e.printStackTrace();
                        System.out.println("ERROR: We couldn't process your input");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR: Invalid input for REPL");
            }
        }
        public HttpRequest getIntroGetRequest() {
            //get all urls
            List<String> url_list = get_url();
            //randomize the url_list
            Collections.shuffle(url_list);
            //stop when max_iter has been reached or when all urls have been searched
            Gson gson = new Gson();

            for (int i = 0; i <= url_list.size() && i <= get_max_iter(); i++){
                String curr_url = url_list.get(i);
                System.out.println("Current url is " + curr_url);
                String reqUri = curr_url + "?auth=hcunnin4&key=bi4w98vsP2";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(reqUri))
                        .build();
                //need to deserialize request
                ApiClient client = new ApiClient();
                HttpResponse<String> response = client.makeRequest(request);
                System.out.println("Status " + response.statusCode());
                if(399 < response.statusCode() && response.statusCode() < 500){
                    //checks to see if status code starts with a 4
                    //code taken from https://stackoverflow.com/questions/2967898/retrieving-the-first-digit-of-a-number/2968068
                    System.out.println("response class " + response.getClass());
                    Set<String> url_responses = gson.fromJson(response.body(), Set.class);
                    System.out.println("gson modified class "+url_responses.getClass());
                    url_responses.addAll(get_data());
                    set_data(url_responses);
                }

                System.out.println("the current data is: " + _data);

            }

            // See https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.html and
            // https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.Builder.html
        return null;
        }






    public static List<String> get_url(){return _baseurls;}
    public int get_num_entries(){return _num_entries;}
    public static int get_max_iter(){return _max_iter;}
    public double get_max_time(){return _max_time;}
    public Set<String> get_data(){return _data;}
    public void set_data(Set<String> response_gson) {_data = response_gson;}

    }

