package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {


    /*  table:      account
     *  headers:    account_id  |  username  |  password 
     */

    public Account getAccountByUsername(String username) {
        System.out.print("\n\ngetAccount( username = " + username + " )\n\n");
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select * from account where account.username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            System.out.print("\n\n rs: " + rs + " \n\n");
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                System.out.print("\n\n new account:\n" + account.toString() + "\n\n");
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int posted_by) {
        System.out.print("\n\ngetAccountById:  posted_by = " + posted_by + " )\n\n");

        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select * from account where account.account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, posted_by);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                System.out.print("\n\n found account:\n" + account.toString() + "\n\n");
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer insertAccount(Account ac) {
        System.out.print("\n\ninsertAccount( ac =\n" + ac.toString() + " )\n\n");
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "insert into account(username, password) values(?,?)";
            PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ac.getUsername());
            ps.setString(2, ac.getPassword());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Inserting account failed, no rows affected");
            }
            try ( ResultSet genKey = ps.getGeneratedKeys() ) {
                if (genKey.next()) {
                    return genKey.getInt("account_id");  
                } else {
                    throw new SQLException("Inserting account failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer verifyLoginAccount(Account ac) {
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select account_id from account where account.username = ? and account.password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ac.getUsername());
            ps.setString(2, ac.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getInt("account_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
