package contacts.model;

import java.util.ArrayList;

public interface DataOperation {
    // Проверка введенных данных на соответствие требуемому формату
    Data checkData(String[] strData);

    // Запись данных в файл
    void writeData(Data data);
}
