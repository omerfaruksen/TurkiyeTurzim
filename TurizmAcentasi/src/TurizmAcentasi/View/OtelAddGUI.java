package TurizmAcentasi.View;

import TurizmAcentasi.Helper.Config;
import TurizmAcentasi.Helper.Helper;
import TurizmAcentasi.Model.Employee;
import TurizmAcentasi.Model.Otel;
import TurizmAcentasi.Model.OtelType;
import TurizmAcentasi.Model.Season;

import javax.swing.*;

public class OtelAddGUI extends JFrame {

    private JPanel wrapper;
    private JTextField fld_otel_name;
    private JComboBox cmb_otel_star;
    private JTextArea txt_Otel_Property;
    private JTextArea txt_otel_address;
    private JTextField fld_otel_phone;
    private JTextField fld_otel_mail;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JRadioButton radioButton7;
    private JTextField fld_season_start;
    private JTextField fld_season_end;
    private JTextField fld_season_start2;
    private JTextField fld_season_end2;
    private JButton sistemeKaydetButton;
    private String select_star;
    private final Employee employee;
    private int added_otel_id;//Eklenen otellerin id sini tutacak
    public OtelAddGUI(Employee employee){
        this.employee=employee;
        add(wrapper);
        setSize(800,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        radioButton1.setText(Helper.otelType("1"));
        radioButton2.setText(Helper.otelType("2"));
        radioButton3.setText(Helper.otelType("3"));
        radioButton4.setText(Helper.otelType("4"));
        radioButton5.setText(Helper.otelType("5"));
        radioButton6.setText(Helper.otelType("6"));
        radioButton7.setText(Helper.otelType("7"));

        select_star=cmb_otel_star.getSelectedItem().toString();

        sistemeKaydetButton.addActionListener(e -> {
            if (Helper.isFiledEmpty(fld_otel_name)|| Helper.isFiledEmpty(fld_otel_mail)||Helper.isFiledEmpty(fld_otel_phone)|| Helper.isFiledEmpty(fld_season_end)||
                    Helper.isTextArea(txt_otel_address)|| Helper.isTextArea(txt_Otel_Property)||Helper.isFiledEmpty(fld_season_start)||
                    (!radioButton1.isSelected() && !radioButton2.isSelected() && !radioButton3.isSelected() && !radioButton4.isSelected() &&
                            !radioButton5.isSelected() && !radioButton6.isSelected() && !radioButton7.isSelected())){
                Helper.showMessage("fill");
            }else{
                String name=fld_otel_name.getText();
                String star=(String) cmb_otel_star.getSelectedItem();
                String property=txt_Otel_Property.getText();
                String address=txt_otel_address.getText();
                String phone = fld_otel_phone.getText();
                String mail=fld_otel_mail.getText();
                String season_start1=fld_season_start.getText();
                String season_end1=fld_season_end.getText();
                String season_start2=fld_season_start2.getText();
                String season_end2=fld_season_end2.getText();

                if (Otel.add(name,star,property,address,phone,mail)){
                    Otel addedOtel=Otel.getFetch(mail);
                    added_otel_id=addedOtel.getId();
                    //RADİOBUTTON KONTROLÜ VE PANSİYONLARIN EKLENMESİ

                                if (radioButton1.isSelected()){
                                    OtelType.add(radioButton1.getText(),added_otel_id);
                                }
                                if (radioButton2.isSelected()){
                                    OtelType.add(radioButton2.getText(),added_otel_id);
                                }
                                if (radioButton3.isSelected()){
                                    OtelType.add(radioButton3.getText(),added_otel_id);
                                }
                                if (radioButton4.isSelected()){
                                    OtelType.add(radioButton4.getText(),added_otel_id);
                                }
                                if (radioButton5.isSelected()){
                                    OtelType.add(radioButton5.getText(),added_otel_id);
                                }
                                if (radioButton6.isSelected()){
                                    OtelType.add(radioButton6.getText(),added_otel_id);
                                }
                                if (radioButton7.isSelected()){
                                    OtelType.add(radioButton7.getText(),added_otel_id);
                                }


                    Season.add(season_start1,season_end1,added_otel_id);
                    if (!Helper.isFiledEmpty(fld_season_start2)&&!Helper.isFiledEmpty(fld_season_end2)){
                        Season.add(season_start2,season_end2,added_otel_id);


                        //İçi doldurulan herşeyin için boşalt!!
                    }
                    Helper.showMessage("done");
                    fld_otel_name.setText(null);
                    cmb_otel_star.setSelectedItem(0);
                    txt_otel_address.setText(null);
                    txt_Otel_Property.setText(null);
                    fld_otel_mail.setText(null);
                    fld_otel_phone.setText(null);
                    fld_season_end.setText(null);
                    fld_season_start.setText(null);
                    fld_season_end2.setText(null);
                    fld_season_start2.setText(null);
                    radioButton1.setSelected(false);
                    radioButton2.setSelected(false);
                    radioButton3.setSelected(false);
                    radioButton4.setSelected(false);
                    radioButton5.setSelected(false);
                    radioButton6.setSelected(false);
                    radioButton7.setSelected(false);

                }

            }
        });
    }
}
