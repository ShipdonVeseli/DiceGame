package com.example.dicegame.random;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;

public class RandomOrgRandom implements RandomStrategy{
    private final String websiteURL ="https://www.random.org/integers/";
    private final String httpMethod ="GET";

    @Override
    public Queue<Integer> fillQueue(Queue queue) throws Exception {
        System.out.println("Try to get values from random.org");
        throw new Exception("This is not implemented");
        //return queue;
    }


    private String genURL(int num,int min,int max, int col,String base,String format,String rnd){

        return websiteURL+"?"+"num="+num+"&min="+min+"&max="+max+"&col="+col+"&base="+base+"&format="+format+"&rnd="+rnd;
    }

    //FixME
    public void getRandomValuesFromRandomOrg()throws Exception{
        try {
            URL url=new URL(genURL(DiceManager.size,1,6,1,"10","plain","new"));
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);

      /*      connection.setRequestProperty("num", String.valueOf(DiceManager.size));
            connection.setRequestProperty("min", "1");
            connection.setRequestProperty("max", "6");

            connection.setRequestProperty("col", "1");
            connection.setRequestProperty("base", "10");
            connection.setRequestProperty("format", "plain");

            connection.setRequestProperty("rnd", "new");*/



            System.out.println(connection.getURL());

            int responseCode= connection.getResponseCode();
            System.out.println( "response code=  "+ responseCode);
            if(responseCode!=200){
                //TODO: Throw Exeption
            }
            InputStream response =connection.getInputStream();

            write(response);




        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    private void write(InputStream inputStream){
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        String line=null;
        bufferedReader.lines().forEach(e-> System.out.println(e));
//        do {
//            try {
//
//
//                line = bufferedReader.readLine();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }while (line!=null);
    }

}
