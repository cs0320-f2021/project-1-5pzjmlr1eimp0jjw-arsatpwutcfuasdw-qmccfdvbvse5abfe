package edu.brown.cs.student.main.kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * class that handles finding the nearest neighbors from a KD tree
 */
public class nearestNeighbor {

  /**
   *
   * @param a - first coord to calcualte euclidean distance of
   * @param b - second coord to calcualte euclidean distance of
   * @return - euclidean distance
   */
  public double euclidDistance(List<Number> a, List<Number> b) {
    double n = 0;
    for (int i = 0; i < a.size(); i++) {
      n = n + Math.pow(a.get(i).doubleValue() - b.get(i).doubleValue(), 2);
    }
    return (Math.sqrt(n));
  }

  //nearest k neighbors search algorithm for kd trees
  public List<List<Number>> findNeighbors(Node kdtree, int k, List<Number> target, List<List<Number>> neighbors) {
    // if size of neighbors < neighbors wanted, automatically add node
    if (neighbors.size() < k) {
      neighbors.add(kdtree.value);
    }
    //else, check to see if the node is < the current farthest neighbor
    else {
      for (int i = 0; i < neighbors.size(); i++) {
        if (this.euclidDistance(kdtree.value, target) <
            this.euclidDistance(neighbors.get(i), target)) {
          neighbors.set(i, kdtree.value);
        }
      }
    }

    if (neighbors.size() > 0) {
      //calculate the dimension we're on
      int dim = kdtree.depth % target.size();

      //finding the farthest neighbor
      List<Number> farthestNeighbor = neighbors.get(0);

      //calculates distance for each neighbor and updates farthestNeighbor accordingly
      for (List<Number> neigh : neighbors) {
        if (this.euclidDistance(neigh, target) < this.euclidDistance(farthestNeighbor, target)) {
          farthestNeighbor = neigh;
        }
      }

      //calculating the axis distance
      double targetDimValue = target.get(dim).doubleValue();
      double nodeDimValue = kdtree.value.get(dim).doubleValue();
      double axisDistance = Math.abs(targetDimValue - nodeDimValue);


      //if the euclid distance between target & farthest point is greater than axis, recur on both branches
      if (this.euclidDistance(farthestNeighbor, target) > axisDistance && kdtree.leftBranch != null) {
        List<List<Number>> leftNeighbors = findNeighbors(kdtree.leftBranch, k, target, neighbors);
        findNeighbors(kdtree.rightBranch, k, target, leftNeighbors);
      }
      else {
        //if node value < target value, recur on right branch
        if (nodeDimValue < targetDimValue && kdtree.rightBranch != null) {
          findNeighbors(kdtree.rightBranch, k, target, neighbors);
        }
        //if node value > target value, recur on left branch
        else {
          if (kdtree.leftBranch != null) {
            findNeighbors(kdtree.leftBranch, k, target, neighbors);
          }
        }
      }
    }
    //return list of neighbors
    return neighbors;
  }
}
