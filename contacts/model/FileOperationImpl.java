package contacts.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperationImpl implements FileOperation{
    // Запись в файл введенных данных
    @Override
    public void saveData(Data data) {
        String fileName = String.join("\\", String.join("\\",
                new File("").getAbsolutePath(), "contacts\\files"),
                data.getLastName().concat(".txt"));
        try {
            createDataFile(fileName);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(data.toString());
            fileWriter.append('\n');
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("Данные успешно внесены в файл " + fileName);
    }

    // Создание файла, если он не существует
    @Override
    public void createDataFile(String fileName) throws IOException {
        File file = new File(fileName);
        boolean created = file.createNewFile();
    }
}
