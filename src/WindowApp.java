import javax.swing.*;
import java.awt.*;

public class WindowApp extends JFrame {
        public WindowApp() {
            super("Работа с выписками");
            setBounds(1000, 500, 1000, 1000);
        }


    public static void main(String[] args) {
        WindowApp window = new WindowApp();

        JButton uploadFile = new JButton("Загрузить файл");
        JButton sendToProssesing = new JButton("Подать на обработку");
        JButton getFile = new JButton("Скачать готовый файл");

        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new FlowLayout());




        panel.add(uploadFile);
        panel.add(sendToProssesing);
        panel.add(getFile);

        window.setVisible(true);
        window.add(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}