package edu.brown.cs.student.main.kdtree;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class NodeComparator<T extends Node> implements Comparator<T> {
  int dimension;

  public void setDimension(int d) {
    this.dimension = d;
  }

  @Override
  public int compare(T o1, T o2) {
    List<Number> data1 = o1.value;
    List<Number> data2 = o2.value;
    Number value1 = data1.get(dimension);
    Number value2 = data2.get(dimension);
    Random n = new Random();
    int compareVal = Double.compare(value1.doubleValue(), value2.doubleValue());
    if (compareVal == 0) {
      return  n.nextInt();
    }
    else {
      return compareVal;
    }
  }
}
