package edu.brown.cs.student.main.triggerActions.stars;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.brown.cs.student.main.Database;
import edu.brown.cs.student.main.api.API;
import edu.brown.cs.student.main.kdtree.Student;
import edu.brown.cs.student.main.repl.TriggerAction;
import edu.tables.interests;

public class studentData implements TriggerAction {

    @Override
    public String command() {
        return "recsys_load";
    }

    @Override
    public String execute(String[] args) {
        ArrayList<Student> res;
        System.out.println("Start Loading Student Data via API call");
        API api = new API(null, null);
        HashMap<String, String> hm = new HashMap<>();
        try {
            res = api.getSecuredAPI();
            Collections.sort(res);
            System.out.println(res.size() + " students created!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error occuered!");
            e.printStackTrace();
        }
        try {
            Database db = new Database("data/project-1/integration.sqlite3");
            interests<Student> in = new interests<Student>(null);

            try {
                for (int i = 1; i < 61; i++) {
                    hm.put("id", String.valueOf(i));
                    List<interests> out = db.select(interests.class, hm);
                    // res.get(i).setInterest(out);
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                System.out.println("Error occurred!");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int[] getNumPara() {

        return new int[] { 1 };
    }

}
