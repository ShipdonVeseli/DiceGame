package com.example.dicegame.random;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

//WARNING
//This test Class SHOULD NOT be automatically run
//You may be baned from Random.org
class RandomOrgRandomTest {

    @Test
     void testSizeOfResponse(){
        try {
           ConcurrentLinkedQueue concurrentLinkedQueue=new ConcurrentLinkedQueue();
           RandomOrgRandom randomOrgRandom =new RandomOrgRandom();
           int expectedSize=DiceManager.size;
           int actualSize=0;

           randomOrgRandom.getRandomValuesFromRandomOrg(concurrentLinkedQueue);
           actualSize=concurrentLinkedQueue.size();

           System.out.println(concurrentLinkedQueue);

           assertEquals(expectedSize,actualSize);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
     void testWrite(){
        //ToDo
    }

    @Test
    void getRandomValuesFromRandomOrgTest(){
        //ToDo
    }


}