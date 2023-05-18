package contacts.views;

import contacts.controllers.Controller;
import contacts.model.Data;

import java.util.Scanner;

public class ViewUser {
    private Controller controller;

    public ViewUser(Controller controller) {
        this.controller = controller;
    }

    public void run() {
            System.out.println("Введите следующие данные в произвольном порядке, разделенные пробелом:\n" +
                    "Фамилия (одно слово), Имя (одно слово), Отчество (одно слово), \n" +
                    "дата рождения (в формате dd.mm.yyyy), номер телефона (только цифры, без пробелов), пол ('f' или 'm')");
            String dataString = prompt();
            String[] dataArr = dataString.trim().split(" ");
            try {
                dataCount(dataArr); // Проверка введенных данных на количество
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Data data = controller.check(dataArr);
//            System.out.println(data); //*
            controller.writeDataToFile(data);
    }

    // Проверка введенных данных на количество
    private void dataCount(String[] dataStr) throws Exception {
        if (dataStr.length < 6) {
             throw new RuntimeException("Код ошибки -1. Введено меньше данных, чем нужно.");
        } else if (dataStr.length > 6) {
              throw new RuntimeException("Код ошибки -2. Введено больше данных, чем нужно.");
        }
    }

    private String prompt() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
