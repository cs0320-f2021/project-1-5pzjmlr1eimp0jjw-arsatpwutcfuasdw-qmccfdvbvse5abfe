package edu.brown.cs.student.main.repl;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerActionSetUp {
  Map<String, TriggerAction> actions;

  /** Construct a TriggerAction list given the possible commands
   * @param tas List of actions corresponding to commands that can be executed*/
  public TriggerActionSetUp(List<TriggerAction> tas) {
    actions =  new HashMap<>();
    for (TriggerAction ta : tas) {
      actions.put(ta.command(), ta);
    }
  }

  /** Executes a given command using the args passed in
   * @param command Command in question
   * @param args arguments that command will use
   */

  public String executeTriggerAction(String command, String[] args)
    throws IllegalArgumentException{
      String result = "";
      TriggerAction corAction = actions.get(command);

     if(corAction != null){
       boolean invalidArgument = true;
       for (int possibleParameters : corAction.getNumPara()){
         if (args.length == possibleParameters)  //needed args = input args
         invalidArgument = false;
         result = corAction.execute(args);  //execute the corresponding execute()
       } if(invalidArgument){
         throw new IllegalArgumentException("ERROR: Invalid number of Arguments"); //not enough args
       }
     } else {
       throw new IllegalArgumentException("ERROR: Command not understood"); //no corresponding execute function
     }

     return result;
  }
}

