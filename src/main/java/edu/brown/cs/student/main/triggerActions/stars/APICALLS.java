package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.api.API;
import edu.brown.cs.student.main.kdtree.JsonFileReader;
import edu.brown.cs.student.main.kdtree.User;
import edu.brown.cs.student.main.repl.TriggerAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APICALLS implements TriggerAction {
  static ArrayList<User> _storedUsers;
  int _shortArg;
  int _longArg;
  String[] urls;

  public APICALLS() {
    _storedUsers = new ArrayList<>();
    _shortArg = 1;
    _longArg = 3;
    this.urls = new String[] { "data/urls/UserData", "data/urls/ReviewData", "data/urls/Rent" };
  }

  @Override
  public String command() {
    return "users";
  }

  @Override
  public String execute(String[] args) {
    String ans = "";
    System.out.println("executing...");

    try {
      if (args.length == _longArg) {

        System.out.println("...in online!");
        String pathToFile = this.whichData(urls, args[1]);
        File file = new File(pathToFile);

        System.out.println(args[0] + " file made!");
        API call = new API(this.url_to_urls(file), args[2]);
        System.out.println("...api calls being processed");
        call.getIntroGetRequest();
        ans = call.get_data().size() + " new instances of User have been uploaded through API aggregator!";
        ArrayList<User> temp = new ArrayList<>(call.get_data());
        _storedUsers = temp;
      } else {
        System.out.print("finding document");
        JsonFileReader reader = new JsonFileReader(args[0]);
        _storedUsers = reader.loadUsers();
        ans = _storedUsers.size() + " new instances of User have been uploaded!";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ans;
  }

  @Override
  public int[] getNumPara() {
    return new int[] { _shortArg, _longArg };
  }

  /**
   * return the data depending on the repl input
   */
  public String whichData(String[] possibleData, String wanted) {

    if (wanted.equals("User")) {
      return possibleData[0];
    } else if (wanted.equals("Review")) {
      return possibleData[1];
    } else {
      return possibleData[2];
    }
  }

  public List<String> url_to_urls(File file) throws IOException {
    BufferedReader bf = new BufferedReader(new FileReader(file));
    String line = "";
    List<String> urls = new ArrayList<String>();

    while ((line = bf.readLine()) != null) {
      urls.add(line);
    }
    return urls;
  }

  public static ArrayList<User> get_storedUsers() {
    System.out.println(_storedUsers.size());
    return _storedUsers;
  }

}
