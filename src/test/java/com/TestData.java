package com;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TestData {

    int testnumtest=1;
    public static void main(String args[]) throws Exception {
        int testnum=4;


        if (testnum==2){
            System.out.println("========");
        }
        if (testnum==1){
            System.out.println(returndata(testnum));
        }

        else if (testnum==1){
            System.out.println("222222222222");
        }


    }

    public static String returndata(int i ) {
        String data="";
         if (i==1){
             data=returndata2(i);
         }else {
             data="other";
         }
        return  data;
    }
    public static String returndata2(int i ) {
        String data="";
         if (i==1){
             data="111111111";
         }else {
             data="other";
         }
        return  data;
    }



        public static void main3(String args[]) throws Exception {

        FileInputStream inputStream = new FileInputStream("G://小鹤音形冰凌词库.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

        Map data=new HashMap<>();
        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            str= str.replace("300","");
           String[] datas= str.split("\\t");

           String pinyin=datas[0];
           String hanzi=datas[1];

            System.out.println(str);
        }

        //close
        inputStream.close();
        bufferedReader.close();


    }
}
