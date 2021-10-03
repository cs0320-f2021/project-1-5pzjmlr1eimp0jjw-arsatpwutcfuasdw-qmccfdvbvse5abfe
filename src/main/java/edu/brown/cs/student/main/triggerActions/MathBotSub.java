package edu.brown.cs.student.main.triggerActions;

import edu.brown.cs.student.main.repl.TriggerAction;

public class MathBotSub implements TriggerAction {
  @Override
  public String command() {
    return "subtract";
  }

  @Override
  public String execute(String[] args) {
    String ans = "";
    try {
      double n1 = Double.parseDouble(args[0]);
      double n2 = Double.parseDouble(args[1]);
      ans = String.format("%f", n2 - n1);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    } catch (Exception e) {
      System.err.println("ERROR: Unknown Error encountered");
    }
    return ans;
  }

  @Override
  public int[] getNumPara() {
    return new int[] {2};
  }
}
