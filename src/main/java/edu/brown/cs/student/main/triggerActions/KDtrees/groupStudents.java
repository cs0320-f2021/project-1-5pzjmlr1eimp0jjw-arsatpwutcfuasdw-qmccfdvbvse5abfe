package edu.brown.cs.student.main.triggerActions.KDtrees;

import edu.brown.cs.student.main.kdtree.Student;
import edu.brown.cs.student.main.kdtree.User;
import edu.brown.cs.student.main.repl.TriggerAction;

import java.util.ArrayList;
import java.util.List;

import static edu.brown.cs.student.main.triggerActions.stars.APICALLS.get_storedUsers;

public class groupStudents implements TriggerAction {
  @Override
  public String command() {
    return "recsys_gen_groups";
  }

  @Override
  public String execute(String[] args) {
    List<Student> studentList = new ArrayList<>(); //replace with the stored students
    List<List<Integer>> printGroups = new ArrayList<>();
    int groupSize = (int)Math.ceil(studentList.size() / Double.parseDouble(args[0]));
    for (Student stud : studentList) {
      //list of students in the first group
      List<Integer> studGroup = stud.returnNeighbors(studentList, groupSize - 1, stud.algorithm_skills, stud.frontend_skills,
              stud.commenting_skills, stud.teamwork_skills, stud.testing_skills, stud.OOP_skills);
      studGroup.add(Integer.parseInt(stud.id)); //adding the original student to the group

      //adding the group to the master list of groups we want to print
      printGroups.add(studGroup);

      //removing the students in our first group from our master list of students
      for (Integer studID : studGroup) {
        for (Student stud1 : studentList) {
          Integer id = Integer.parseInt(stud1.id);
          if (studID.equals(id)) {
            studentList.remove(stud1);
          }
        }
      }
    }

    for (List<Integer> subgroup : printGroups) {
      System.out.println("[");
      for (Integer studentName : subgroup) {
        System.out.println(studentName);
      }
      System.out.println("]");
    }
    return null;
  }

  @Override
  public int[] getNumPara() {
    return new int[]{1};
  }
}
