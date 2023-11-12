package TurizmAcentasi.Model;

import TurizmAcentasi.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtelType {
    private int id;
    private String type;
    private int otel_id;

    public OtelType(int id, String type, int otel_id) {
        this.id = id;
        this.type = type;
        this.otel_id = otel_id;
    }
    public OtelType(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }
    public static ArrayList<OtelType> getList(){
        ArrayList<OtelType> otelTypeList = new ArrayList<>();
        OtelType obj;
        String query = "SELECT * FROM type_otel";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery(query);
            while (rs.next()){
                obj = new OtelType();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setOtel_id(rs.getInt("otel_id"));
                otelTypeList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otelTypeList;
    }

    public static boolean add(String type, int otel_id){
        String query="INSERT INTO type_otel (type,otel_id) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,type);
            pr.setInt(2,otel_id);
            return pr.executeUpdate() !=-1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public static OtelType getFetch(int id) {
        OtelType obj = null;
        String query = "SELECT * FROM type_otel WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new OtelType(rs.getInt("id"), rs.getString("type"), rs.getInt("otel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }
    public static ArrayList<OtelType> getListByOtelID( int id){//Otel IDsine göre pansiyon tipleri
        ArrayList<OtelType> otelTypeList = new ArrayList<>();
        OtelType obj;
        String query = "SELECT * FROM type_otel WHERE otel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new OtelType();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setOtel_id(rs.getInt("otel_id"));
                otelTypeList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otelTypeList;
    }
}
