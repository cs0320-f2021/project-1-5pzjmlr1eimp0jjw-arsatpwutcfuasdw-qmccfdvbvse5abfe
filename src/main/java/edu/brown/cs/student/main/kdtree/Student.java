package edu.brown.cs.student.main.kdtree;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Student {
    //api data
    public String id;
    public String name;
    public String meeting;
    public String grade;
    public String years_of_experience;
    public String horoscope;
    public String meeting_times;
    public String preferred_language;
    public String marginalized_groups;
    public String prefer_group;
    //orm data
    public int commenting_skills;
    public int testing_skills;
    public int OOP_skills;
    public int algorithm_skills;
    public int teamwork_skills;
    public int frontend_skills;

    public Student() {
        super();
    }
    public Student(String id, String name, String meeting, String grade, String years, String horoscope, String time,
                   String language, String marginalized_groups, String preferences, int commenting, int testing,
                   int OOP, int algorithm, int teamwork, int frontend) {
        this.id = id;
        this.name = name;
        this.meeting = meeting;
        this.grade = grade;
        this.years_of_experience = years;
        this.horoscope = horoscope;
        this.meeting_times = time;
        this.preferred_language = language;
        this.marginalized_groups = marginalized_groups;
        this.prefer_group = preferences;
        this.commenting_skills = commenting;
        this.testing_skills = testing;
        this.OOP_skills = OOP;
        this.algorithm_skills = algorithm;
        this.teamwork_skills = teamwork;
        this.frontend_skills = frontend;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.id); // using the id as a hashcode
    }

    @Override
    public boolean equals(Object other) { // compare based off User's hashcode
        if (this == other) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        if (this.hashCode() == otherStudent.hashCode()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param studentList - users to build KD tree from
     * @return - KD tree with users
     */
    public Node kdTreeofStudents(Collection<Student> studentList) {
        // converts user list to a list of list of numbers
        List<List<Number>> studentInfo = new ArrayList<>();
        for (Student person : studentList) {
            List<Number> studentData = new ArrayList<>();
            Double we2 = Double.parseDouble(person.height.replaceAll("[^0-9]", ""));
            Double age = Double.parseDouble(person.age.replaceAll("[^0-9]", ""));
            Double we = Double.parseDouble(person.weight.replaceAll("[^0-9]", ""));
            studentData.add(0, we);
            studentData.add(1, we2);
            studentData.add(2, age);
            studentInfo.add(studentData);
        }
        NodeComparator comparator = new NodeComparator();
        KDTreeBuilder builder = new KDTreeBuilder(studentInfo, comparator, 6);
        List<Node> userNodes = builder.convertData();
        return builder.buildTree(comparator, userNodes, 1);
    }

    /**
     * finds the nearest neighbors and returns list of points
     *
     * @param userList     - the list of users we want to search through
     * @param numNeighbors - number of neighbors we want to find
     * @param weight       - target weight
     * @param height       -- target height
     * @param age          - target age
     * @return - list of coordinates that are nearest
     */
    public List<List<Number>> findUsers(Collection<User> userList, int numNeighbors, Number weight, Number height,
                                        Number age) {
        List<Number> targetUser = new ArrayList<>(3);
        targetUser.add(0, weight);
        targetUser.add(1, height);
        targetUser.add(2, age);

        Node userTree = kdTreeofUsers(userList);
        nearestNeighbor x = new nearestNeighbor();
        // initialize dummy list to pass into findNeighbors
        List<List<Number>> dummyList = new ArrayList<>();
        return x.findNeighbors(userTree, numNeighbors, targetUser, dummyList);

    }
    /**
     * given the datapoints of a target student, returns a list of closest students
     *
     * @param studentList - list of student to search through
     * @param numNeighbors - number of neighbors to find
     * @param weight       - target weight
     * @param height       - target height
     * @param age          - target age
     * @return - list of integers representing closest student IDs
     */
    public List<Integer> returnNeighbors(Collection<Student> studentList, int numNeighbors, Number weight, Number height,
                                         Number age) {
        List<List<Number>> closeStudents = findUsers(studentList, numNeighbors, weight, height, age);
        return getUserID(closeStudents, studentList);
    }

}
