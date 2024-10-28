import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WindowApp extends JFrame {
    JButton uploadFile = new JButton("Импортировать файл");
    JButton sendToProssesing = new JButton("Подать на обработку");
    JButton getFile = new JButton("Скачать готовый файл");

    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Excel", "xlsx");



    public WindowApp() {
        super("Работа с выписками");

        //Добавляем функцию загрузки файла (кнопка uploadFile)
        uploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор файла");
                // Определение режима - только каталог
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(WindowApp.this);
                // Если директория выбрана, покажем ее в сообщении
                if (result == JFileChooser.APPROVE_OPTION )
                    JOptionPane.showMessageDialog(WindowApp.this,
                            "Файл '" + fileChooser.getSelectedFile() + "' импортирован.");
            }
        });

        //Добавляем функцию сохранения файла (кнопка getFile)
        getFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Сохранение файла");
                // Определение режима - только файл
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(WindowApp.this);
                // Если файл выбран, то представим его в сообщении
                if (result == JFileChooser.APPROVE_OPTION )
                    JOptionPane.showMessageDialog(WindowApp.this,
                            "Файл '" + fileChooser.getSelectedFile() +
                                    " ) сохранен");
            }
        });

        //Добавляем фильтр форматов загружаемого файла (only Excel)
        fileChooser.setFileFilter(filter);

        //Размещение кнопок в интерфейсе
        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new FlowLayout());

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