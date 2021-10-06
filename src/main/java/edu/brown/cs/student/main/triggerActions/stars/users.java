package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.api.API;
import edu.brown.cs.student.main.kdtree.JsonFileReader;
import edu.brown.cs.student.main.kdtree.User;
import edu.brown.cs.student.main.repl.TriggerAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class users implements TriggerAction {
  static ArrayList<User> _storedUsers;
  public users() {
    _storedUsers = new ArrayList<>();
  }
  @Override
  public String command() {
    return "users";
  }

  @Override
  public String execute(String[] args) {
    System.out.println("executing...");

    try {
      if(args[0].trim().equals("online")){
        System.out.println("in online");
        File file = new File("data/urls/two-urls.csv");
        System.out.println("file made");
        List<String> urlsList = api_urls(file);
        System.out.println("list of urls to search made");
        API call = new API(urlsList);
        System.out.println("api made");
        call.getIntroGetRequest();

      } else {
        System.out.print("finding document");
        JsonFileReader reader = new JsonFileReader(args[0]);
        ArrayList<User> users = reader.loadUsers();
        _storedUsers = users;


      }

      //if we are api
      //load file and make the needed api calls
      // returning a json
//      if we are not running api and pulling from a json
      //we can just run what we have below directly
//    String line;
//    ArrayList<String> strArr = new ArrayList<>();
//    String ans = "";
//
//      BufferedReader fileR = new BufferedReader(new FileReader(args[0]));
//      int counter = 0;
//      while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
//        line = line.trim();
//        String[] stars = line.split(",");
//
//        if (counter >= 1) { //skipping first line as it does not contain any star info
//          star newStar = new star(Integer.parseInt(stars[0]), stars[1], Double.parseDouble(stars[2]),
//              Double.parseDouble(stars[3]), Double.parseDouble(stars[4]));  //create a new instance of star. Passing in as well all the coordinates info
//          strArr.add(newStar);
//        }
//        counter++;
//      }
//      _storedStar = strArr;
//      System.out.println(_storedStar.size());
//      ans = String.format("Read %d stars from %s", _storedStar.size(), args[0]);
//
    } catch (Exception e) {
      e.printStackTrace();
    } return "ans";
  }

  @Override
  public int[] getNumPara() {
    return new int[]{1};
  }

  public static ArrayList<User> get_storedUsers(){
    System.out.println(_storedUsers.size());
    return _storedUsers;
  }

  /**
   * file reading for api
   * @param file list of base urls for api to search
   * @return a list of strings that has one entry for each potential url
   */
  public List<String> api_urls(File file) {

    String line;
    List<String> strArr = new ArrayList<String>();
    try {

      BufferedReader fileR = new BufferedReader(new FileReader(file));
      while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
        line = line.trim();
        strArr.add(line);
      }


    } catch (Exception e) {
      e.printStackTrace();
    } return strArr;
  }
}
