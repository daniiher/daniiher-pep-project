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
        Connection con = ConnectionUtil.getConnection();
        try {
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

    public Message insertMessage(Message msg) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

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

    public Message getMessageById(int message_id) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        Connection con = ConnectionUtil.getConnection();
        try {
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

    public Message updateMessageById(Message msg) {
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text = ?, time_posted_epoch = ? where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, msg.message_text);
            ps.setLong(2, msg.getTime_posted_epoch());
            ps.setInt(3, msg.getMessage_id());

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

    public List<Message> getAllMessagesFromAccount(int posted_by) {
        Connection con = ConnectionUtil.getConnection();
        try {
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
