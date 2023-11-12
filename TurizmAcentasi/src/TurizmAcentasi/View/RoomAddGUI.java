package TurizmAcentasi.View;

import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Helper.Item;
import TurizmAcentasi.Model.*;

import javax.swing.*;
import java.util.ArrayList;

public class RoomAddGUI extends JFrame{
    private JPanel wrapper;
    private JComboBox cmb_otel_room_name;
    private JComboBox cmb_room_type;
    private JTextField fld_stok;
    private JComboBox cmb_otel_type;
    private JComboBox cmb_season;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JTextField fld_bed;
    private JTextField fld_area;
    private JButton btn_room_add;
    private JPanel fld_room_area;
    private Employee employee;
    private int addedRoom_id;
    public RoomAddGUI(Employee employee) {
        this.employee = employee;
        add(wrapper);
        setSize(800, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        radioButton1.setText(Helper.roomProperty("1"));
        radioButton2.setText(Helper.roomProperty("2"));
        radioButton3.setText(Helper.roomProperty("3"));
        radioButton4.setText(Helper.roomProperty("4"));
        radioButton5.setText(Helper.roomProperty("5"));

        loadOtelNameCombo();
        loadOtelTypeCombo();
        loadSeasonCombo();

//oda ekleme sayfasında seçilen odaya göre dinamik olarak pansiyon tipini getiren metod
        cmb_otel_room_name.addActionListener(event -> {
            loadOtelTypeCombo();
            loadSeasonCombo();
            cmb_room_type.setSelectedIndex(0);
            cmb_season.setSelectedIndex(0);
        });

//oda ekle butonu kodları başlangıcı
        btn_room_add.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_stok) || Helper.isFiledEmpty(fld_adult_price) || Helper.isFiledEmpty(fld_child_price) ||
                    Helper.isFiledEmpty(fld_bed) || Helper.isFiledEmpty(fld_area) ||
                    cmb_room_type.getSelectedItem().toString().equals("") || cmb_otel_type.getSelectedItem() == null ||
                    cmb_season.getSelectedItem() == null || cmb_otel_room_name == null ||
                    (!radioButton1.isSelected() && !radioButton2.isSelected() && !radioButton3.isSelected() &&
                            !radioButton4.isSelected() && !radioButton5.isSelected())){
                System.out.println("BURADASINNNN");
                Helper.showMessage("fill");
            }
            else {
                String room_type = cmb_room_type.getSelectedItem().toString();
                int stock = Integer.parseInt(fld_stok.getText());
                int season_id=0;
                int adult_price = Integer.parseInt(fld_adult_price.getText().toString());
                int child_price = Integer.parseInt(fld_child_price.getText().toString());
                Item otelTypeItem = (Item) cmb_otel_type.getSelectedItem();
                int otel_type_id = otelTypeItem.getKey();
                Item otelItem = (Item) cmb_otel_room_name.getSelectedItem();
                int otel_id = otelItem.getKey();
                for (Season obj : Season.getListByOtelID(otel_id)){ //season ıd yi çekmek için yazıldı
                    String season = (obj.getSeason_start().toString() + "  -  " + obj.getSeason_end().toString());
                    if (season.equals(cmb_season.getSelectedItem().toString())){
                        season_id = obj.getId();
                        break;
                    }
                    System.out.println("BURADASIN 2");
                }

                if (Room.add(room_type, stock, season_id, adult_price, child_price, otel_type_id, otel_id)){
                    ArrayList<Room> roomList = Room.getList();
                    Room addedRoom = roomList.get(Room.getList().size()-1);
                    addedRoom_id = addedRoom.getId();
                    String room_properties = "";
                    for (int i = 1; i<=7; i++){  //room property ekleme
                        switch (i){
                            case 1:
                                if (radioButton1.isSelected()){
                                    room_properties += radioButton1.getText();
                                }
                                break;
                            case 2:
                                if (radioButton2.isSelected()){
                                    room_properties += "\n"+radioButton2.getText();
                                }
                                break;
                            case 3:
                                if (radioButton3.isSelected()){
                                    room_properties += "\n"+radioButton3.getText();
                                }
                                break;
                            case 4:
                                if (radioButton4.isSelected()){
                                    room_properties += "\n" + radioButton4.getText();
                                }
                                break;
                            case 5:
                                if (radioButton5.isSelected()){
                                    room_properties += "\n" + radioButton5.getText();
                                }
                                break;
                        }
                    }
                    System.out.println("BURADASIN ^3");
                    RoomProperty.add(room_properties, addedRoom_id, fld_bed.getText(), Integer.parseInt(fld_area.getText().toString()) );
                    Helper.showMessage("done");
                    cmb_otel_room_name.setSelectedIndex(0);
                    cmb_room_type.setSelectedIndex(0);
                    fld_stok.setText(null);
                    cmb_otel_type.setSelectedIndex(0);
                    cmb_season.setSelectedIndex(0);
                    fld_adult_price.setText(null);
                    fld_child_price.setText(null);
                    fld_bed.setText(null);
                    fld_area.setText(null);
                    radioButton1.setSelected(false);
                    radioButton2.setSelected(false);
                    radioButton3.setSelected(false);
                    radioButton4.setSelected(false);
                    radioButton5.setSelected(false);
                }
            }
        });
//oda ekle butonu kodları bitişi

    }

    //otel isimlerini combo box a aktaran metod
    public void loadOtelNameCombo(){
        cmb_otel_room_name.removeAllItems();
        cmb_otel_room_name.addItem(new Item(0,null));
        for (Otel obj : Otel.getOtelList()){
            cmb_otel_room_name.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    //oda ekleme sayfasında seçilen otele göre pansiyon türlerini combo box a aktaran metod
    private void loadOtelTypeCombo() {
        Item otelItem = (Item) cmb_otel_room_name.getSelectedItem();
        cmb_otel_type.removeAllItems();
        cmb_otel_type.addItem(new Item(0,null));
        for (OtelType obj : OtelType.getListByOtelID(otelItem.getKey())){

            cmb_otel_type.addItem(new Item(obj.getId(), obj.getType()));
        }
    }

    //oda ekleme sayfasında seçilen otele göre sezon türlerini combo box a aktaran metod
    private void loadSeasonCombo() {
        Item otelItem = (Item) cmb_otel_room_name.getSelectedItem();
        cmb_season.removeAllItems();
        cmb_season.addItem(new Item(0,null));
        for (Season obj : Season.getListByOtelID(otelItem.getKey())){
            cmb_season.addItem(new Item(obj.getId(), (obj.getSeason_start() + "  -  " + obj.getSeason_end())));
        }
    }

}
