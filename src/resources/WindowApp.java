package resources;

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
    Color orange = new Color(229,125,38);
    Color dark_orange = new Color(162,107,1);
    JLabel l = new JLabel();
    JFileChooser fileChooser = new JFileChooser();
    JProgressBar progressBar = new JProgressBar();
    JPanel panel = new JPanel(new FlowLayout());
    JLabel userGuide = new JLabel(UserGuideHTML.HTML_CONTENT);
    JLabel userGuideLink = new JLabel(UserGuideLinkHTML.HTML_CONTENT);
    JPanel menuPanel = new JPanel();
    JPanel contentPanel = new JPanel();



    RoundedButton uploadFile = new RoundedButton("Выбрать файлы");
    RoundedButton sendToProssesing = new RoundedButton("Подать на обработку");
    RoundedButton filterFile = new RoundedButton("Получить кадастровые номера и пришедшие выписки");
    RoundedButton cutFile = new RoundedButton("Нарезать файл по 50 строк");

    File[] files;
    FileNameExtensionFilter formatFilter = new FileNameExtensionFilter(
            "csv", "csv");

    WebDriver driver;


    public void setJFileChooserButtonText(){
        UIManager.put("FileChooser.openButtonText", "Добавить файл");
        UIManager.put("FileChooser.saveButtonText", "Сохранить файл");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.approveButtonText", "Выбрать");
        UIManager.put("FileChooser.saveAsButtonText", "Сохранить как...");
        UIManager.put("FileChooser.newFolderToolTipText", "Новая папка");
        UIManager.put("FileChooser.fileAttrPatternText", "Показать скрытые файлы");
    }

    public File[] upload(){
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setMultiSelectionEnabled(true);
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

    public void cut() {
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(null,
                    "Список не загружен! Нажмите кнопку 'Выбрать файлы'.");
            return;
        }

        for (File file : files) {
            BigCSVCutter cutter = new BigCSVCutter(file.getAbsolutePath());

            boolean result = cutter.cut();
            if (result) {
                try {
                    JOptionPane.showMessageDialog(null,
                            "Файл " + file.getName() + " успешно нарезан и сохранен в папке "+ file.getAbsolutePath());
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null,
                            "Ошибка при нарезке файла " + file.getName() + ".");
                }
            }
        }

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
                JOptionPane.showMessageDialog(null, "Неверный путь к папке!"    );
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

        setJFileChooserButtonText();

        //функционал написания письма в техподдержку
        userGuideLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String labelText = userGuideLink.getText();
                if (labelText.contains("fondkr.support@fondkrro.ru")) {
                    try {
                        Desktop.getDesktop().mail(new URI("mailto:fondkr.support@fondkrro.ru"));
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
        uploadFile.addActionListener(X -> files = upload());

        //Добавляем поток сохранения файла (если активна кнопка getFile)
        sendToProssesing.addActionListener(X -> process(files));

        //обработанные файлы достаем из папки dstFiles подаем в следующую функцию
        filterFile.addActionListener(X -> filter());

        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(formatFilter);

        //Добавляем функционал на кнопку нарезки файлов по 50
        cutFile.addActionListener(e -> cut());


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


        //Добавиола стилей на кнопку нарезки

        cutFile.setPreferredSize(new Dimension(350, 50));
        cutFile.setBgColor(orange);
        cutFile.setBorderColor(dark_orange);
        cutFile.setTextColor(Color.WHITE);
        cutFile.setArcWidth(20);
        cutFile.setArcHeight(20);
        cutFile.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Размещение кнопок в интерфейсе
        contentPanel.setLayout(new FlowLayout());

        //создала нижнюю панель для последних двух блоков с инструкцией и ссылкой
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        //прогресс-бар
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(650, 30));
        progressBar.setVisible(false);

        contentPanel.add(l);

        bottomPanel.add(userGuide);
        bottomPanel.add(Box.createVerticalStrut(10)); // Отступ между блоками
        bottomPanel.add(userGuideLink);
        contentPanel.add(bottomPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 150, 1150, 800);

        
        menuPanel.setLayout(new GridLayout(5, 1, 5, 5)); //меню
        menuPanel.setBackground(dark_dark_green);
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));


        addMenuItem("Главная", new JLabel("Главная страница"), () -> {
            contentPanel.removeAll();
            contentPanel.add(l);
            contentPanel.add(bottomPanel);
            contentPanel.revalidate(); // Обновляем интерфейс
            contentPanel.repaint();
        });
        addMenuItem("Выбор файла", new JLabel("Выберите файл с компьютера для дальнейшей работы с ним:"), () -> {
            contentPanel.removeAll();
            contentPanel.add(uploadFile);
            contentPanel.revalidate(); // Обновляем интерфейс
            contentPanel.repaint();
        });
        addMenuItem("Нарезка по 50", new JLabel("Отправьте выбранный файл на разбиение по 50 строк"), () -> {
            contentPanel.removeAll();
            contentPanel.add(cutFile);
            contentPanel.revalidate(); // Обновляем интерфейс
            contentPanel.repaint();
        });
        addMenuItem("Получить кадастры", new JLabel("Отправьте выбранный файл на разбиение по 50 строк"), () -> {
            contentPanel.removeAll();
            contentPanel.add(sendToProssesing);
            contentPanel.revalidate(); // Обновляем интерфейс
            contentPanel.repaint();
        });
        addMenuItem("Сравнить кол-во", new JLabel("пустой"), () -> {
            contentPanel.removeAll();
            contentPanel.add(filterFile);
            contentPanel.add(new JLabel("Отправьте выбранный файл на разбиение по 50 строк"));
            contentPanel.revalidate(); // Обновляем интерфейс
            contentPanel.repaint();
        });

        // Добавление панелей в главное окно
        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addMenuItem(String name, JComponent content, Runnable action) {
        JLabel menuItem = new JLabel(name, SwingConstants.CENTER);
        menuItem.setOpaque(true);
        menuItem.setBackground(dark_green);
        menuItem.setForeground(Color.WHITE);
        menuItem.setFont(new Font("Arial", Font.BOLD, 20));
        menuItem.setPreferredSize(new Dimension(200, 50));

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(dark_dark_green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(dark_green);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (action != null) {
                    action.run();
                }
                contentPanel.show();
            }
        });

        // Добавляем кнопку на панель меню
        menuPanel.add(menuItem);

        // Добавляем соответствующую панель контента
        contentPanel.add(content, name);
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
    public void setProgressBar(int step){
        this.progressBar.setValue(step);
    }

}