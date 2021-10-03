package edu.brown.cs.student.main.repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class REPL {
  private REPL(){
  }
  public static void run(List<TriggerAction> tas) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) { //while the REPL has input
        try {
          input = input.trim();
          String[] arguments = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
          String command = arguments[0]; //retrieve the command (ex "stars", "add")
          String[] args = Arrays.copyOfRange(arguments, 1, arguments.length); //copy the rest of arguments into different array

          TriggerActionSetUp executor = new TriggerActionSetUp(tas); 
          System.out.println(executor.executeTriggerAction(command, args)); //print out the results of the execute method
        } catch (IllegalArgumentException e) {
          System.err.println(e.getMessage());
        } catch (Exception e){
          e.printStackTrace();
          System.err.println("Error: Unknown cause of error");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Invalid Input for REPL");
    }
  }
}
