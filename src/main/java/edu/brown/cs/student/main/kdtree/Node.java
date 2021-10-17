package edu.brown.cs.student.main.kdtree;

import java.util.Collection;
import java.util.List;

/**
 * the nodes for a KD tree -- each KD tree is also a node
 */
public class Node {
  List<Number> value; //the value of the node itself
  Node leftBranch;
  Node rightBranch;
  int depth; // the depth of the node within the KD tree

  public Node(List<Number> value, Node leftBranch, Node rightBranch, int depth) {
    this.value = value;
    this.leftBranch = leftBranch;
    this.rightBranch = rightBranch;
    this.depth = 0;
  }

  //returns the value of the node
  public List<Number> getValue() {
    return this.value;
  }

  //returns the left branch
  public Node getLeftBranch() {
    return this.leftBranch;
  }

  public Node getRightBranch() {
    return this.rightBranch;
  }

  public int getDepth() {
    return this.depth;
  }

  public Boolean hasLeft() {
    return this.leftBranch != null;
  }

  public Boolean hasRight() {
    return this.rightBranch != null;
  }
}
