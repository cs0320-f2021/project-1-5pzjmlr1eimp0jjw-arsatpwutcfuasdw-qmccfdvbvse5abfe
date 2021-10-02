package edu.brown.cs.student.main;

import java.util.ArrayList;

public class user {
  Number user_id;
  Number weight;
  String bust_size;
  ArrayList<Double> height;
  Number age;
  String body_type;
  String horoscope;

  public user(int user_id, double weight, String bust_size, ArrayList<Double> height, int age,
              String body_type, String horoscope) {
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

}
