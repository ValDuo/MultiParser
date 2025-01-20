package ru.sfedu.dubina.api;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import ru.sfedu.dubina.models.*;
import ru.sfedu.dubina.utils.Constants;

public class DataProviderCSV {
    private Logger logger = Logger.getLogger(DataProviderCSV.class);
    public DataProviderCSV() {
        File file = new File(Constants.CUTTER_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("id,countLine,countFile,date,folder,created\n");
                }
            } catch (IOException e) {
                logger.error("Ошибка при создании файла: " + e.getMessage());
            }
        }
    }

    // CREATE
    public boolean createBigCSVCutter(BigCSVCutter cutter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CUTTER_PATH, true))) {
            writer.write(String.format("%s,%d,%d,%s,%s,%b\n",
                    cutter.getId(),
                    cutter.getCountLine(),
                    cutter.getCountFile(),
                    cutter.getDate(),
                    cutter.getFolder(),
                    cutter.getCreated()));
            return true;
        } catch (IOException e) {
            logger.error("Ошибка при добавлении записи: " + e.getMessage());
        }
        return false;
    }

    // READ
    public BigCSVCutter getBigCSVCutterById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CUTTER_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id.toString())) {
                    return new BigCSVCutter(
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            parts[3],
                            parts[4],
                            Boolean.parseBoolean(parts[5])
                    );
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении записи: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updateBigCSVCutter(UUID id, BigCSVCutter updatedCutter) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CUTTER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean updated = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 6 && parts[0].equals(id.toString())) {
                    lines.set(i, String.format("%s,%d,%d,%s,%s,%b",
                            id,
                            updatedCutter.getCountLine(),
                            updatedCutter.getCountFile(),
                            updatedCutter.getDate(),
                            updatedCutter.getFolder(),
                            updatedCutter.getCreated()));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CUTTER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при обновлении записи: " + e.getMessage());
        }
        return false;
    }




    // DELETE
    public boolean deleteBigCSVCutter(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CUTTER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean deleted = false;

            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.startsWith(id.toString() + ",")) {
                    iterator.remove();
                    deleted = true;
                    break;
                }
            }

            if (deleted) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.CUTTER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при удалении записи: " + e.getMessage());
        }
        return false;
    }

    //круды с csv-reader

    public boolean createCSVReader(CSVReader csvReader) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.READER_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%d\n",
                    csvReader.getId(),
                    csvReader.getPathName(),
                    csvReader.getCsvFile(),
                    csvReader.getWords()));
            return true;
        } catch (IOException e) {
            logger.error("Ошибка при добавлении записи CSVReader: " + e.getMessage());
        }
        return false;
    }

    public CSVReader getCSVReaderById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.READER_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    return new CSVReader(
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3])
                    );
                }
            }
        } catch (IOException | NumberFormatException e) {
            logger.error("Ошибка при чтении записи CSVReader: " + e.getMessage());
        }
        return null;
    }

    public boolean updateCSVReader(UUID id, CSVReader updatedCSVReader) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.READER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean updated = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    lines.set(i, String.format("%s,%s,%s,%d",
                            id,
                            updatedCSVReader.getPathName(),
                            updatedCSVReader.getCsvFile(),
                            updatedCSVReader.getWords()));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.READER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при обновлении записи CSVReader: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCSVReader(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.READER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean deleted = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    lines.remove(i);
                    deleted = true;
                    break;
                }
            }

            if (deleted) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.READER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при удалении записи CSVReader: " + e.getMessage());
        }
        return false;
    }
    //круды для incomingEmails

    public boolean createIncomingEmail(IncomingEmails email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMAILS_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s\n",
                    email.getId(),
                    email.getEmailAddress(),
                    email.getEmailSender(),
                    email.getEmailReceiver()));
            return true;
        } catch (IOException e) {
            logger.error("Ошибка при добавлении записи IncomingEmails: {}" + e.getMessage());
        }
        return false;
    }

    public IncomingEmails getIncomingEmailById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.EMAILS_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    return new IncomingEmails(parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении записи IncomingEmails: {}"+ e.getMessage());
        }
        return null;
    }

    public boolean updateIncomingEmail(UUID id, IncomingEmails updatedEmail) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.EMAILS_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean updated = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    lines.set(i, String.format("%s,%s,%s,%s",
                            id,
                            updatedEmail.getEmailAddress(),
                            updatedEmail.getEmailSender(),
                            updatedEmail.getEmailReceiver()));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMAILS_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при обновлении записи IncomingEmails: {}"+e.getMessage());
        }
        return false;
    }

    public boolean deleteIncomingEmail(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.EMAILS_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean deleted = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 4 && parts[0].equals(id.toString())) {
                    lines.remove(i);
                    deleted = true;
                    break;
                }
            }

            if (deleted) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.EMAILS_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при удалении записи IncomingEmails: {}"+ e.getMessage());
        }
        return false;
    }

    //круды seleniumParser

    public boolean createSeleniumParser(SeleniumParser parser) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PARCER_PATH, true))) {
            writer.write(String.format("%s,%d,%s\n",
                    parser.getId(),
                    parser.getDriver(),
                    parser.getSrcDstFiles()));
            return true;
        } catch (IOException e) {
            logger.error("Ошибка при создании записи: " + e.getMessage());
        }
        return false;
    }

    public SeleniumParser getSeleniumParserById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.PARCER_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id.toString())) {
                    return new SeleniumParser(
                            Integer.parseInt(parts[1]),
                            parts[2]
                    );
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении записи: " + e.getMessage());
        }
        return null;
    }

    public boolean updateSeleniumParser(UUID id, SeleniumParser updatedParser) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.PARCER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean updated = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts.length == 3 && parts[0].equals(id.toString())) {
                    lines.set(i, String.format("%s,%d,%s",
                            id,
                            updatedParser.getDriver(),
                            updatedParser.getSrcDstFiles()));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PARCER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при обновлении записи: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteSeleniumParser(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.PARCER_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean deleted = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(id.toString())) {
                    lines.remove(i);
                    deleted = true;
                    break;
                }
            }

            if (deleted) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PARCER_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при удалении записи: " + e.getMessage());
        }
        return false;
    }

    //круды для WithKadastrs
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean createWithKadastrList(WithKadastrList record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.KADASTRS_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s\n",
                    record.getId(),
                    record.getSource(),
                    record.getDestination(),
                    dateFormat.format(record.getCreatedTime())));
            return true;
        } catch (IOException e) {
            logger.error("Ошибка при создании записи: {}"+ e.getMessage());
        }
        return false;
    }

    public WithKadastrList getWithKadastrListById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.KADASTRS_PATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id.toString())) {
                    return new WithKadastrList(parts[1], parts[2], dateFormat.parse(parts[3]));
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при чтении записи: {}"+ e.getMessage());
        }
        return null;
    }

    public boolean updateWithKadastrList(UUID id, WithKadastrList updatedRecord) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.KADASTRS_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean updated = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(id.toString())) {
                    lines.set(i, String.format("%s,%s,%s,%s",
                            id,
                            updatedRecord.getSource(),
                            updatedRecord.getDestination(),
                            dateFormat.format(updatedRecord.getCreatedTime())));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.KADASTRS_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при обновлении записи: {}"+ e.getMessage());
        }
        return false;
    }

    public boolean deleteWithKadastrList(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.KADASTRS_PATH))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            boolean deleted = false;

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(id.toString())) {
                    lines.remove(i);
                    deleted = true;
                    break;
                }
            }

            if (deleted) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.KADASTRS_PATH))) {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                }
                return true;
            }
        } catch (IOException e) {
            logger.error("Ошибка при удалении записи: {}"+ e.getMessage());
        }
        return false;
    }

}
