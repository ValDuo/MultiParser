import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.filechooser.*;

public class WindowApp extends JFrame{
    JLabel l = new JLabel("Файлы не выбраны");
    JFileChooser fileChooser = new JFileChooser();
    ProcessThread processingEvent = new ProcessThread();
    JPanel panel = new JPanel(new FlowLayout());

    JButton uploadFile = new JButton("Выбрать файлы");
    JButton sendToProssesing = new JButton("Подать на обработку");
    JButton getFile = new JButton("Скачать готовый файл");


    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "csv", "csv");

    public void upload(JLabel l, JFileChooser fileChooser){
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
                l.setText(l.getText() +"   "+ file.getName());
            }
        } else {
            l.setText("Вы отменили операцию.");
        }
    }


    public File[] process(ProcessThread processingEvent, File[] mass, int n) {
        processingEvent.start();
        File[] files = new File[n];

        int result = fileChooser.showSaveDialog(null);
        // Если файл выбран, то представим его в сообщении
        if (result == JFileChooser.APPROVE_OPTION ) {
            for(File file : mass){

            }
        }
        else {
            l.setText("Вы отменили операцию сохранения.");
            processingEvent.disable();
        }
        return files;
    }

    public void save(File file) {
        fileChooser.setDialogTitle("Сохранение файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        // Если файл выбран, то представим его в сообщении
        if (result == JFileChooser.APPROVE_OPTION ) {
            System.out.println("Сохранить как: " + file.getAbsolutePath());
            JOptionPane.showMessageDialog(null,
                    "Файл(ы) сохранены.");
            l.setText("");
        }
        else {
            l.setText("Вы отменили операцию сохранения.");
        }
    }


    public WindowApp() {
        super("Работа с выписками");

        //Добавляем поток загрузки файла (если активна кнопка uploadFile)

        uploadFile.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                upload(l, fileChooser);
             }
        });

        //Добавляем поток сохранения файла (если активна кнопка getFile)


//        sendToProssesing.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                process(processingEvent, mass, size);
//            }
//        });

        getFile.addActionListener(new ActionListener() {
            File f = new File("////Nasnet//общий обмен//ИТ//Калмыков АН//Дебеторка//08.11.24 2//csv50_24-10-30-10-22-00_179.csv");
            @Override
            public void actionPerformed(ActionEvent e) {
                save(f);
            }
        });

        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(filter);



        //Размещение кнопок в интерфейсе

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