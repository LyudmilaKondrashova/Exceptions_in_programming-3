package contacts.model;

import java.io.IOException;

public interface FileOperation {
    // Запись в файл введенных данных
    void saveData(Data data);

    // Создание файла, если он не существует
    void createDataFile(String fileName) throws IOException;
}
