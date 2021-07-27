//package banking;

 package com.kabir.milton;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
class CardDatabase {
    private String[] args;
    private String fileName;
    SQLiteDataSource dataSource = new SQLiteDataSource();


    public void setArgs(String[] args) {
        this.args = args;
        extractFilePath();
    }
    public void extractFilePath() {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-fileName") && args.length >= i + 2) {
                fileName = args[i + 1];
            }
        }
//        dataSource.setUrl("jdbc:sqlite:" + fileName);
        dataSource.setUrl("jdbc:sqlite:C:\\sqlite3\\db\\" + fileName);
    }
    public boolean cardExists(String cardNo) {
        int id = -1;
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet existing = statement.executeQuery("SELECT id FROM card WHERE "
                        + "number = \"" + cardNo + "\";")) {
                    while (existing.next()) {
                        id = existing.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id != -1;
    }
    public void addCard(String cardNo, String pin) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(String.format("INSERT INTO card (number, pin) VALUES " +
                        "(\"%s\", \"%s\");", cardNo, pin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkLogIn(String acc, String pin) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet pinMatch = statement.executeQuery("SELECT pin FROM card WHERE "
                        + "number = \"" + acc + "\";")) {
                    while (pinMatch.next()) {
                        if (pin.equals(pinMatch.getString("pin"))) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int balance(String acc) {
        int bal = 0;
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet balance = statement.executeQuery("SELECT balance FROM card WHERE "
                        + "number = \"" + acc + "\";")) {
                    bal =  balance.getInt("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bal;
    }
}
public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner sc=new Scanner(System.in);
        HashMap<String, String> cardList = new HashMap<>();
        HashMap<String, Integer> acBal = new HashMap<>();
        CardDatabase cardDb = new CardDatabase();
        cardDb.setArgs(args);
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
                    if (!cardDb.cardExists(st.toString())) {
                        cardDb.addCard(st.toString(), pp);
                        break;
                    }
//                    if(!cardList.containsKey(st)){
//                        cardList.put(st,pp);
//                        acBal.put(st,0);
//                        break;
//                    }
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
                if(cardDb.checkLogIn(ac, pp)){
                    System.out.println("You have successfully logged in!");
                    while (true){
                        System.out.println("1. Balance\n" +
                                "2. Log out\n" +
                                "0. Exit");
                        String aab=sc.nextLine();
                        int aa=Integer.parseInt(aab);
                        if(aa==0){
                            break;
                        }
                        if(aa==1){
                            System.out.println("Balance: "+cardDb.balance(ac));
                        }
                        else{
                            System.out.println("You have successfully logged out!");
                            break;
                        }

                    }
                }
                else{
                    System.out.println("Wrong card number or PIN!");
                    continue;
                }
            }
        }
    }
}
