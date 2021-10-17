package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.util.ArrayList;
import java.util.List;

public class stars implements TriggerAction {
  csvReader cR;
  static ArrayList<star> _storedStar;
  public stars(){
    this.cR = new csvReader();
    _storedStar = new ArrayList<>();
  }
  @Override
  public String command() {
    return "stars";
  }

  @Override
  public String execute(String[] args) {

    ArrayList<star> strArr = new ArrayList<star>();
    List<String[]> myStarData = cR.read(args);
    int counter = 0;
    String ans = "";

    for (String[] line: myStarData){
      if (counter >= 1) { //skipping first line as it does not contain any star info
        star newStar = new star(Integer.parseInt(line[0]), line[1], Double.parseDouble(line[2]),
            Double.parseDouble(line[3]), Double.parseDouble(line[4]));  //create a new instance of star. Passing in as well all the coordinates info
        strArr.add(newStar);
      }
      counter++;
    }

    _storedStar = strArr;
    ans = String.format("Read %d stars from %s", _storedStar.size(), args[0]);
    System.out.println(ans);
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[]{1};
  }
  public static ArrayList<star> getCurStarData(){
    return _storedStar;
  }
}

