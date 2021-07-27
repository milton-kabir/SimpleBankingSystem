//package banking;

package com.kabir.milton;

import java.sql.*;
import java.util.*;
class AccountDB {
    String url = "jdbc:sqlite:";

    //constructor
    public AccountDB(String input) {
        url = url + input;
        createNewDataBase();
    }
    //method to create the initial database
    public void createNewDataBase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                createNewTable();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //method to create the card table
    public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " number TEXT,\n"
                + " pin TEXT,\n"
                + " balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(String num, String pin) {
        String insertSql =  "INSERT INTO card (number, pin) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, num);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean containsRecord(String num, String pin) {
        boolean found = false;
        String sql = "SELECT * FROM card WHERE number = ? AND pin = ?";;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, num);
            pstmt.setString(2, pin);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return found;
    }

    public void printBalance(String num, String pin) {
        String balanceSQL = "SELECT balance FROM card WHERE number = ? AND pin = ?";
        try (Connection conn = this.connect();
             PreparedStatement prep = conn.prepareStatement(balanceSQL)){
            prep.setString(1, num);
            prep.setString(2, pin);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                System.out.println("Balance: " + rs.getInt("balance"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void displayTable() {
        String selectAll = "SELECT * FROM card;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(selectAll)){

            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("number") + "\t" +
                        rs.getString("pin") + "\t" +
                        rs.getInt("balance"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



}
public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner sc=new Scanner(System.in);
        HashMap<String, String> cardList = new HashMap<>();
        HashMap<String, Integer> acBal = new HashMap<>();
        AccountDB ops = new AccountDB(args[1]);
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
                    if(!ops.containsRecord(st, pp)){
                        ops.insertData(st, pp);
//                        cardList.put(st,pp);
//                        acBal.put(st,0);
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

                if(!ops.containsRecord(ac, pp)){
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
                        ops.printBalance(ac, pp);
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
