package resources;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class BigCSVCutter extends CSV_IO {

    public BigCSVCutter(String pathname) {
        super(pathname);
    }
    protected boolean extractInFolder(File folder, int linesAmount){
        if (linesAmount == 0){
            return true;
        }
        else {
            ArrayList<String> lines = this.readLikeCSV();
            int countLine = 0;
            int countFile = 1;
            stop:
            for (int i = 0; i < lines.size(); i += linesAmount) {
                CSV_IO curFile = new CSV_IO(folder.getAbsolutePath() + "/" + countFile + ".csv");
                try {
                    curFile.createNewFile();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                            "Ошибка при нарезке файла " + e + ".");
                    return false;
                }
                for (int j = i; j < i + linesAmount; j++) {
                    if (j >= lines.size()) break stop;
                    curFile.write(lines.get(j) + "\n");
                }
                countFile += 1;
            }
            return true;
        }
    }

    protected File createFolder() throws IOException {
        String path = this.csvFile.getAbsolutePath();
        if (path.endsWith(".csv")) {
            path = path.substring(0, path.lastIndexOf(".csv"));
        }
        path += "_cut";
        File folder = new File(path);
        if (folder.exists()) {
            JOptionPane.showMessageDialog(null, "Такой файл уже прошел этап нарезки, смотрите результат в папке "+ folder.getAbsolutePath());
            return folder;
        }

        boolean created = folder.mkdir();
        if (created) {
            return folder;
        } else {
            throw new IOException("Не удалось создать папку: " + path);
        }
    }

    public boolean cut() {
        File folder;
        int lineAmount;
        try {
            // Пытаемся создать папку
            folder = createFolder();
        } catch (IOException e) {
            // Показываем сообщение об ошибке и возвращаем false
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }

        String input = JOptionPane.showInputDialog(null, "Введите количество строк на разбиение:",
                "Количество строк", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Отмена операции.");
            return false;
        }

        try {
            lineAmount = Integer.parseInt(input);
            if (lineAmount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введите корректное положительное число.");
            return false;
        }

        // Пытаемся выполнить извлечение в папку
        boolean result = extractInFolder(folder, lineAmount);

        return result;
    }
    //TODO: нарезка файлов по формату подаваемого файла


}
