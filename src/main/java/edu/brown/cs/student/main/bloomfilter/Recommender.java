package edu.brown.cs.student.main.bloomfilter;

import java.util.List;

public interface Recommender <T extends Item> {
    List<T> getTopKRecommendations(T item, int k);
}
