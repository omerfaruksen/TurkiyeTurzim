package TurizmAcentasi.View;

import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Model.Admin;
import TurizmAcentasi.Model.Employee;
import TurizmAcentasi.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel fld_logo_area;
    private JPanel fld_user_login;
    private JTextField fld_user_name;
    private JPasswordField fld_user_pass;
    private JButton btn_user_login;

    public LoginGUI(){

        add(wrapper);
        setSize(500,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        //Login ekranı "Giriş Yap" butonu metodu
        btn_user_login.addActionListener(e -> {
            //Kullanıcı adı ve şifre alanı doldurulmuş mu kontrolünü yapıyoruz.
            if (Helper.isFiledEmpty(fld_user_name)|Helper.isFiledEmpty(fld_user_pass)){
                Helper.showMessage("fill");//Doldurulması gereken alan boş ise hata mesajı veriliyor
            }else{
                User u=User.getFetch(fld_user_name.getText(),fld_user_pass.getText());//GetFetch'ten gelen userın ataması
                if (u==null){//Database sorgu işlemi sonucunda,bize bir user objesi dönecek.Eğer obje dönmezse otomatik null olacak
                    Helper.showMessage("Kullanıcı Bulunamadı! Kullanıcı adını ve şifresini kontrol ediniz!");
                }else{
                    switch (u.getType()){
                        case "Admin":
                            AdminGUI adminGUI=new AdminGUI((Admin)u);
                            break;
                        case "Employee":
                            EmployeeGUI employeeGUI=new EmployeeGUI((Employee)u);
                            break;
                        default:
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI login=new LoginGUI();
    }
}


