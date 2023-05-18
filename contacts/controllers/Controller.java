package contacts.controllers;

import contacts.model.Data;
import contacts.model.DataOperation;

public class Controller {
    private final DataOperation dataOperation;

    public Controller(DataOperation dataOperation) {
        this.dataOperation = dataOperation;
    }

    // Проверка введенных данных на соответствие требуемому формату
    public Data check(String[] strData) {
        return dataOperation.checkData(strData);
    }

    // Запись данных в файл
    public void writeDataToFile(Data data) {
        dataOperation.writeData(data);
    }
}
