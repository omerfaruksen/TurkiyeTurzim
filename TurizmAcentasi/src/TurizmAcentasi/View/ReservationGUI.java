package TurizmAcentasi.View;

import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.DBConnector;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Model.Otel;
import TurizmAcentasi.Model.Reservation;
import TurizmAcentasi.Model.Room;
import TurizmAcentasi.Model.RoomProperty;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_add_reservation;
    private JTextField fld_client_name;
    private JTextField fld_client_phone;
    private JTextField fld_client_mail;
    private JTextArea txtArea_client_note;
    private JTextField fld_otel_name;
    private JTextArea txtArea_otel_address;
    private JTextField fld_otel_phone;
    private JTextArea txtArea_otel_property;
    private JTextField fld_room_type;
    private JTextArea txtArea_room_property;
    private JTextField fld_adult_num;
    private JTextField fld_child_num;
    private JTextField fld_check_in_date;
    private JTextField fld_check_out_date;
    private JTextField fld_total_price;
    private final Room room;
    private int adult_num = 0;
    private int child_num = 0;
    private String check_in;
    private String check_out;
    private int total_price;
    private int total_price2;

    public ReservationGUI(Room room, int adult_num, int child_num, String check_in, String check_out){
        this.room = room;
        this.adult_num = adult_num;
        this.child_num = child_num;
        this.check_in = check_in;
        this.check_out = check_out;
        add(wrapper);
        setSize(1200,700);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        Otel otel = Otel.getFetch(room.getOtel_id());
        RoomProperty roomProperty = RoomProperty.getFetch(room.getId());

        fld_otel_name.setText(otel.getName());
        txtArea_otel_address.setText(otel.getAddress());
        fld_otel_phone.setText(otel.getPhone());
        txtArea_otel_property.setText(otel.getProperty());
        fld_room_type.setText(room.getRoom_type());
        txtArea_room_property.setText(roomProperty.getProperty() + "\n" + roomProperty.getBed() + "\n" + roomProperty.getArea());
        fld_adult_num.setText(Integer.toString(adult_num));
        fld_child_num.setText(Integer.toString(child_num));
        fld_check_in_date.setText(check_in);
        fld_check_out_date.setText(check_out);

        total_price2 = 2* (   (room.getAdult_price() * adult_num) + (room.getChild_price() * child_num)  );
        //fld_total_price2.setText(total_price2 + " TL");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date check_in_date = null;
        Date check_out_date = null;
        try {
            check_in_date = formatter.parse(check_in);
            check_out_date = formatter.parse(check_out);
        } catch (ParseException ex) {

        }

        long diff = check_out_date.getTime() - check_in_date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        total_price = (int) (days * (   (room.getAdult_price() * adult_num) + (room.getChild_price() * child_num)   ));
        fld_total_price.setText(String.valueOf(total_price) + " TL");

        btn_add_reservation.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_client_name) || Helper.isFiledEmpty(fld_client_phone) || Helper.isFiledEmpty(fld_client_mail) || Helper.isTextArea(txtArea_client_note)){
                Helper.showMessage("Lütfen Rezervasyon bilgilerini doldurunuz.");
            }
            else{
                String client_name = fld_client_name.getText();
                String client_phone = fld_client_phone.getText();
                String client_email = fld_client_mail.getText();
                String client_note = txtArea_client_note.getText();
                int room_id = room.getId();

                if (Reservation.add(client_name, client_phone, client_email, client_note, room_id, check_in, check_out, adult_num, child_num, total_price)){
                    Helper.showMessage("Rezervasyon işlemi yapıldı.");
                    int newStock = room.getStock() - 1;
                    updateRoomStock(newStock, room_id);
                }
            }
        });
    }

    public static boolean updateRoomStock (int stock, int id){
        String query = "UPDATE room SET stock = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,stock);
            pr.setInt(2,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
