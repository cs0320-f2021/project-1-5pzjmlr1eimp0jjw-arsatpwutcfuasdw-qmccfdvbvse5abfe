package edu.brown.cs.student.main;


import static org.junit.Assert.assertEquals;


import edu.brown.cs.student.main.kdtree.KDTreeBuilder;
import edu.brown.cs.student.main.kdtree.Node;
import edu.brown.cs.student.main.kdtree.NodeComparator;
import edu.brown.cs.student.main.kdtree.nearestNeighbor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KDTreeBuilderTest {

  //tests the building of a KDtree
  @Test
  public void testNode() {
    //basic example of building a KDtree
    Collection<List<Number>> dataset1 = new ArrayList<>();
    ArrayList<Number> set1 = new ArrayList<Number>();
    set1.add(0);
    set1.add(0);
    set1.add(0);
    ArrayList<Number> set2 = new ArrayList<Number>();
    set2.add(1);
    set2.add(1);
    set2.add(1);
    ArrayList<Number> set3 = new ArrayList<Number>();
    set3.add(2);
    set3.add(2);
    set3.add(2);
    dataset1.add(set1);
    dataset1.add(set2);
    dataset1.add(set3);
    NodeComparator<Node> comparator= new NodeComparator<Node>();
    KDTreeBuilder kdTree1 = new KDTreeBuilder(dataset1, comparator, 3);
    List<Node> nodeSet1 = kdTree1.convertData();
    Node medianNode1 = kdTree1.getMedian(nodeSet1);
    assertEquals(medianNode1.getValue(), set2);
    //kd tree
    Node builtKDTree = kdTree1.buildTree(comparator, nodeSet1, 0);
    assertEquals(builtKDTree.getValue(), set2);
    assertEquals(builtKDTree.getLeftBranch().getValue(), set1);
    assertEquals(builtKDTree.getRightBranch().getValue(), set3);
    //testing findNeighbors function
    nearestNeighbor n = new nearestNeighbor();
    List<List<Number>> dummyList= new ArrayList();
    assertEquals(n.findNeighbors(builtKDTree, 0, set1, dummyList), dummyList);

    List<List<Number>> dummyList1= new ArrayList();
    dummyList1.add(set1);
    assertEquals(n.findNeighbors(builtKDTree, 1, set1, dummyList), dummyList1);

    //new search example
    ArrayList<Number> searchSet = new ArrayList<Number>();
    searchSet.add(0);
    searchSet.add(1);
    searchSet.add(0);
    assertEquals(n.findNeighbors(builtKDTree, 1, searchSet, dummyList), dummyList1);

    ArrayList<Number> searchSet2 = new ArrayList<Number>();
    searchSet2.add(2);
    searchSet2.add(1);
    searchSet2.add(0);

    List<List<Number>> dummyList2= new ArrayList();
    dummyList2.add(set2);
    assertEquals(n.findNeighbors(builtKDTree, 1, searchSet2, dummyList), dummyList2);

    //test when k > number of elements
    List<List<Number>> dummyList3= new ArrayList();
    dummyList3.add(set2);
    dummyList3.add(set1);
    dummyList3.add(set3);
    List<List<Number>> dummyList4= new ArrayList();
    List<List<Number>> dummyList5= new ArrayList();
    assertEquals(n.findNeighbors(builtKDTree, 3, set1, dummyList4), dummyList3);
    assertEquals(n.findNeighbors(builtKDTree, 5, set1, dummyList5), dummyList3);
  }


}
