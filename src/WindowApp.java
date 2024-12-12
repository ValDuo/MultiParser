
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


public class WindowApp extends JFrame{

    private static WindowApp instance;

    Color dark_green = new Color(48, 133, 66);
    Color dark_dark_green = new Color(23, 83, 36);
    Color dark_blue = new Color(38, 99, 191);
    Color dark_dark_blue = new Color(5, 48, 113);
    Color purple = new Color(141,68,173);
    Color dark_purple = new Color(88, 17, 120);
    JLabel l = new JLabel();
    JFileChooser fileChooser = new JFileChooser();
    JProgressBar progressBar = new JProgressBar();
    JPanel panel = new JPanel(new FlowLayout());
    JLabel userGuide = new JLabel(UserGuideHTML.HTML_CONTENT);
    JLabel userGuideLink = new JLabel(UserGuideLinkHTML.HTML_CONTENT);

    RoundedButton uploadFile = new RoundedButton("Выбрать файлы");
    RoundedButton sendToProssesing = new RoundedButton("Подать на обработку");
    RoundedButton filterFile = new RoundedButton("Сравнить отправленные и полученные файлы");

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
            for (File file : files) {
                JOptionPane.showMessageDialog(null,
                        "Файл " + file.getName() + " импортирован.");
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



    //подаем на вход массив файлов File [] из папки обработанных функцией process() файлов и возвращаем обработанный массив файлов пользователю в другую папку
    public void filter() {
        JFileChooser zipChooser = new JFileChooser();
        zipChooser.setDialogTitle("Выберите папку с zip-архивами");
        zipChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        zipChooser.setMultiSelectionEnabled(false);
        int zipResult = zipChooser.showOpenDialog(null);
        if (zipResult == JFileChooser.APPROVE_OPTION) {
            File selectedZipFiles = zipChooser.getSelectedFile();
            if (selectedZipFiles == null) {
                JOptionPane.showMessageDialog(null, "Неверный путь к папке!");
                return;
            }
            if (files != null) {
                // Запускаем поток для обработки выбранного файла С КАДАСТРАМИ и архива
                FilterThread filteringEvent = new FilterThread(files, selectedZipFiles.getAbsolutePath());
                filteringEvent.start();
            }
            else {
                JOptionPane.showMessageDialog(null, "Файл .csv с кадастровыми номерами не выбран!");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Выберите папку с zip-архивами.");
            return;
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

        //прогресс-бар
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(650, 30));

        panel.add(l);
        panel.add(uploadFile);
        panel.add(sendToProssesing);
        panel.add(filterFile);
        panel.add(progressBar);

        bottomPanel.add(userGuide);
        bottomPanel.add(Box.createVerticalStrut(10)); // Отступ между блоками
        bottomPanel.add(userGuideLink);
        panel.add(bottomPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 150, 1150, 800);
        add(panel);
        setVisible(true);

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
    }


    //для реализации паттерна синглтон
    private static void setInstance(){
        if (WindowApp.instance == null)
            WindowApp.instance = new WindowApp();

    }

    public static WindowApp getInstance() {
        setInstance();
        return WindowApp.instance;
    }

}