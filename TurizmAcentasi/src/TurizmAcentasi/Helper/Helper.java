package TurizmAcentasi.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
    public static int screenCenterPoint(String coordinate, Dimension size){
        int point;
        switch (coordinate){
            case "x":
                point=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y":
                point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default:
                point=0;
        }
        return point;
    }
    public static boolean isFiledEmpty(JTextField field){//Text boxların dolu olup olmadığının kontrolü
        return field.getText().trim().isEmpty();
    }
    public static boolean isTextArea(JTextArea field){//Text boxların dolu olup olmadığının kontrolü
        return field.getText().trim().isEmpty();
    }
    public static void showMessage(String str){
        optionPageTR();
        String msg;
        String title;
        switch (str){
            case "fill":
                msg="Lütfen gerekli olan tüm alanları doldurunuz ! ";
                title="UYARI MESAJI";
                break;
            case "done" :
                msg="İşlem Başarılı!";
                title="BAŞARILI";
                break;
            case "error":
                msg="Beklenmeyen bir hata ile karşılaşıldı!";
                title="ERROR!";
                break;
            default:
                msg=str;
                title="Mesaj";
        }
        JOptionPane.showConfirmDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public  static String otelType(String number){
        String type="";
        switch (number){
            case "1":
            type="Ultra Herşey Dahil";
                break;
            case "2":
                type="Herşey Dahil";
                break;
            case "3":
                type="Tam Pansiyon";
                break;
            case "4":
                type="Yarım Pansiyon";
                break;
            case "5":
                type="Oda Kahvaltı";
                break;
            case "6":
                type="Sadece Yatak";
                break;
            case "7":
                type="Alkolsüz Herşey Dahil";
                break;
        }
        return type;
    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }

    public static boolean confirm(String str){
        String msg;
        switch (str){
            case "sure" :
                msg="Bu işlemi yapmak istediğinize emin misiniz?";
                break;
            default:
                msg=str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Emin misiniz?",JOptionPane.YES_NO_OPTION)==0;
    }
    public static String roomProperty(String number){
        String property="";
        switch (number){
            case "1":
                property = "Televizyon ";
                break;
            case "2":
                property = "Minibar ";
                break;
            case "3":
                property = "Oyun Konsolu";
                break;
            case "4":
                property = "Kasa";
                break;
            case "5":
                property = "Projeksiyon";
                break;
        }
        return property;
    }

}
