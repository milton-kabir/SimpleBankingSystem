//package banking;

package com.kabir.milton;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner sc=new Scanner(System.in);
        HashMap<String, String> cardList = new HashMap<>();
        HashMap<String, Integer> acBal = new HashMap<>();
        while(true){
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            String ssd=sc.nextLine();
            int tt=Integer.parseInt(ssd);
            if(tt==0){
                System.out.println("Bye!");
                break;
            }
            if(tt==1){
                String cc="400000";
                String pp="";
                List<Integer> list = new ArrayList<>();
                for(int i=0;i<10;i++){
                    list.add(i);
                }

                for(int i=0;i<4;i++){
                    Collections.shuffle(list);
                    pp+=Integer.toString(list.get(0));
                }
                String st;
                while(true){
                    st="";
                    for(int i=0;i<9;i++){
                        Collections.shuffle(list);
                        st+=Integer.toString(list.get(0));
                    }
                    String ab=cc+st;
//                    ab="400000748954886";

                    int xy=0;
                    for(int i=0;i<ab.length();i++){
                        int yz=Integer.parseInt(ab.substring(i,i+1));
                        if((i+1)%2==1){
                            yz*=2;
                        }
                        if(yz>9){
                            yz-=9;
                        }
//                        System.out.println(yz);
                        xy+=yz;
                    }
//                    System.out.println("-->"+xy);
                    int xx=0;
                    for(int i=10;i<=90;i+=10){
                        if(i>=xy){
                            xx=i-xy;
                            break;
                        }
                    }

                    ab+=Integer.toString(xx);
                    st=ab;
                    if(!cardList.containsKey(st)){
                        cardList.put(st,pp);
                        acBal.put(st,0);
                        break;
                    }
                }
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(st);
                System.out.println("Your card PIN:");
                System.out.println(pp);
            }
            else{
                System.out.println("Enter your card number:");
                String ac=sc.nextLine();
                System.out.println("Enter your PIN:");
                String pp=sc.nextLine();
                int ck=0;
                for(Map.Entry m:cardList.entrySet()){
                    if(ac.equals(m.getKey())&&pp.equals(m.getValue())){
                        ck=1;
                        break;
                    }
                }
                if(ck==0){
                    System.out.println("Wrong card number or PIN!");
                    continue;
                }
                System.out.println("You have successfully logged in!");
                while(true){
                    System.out.println("1. Balance\n" +
                            "2. Log out\n" +
                            "0. Exit");
                    String aab=sc.nextLine();
                    int aa=Integer.parseInt(aab);
                    if(aa==0){
                        break;
                    }
                    if(aa==1){
                        System.out.println("Balance: "+acBal.get(ac));
                    }
                    else{
                        System.out.println("You have successfully logged out!");
                        break;
                    }
                }

            }

        }
    }
}
