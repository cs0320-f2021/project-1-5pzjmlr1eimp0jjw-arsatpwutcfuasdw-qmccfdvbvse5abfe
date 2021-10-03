package edu.brown.cs.student.main.triggerActions.stars;


import edu.brown.cs.student.main.triggerActions.stars.star;

import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Iterator;

/**This class compares the values of hashmap (stars distance from given location) and return an sorted iterator
 * The way it works, first you put all the elements in the hashmap into arraylist. Then, using comparator sort the list*/
public class HashMapSort {

  HashMap<star, Double> _myMap;
  ArrayList<Map.Entry<star, Double>> _myArrList;

  public HashMapSort(HashMap<star, Double> myMap){

    _myMap = myMap;  //unsorted data
    _myArrList = new ArrayList<>(); //iterator of this sorted array is returned

  }

  public Iterator<Map.Entry<star, Double>> hashSort(){
    //transfer elements
    _myArrList.addAll(_myMap.entrySet());
    Comparator<Map.Entry<star, Double>> doubleComp = new Comparator<Map.Entry<star, Double>>(){
      @Override
      public int compare(Map.Entry<star, Double> ele1, Map.Entry<star, Double> ele2){
        Double val1 = ele1.getValue();  //double comparator is defined here
        Double val2 = ele2.getValue();
        return val1.compareTo(val2);
      }
    };

    _myArrList.sort(doubleComp);   //sort
    return _myArrList.iterator();
  }
}