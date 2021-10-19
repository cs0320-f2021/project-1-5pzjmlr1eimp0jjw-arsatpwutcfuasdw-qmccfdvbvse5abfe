package edu.brown.cs.student.main.kdtree;

import edu.brown.cs.student.main.bloomfilter.Item;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Student implements Item {
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
            int alg = person.algorithm_skills;
            int frontend = person.frontend_skills;
            int comment = person.commenting_skills;
            int team = person.teamwork_skills;
            int testing = person.testing_skills;
            int oop = person.OOP_skills;
            studentData.add(0, alg);
            studentData.add(1, frontend);
            studentData.add(2, comment);
            studentData.add(3, team);
            studentData.add(4, testing);
            studentData.add(5, oop);
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
     * @param studentList     - the list of Students we want to search through
     * @param numNeighbors - number of neighbors we want to find
     * @param alg       - target algorithm
     * @param frontend       -- target frontend
     * @param comment          - target commenting
     * @param team       - target team
     * @param testing       -- target testing
     * @param oop          - target oop
     * @return - list of coordinates that are nearest
     */
    public List<List<Number>> findStudents(Collection<Student> studentList, int numNeighbors,
                                        Number alg, Number frontend, Number comment, Number team, Number testing,
                                        Number oop) {
        List<Number> targetStudent = new ArrayList<>(3);
        targetStudent.add(0, alg);
        targetStudent.add(1, frontend);
        targetStudent.add(2, comment);
        targetStudent.add(3, team);
        targetStudent.add(4, testing);
        targetStudent.add(5, oop);
        Node studentTree = kdTreeofStudents(studentList);
        nearestNeighbor x = new nearestNeighbor();
        // initialize dummy list to pass into findNeighbors
        List<List<Number>> dummyList = new ArrayList<>();
        return x.findNeighbors(studentTree, numNeighbors, targetStudent, dummyList);

    }
    /**
     * given the datapoints of a target student, returns a list of closest students
     *
     * @param studentList - list of student to search through
     * @param numNeighbors - number of neighbors to find
     * @param alg       - target algorithm skill
     * @param frontend       - target frontend skill
     * @param comment          - target comment skill
     * @return - list of integers representing closest student IDs
     */
    public List<Integer> returnNeighbors(Collection<Student> studentList, int numNeighbors,
                                         Number alg, Number frontend, Number comment, Number team, Number testing,
                                         Number oop) {
        List<List<Number>> closeStudents = findStudents(studentList, numNeighbors, alg, frontend, comment,
                team, testing, oop);
        return getStudentID(closeStudents, studentList);
    }
    public List<Integer> getStudentID(List<List<Number>> neighbors, Collection<Student> studentsToSearch) {
        List<Integer> idList = new ArrayList<>();
        for (List<Number> elt : neighbors) {
            for (Student person : studentsToSearch) {
                int i = Integer.parseInt(person.id);
                int alg = person.algorithm_skills;
                int frontend = person.frontend_skills;
                int comment = person.commenting_skills;
                int team = person.teamwork_skills;
                int testing = person.testing_skills;
                int oop = person.OOP_skills;
                if ((elt.get(0).equals(alg)) && (elt.get(1).equals(frontend)) && (elt.get(2).equals(comment))
                        && (elt.get(3).equals(team)) && (elt.get(4).equals(testing)) && (elt.get(5).equals(oop))) {
                    idList.add(i);
                }
            }
        }
        return idList;
    }

    @Override
    public List<String> getVectorRepresentation() {
        return null;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
