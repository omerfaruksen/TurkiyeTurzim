package TurizmAcentasi.View;
import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Model.Admin;
import TurizmAcentasi.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {
    private JPanel wrapper;
    private JLabel fld_wellcome;
    private JButton btn_exist;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrl_userList;
    private JTable tbl_userList;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JTextField fld_user_password;
    private JComboBox cmb_add_user;
    private JButton btn_add;
    private JTextField fld_user_dlt;
    private JButton btn_user_dlt;
    private final Admin admin;
    private DefaultTableModel mdl_userList;
    private Object[] row_user_list;

    public AdminGUI(Admin admin){
        this.admin=admin;
        add(wrapper);
        setSize(700,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_wellcome.setText("Hoşgeldin : " +admin.getName());

//Kullanıcı Kolar Kısmı
        mdl_userList=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object [] col_user_list={"ID","Adı Soyadı","Kullanıcı Adı","Şifre","Statü"};//Oluşturduğumuz tablonun sütun isimlerini tutan bir array tanımladık
            mdl_userList.setColumnIdentifiers(col_user_list);
            row_user_list=new Object[col_user_list.length];
            loadUserModel();
            tbl_userList.setModel(mdl_userList);
            tbl_userList.getTableHeader().setReorderingAllowed(false);//Sütunları sürükleyerek yerini değiştirmeyi engelledik
            tbl_userList.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl_userList.getColumnModel().getColumn(3).setMaxWidth(75);
        //Tabloya tıkladığımızda Userın ID'sini sil butonu üzerindeki kutucuğa yazdıran alan
        //Tabloya tıklandığında tıklanan satırın ID'sini alan kısım
            tbl_userList.getSelectionModel().addListSelectionListener(e -> {
                try {
                    String selected_user_id=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString();
                    fld_user_dlt.setText(selected_user_id);
                }catch ( Exception exception){

                }
            });

        //Çıkış Yap Butonu Listener !!
        btn_exist.addActionListener(e -> {
            dispose();//Çıkış tuşuna basıldı ve ekran kapatıldı.
            LoginGUI loginGUI=new LoginGUI();//Ekran kapatıldıktan sonra Login ekranına yönlendirildi.
        });
        //Kullanıcı Ekleme Butonu Listener !!
        btn_add.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_user_name)|Helper.isFiledEmpty(fld_user_username)|Helper.isFiledEmpty(fld_user_password)){
                Helper.showMessage("fill");
            }else{
                String name=fld_user_name.getText();
                String username=fld_user_username.getText();
                String password=fld_user_password.getText();
                String type=cmb_add_user.getSelectedItem().toString();
                if (User.add(name,username,password,type)){
                    Helper.showMessage("done");
                    loadUserModel();
                    fld_user_name.setText(null);//Ekleme yapıldıktan sonra texboxların iç kısımlarını boşaltıyoruz.
                    fld_user_password.setText(null);//Bu işlemi yapmazsak textboxların içinde enson eklenen kişinin bilgileri yer alıyor.
                    fld_user_username.setText(null);
                }
            }

        });
        //Silme butonu işlemleri
        btn_user_dlt.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_user_dlt)){
                Helper.showMessage("fill");
            }else{
                if (Helper.confirm("sure")){
                    int fld_user_id=Integer.parseInt(fld_user_dlt.getText());
                    if (User.delete(fld_user_id))
                        Helper.showMessage("done");
                    loadUserModel();
                    fld_user_dlt.setText(null);
                }else{
                    Helper.showMessage("erorr");
                }
            }
        });
    }
    public void loadUserModel(){//Databaseden userları alıyor ve satır olarak tabloya işleme işlemini gerçekleştiriyor
        DefaultTableModel clearModel= (DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getUserList()){
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUsername();
            row_user_list[i++]=obj.getPassword();
            row_user_list[i++]=obj.getType();
            mdl_userList.addRow(row_user_list);
        }
    }/*

    public static void main(String[] args) {
        Helper.setLayout();
        /*Admin ad=new Admin();
        ad.setId(2);
        ad.setName("Haticenur Dinçel Şen");
        ad.setPassword("123456789");
        ad.setType("Admin");
        ad.setUsername("haticenur.dincelsen");*/


}


