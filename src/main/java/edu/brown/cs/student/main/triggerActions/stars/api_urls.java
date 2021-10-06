package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.api.API;
import edu.brown.cs.student.main.repl.TriggerAction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class api_urls implements TriggerAction {

  //  static ArrayList<String> _storedStar;
//  public api_urls() {
//    _storedStar = new ArrayList<>();
//  }
  @Override
  public String command() {
    return "users";
  }

  @Override
  public String execute(String[] args) {
//    File file = new File(args[0]);
    String ans = "";


//    curData = this.api_urls(file);
//            //now _curData is an array list of strings with each entry being a URL
//            //now we need to make a new api object

//    API curr_api = new API(_curData);
    ArrayList<String> strArr = new ArrayList<String>();
    String line;
    try {
      BufferedReader fileR = new BufferedReader(new FileReader(args[0]));
      while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
        line = line.trim();
        strArr.add(line);
      }
      API curr_api = new API(strArr);
      curr_api.getIntroGetRequest();
//      _storedUrls = strArr;
//      System.out.println(_storedUrls.size());
//      ans = String.format("Read %d urls from %s", _storedUrls.size(), args[0]);
      ans = String.format("Read %d urls from %s", strArr.size(), args[0]);


    } catch (Exception e) {
      e.printStackTrace();
    }
    return ans;
  }

//    try {
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


  @Override
  public int[] getNumPara() {
    return new int[]{1};
  }

}
