package edu.brown.cs.student.main.triggerActions.stars;

import java.io.IOException;

import java.net.http.HttpResponse;
import java.util.ArrayList;

import edu.brown.cs.student.main.api.API;
import edu.brown.cs.student.main.kdtree.Student;
import edu.brown.cs.student.main.repl.TriggerAction;

public class studentData implements TriggerAction {

    @Override
    public String command() {
        return "recsys_load";
    }

    @Override
    public String execute(String[] args) {
        System.out.println("Start Loading Student Data via API call");
        API api = new API(null, null);
        try {
            Student[] res = api.getSecuredAPI();
            System.out.println(res.length + " students created!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error occuered!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int[] getNumPara() {

        return new int[] { 1 };
    }

}
