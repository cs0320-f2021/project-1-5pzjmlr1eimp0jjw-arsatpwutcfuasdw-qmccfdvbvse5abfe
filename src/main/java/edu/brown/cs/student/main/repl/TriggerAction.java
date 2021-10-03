package edu.brown.cs.student.main.repl;

public interface TriggerAction {
  //Interface for all classes
  String command();
  String execute(String[] args);
  int[] getNumPara();
}
