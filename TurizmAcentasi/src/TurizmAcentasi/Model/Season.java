package TurizmAcentasi.Model;

import TurizmAcentasi.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Season {
    private int id;
    private String season_start;
    private String season_end;
    private int otel_id;

    private Otel otel;
    private Season(){}

    public Season(int id, String season_start, String season_end, int otel_id) {
        this.id = id;
        this.season_start = season_start;
        this.season_end = season_end;
        this.otel_id = otel_id;
        this.otel = Otel.getFetch(otel_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeason_start() {
        return season_start;
    }

    public void setSeason_start(String season_start) {
        this.season_start = season_start;
    }

    public String getSeason_end() {
        return season_end;
    }

    public void setSeason_end(String season_end) {
        this.season_end = season_end;
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

    public static Season getFetch(int id) {
        Season obj = null;
        String query = "SELECT * FROM season WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Season(rs.getInt("id"), rs.getString("season_start"), rs.getString("season_end"), rs.getInt("otel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }


    public static ArrayList<Season> getList(){
        ArrayList<Season> SeasonList = new ArrayList<>();
        Season obj;
        String query = "SELECT * FROM season";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery(query);
            while (rs.next()){
                obj = new Season();
                obj.setSeason_start(rs.getString("season_start"));
                obj.setSeason_end(rs.getString("season_end"));

                SeasonList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return SeasonList;
    }

    //konaklama sezonlarının otel id sine db den alan metod
    public static ArrayList<Season> getListByOtelID( int id){
        ArrayList<Season> SeasonList = new ArrayList<>();
        Season obj;
        String query = "SELECT * FROM season WHERE otel_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Season();
                obj.setId(rs.getInt("id"));
                obj.setSeason_start(rs.getString("season_start"));
                obj.setSeason_end(rs.getString("season_end"));
                obj.setOtel_id(rs.getInt("otel_id"));
                SeasonList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return SeasonList;
    }

    public static boolean add(String season_start, String season_end, int otel_id){
        String query = "INSERT INTO season (season_start, season_end, otel_id) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,season_start);
            pr.setString(2,season_end);
            pr.setInt(3, otel_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
