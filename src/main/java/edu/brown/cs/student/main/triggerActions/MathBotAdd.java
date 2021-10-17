package edu.brown.cs.student.main.triggerActions;

import edu.brown.cs.student.main.repl.TriggerAction;

import java.io.IOException;

public class MathBotAdd implements TriggerAction {
  @Override
  public String command() {
    return "add";
  }

  @Override
  public String execute(String[] args) {
    String ans = "";
    try {
      double n1 = Double.parseDouble(args[0]);
      double n2 = Double.parseDouble(args[1]);
      ans = String.format("%f", n1 + n2);
      System.out.println("results of Addition: " + ans);
    } catch (IllegalArgumentException e){
      System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("ERROR: Unknown Error encountered");
    }
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[] {2};
  }
}
