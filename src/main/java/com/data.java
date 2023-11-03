package com;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class data {
    static  int[]    datasnew=new int[5];


    public  static   void  sort(int  arg){
        int index=0;

        for (int  i=0;i<datasnew.length;i++){
            int  datitem=  datasnew[i];

            if (datitem>arg){
                index=i;
            }

        }


    }

      public   static void  main(String[] arg){

             int[]    datas={1,4,2,6,10};
             int temp=-1;

//          for (int  i=0;i<datas.length-1;i++){
//
//             int  datitem=  datas[i];
//              temp=datitem;
//             int  datitemSecond=  datas[i+1];
//               if(datitem>datitemSecond){
//                   datas[i]=datitemSecond;
//                   datas[i+1]=temp;
//               }
//
//
//
//
//          }
//
//          System.out.println(Arrays.toString(datas));
//          Arrays.stream(datas).forEachOrdered(new IntConsumer() {
//              @Override
//              public void accept(int value) {
//
//              }
//          });


//             Stream.builder().accept(datas);

          List<Integer> list = Arrays.asList(1, 4, 3, 2, 5);
          List<Integer> listNess =   list.stream().sorted(new Comparator<Integer>() {
              @Override
              public int compare(Integer o1, Integer o2) {
                  return o1-o2;
              }
          }).collect(Collectors.toList());
          listNess.stream().forEach(System.out::println);


      }



    }
