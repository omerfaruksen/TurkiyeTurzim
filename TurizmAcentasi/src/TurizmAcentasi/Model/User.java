package TurizmAcentasi.Model;

import TurizmAcentasi.Helper.DBConnector;
import TurizmAcentasi.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;
    public User(){}

    public User(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }












    public static ArrayList<User> getUserList(){//Databaseden verileri alıp bir objeye entegre ediyoruz
        ArrayList<User> userList=new ArrayList<>();
        String query="SELECT*FROM user";
        User obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }

    //Arayüz üzerinden ekleme yaptığımızda, ekleme işlemini gerçekleştiren nokta
    public static boolean add(String name, String username, String password, String type){
        String query="INSERT INTO user (name,username,password,type) VALUES(?,?,?,?)";
        User findUser=User.getFetch(username);
        if (findUser!=null){//eğer sistemde böyle bir kullanıcı var ise uyarı mesajı veriyoruz
            Helper.showMessage("Bu kullanıcı daha önce eklenmiş. Lütfen yeni bir kullanıcı giriniz!");
            return false;
        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,password);
            pr.setString(4,type);
            int response=pr.executeUpdate();
            if (response==-1){
                Helper.showMessage("error");
            }
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getFetch(String username){//Kullanı adı kontrolünün yapıldığı kısım
        User obj=null;
        String query="SELECT*FROM user WHERE username=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static User getFetch(String username, String password){ //Login giriş kontrolünü yapıyoruz
        User obj=null;
        String query="SELECT*FROM user WHERE username=? AND password=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2,password);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){//Eğer databaseden gelen bir veri varsa?
                switch (rs.getString("type")){//Userin typenı kontrol ediyoruz.
                    case "Admin":
                        obj=new Admin();//Admin nesnesi oluşturuyoruz
                        break;
                    case "Employee":
                        obj=new Employee();//Employee nesnesi oluşturuyoruz
                        break;
                    default:
                        obj= new User();
                }
                //Databaseden gelen verileri userın içerisine setledik
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    return obj;
    }
    //Silme İşlemini gerçekleştirdiğimiz kısım
    public static boolean delete(int id){//ID ye göre silme işlemi
        String query="DELETE FROM user WHERE id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
