package edu.brown.cs.student.main.triggerActions.KDtrees;

import edu.brown.cs.student.main.repl.TriggerAction;
import edu.brown.cs.student.main.kdtree.User;

import java.util.ArrayList;
import java.util.List;

import static edu.brown.cs.student.main.triggerActions.stars.APICALLS.get_storedUsers;

public class similarUsers implements TriggerAction {
  int _fromID;
  int _fromInfo;

  public similarUsers() {
    _fromID = 2;
    _fromInfo = 4;
  }

  @Override
  public String command() {
    return "similar";
  }

  @Override
  public String execute(String[] args) {
    List<User> userList = get_storedUsers();
    User dummyUser = new User("0", "0", "", "0", "0", "", "");
    /*
     * TODO: insert methods that will output a user list
     */
    if (Integer.parseInt(args[0]) == 0) { // find zero neighbors -> terminate
      return "zero users returned";
    }
    if (args.length == this.getNumPara()[0]) {
      List<Integer> similarList = dummyUser.returnNeighbors(userList, Integer.parseInt(args[1]),
          Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
      for (int id : similarList) {
        System.out.println(id);
      }
    } else if (args.length == this.getNumPara()[1]) {
      // extract user information from user list
      Number weight = 0;
      Number height = 0;
      Number age = 0;
      int i = 0;
      while (i < userList.size()) {
        if (Integer.parseInt(userList.get(i).user_id) == Integer.parseInt(args[1])) {
          weight = Double.parseDouble(userList.get(i).weight.replaceAll("[^0-9]", ""));
          height = Double.parseDouble(userList.get(i).height.replaceAll("[^0-9]", ""));
          age = Double.parseDouble(userList.get(i).age.replaceAll("[^0-9]", ""));
        }
        i++;
      }
      List<Integer> similarList = dummyUser.returnNeighbors(userList, Integer.parseInt(args[1]), weight, height, age);

      for (int id : similarList) {
        System.out.println(id);
      }
    } else {
      // not conforming to the two formats!
      return "Invalid Input: Please follow the format";
    }
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[] { _fromInfo, _fromID };
  }

}
