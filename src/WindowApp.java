
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import javax.swing.filechooser.*;
/*

<html>" +
            "<div style='width:700px; margin: 0 auto; font-weight: normal;'>" +
            "<i><h1 style='text-align: center; margin-top: 45px;'>Руководство пользователя</h1></i>" +
            "<ol style='font-size: 16px;'>" +
            "<li style='margin-top: 10px;'>Для выбора файлов на подачу на обработку необходимо нажать зеленую кнопку «Выбрать файлы». <div style='color: rgb(118, 15, 15); font-weight: bold; font-style: italic;'>Внимание! Вы можете выбрать только файлы формата .csv</div></li>" +
            "<li style='margin-top: 10px;'>Для подачи файла на поиск кадастровых номеров достаточно нажать на синюю кнопку «Подать на обработку».</li>" +
            "<li style='margin-top: 10px;'>При успешном завершении операции обработанные файлы будут храниться на вашем компьютере в выбранной вами папке.</li>" +
            "<li style='margin-top: 10px;'>Для того, чтобы просеять папку с файлами на наличие факта получения загруженных файлов из Росеестра, нужно нажать на кнопку «Отбор файлов без кадастрового номера».</li>" +
            "</ol></div>" +
            "<div><h1 style='text-align: center; margin-top: 20px;'>Контакты техподдержки</h1>" +
            "<div style = 'font-size: 16px; text-align: center; font-weight: normal;'>Что-то не работает? Пишите нам на почту, мы Вам ответим, как только сможем!<br><a style ='font-size: 16px; font-weight: bold;' href='mailto:dvdlera@gmail.com'>dvdlera@gmail.com</a></div>" +
            "</div></html>

            */

public class WindowApp extends JFrame{
    Color dark_green = new Color(48, 133, 66);
    Color dark_dark_green = new Color(23, 83, 36);
    Color dark_blue = new Color(38, 99, 191);
    Color dark_dark_blue = new Color(5, 48, 113);
    Color purple = new Color(141,68,173);
    Color dark_purple = new Color(88, 17, 120);
    JLabel l = new JLabel("<html><h2>Файлы не выбраны</h2></html>");
    JFileChooser fileChooser = new JFileChooser();

    JPanel panel = new JPanel(new FlowLayout());
    JLabel userGuide = new JLabel("<html>" +
            "<div style='width:700px; margin: 0 auto; font-weight: normal;'>" +
            "<i><h1 style='text-align: center; margin-top: 45px;'>Руководство пользователя</h1></i>" +
            "<ol style='font-size: 16px;'>" +
            "<li style='margin-top: 10px;'>Для выбора файлов на подачу на обработку необходимо нажать зеленую кнопку «Выбрать файлы». <div style='color: rgb(118, 15, 15); font-weight: bold; font-style: italic;'>Внимание! Вы можете выбрать только файлы формата .csv</div></li>" +
            "<li style='margin-top: 10px;'>Для подачи файла на поиск кадастровых номеров достаточно нажать на синюю кнопку «Подать на обработку».</li>" +
            "<li style='margin-top: 10px;'>При успешном завершении операции обработанные файлы будут храниться на вашем компьютере в выбранной вами папке.</li>" +
            "<li style='margin-top: 10px;'>Для того, чтобы просеять папку с файлами на наличие факта получения загруженных файлов из Росеестра, нужно нажать на кнопку «Отбор файлов без кадастрового номера».</li>" +
            "</ol></div>" +
            "<div><h1 style='text-align: center; margin-top: 20px;'>Контакты техподдержки</h1>" +
            "<div style = 'font-size: 16px; text-align: center; font-weight: normal;'>Что-то не работает? Пишите нам на почту, мы Вам ответим, как только сможем!</div>" +
            "</div></html>");
    JLabel userGuideLink = new JLabel("<html><div style='margin-left: 260px;'><a style ='font-size: 16px; font-weight: bold; text-align: center;' href='mailto:dvdlera@gmail.com'>dvdlera@gmail.com</a></div></html>");
    RoundedButton uploadFile = new RoundedButton("Выбрать файлы");
    RoundedButton sendToProssesing = new RoundedButton("Подать на обработку");
    RoundedButton filterFile = new RoundedButton("Отбор файлов без кадастрового номера");

    File[] files;
    FileNameExtensionFilter formatFilter = new FileNameExtensionFilter(
            "csv", "csv");

    WebDriver driver;




    public File[] upload(){
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setMultiSelectionEnabled(true);
        // Определение режима - только файлы
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            JOptionPane.showMessageDialog(null,
                    "Файл(ы) импортированы.");
            l.setText("");
            for (File file : files) {
                l.setText(l.getText() +"   "+ file.getName());
            }
            return files;
        } else {
            l.setText("<html><h2>Вы отменили операцию.</html></h2>");
        }
        return null;
    }


    public void process(File[] files) {
        if (files == null){
            JOptionPane.showMessageDialog(null,
                    "Список не загружен! Нажмите кнопку 'Выбрать файлы'.");
        }
        else {
            WebDriver driver = chooseBrowser();
            for (File file : files) {
                ProcessThread processingEvent = new ProcessThread(new CSV_IO(file.getAbsolutePath()), driver);
                processingEvent.start();
            }

        }
    }
    // Метод для выбора браузера
    private WebDriver chooseBrowser() {
        String[] browsers = {"Chrome", "Firefox", "Edge"};
        // Модальное окно для выбора браузера
        String selectedBrowser = (String) JOptionPane.showInputDialog(
                this,
                "Выберите браузер для обработки:",
                "Выбор браузера",
                JOptionPane.QUESTION_MESSAGE,
                null,
                browsers,
                browsers[0]
        );
        if (selectedBrowser.equalsIgnoreCase("edge")){
            return this.driver = new EdgeDriver(new EdgeOptions());
        }
        else if (selectedBrowser.equalsIgnoreCase("firefox")){
            return this.driver = new FirefoxDriver(new FirefoxOptions());
        }
        return this.driver = new ChromeDriver(new ChromeOptions());
    }

    //подаем на вход массив файлов File [] из папки обработанных функцией process() файлов и возвращаем обработанный массив файлов пользователю в другую папку
    public void filter() {
        JFileChooser zipChooser = new JFileChooser();
        zipChooser.setDialogTitle("Выберите zip-архив");
        zipChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        zipChooser.setMultiSelectionEnabled(false);
        int zipResult = zipChooser.showOpenDialog(null);
        if (zipResult == JFileChooser.APPROVE_OPTION) {
            File selectedZipFile = zipChooser.getSelectedFile();
            if (selectedZipFile == null || !selectedZipFile.exists() || !selectedZipFile.isFile()) {
                JOptionPane.showMessageDialog(null, "Неверный путь к zip-архиву.");
                return;
            }
            if (files[0] != null) {
                // Запускаем поток для обработки выбранного файла С КАДАСТРАМИ и архива
                FilterThread filteringEvent = new FilterThread(files[0], selectedZipFile.getAbsolutePath());
                filteringEvent.start();
            }
            else {
                JOptionPane.showMessageDialog(null, "Файл .csv с кадастровыми номерами не выбран!");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Выберите zip-архив.");
            return;
        }
    }



    public WindowApp() {
        super("Работа с выписками");

        //функционал написания письма в техподдержку
        userGuideLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String labelText = userGuideLink.getText();
                if (labelText.contains("dvdlera@gmail.com")) {
                    try {
                        Desktop.getDesktop().mail(new URI("mailto:dvdlera@gmail.com"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            //курсор рука на ссылке
            @Override
            public void mouseEntered(MouseEvent e) {
                userGuideLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            //возвращаем дефолтный курсор
            @Override
            public void mouseExited(MouseEvent e) {
                userGuideLink.setCursor(Cursor.getDefaultCursor());
            }
        });

        //Добавляем поток загрузки файла (если активна кнопка uploadile)

        uploadFile.addActionListener(_ -> files = upload());

        //Добавляем поток сохранения файла (если активна кнопка getFile)

        sendToProssesing.addActionListener(_ -> process(files));


        //обработанные файлы достаем из папки dstFiles подаем в следующую функцию

        filterFile.addActionListener(_ -> filter());

        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(formatFilter);

        //Добавляем стилизацию на кнопку добавления файлов
        uploadFile.setPreferredSize(new Dimension(350, 50));
        uploadFile.setBgColor(dark_green);
        uploadFile.setBorderColor(dark_dark_green);
        uploadFile.setTextColor(Color.WHITE);
        uploadFile.setArcWidth(20);
        uploadFile.setArcHeight(20);
        uploadFile.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Добавляем стилизацию на кнопку обработки файлов
        sendToProssesing.setPreferredSize(new Dimension(380, 50));
        sendToProssesing.setBgColor(dark_blue);
        sendToProssesing.setBorderColor(dark_dark_blue);
        sendToProssesing.setTextColor(Color.WHITE);
        sendToProssesing.setArcWidth(20);
        sendToProssesing.setArcHeight(20);
        sendToProssesing.setCursor(new Cursor(Cursor.HAND_CURSOR));


        //Добавила стилизацию на кнопку
        filterFile.setPreferredSize(new Dimension(650, 60));
        filterFile.setBgColor(purple);
        filterFile.setBorderColor(dark_purple);
        filterFile.setTextColor(Color.WHITE);
        filterFile.setArcWidth(20);
        filterFile.setArcHeight(20);
        filterFile.setCursor(new Cursor(Cursor.HAND_CURSOR));




        //Размещение кнопок в интерфейсе
        panel.setLayout(new FlowLayout());

        //создала нижнюю панель для последних двух блоков с инструкцией и ссылкой
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        panel.add(l);
        panel.add(uploadFile);
        panel.add(sendToProssesing);
        panel.add(filterFile);

        bottomPanel.add(userGuide);
        bottomPanel.add(Box.createVerticalStrut(10)); // Отступ между блоками
        bottomPanel.add(userGuideLink);


        //Вывод окна на экран
        panel.add(bottomPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 150, 1150, 800);
        add(panel);
        setVisible(true);
    }


    public static void main(String[] args) {


        UIManager.put(
                "FileChooser.saveButtonText", "Сохранить");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");
        new WindowApp();


    }
}