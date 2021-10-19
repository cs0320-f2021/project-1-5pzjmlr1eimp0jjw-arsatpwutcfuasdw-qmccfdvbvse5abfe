package edu.brown.cs.student.main.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClientAuth {
    public ClientAuth() {
    }

    public static String getApiKey() throws IOException {
        File file = new File("data/urls/apikey.txt");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        return bf.readLine();
    }
}