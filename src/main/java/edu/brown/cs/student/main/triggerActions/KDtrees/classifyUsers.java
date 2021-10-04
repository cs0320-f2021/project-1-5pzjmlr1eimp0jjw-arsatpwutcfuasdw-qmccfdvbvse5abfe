package edu.brown.cs.student.main.triggerActions.KDtrees;

import edu.brown.cs.student.main.repl.TriggerAction;
import edu.brown.cs.student.main.kdtree.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class classifyUsers implements TriggerAction {
  int _fromID;
  int _fromInfo;

  public classifyUsers(){
    _fromID = 2;
    _fromInfo =4;
  }

  @Override
  public String command() {
    return "classify";
  }

  @Override
  public String execute(String[] args) {
    List<User> userList = new ArrayList<>();
    User dummyUser = new User(0, 0, "",0 ,0 , "", "");
    /*TODO: insert methods that will output a user list
     */
    if (Integer.parseInt(args[0]) == 0) {  //find zero neighbors -> terminate
      return "zero users returned";
    }
    if (args.length == this.getNumPara()[0]) {
      HashMap<String, Integer> classifyList = dummyUser.classify(userList, Integer.parseInt(args[1]),
          Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
      for (String id : classifyList.keySet()) {
        System.out.println(id);
        System.out.println(classifyList.get(id));
      }
    }
    else if (args.length == this.getNumPara()[1]) {
      //extract user information from user list
      Number weight = 0;
      Number height = 0;
      Number age = 0;
      int i = 0;
      while (i < userList.size()) {
        if (userList.get(i).user_id == Integer.parseInt(args[2])) {
          weight = userList.get(i).weight;
          height = userList.get(i).height;
          age = userList.get(i).age;
        }
      }
      HashMap<String, Integer> classifyList = dummyUser.classify(userList, Integer.parseInt(args[1]),
          weight, height, age);
      for (String id : classifyList.keySet()) {
        System.out.println(id);
        System.out.println(classifyList.get(id));
      }
    }
    else {
      //not conforming to the two formats!
      return "Invalid Input: Please follow the format";
    }
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[] {_fromInfo, _fromID} ;
  }

}
