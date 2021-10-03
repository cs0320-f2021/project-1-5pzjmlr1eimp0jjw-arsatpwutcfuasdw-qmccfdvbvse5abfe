package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class csvReader implements TriggerAction {
  static ArrayList<star> _storedStar;
  public csvReader() {
    _storedStar = new ArrayList<>();
  }
  @Override
  public String command() {
    return "stars";
  }

  @Override
  public String execute(String[] args) {

    String line;
    ArrayList<star> strArr = new ArrayList<star>();
    String ans = "";

    try {

      BufferedReader fileR = new BufferedReader(new FileReader(args[0]));
      int counter = 0;
      while ((line = fileR.readLine()) != null) {  //abstract out only the relevant parts of the input
        line = line.trim();
        String[] stars = line.split(",");

        if (counter >= 1) { //skipping first line as it does not contain any star info
          star newStar = new star(Integer.parseInt(stars[0]), stars[1], Double.parseDouble(stars[2]),
              Double.parseDouble(stars[3]), Double.parseDouble(stars[4]));  //create a new instance of star. Passing in as well all the coordinates info
          strArr.add(newStar);
        }
        counter++;
      }
      _storedStar = strArr;
      System.out.println(_storedStar.size());
      ans = String.format("Read %d stars from %s", _storedStar.size(), args[0]);

    } catch (Exception e) {
      e.printStackTrace();
    }return ans;
  }

  @Override
  public int[] getNumPara() {
    return new int[]{1};
  }

  public static ArrayList<star> getCurStarData(){
    System.out.println(_storedStar.size());
    return _storedStar;
  }
}
