package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class naiveNeighbors implements TriggerAction {

  int _fromStar;
  int _fromPos;

  public naiveNeighbors(){


    _fromStar = 2;
    _fromPos = 4;

  }

  @Override
  public String command() {
    return "naive_neighbors";
  }

  @Override
  public String execute(String[] args) {

    int index;
    ArrayList<star> curStarData = csvReader.getCurStarData();

    HashMap<star, Double> result = new HashMap<>();

    if (Integer.parseInt(args[0]) == 0) {  //find zero star -> terminate
      return "zero star returned";
    }

    if (args.length == this.getNumPara()[0]) {
      String removedQuotes = this.removeQuotes(args[1]); //remove quotes from the star name passed in
      args[1] = removedQuotes;
      index = this.findIndex(args[1], curStarData);

      if (!this.exceptionHandling(curStarData,
          args[1])) {  //if the star is not in the data read previously
        return "starting star not in the data";
      } else if (curStarData.size() == 1) {    //if the starting star is the only star in the data
        return "star data only contains one star";
      }

      for (star s : curStarData) {
        double temp;
        temp = this.findDis(curStarData.get(index),
            s);  //find the distance of each star from the starting star
        result.put(s, temp);  //put it in a list

      }

    } else if(args.length == this.getNumPara()[1]){ //Pattern 2: naive_neighbors <k> <x> <y> <z>
        for(star s: curStarData){
          double temp2;
          temp2 = this.approxDis(s, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
          result.put(s, temp2);
        }

      } else {
          //not conforming to the two formats!
          return "Invalid Input: Please follow the format";
        }

    HashMapSort HMS = new HashMapSort(result);
    Iterator<Map.Entry<star, Double>> hmIterator = HMS.hashSort(); //here the sorted iterator is returned from HashMapSort class!

    int i = 0;

    while((hmIterator.hasNext()) && (i < Integer.parseInt(args[0]))){
      star curStar = hmIterator.next().getKey();
      if(args.length == 2) {
        if (!curStar.getName().equals(args[1])){ //do not print out the starting star!
          System.out.println(curStar.getID());
          i++;
        }
      } else {
        System.out.println(curStar.getID());
        i++;
      }
    }

    return null;
  }

  @Override
  public int[] getNumPara() {

    return new int[] {_fromStar, _fromPos} ;
  }
  /**distance calculating equation for 5 inputs*/
  public double approxDis(star s1, Double x, Double y, Double z){
    double ans = 0.0;
    ans = Math.pow((s1.getX() - x),2) + Math.pow((s1.getY() - y),2) + Math.pow((s1.getZ() - z),2);
    return ans;
  }
  /**distance calculating eqution for 3 inputs*/
  public double findDis(star s1, star s2){
    double ans = 0.0;
    ans = Math.pow((s1.getX() - s2.getX()),2) + Math.pow((s1.getY() - s2.getY()),2) + Math.pow((s1.getZ() - s2.getZ()),2);
    return ans;
  }

  /**finds the index of the star passed in by iterating through the List and doing .equals with each element*/
  public int findIndex(String target, List<star> st){
    int index = -1;
    for (star str: st){
      if(str.getName().toString().equals(target.toString())){
        index = st.indexOf(str);
      }
    } return index;
  }

  /**This method returns true if a star exits in the list passed in. Otherwise false*/
  public boolean exceptionHandling(List<star> strList, String targetName){
    boolean exist = false;
    for(star str: strList){
      if (str.getName().equals(targetName)) {
        exist = true;
        break;
      }
    } return exist;
  }

  /**removes quotes of the string passed in*/
  public String removeQuotes(String inputStr){
    String returnStr;
    returnStr = inputStr.replace("\"", "");
    return returnStr;
  }

}
