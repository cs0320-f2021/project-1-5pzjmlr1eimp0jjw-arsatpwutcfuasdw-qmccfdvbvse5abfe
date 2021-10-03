package edu.brown.cs.student.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerActionSetUp {
  Map<String, TriggerAction> actions;

  public TriggerActionSetUp(List<TriggerAction> tas) {
    actions =  new HashMap<>();
    for (TriggerAction ta : tas) {
      actions.put(ta.command(), ta);
    }
  }

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

