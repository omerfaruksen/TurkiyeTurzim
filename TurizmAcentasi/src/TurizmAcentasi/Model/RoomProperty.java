package TurizmAcentasi.Model;

import TurizmAcentasi.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomProperty {
    private int id;
    private String property;
    private int room_id;
    private String bed;
    private int area;

    private Room room;
    public RoomProperty(){}

    public RoomProperty(int id, String property, int room_id, String bed, int area) {
        this.id = id;
        this.property = property;
        this.room_id = room_id;
        this.bed = bed;
        this.area = area;
        this.room = Room.getFetch(room_id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    //oda özellilerini oda id sine db den alan metod
    public static ArrayList<RoomProperty> getListByRoomID(int id){
        ArrayList<RoomProperty> roomPropertyList = new ArrayList<>();
        RoomProperty obj;
        String query = "SELECT * FROM room_property WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new RoomProperty();
                obj.setProperty(rs.getString("property"));
                obj.setBed(rs.getString("bed"));
                obj.setArea(rs.getInt("area"));
                roomPropertyList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomPropertyList;
    }

    public static boolean add(String property, int room_id, String bed, int area){
        String query = "INSERT INTO room_property (property, room_id, bed, area ) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,property);
            pr.setInt(2,room_id);
            pr.setString(3, bed);
            pr.setInt(4, area);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static RoomProperty getFetch(int id) {
        RoomProperty obj = null;
        String query = "SELECT * FROM room_property WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new RoomProperty(rs.getInt("id"), rs.getString("property"), rs.getInt("room_id"), rs.getString("bed"), rs.getInt("area"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }




}
