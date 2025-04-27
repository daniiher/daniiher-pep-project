package DAO;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.ConnectionUtil;
import Model.Message;


public class MessageDAO {
    
    /*  table:      message
     *  headers:    message_id  |  posted_by  |  message_text  |  time_posted_epoch 
     *                foreign key (posted_by) references  account(account_id)
     */

    public List<Message> getAllMessages() {
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select * from message";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Message> messages = new ArrayList<Message>();
            while (rs.next()) {
                messages.add( new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer insertMessage(Message msg) {
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Inserting message failed, no rows affected");
            }
            try ( ResultSet genKey = ps.getGeneratedKeys() ) {
                
                if (genKey.next()) {
                    return genKey.getInt("message_id");
                } else {
                    throw new SQLException("Inserting message failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageById(int msg_id) {
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select * from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, msg_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "delete from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message_id);

            if (ps.execute()) {
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    );
                }
            } else return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Boolean updateMessageById(String msg_text, int msg_id) {
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, msg_text);
            ps.setInt(2, msg_id);

            int affectedRows = ps.executeUpdate();
            switch (affectedRows) {
                case 0: throw new SQLException("Updating message failed, no rows affected");
                case 1: return true;
                default: return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Message> getAllMessagesFromAccount(int posted_by) {
        
        try {
            Connection con = ConnectionUtil.getConnection();
            String sql = "select * from message where message.posted_by = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, posted_by);
            ResultSet rs = ps.executeQuery();
            List<Message> messages = new ArrayList<Message>();
            while (rs.next()) {
                messages.add( new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
