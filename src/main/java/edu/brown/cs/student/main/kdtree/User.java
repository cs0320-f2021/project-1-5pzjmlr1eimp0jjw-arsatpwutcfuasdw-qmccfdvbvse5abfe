package edu.brown.cs.student.main.kdtree;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class User {
  public String user_id;     //all fields have to be of type String because of type mismatch when loading from json
  public String weight;
  public String bust_size;
  public String height;
  public String age;
  public String body_type;
  public String horoscope;


  public User(){
    super();
  }
  public User(String user_id, String weight, String bust_size, String height, String age,
              String body_type, String horoscope) {
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;


  }
    @Override
    public int hashCode() {
      return Integer.parseInt(this.user_id);  //using the id as a hashcode
    }

    @Override
    public boolean equals(Object other){   //compare based off User's hashcode
      if(this == other){
        return true;
      }

      if(!(other instanceof User)){
        return false;
      }

      User otherUser = (User) other;
      if(this.hashCode() == otherUser.hashCode()){
        return true;
      }
      return false;
    }
  /**
   *
   * @param userList - users to build KD tree from
   * @return - KD tree with users
   */
  public Node kdTreeofUsers(Collection<User> userList) {
    //converts user list to a list of list of numbers
    List<List<Number>> userInfo = new ArrayList<>();
    for (User person : userList) {
      List<Number> userData = new ArrayList<>();
      Double we2 = Double.parseDouble(person.height.replaceAll("[^0-9]", ""));
      Double age = Double.parseDouble(person.age.replaceAll("[^0-9]", ""));
      Double we = Double.parseDouble(person.weight.replaceAll("[^0-9]", ""));
      userData.set(0, we);
      userData.set(1, we2);
      userData.set(2, age);
      userInfo.add(userData);
    }
    NodeComparator comparator = new NodeComparator();
    KDTreeBuilder builder = new KDTreeBuilder(userInfo, comparator, 3);
    List<Node> userNodes = builder.convertData();
    return builder.buildTree(comparator, userNodes, 1);
  }

  /**
   * finds the nearest neighbors and returns list of points
   * @param userList - the list of users we want to search through
   * @param numNeighbors - number of neighbors we want to find
   * @param weight - target weight
   * @param height -- target height
   * @param age - target age
   * @return - list of coordinates that are nearest
   */
  public List<List<Number>> findUsers(Collection<User> userList, int numNeighbors, Number weight, Number height, Number age) {
    List<Number> targetUser = new ArrayList<>();
    targetUser.set(0, weight);
    targetUser.set(1, height);
    targetUser.set(2, age);

    Node userTree = kdTreeofUsers(userList);
    nearestNeighbor x = new nearestNeighbor();
    //initialize dummy list to pass into findNeighbors
    List<List<Number>> dummyList = new ArrayList<>();
    return x.findNeighbors(userTree, numNeighbors, targetUser, dummyList);

  }

  /**
   * converts list of coordinated to list of IDs
   * @param neighbors - the nearest neighbors, outputted from findUsers method
   * @return list of user IDs
   */
  public List<Integer> getUserID(List<List<Number>> neighbors, Collection<User> usersToSearch) {
    List <Integer> idList = new ArrayList<>();
    for (List<Number> elt : neighbors) {
      for (User user : usersToSearch) {
        int i = Integer.parseInt(user.user_id);
        Double age = Double.parseDouble(user.age.replaceAll("[^0-9]", "")); //get rid of lbs
        Double weight = Double.parseDouble(user.weight.replaceAll("[^0-9]", ""));
        Double height = Double.parseDouble(user.height.replaceAll("[^0-9]", ""));
        if ((elt.get(0).equals(weight)) && (elt.get(1).equals(height)) && (elt.get(2)
            .equals(age))) {
          idList.add(i);
        }
      }
    }
    return idList;
  }

  /**
   * given the datapoints of a target user, returns a list of closest users
   * @param userList - list of users to search through
   * @param numNeighbors - number of neighbors to find
   * @param weight - target weight
   * @param height - target height
   * @param age - target age
   * @return - list of integers representing closest user IDs
   */
  public List<Integer> returnNeighbors(Collection<User> userList, int numNeighbors, Number weight, Number height, Number age) {
    List<List<Number>> closeUsers = findUsers(userList, numNeighbors, weight, height, age);
    return getUserID(closeUsers, userList);
  }

  /**
   *
   * @param userList - list of users to search
   * @param numNeighbors - num of neighbors to find
   * @param weight - target weight
   * @param height - target height
   * @param age - target age
   * @return HashMap with KV pairs representing horoscope and number of users
   */
  public HashMap<String, Integer> classify(Collection<User> userList, int numNeighbors, Number weight, Number height, Number age) {
    List<Integer> closestUsers = returnNeighbors(userList, numNeighbors, weight, height, age);
    //initialize horoscope hashmap with all horoscope signs
    HashMap<String, Integer> horoscopeChart = new HashMap<>();
    horoscopeChart.put("Aries", 0);
    horoscopeChart.put("Taurus", 0);
    horoscopeChart.put("Gemini", 0);
    horoscopeChart.put("Cancer", 0);
    horoscopeChart.put("Leo", 0);
    horoscopeChart.put("Virgo", 0);
    horoscopeChart.put("Libra", 0);
    horoscopeChart.put("Scorpio", 0);
    horoscopeChart.put("Sagittarius", 0);
    horoscopeChart.put("Capricorn", 0);
    horoscopeChart.put("Aquarius", 0);
    horoscopeChart.put("Pisces", 0);

    //parses through IDs of closest users, gets their horoscopes, and adds 1 value of horoscope in hashmap
    for (int n : closestUsers) {
      for (User u : userList) {
        int i = Integer.parseInt(u.user_id);
        if (i == n) {
         String horoscope = u.horoscope;
         horoscopeChart.put(horoscope, horoscopeChart.get(horoscope) + 1); //adds 1 to horoscope value
        }
      }
    }
    return horoscopeChart;
  }
}
