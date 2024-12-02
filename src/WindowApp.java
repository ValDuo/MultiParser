
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.*;


public class WindowApp extends JFrame{
    Color dark_green = new Color(48, 133, 66);
    Color dark_dark_green = new Color(23, 83, 36);
    Color dark_blue = new Color(38, 99, 191);
    Color purple = new Color(141,68,173);
    JLabel l = new JLabel("Файлы не выбраны");
    JFileChooser fileChooser = new JFileChooser();

    JPanel panel = new JPanel(new FlowLayout());

    RoundedButton uploadFile = new RoundedButton("Выбрать файлы");
    RoundedButton sendToProssesing = new RoundedButton("Подать на обработку");
    RoundedButton filterFile = new RoundedButton("Просеять файл");

    File[] files;
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv", "csv");

    public File[] upload(){
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setMultiSelectionEnabled(true);
        // Определение режима - только каталог
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        // Если директория выбрана, покажем ее в сообщении
        if (result == JFileChooser.APPROVE_OPTION) {
            //массив выбранных файлов
            File[] files = fileChooser.getSelectedFiles();
            JOptionPane.showMessageDialog(null,
                    "Файл(ы) импортированы.");
            l.setText("");

            for (File file : files) {
                l.setText(l.getText() +"   "+ file.getName());
            }
            return files;
        } else {
            l.setText("Вы отменили операцию.");
        }
        return null;
    }


    public void process(File[] files) {
        if (files == null){
            JOptionPane.showMessageDialog(null,
                    "Список не загружен! Нажмите кнопку 'Выбрать файлы'.");
        }
        else {
            for (File file : files) {
                ProcessThread processingEvent = new ProcessThread(new CSV_IO(file.getAbsolutePath()));
                processingEvent.start();
            }
        }
    }
    public void filter(File[] files) {
        if (files == null){
            JOptionPane.showMessageDialog(null,
                    "Ошибка фильтрации! Повторите этап обработки.");
        }
        else {
            for (File file : files) {
                ProcessThread processingEvent = new ProcessThread(new CSV_IO(file.getAbsolutePath()));
                processingEvent.start();
            }
        }
    }



    public WindowApp() {
        super("Работа с выписками");

        //Добавляем поток загрузки файла (если активна кнопка uploadFile)

        uploadFile.addActionListener(e -> files = upload());

 //       Добавляем поток сохранения файла (если активна кнопка getFile)


        sendToProssesing.addActionListener(e -> process(files));

        filterFile.addActionListener(e -> filter(files));


        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(filter);

        //Добавляем стилизацию на кнопку добавления файлов
        uploadFile.setPreferredSize(new Dimension(200, 40));
        uploadFile.setBgColor(dark_green);
        uploadFile.setBorder(BorderFactory.createLineBorder(dark_dark_green, 2));
        uploadFile.setTextColor(Color.WHITE);
        uploadFile.setArcWidth(20);
        uploadFile.setArcHeight(20);
        uploadFile.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Добавляем стилизацию на кнопку обработки файлов
        sendToProssesing.setPreferredSize(new Dimension(270, 40));
        sendToProssesing.setBgColor(dark_blue);
        sendToProssesing.setBorder(BorderFactory.createLineBorder(dark_dark_green, 2));
        sendToProssesing.setTextColor(Color.WHITE);
        sendToProssesing.setArcWidth(20);
        sendToProssesing.setArcHeight(20);
        sendToProssesing.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Добавила стилизацию на кнопку
        filterFile.setPreferredSize(new Dimension(450, 50));
        filterFile.setBgColor(purple);
        filterFile.setBorder(BorderFactory.createLineBorder(dark_dark_green, 2));
        filterFile.setTextColor(Color.WHITE);
        filterFile.setArcWidth(30);
        filterFile.setArcHeight(30);
        filterFile.setCursor(new Cursor(Cursor.HAND_CURSOR));



        //Размещение кнопок в интерфейсе
        panel.setLayout(new FlowLayout());


        panel.add(l);
        panel.add(uploadFile);
        panel.add(sendToProssesing);
        panel.add(filterFile);

        //Вывод окна на экран
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(750, 350, 700, 400);
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