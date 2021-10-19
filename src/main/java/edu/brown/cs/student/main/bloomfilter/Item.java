package edu.brown.cs.student.main.bloomfilter;
import java.util.List;

public interface Item {
    List<String> getVectorRepresentation();
    String getId();
}
