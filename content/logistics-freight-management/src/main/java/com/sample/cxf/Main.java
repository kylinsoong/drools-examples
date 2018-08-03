package com.sample.cxf;

public class Main {

    public static void main(String[] args) {

        
        for(String s : Constants.commands) {
            System.out.println(s.replace("default-stateless-ksession", "logistics-ksession"));
        }
    }

}
