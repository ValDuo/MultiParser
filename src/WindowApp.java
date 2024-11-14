import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.*;

public class WindowApp extends JFrame{
    JLabel l = new JLabel("Файлы не выбраны");
    JFileChooser fileChooser = new JFileChooser();
    UploadThread uploadEvent = new UploadThread();
    ProcessThread processingEvent = new ProcessThread();


    JButton uploadFile = new JButton("Выбрать файлы");
    JButton sendToProssesing = new JButton("Подать на обработку");
    JButton getFile = new JButton("Скачать готовый файл");


    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv", "csv");

    public static void upload(UploadThread uploadEvent, JLabel l, JFileChooser fileChooser){
        uploadEvent.start();
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setMultiSelectionEnabled(true);
        // Определение режима - только каталог
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        // Если директория выбрана, покажем ее в сообщении
        if (result == JFileChooser.APPROVE_OPTION) {
            //массив выбранных файлов
            File files[] = fileChooser.getSelectedFiles();

            JOptionPane.showMessageDialog(null,
                    "Файл(ы) импортированы.");
            l.setText("");

            for (File file : files) {
                l.setText(l.getText() + "\n" + file.getName());
            }
        } else {
            l.setText("Вы отменили операцию.");
            uploadEvent.disable();
        }
    }

    public static void process(ProcessThread processingEvent, JLabel l, JFileChooser fileChooser) {
        processingEvent.start();
        fileChooser.setDialogTitle("Сохранение файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        // Если файл выбран, то представим его в сообщении
        if (result == JFileChooser.APPROVE_OPTION ) {
            File files[] = fileChooser.getSelectedFiles();
            JOptionPane.showMessageDialog(null,
                    "Файл(ы) сохранены.");
            l.setText("");

            for (File file : files) {
                l.setText(l.getText() + "\n" + file.getName());
            }
        }
        else {
            l.setText("Вы отменили операцию сохранения.");
            processingEvent.disable();
        }
    }


    public WindowApp() {
        super("Работа с выписками");

        //Добавляем поток загрузки файла (если активна кнопка uploadFile)

        uploadFile.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                upload(uploadEvent, l, fileChooser);
             }
        });

        //Добавляем поток сохранения файла (если активна кнопка getFile)


        sendToProssesing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process(processingEvent, l, fileChooser);
            }
        });
        //save(processingEvent, l, fileChooser, uploadFile);

        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(filter);



        //Размещение кнопок в интерфейсе
        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new FlowLayout());

        panel.add(l);
        panel.add(uploadFile);
        panel.add(sendToProssesing);
        panel.add(getFile);

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