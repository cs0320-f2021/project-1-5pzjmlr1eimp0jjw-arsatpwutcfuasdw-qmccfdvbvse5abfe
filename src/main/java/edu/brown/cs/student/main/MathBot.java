package edu.brown.cs.student.main;

import edu.brown.cs.student.main.repl.TriggerAction;

public class MathBot implements TriggerAction {

  /**
   * Default constructor.
   */
  public MathBot() {

  }

  /**
   * Adds two numbers together.
   *
   * @param num1 the first number.
   * @param num2 the second number.
   * @return the sum of num1 and num2.
   */
  public double add(double num1, double num2) {
    return num1 + num2;
  }

  /**
   * Subtracts two numbers.
   *
   * @param num1 the first number.
   * @param num2 the second number.
   * @return the difference of num1 and num2.
   */
  public double subtract(double num1, double num2) {
    return num2 - num1;
  }

  @Override
  public String command() {
    return "add";
  }

  @Override
  public String execute(String[] args) {
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[0];
  }
}
