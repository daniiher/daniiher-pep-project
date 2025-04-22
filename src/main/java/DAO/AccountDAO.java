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
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account.username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int posted_by) {
        Connection con = ConnectionUtil.getConnection();
        try {
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
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account ac) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account(account_id, username, password) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ac.getAccount_id());
            ps.setString(2, ac.getUsername());
            ps.setString(3, ac.getPassword());
            Boolean result = ps.execute();
            if (result) {
                ResultSet rs = ps.getResultSet();
                while(rs.next()) {
                    Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                    return account;
                }
            } else return null;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account verifyLoginAccount(Account ac) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account.username = ? and account.password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ac.getUsername());
            ps.setString(2, ac.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
