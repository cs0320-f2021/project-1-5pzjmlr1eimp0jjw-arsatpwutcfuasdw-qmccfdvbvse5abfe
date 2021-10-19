package edu.brown.cs.student.main.triggerActions.KDtrees;

import edu.brown.cs.student.main.kdtree.Student;
import edu.brown.cs.student.main.kdtree.User;
import edu.brown.cs.student.main.repl.TriggerAction;

import java.util.ArrayList;
import java.util.List;
//TODO: modify this to take on STUDENTS

import static edu.brown.cs.student.main.triggerActions.stars.APICALLS.get_storedUsers;

public class similarStudents implements TriggerAction {
    int _fromID;
    int _fromInfo;

    public similarStudents() {
        _fromID = 2;
        _fromInfo = 4;
    }

    @Override
    public String command() {
        return "recsys_rec";
    }

    @Override
    public String execute(String[] args) {
        List<Student> studentList = new ArrayList<Student>(); // replace with way to get Students
        Student dummyUser = new Student("0", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0);
        /*
         * TODO: insert methods that will output a user list
         */
        if (Integer.parseInt(args[0]) == 0) { // find zero neighbors -> terminate
            return "zero Students returned";
        }
        if (args.length == this.getNumPara()[0]) {
            List<Integer> similarList = dummyUser.returnNeighbors(studentList, Integer.parseInt(args[0]),
                    Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                    Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
            for (int id : similarList) {
                System.out.println(id);
            }
        } else if (args.length == this.getNumPara()[1]) {
            // extract user information from user list
            Number alg = 0;
            Number frontend = 0;
            Number comment = 0;
            Number team = 0;
            Number testing = 0;
            Number oop = 0;
            int i = 0;
            while (i < studentList.size()) {
                if (Integer.parseInt(studentList.get(i).id) == Integer.parseInt(args[1])) {
                    alg = studentList.get(i).algorithm_skills;
                    frontend = studentList.get(i).frontend_skills;
                    comment = studentList.get(i).commenting_skills;
                    team = studentList.get(i).teamwork_skills;
                    testing = studentList.get(i).testing_skills;
                    oop = studentList.get(i).OOP_skills;
                }
                i++;
            }
            List<Integer> similarList = dummyUser.returnNeighbors(studentList, Integer.parseInt(args[0]),
                    alg, frontend, comment, team, testing, oop);

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
