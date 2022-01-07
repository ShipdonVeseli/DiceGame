package com.example.dicegame.servlets;

import javax.servlet.http.HttpServlet;
import java.util.Map;

public class ServletFunctions  {
    public static void printNames(Map<String, String[]> map) {
        map.forEach((e, v) -> {
            System.out.println("parameter: " + e + " has Values:");

            for (String parameterValue : v) {
                System.out.println("Value: " + parameterValue);
            }

        });
    }

    public static String getParameterValue(Map<String, String[]> map, String name) {
        final String[] result = new String[1];
        result[0] = "error";
        map.forEach((e, v) -> {
            if (e.equalsIgnoreCase(name)) {
                result[0] = v[0];
            }
        });
        return result[0];
    }

    public static void initFunction(){
        System.out.println("************************************************************************************");
        System.out.println("Server Started");
        System.out.println("Hallo World");
        System.out.println("************************************************************************************");
    }


}
