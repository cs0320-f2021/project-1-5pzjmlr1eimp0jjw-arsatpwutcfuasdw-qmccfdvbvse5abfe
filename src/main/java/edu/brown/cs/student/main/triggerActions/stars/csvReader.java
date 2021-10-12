package edu.brown.cs.student.main.triggerActions.stars;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class csvReader {

  public csvReader() {
  }
  public List<String[]> read(String[] args){
    List<String[]> readData = new ArrayList<String[]>();
    String line;
    try {

      BufferedReader fileR = new BufferedReader(new FileReader(args[0]));

      while ((line = fileR.readLine()) !=
          null) {  //abstract out only the relevant parts of the input
        line = line.trim();
        String[] stars = line.split(",");
        readData.add(stars);
      }
    } catch (Exception e){
    e.printStackTrace();
    }
    return readData;
  }

}
