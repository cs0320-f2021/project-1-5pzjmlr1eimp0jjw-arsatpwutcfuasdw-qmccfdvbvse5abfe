package edu.brown.cs.student.main.kdtree;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * this class is responsible for building out the KD tree from a Collection of List of numbers
 */
public class KDTreeBuilder {
  Collection<List<Number>> dataset;
  Comparator<Node> comparator;
  int dimensions;

  public KDTreeBuilder(Collection<List<Number>> dataset, Comparator<Node> comparator,
                       int dimensions) {
    this.dataset = dataset;
    this.comparator = comparator;
    this.dimensions = dimensions;
  }

  //list of nodes to turn into KD tree

  /**
   *   converts each piece of data into a node with leaves as branches and depth 1
   */
  public List<Node> convertData() {
    List<Node> nodeSet = new ArrayList<>();
    for(List<Number> d : this.dataset) {
      Node n = new Node(d, null, null, 1);
      nodeSet.add(n);
    }
    return nodeSet;
  }

  /**
   * gets the middle node of the set (in order to figure out first level of tree)
   */
  public Node getMedian(List<Node> nodeSet) {
    if (nodeSet.isEmpty()) {
      return new Node(null, null, null, 1);
    } else if (nodeSet.size() == 1) {
      return nodeSet.get(0);
    } else {
      nodeSet.sort(comparator);
      int n = Math.round(nodeSet.size() / 2);
      return nodeSet.get(n);
    }
  }

  /**
   *
   * @param comparator - used to compare nodes
   * @param nodeSet - the set of nodes to convert into a KD tree
   * @param depth - how far down in the tree we are
   * @return
   */
    public Node buildTree(NodeComparator<Node> comparator, List<Node> nodeSet, int depth) {
      int dim = depth % dimensions; // this tells us which dimension we should be looking at
      comparator.setDimension(dim); //set our comparator to work with the right dimension

      //if the dataset is empty
      if (nodeSet.isEmpty()) {
        return null;
      }

      //if there is only one node
      if (nodeSet.size() ==1) {return nodeSet.get(0);}

      //multiple nodes
      nodeSet.sort(comparator); //sorts list of nodes
      int midpoint = Math.round(nodeSet.size() / 2); //finds index of midpoint
      Node parentNode = this.getMedian(nodeSet); //assigns the parent node

      //list of nodes to include on the left and right
      List<Node> leftNodes = nodeSet.subList(0, midpoint);
      List<Node> rightNodes = nodeSet.subList(midpoint+1, nodeSet.size());

      //create two dummy nodes to be replaced
      Node leftNode = new Node(null, null, null, 0);
      Node rightNode = new Node(null, null, null, 0);

      if (leftNodes.isEmpty()) { leftNode = null; }
      else { leftNode = this.buildTree(comparator, leftNodes, depth+1);}
      parentNode.leftBranch = leftNode;

      if (rightNodes.isEmpty()) { rightNode = null; }
      else { rightNode = this.buildTree(comparator, rightNodes, depth+1); }
      parentNode.rightBranch = rightNode;
      return parentNode;
    }
}


