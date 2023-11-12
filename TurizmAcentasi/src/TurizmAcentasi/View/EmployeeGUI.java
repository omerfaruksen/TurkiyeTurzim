package TurizmAcentasi.View;

import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_logout;
    private JButton btn_room_add;//KONTROL ET
    private JTabbedPane tabbedPane1;
    private JLabel fld_user_welcome;
    private JButton btn_otel_add;
    private JTable tbl_otel_list;
    private JTable tbl_otel_season;
    private JScrollPane pnl_otel;
    private JTable tbl_otel_type;
    private JTable tbl_room_list;
    private JTextField fld_region_otelName;
    private JTextField fld_checkin;
    private JTextField fld_checkout;
    private JTextField fld_adult_num;
    private JTextField fld_child_num;
    private JButton btn_room_search;
    private JTextField fld_room_id;
    private JButton btn_room_reservation;
    private JTable tbl_room_property;
    private JTable tbl_reservation_list;
    private JTextField fld_room_otel;
    private JTextArea fld_room_property;
    private JComboBox cmb_roomSelect;
    private final Employee employee;
    private DefaultTableModel mdl_otelsList;
    private DefaultTableModel mdl_roomsList;
    private DefaultTableModel mdl_otel_season;
    private DefaultTableModel mdl_reservation_list;
    private DefaultTableModel mdl_otel_type;
    DefaultTableModel mdl_room_list;

    private DefaultTableModel mdl_room_property;
    private int select_room_id;
    private int reservation_room_id;
    private String check_in;
    private String check_out;
    private int adult_num = 0;
    private int child_num = 0;


    private Object [] row_otel_list;
    private Object[] row_room_list;
    private Object [] row_otel_type;
    private Object[] row_reservation_list;
    private Object [] row_otel_season;
    private Object [] row_room_type_list;
    private Object[] row_room_properties;
    private int select_otel_id;


    public EmployeeGUI(Employee employee){
        this.employee=employee;
        add(wrapper);
        setSize(1000,700);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_user_welcome.setText("Hoşgeldiniz : "+employee.getName());

        //Otel Listeleme işlemleri
        mdl_otelsList=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_otel_list={"ID","Otel Adı", "Yıldız","Tesis Özellikleri","Adres","Telefon","E-posta"};
        mdl_otelsList.setColumnIdentifiers(col_otel_list);
        row_otel_list=new Object[col_otel_list.length];
        loadOtelModel();
        tbl_otel_list.setModel((mdl_otelsList));
        tbl_otel_list.getTableHeader().setReorderingAllowed(false);
        tbl_otel_list.getColumnModel().getColumn(0).setMaxWidth(30);
        //Tabloya tıkladığımızda otelin ID'sini sil butonu üzerindeki kutucuğa yazdıran alan
        tbl_otel_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_otel_id=tbl_otel_list.getValueAt(tbl_otel_list.getSelectedRow(),0).toString();
                //fld_otel_dlt.setText(selected_otel_id);
                //fld_otel_id.setText(selected_otel_id);
            }catch ( Exception exception){

            }
        });
//pansiyon tiplerini ve sezonları listelemek için otel id sini alma.
        tbl_otel_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_otel_id = Integer.parseInt(tbl_otel_list.getValueAt(tbl_otel_list.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            loadOtelTypeModel(select_otel_id);
            loadSeasonModel(select_otel_id);
            select_otel_id = 0;
        });

//otel tablosu kodları bitişi

//otel konaklama tablosu(yarım pansiyon, tam pansiyon, herşey dahil vs) kodları başlangıcı
        mdl_otel_type = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_room_type_list = {"Pansiyon Tipleri"};
        mdl_otel_type.setColumnIdentifiers(col_room_type_list);
        row_otel_type = new Object[col_room_type_list.length];
        //loadOtelTypeModel();
        tbl_otel_type.setModel(mdl_otel_type);
        tbl_otel_type.getTableHeader().setReorderingAllowed(false);

//otel konaklama tablosu(yarım pansiyon, tam pansiyon, herşey dahil vs) kodları bitişi

//otel sezon tablosu kodları başlangıcı
        mdl_otel_season = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_otel_season = {"Dönem Başlangıcı", "Dönem Bitişi"};
        mdl_otel_season.setColumnIdentifiers(col_otel_season);
        row_otel_season = new Object[col_otel_season.length];
        //loadSeasonModel();
        tbl_otel_season.setModel(mdl_otel_season);
        tbl_otel_season.getTableHeader().setReorderingAllowed(false);

//otel sezon tablosu kodları başlangıcı

//oda tablosu kodları başlangıcı

        mdl_room_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_room_list = {"id", "Otel Adı", "Oda Tipi", "Stok", "Sezon Tarihleri", "Yetişkin Fiyatı","Çocuk Fiyatı", "Pansiyon Tipi"};
        mdl_room_list.setColumnIdentifiers(col_room_list);
        row_room_list = new Object[col_room_list.length];
        loadRoomListModel();
        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);
        tbl_room_list.getColumnModel().getColumn(0).setMaxWidth(75);
//oda tablosu kodları bitişi

//oda özellikleri tablosu kodları başlangıcı
        mdl_room_property = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_room_properties = {"Oda Özellikleri", "Yatak Bilgisi", "Alan(m2)" };
        mdl_room_property.setColumnIdentifiers(col_room_properties);
        row_room_properties = new Object[col_room_properties.length];
        //loadRoomPropertiesModel();
        tbl_room_property.setModel(mdl_room_property);
        tbl_room_property.getTableHeader().setReorderingAllowed(false);

//oda özelliklerini listelemek için tıklanınca oda id sini alma.
        tbl_room_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                select_room_id = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            fld_room_id.setText(Integer.toString(select_room_id));
            loadRoomPropertiesModel(select_room_id);
            reservation_room_id = select_room_id;
            select_room_id = 0;
        });

//oda özellikleri tablosu kodları bitişi

//admin panel çıkış butonu
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI logGUI = new LoginGUI();

        });

//otel yönetim sayfası otel ekle butonu
        btn_otel_add.addActionListener(e -> {
            OtelAddGUI otelAdd = new OtelAddGUI(employee);
            otelAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadOtelModel();
                    tbl_otel_list.getSelectionModel().clearSelection();
                }
            });
        });

//oda yönetim sayfası oda ekle butonu
        btn_room_add.addActionListener(e -> {
            RoomAddGUI roomAddGUI = new RoomAddGUI(employee);
            roomAddGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomListModel();
                    tbl_room_list.getSelectionModel().clearSelection();
                    super.windowClosed(e);
                }
            });
        });

//oda arama butonu kodları
        btn_room_search.addActionListener(e -> {
            String regionOtelName = fld_region_otelName.getText();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            check_in = fld_checkin.getText().trim();
            check_out = fld_checkout.getText().trim();
            Date check_in_date = null;
            Date check_out_date = null;
            try {
                check_in_date = formatter.parse(check_in);
                check_out_date = formatter.parse(check_out);
            } catch (ParseException ex) {

            }

            String query = "SELECT * FROM otel WHERE name LIKE '%{{name}}%' OR address LIKE '%{{address}}%'";
            query = query.replace("{{name}}", regionOtelName);
            query = query.replace("{{address}}", regionOtelName);
            ArrayList<Otel> searchingOtel = Otel.searchOtelList(query);

            ArrayList<Room> searchingRoom = new ArrayList<>();

            if (Helper.isFiledEmpty(fld_checkin) && Helper.isFiledEmpty(fld_checkout) && Helper.isFiledEmpty(fld_region_otelName)){
                loadRoomListModel();
            }
            else if (Helper.isFiledEmpty(fld_checkin) && Helper.isFiledEmpty(fld_checkout)){
                for (Otel otel : searchingOtel){
                    Room obj = Room.getFetchByOtelID(otel.getId());
                    searchingRoom.add(obj);
                }
                if (searchingRoom.size() == 0){
                    Helper.showMessage("Aradığınız kriterlere uygun oda bulunamadı");
                }
                else {
                    loadRoomListModel(searchingRoom);
                }
            }
            else {
                for (Otel obj : searchingOtel){
                    ArrayList<Season> searchingSeason = Season.getListByOtelID(obj.getId());
                    for (Season season : searchingSeason){
                        String season_start = season.getSeason_start();
                        String season_end = season.getSeason_end();
                        Date season_start_date = null;
                        Date season_end_date = null;
                        try {
                            season_start_date = formatter.parse(season_start);
                            season_end_date = formatter.parse(season_end);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        if (season_start_date.before(check_in_date) && season_end_date.after(check_out_date)){
                            Room room = Room.getFetchByOtelIDSeasonID(season.getId(), obj.getId());
                            if (room != null){
                                searchingRoom.add(room);
                            }

                        }
                    }
                }

                if (searchingRoom.size() == 0){
                    Helper.showMessage("Aradığınız kriterlere uygun oda bulunamadı");
                }
                else {
                    loadRoomListModel(searchingRoom);
                }
            }
            fld_region_otelName.setText(null);
        });

        btn_room_reservation.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_room_id) || Helper.isFiledEmpty(fld_checkin) || Helper.isFiledEmpty(fld_checkout) || Helper.isFiledEmpty(fld_adult_num) || Helper.isFiledEmpty(fld_child_num)){
                Helper.showMessage("Rezervasyon yapılacak oda seçiniz. Giriş - Çıkış tarihlerini ve misafir sayılarını doldurunuz.");
            }
            else {
                Room room = Room.getFetch(reservation_room_id);
                adult_num = Integer.parseInt(fld_adult_num.getText());
                child_num = Integer.parseInt(fld_child_num.getText());
                check_in = fld_checkin.getText().trim();
                check_out = fld_checkout.getText().trim();

                ReservationGUI resGUI = new ReservationGUI(room, adult_num, child_num, check_in, check_out);
                resGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        fld_checkin.setText(null);
                        fld_checkout.setText(null);
                        fld_adult_num.setText(null);
                        fld_child_num.setText(null);
                        fld_room_id.setText(String.valueOf(0));
                        super.windowClosed(e);
                        loadRoomListModel();
                        loadReservationModel();
                    }
                });
            }
        });

//rezervasyon bilgileri tablosu kodları başlangıcı
        mdl_reservation_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_reservation_list = {"id", "Ad Soyad", "Telefon", "E-mail", "Not", "otel Adı", "Oda tipi", "Giriş Tarihi", "Çıkış Tarihi", "Yetişkin Sayısı", "Çocuk Sayısı", "Toplam Ücret"};
        mdl_reservation_list.setColumnIdentifiers(col_reservation_list);
        row_reservation_list = new Object[col_reservation_list.length];
        loadReservationModel();
        tbl_reservation_list.setModel(mdl_reservation_list);
        tbl_reservation_list.getTableHeader().setReorderingAllowed(false);
        tbl_reservation_list.getColumnModel().getColumn(0).setMaxWidth(75);
//rezervasyon bilgileri tablosu kodları bitişi

    }

    private void loadReservationModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservation_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Reservation obj : Reservation.getList()){
            i = 0;
            row_reservation_list[i++] = obj.getId();
            row_reservation_list[i++] = obj.getClient_name();
            row_reservation_list[i++] = obj.getClient_phone();
            row_reservation_list[i++] = obj.getClient_email();
            row_reservation_list[i++] = obj.getClient_note();
            row_reservation_list[i++] = Otel.getFetch(Room.getFetch(obj.getRoom_id()).getOtel_id()).getName();
            row_reservation_list[i++] = Room.getFetch(obj.getRoom_id()).getRoom_type();
            row_reservation_list[i++] = obj.getCheck_in();
            row_reservation_list[i++] = obj.getCheck_out();
            row_reservation_list[i++] = obj.getAdult_numb();
            row_reservation_list[i++] = obj.getChild_numb();
            row_reservation_list[i++] = obj.getTotal_price();

            mdl_reservation_list.addRow(row_reservation_list);
        }
    }

    private void loadOtelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_otel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Otel obj : Otel.getOtelList()){
            i = 0;
            row_otel_list[i++] = obj.getId();
            row_otel_list[i++] = obj.getName();
            row_otel_list[i++] = obj.getStar();
            row_otel_list[i++] = obj.getProperty();
            row_otel_list[i++] = obj.getAddress();
            row_otel_list[i++] = obj.getPhone();
            row_otel_list[i++] = obj.getMail();
            mdl_otelsList.addRow(row_otel_list);
        }
    }

    private void loadOtelTypeModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_otel_type.getModel();
        clearModel.setRowCount(0);
        int i;
        for (OtelType obj : OtelType.getListByOtelID(id)){
            i = 0;
            row_otel_type[i++] = obj.getType();
            mdl_otel_type.addRow(row_otel_type);
        }
    }

    private void loadSeasonModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_otel_season.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Season obj : Season.getListByOtelID(id)){
            i = 0;
            row_otel_season[i++] = obj.getSeason_start();
            row_otel_season[i++] = obj.getSeason_end();
            mdl_otel_season.addRow(row_otel_season);
        }
    }

    private void loadRoomListModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : Room.getList()){
            i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = Otel.getFetch(obj.getOtel_id()).getName();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            row_room_list[i++] = Season.getFetch(obj.getSeason_id()).getSeason_start() + " - " + Season.getFetch(obj.getSeason_id()).getSeason_end();
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            row_room_list[i++] = OtelType.getFetch(obj.getOtel_type_id()).getType();
            mdl_room_list.addRow(row_room_list);
        }
    }

    private void loadRoomListModel(ArrayList<Room> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        int i;
        if (list == null){
            Helper.showMessage("Aradığınız kriterlere uygun oda bulunamadı");
        }
        else{
            for (Room obj : list){
                i = 0;
                row_room_list[i++] = obj.getId();
                row_room_list[i++] = Otel.getFetch(obj.getOtel_id()).getName();
                row_room_list[i++] = obj.getRoom_type();
                row_room_list[i++] = obj.getStock();
                row_room_list[i++] = Season.getFetch(obj.getSeason_id()).getSeason_start() + " - " + Season.getFetch(obj.getSeason_id()).getSeason_end();
                row_room_list[i++] = obj.getAdult_price();
                row_room_list[i++] = obj.getChild_price();
                row_room_list[i++] = OtelType.getFetch(obj.getOtel_type_id()).getType();
                mdl_room_list.addRow(row_room_list);
            }
        }

    }

    private void loadRoomPropertiesModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_property.getModel();
        clearModel.setRowCount(0);
        int i;
        for (RoomProperty obj : RoomProperty.getListByRoomID(id)){
            i = 0;
            row_room_properties[i++] = obj.getProperty();
            row_room_properties[i++] = obj.getBed();
            row_room_properties[i++] = obj.getArea();
            mdl_room_property.addRow(row_room_properties);
        }
    }
}































/*
        btn_employee_exit.addActionListener(e -> {//Çıkış yap butonu
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });
        btn_otels_add.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_otel_name)||Helper.isFiledEmpty(fld_otel_adress)||Helper.isFiledEmpty(fld_otel_mail)||
            Helper.isFiledEmpty(fld_otel_phone)||Helper.isFiledEmpty(fld_otel_star)||Helper.isTextArea(fld_otel_property)){
                Helper.showMessage("fill");
            }else{
                String otel_name=fld_otel_name.getText();
                String otel_adress=fld_otel_adress.getText();
                String otel_mail=fld_otel_mail.getText();
                String otel_phone=fld_otel_phone.getText();
                String otel_star=fld_otel_star.getText();
                String otel_property=fld_otel_property.getText();
                //String pantype=cbm_pantype.getSelectedItem().toString();
                if (Otel.add(otel_name,otel_adress,otel_mail,otel_phone,otel_star,otel_property)){
                    Helper.showMessage("done");
                    loadOtelModel();//Tabloyu güncelliyoruz
                    //Ekleme yaptıktan sonra Textbox'ların içlerini boşaltıyoruz
                    fld_otel_name.setText(null);
                    fld_otel_adress.setText(null);
                    fld_otel_mail.setText(null);
                    fld_otel_phone.setText(null);
                    fld_otel_star.setText(null);
                    fld_otel_property.setText(null);

                }
            }

        });
        btn_otels_delete.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_otel_dlt)){
                Helper.showMessage("fill");
            }else{
                if (Helper.confirm("sure")){
                    int fld_otel_id=Integer.parseInt(fld_otel_dlt.getText());
                    if (Otel.delete(fld_otel_id))
                        Helper.showMessage("done");
                    loadOtelModel();
                    fld_otel_dlt.setText(null);
                }else{
                    Helper.showMessage("erorr");
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//RoomAdd Buton add
        btn_add.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_room_child)||Helper.isFiledEmpty(fld_room_adult)){
                Helper.showMessage("fill");
            }else{
                int otel_id=Integer.parseInt(fld_room_otel.getText());

                String pan_type=cmb_pan_type.getSelectedItem().toString();
                String room_type=cmb_room_type.getSelectedItem().toString();
                String room_property=fld_room_property.getText();
                int room_numbers= Integer.parseInt(fld_room_numbers.getText());
                int room_adult_price=Integer.parseInt(fld_room_adult.getText());
                int room_chil_price=Integer.parseInt(fld_room_child.getText());
                int season_id=Integer.parseInt(fld_room_season.getText());
                if (Room.add(otel_id,pan_type,room_type,room_property,room_numbers,room_adult_price,room_chil_price,season_id)){
                    Helper.showMessage("done");
                    loadRoomModel();
                }
            }
        });
        //Oda yönetiminde oda silme işlemini yaptığımız kısım
        btn_room_dlt.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_room_dlt)){
                Helper.showMessage("fill");
            }else{
                if (Helper.confirm("sure")){
                    int fld_room_id=Integer.parseInt(fld_room_dlt.getText());
                    if (Room.delete(fld_room_id))
                        Helper.showMessage("done");
                    loadRoomModel();
                    fld_room_dlt.setText(null);
                }else{
                    Helper.showMessage("erorr");
                }
            }
        });
        btn_pansiyon_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int otel_id= Integer.parseInt(fld_otel_id.getText());
                String type=cmb_panList.getSelectedItem().toString();
                if (PanType.add(otel_id,type)){
                    Helper.showMessage("done");
                    loadOtelModel();
                    loadOtelCombo();
                }

            }
        });


    public void loadOtelModel(){
        DefaultTableModel clearModel=(DefaultTableModel)tbl_otel_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Otel obj:Otel.getOtelList()){
        i=0;
        row_otel_list[i++]=obj.getId();
        row_otel_list[i++]=obj.getName();
        row_otel_list[i++]=obj.getStar();
        row_otel_list[i++]=obj.getProperty();
        row_otel_list[i++]=obj.getAddress();
        row_otel_list[i++]=obj.getPhone();
        row_otel_list[i++]=obj.getMail();
        mdl_otelsList.addRow(row_otel_list);

        }
}

        /*
        //Oda Yönetimi
        mdl_roomsList=new DefaultTableModel();
        Object[] col_roomlist={"ID","Otel Adı","Pansiyon Tipi","Oda Tipi","Yetişkin Fiyatı","Çocuk Fiyatı","Sezon","Stok",};
        mdl_roomsList.setColumnIdentifiers(col_roomlist);
        row_rooms_list=new Object[col_roomlist.length];
        loadRoomModel();
        loadOtelCombo();
        LoadPanTypeCombo();
        tbl_roomList.setModel(mdl_roomsList);
        tbl_roomList.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_roomList.getTableHeader().setReorderingAllowed(false);
        //Tabloya tıklandığında ID alınır ve sil butonunun üzerindeki kutucuğa yazar
        tbl_roomList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_room_id=tbl_roomList.getValueAt(tbl_roomList.getSelectedRow(),0).toString();
                fld_room_dlt.setText(selected_room_id);
            }catch ( Exception exception){

            }
        });
    }
    public void LoadPanTypeCombo(){//Pan Type Combo
        cmb_pan_type.removeAllItems();
        for (PanType obj:PanType.getOnlyOtelID()){
            cmb_pan_type.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadOtelCombo(){//Otel Kombo
        cbm_otel_list.removeAllItems();
        for (Otel obj:Otel.getOtelList()){
            cbm_otel_list.addItem(new Item(obj.getId(), obj.getOtel_name()));
        }
    }
    private void loadRoomModel(){
        DefaultTableModel clearModelRoom= (DefaultTableModel) tbl_roomList.getModel();
        clearModelRoom.setRowCount(0);
        int i=0;
        for (Room obj: Room.getRoomList()){
            i=0;
            row_rooms_list[i++]=obj.getOtel_id();
            row_rooms_list[i++]=obj.getOtel().getOtel_name();//Otelin Adı Yazılacak
            row_rooms_list[i++]=obj.getPantype_id();
            row_rooms_list[i++]=obj.getRoom_type();
            row_rooms_list[i++]=obj.getRoom_adult_price();
            row_rooms_list[i++]=obj.getRoom_child_price();
            row_rooms_list[i++]=obj.getSeason_id();//Sezon adı yazılacak
            row_rooms_list[i++]=obj.getRoom_numbers();
            mdl_roomsList.addRow(row_rooms_list);

        }*/





