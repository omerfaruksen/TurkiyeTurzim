package TurizmAcentasi.Model;

import TurizmAcentasi.Helper.DBConnector;
import TurizmAcentasi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Room {

    private int id;
    private String room_type;
    private int stock;
    private int season_id;
    private int adult_price;
    private int child_price;
    private int otel_type_id;
    private int otel_id;

    private Otel otel;
    private Season Season;
    private OtelType otelType;

    public Room(){}

    public Room(int id, String room_type, int stock, int season_id, int adult_price, int child_price, int otel_type_id, int otel_id) {
        this.id = id;
        this.room_type = room_type;
        this.stock = stock;
        this.season_id = season_id;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.otel_type_id = otel_type_id;
        this.otel_id = otel_id;
        this.otel = Otel.getFetch(otel_id);
        this.Season = Season.getFetch(season_id);
        this.otelType = OtelType.getFetch(otel_type_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(int adult_price) {
        this.adult_price = adult_price;
    }

    public int getChild_price() {
        return child_price;
    }

    public void setChild_price(int child_price) {
        this.child_price = child_price;
    }

    public int getOtel_type_id() {
        return otel_type_id;
    }

    public void setOtel_type_id(int otel_type_id) {
        this.otel_type_id = otel_type_id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public Otel getOtel() {
        return otel;
    }

    public void setOtel(Otel otel) {
        this.otel = otel;
    }

    public TurizmAcentasi.Model.Season getSeason() {
        return Season;
    }

    public void setSeason(TurizmAcentasi.Model.Season season) {
        Season = season;
    }

    public OtelType getOtelType() {
        return otelType;
    }

    public void setOtelType(OtelType otelType) {
        this.otelType = otelType;
    }
    public static ArrayList<Room> getList(){
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room";
        Room obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setRoom_type(rs.getString("room_type"));
                obj.setStock(rs.getInt("stock"));
                obj.setSeason_id(rs.getInt("season_id"));
                obj.setAdult_price(rs.getInt("adult_price"));
                obj.setChild_price(rs.getInt("child_price"));
                obj.setOtel_type_id(rs.getInt("otel_type_id"));
                obj.setOtel_id(rs.getInt("otel_id"));
                roomList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomList;
    }
    public static Room getFetch(int id) {//Oda özelliklerini ID göre getiren metot
        Room obj = null;
        String query = "SELECT * FROM room WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Room(rs.getInt("id"), rs.getString("room_type"), rs.getInt("stock"), rs.getInt("season_id"), rs.getInt("adult_price"), rs.getInt("child_price"), rs.getInt("otel_type_id"), rs.getInt("otel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static boolean add(String room_type, int stock, int season_id, int adult_price, int child_price, int otel_type_id, int otel_id) {
        String query = "INSERT INTO room (room_type, stock, season_id, adult_price, child_price, otel_type_id, otel_id) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,room_type);
            pr.setInt(2,stock);
            pr.setInt(3,season_id);
            pr.setInt(4,adult_price);
            pr.setInt(5,child_price);
            pr.setInt(6,otel_type_id);
            pr.setInt(7,otel_id);

            int response = pr.executeUpdate();

            if (response == -1){
                Helper.showMessage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public static Room getFetchByOtelID(int id) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE otel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Room(rs.getInt("id"), rs.getString("room_type"), rs.getInt("stock"), rs.getInt("season_id"), rs.getInt("adult_price"), rs.getInt("child_price"), rs.getInt("otel_type_id"), rs.getInt("otel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Room getFetchByOtelIDSeasonID(int season_id, int otel_id) {
        String query = "SELECT * FROM room WHERE season_id = ? AND otel_id = ?";
        Room obj=null;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, season_id);
            pr.setInt(2, otel_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setRoom_type(rs.getString("room_type"));
                obj.setStock(rs.getInt("stock"));
                obj.setSeason_id(rs.getInt("season_id"));
                obj.setAdult_price(rs.getInt("adult_price"));
                obj.setChild_price(rs.getInt("child_price"));
                obj.setOtel_type_id(rs.getInt("otel_type_id"));
                obj.setOtel_id(rs.getInt("otel_id"));  }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static ArrayList<Room> searchRoomList(int otel_id){
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room WHERE otel_id = ?";
        Room obj;
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, otel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setRoom_type(rs.getString("room_type"));
                obj.setStock(rs.getInt("stock"));
                obj.setSeason_id(rs.getInt("season_id"));
                obj.setAdult_price(rs.getInt("adult_price"));
                obj.setChild_price(rs.getInt("child_price"));
                obj.setOtel_type_id(rs.getInt("otel_type_id"));
                obj.setOtel_id(rs.getInt("otel_id"));
                roomList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomList;
    }















    /* private int id;
    private int otel_id;
    private int pantype_id;
    private String room_type;
    private String room_property;
    private int room_numbers;
    private int room_adult_price;
    private int room_child_price;
    private int season_id;
    private Otel otel;
    private OtelType otelType;
    private Season season;//Sezonun id ile çekmek için nesne oluşturuldu


    public OtelType getOtelType() {
        return otelType;
    }

    public void setOtelType(OtelType otelType) {
        this.otelType = otelType;
    }

    public Room(int id, int otel_id, int pantype_id, String room_type, int room_numbers, int room_adult_price, int room_child_price, int season_id) {
        this.id = id;
        this.otel_id = otel_id;
        this.pantype_id = pantype_id;
        this.room_type = room_type;
        this.room_numbers = room_numbers;
        this.room_adult_price = room_adult_price;
        this.room_child_price = room_child_price;
        this.season_id = season_id;
        //this.otel=Otel.getFetch(otel_id);
        //this.season=Season.getFetch(season_id);
        //this.panType=PanType.getFetch(pantype_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }



    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getRoom_numbers() {
        return room_numbers;
    }

    public void setRoom_numbers(int room_numbers) {
        this.room_numbers = room_numbers;
    }

    public int getRoom_adult_price() {
        return room_adult_price;
    }

    public void setRoom_adult_price(int room_adult_price) {
        this.room_adult_price = room_adult_price;
    }

    public int getRoom_child_price() {
        return room_child_price;
    }

    public void setRoom_child_price(int room_child_price) {
        this.room_child_price = room_child_price;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public Otel getOtel() {
        return otel;
    }

    public void setOtel(Otel otel) {
        this.otel = otel;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public PanType getPanType() {
        return panType;
    }

    public void setPanType(PanType panType) {
        this.panType = panType;
    }


























    /*public static ArrayList<Room> getRoomList(){
        ArrayList<Room> roomList=new ArrayList<>();
        Room obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("SELECT*FROM rooms");
            while (rs.next()){
                int id=rs.getInt("id");
                int otel_id=rs.getInt("otel_id");
                int pantype_id=rs.getInt("pantype_id");
                String room_type=rs.getString("room_type");
                int room_adult_price=rs.getInt("room_adult_price");
                int room_child_price=rs.getInt("room_child_price");
                int season_id=rs.getInt("season_id");
                int room_numbers=rs.getInt("room_numbers");
                obj=new Room(id,otel_id,pantype_id,room_type,room_adult_price,room_child_price,season_id,room_numbers);
                roomList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }
    public static boolean add(int otel_id,String pan_type,String room_type,String room_property, int room_numbers,int room_adult_price,int room_child_price,int season_id){
        String query="INSERT INTO rooms (otel_id, pan_type,room_type,room_property,room_numbers,room_adult_price,room_child_price,season_id) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,otel_id);
            pr.setString(2,pan_type);
            pr.setString(3,room_type);
            pr.setString(4,room_property);
            pr.setInt(5,room_numbers);
            pr.setInt(6,room_adult_price);
            pr.setInt(7,room_child_price);
            pr.setInt(8,season_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static boolean delete(int id){//ID ye göre silme işlemi
        String query="DELETE FROM rooms WHERE id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }*/


}
