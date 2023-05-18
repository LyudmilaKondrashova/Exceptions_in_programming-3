package contacts.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataOperationImpl implements DataOperation {
    private FileOperation fileOperation;

    public DataOperationImpl(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }

    // Проверка введенных данных на соответствие требуемому формату
    @Override
    public Data checkData(String[] strData) {
        ArrayList<String> FIO = new ArrayList<>(); // ФИО во введенных данных
        int countFIO = 0; // Счетчик для ФИО во введенных данных
        int countDate = 0; // Счетчик количества дат рождения во введенных данных
        int countPhone = 0; // Счетчик количества телефонных номеров во введенных данных
        int countGender = 0; // Счетчик количества идентификаторов пола во введенных данных
        int phoneNumb = 0; // Введенный телефонный номер
        String dateBirth = ""; // Введенная дата рождения
        char gend = '0';

        for (String item : strData) {
            item = item.trim();
            if (Character.isDigit(item.charAt(0))) { //Если первый символ является числом, то
                // проверяем - это дата рождения или телефон
                String[] strBirth = item.split("\\.");
                if (strBirth.length == 3) {
                    countDate++;
                    if (countDate > 1) {
                        throw new RuntimeException("Код ошибки -6. " + item + " - либо введена еще " +
                                "одна дата рождения, либо ошибка ввода номера телефона!");
                    }
                    for (String elem : strBirth) {
                        try {
                            int partBirth = Integer.parseInt(elem);
                            if (partBirth <= 0) {
                                throw new RuntimeException("Код ошибки -5. Дата рождения " +
                                        item + " введена не по формату!");
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Код ошибки -5. Дата рождения " +
                                    item + " введена не по формату!");
                        }
                    }
                    dateBirth = item;
                    dataBirthCheck(dateBirth); // Проверка даты рождения на валидность
                } else if (strBirth.length == 2 || strBirth.length > 3) {
                    throw new RuntimeException("Код ошибки -7. " + item + " - либо ошибка ввода " +
                            "даты рождения, либо ошибка ввода номера телефона!");
                } else {
                    try { // Проверяем, является ли элемент массива телефонным номером и,
                        // если такой элемент не один, выводим сообщение
                        int tempPhone = Integer.parseInt(item);
                        if (tempPhone >= 0) {
                            countPhone++;
                            if (countPhone == 1) {
                                phoneNumb = tempPhone;
                            } else {
                                throw new RuntimeException("Код ошибки -4. Введен более, чем один номер телефона!");
                            }
                        } else {
                            throw new RuntimeException("Код ошибки -3. Телефонный номер может содержать только цифры!");
                        }
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Код ошибки -8. " + item + " - формат введенных данных не " +
                                "подходит ни для даты рождения, ни для телефонного номера!");
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            } else if (item.substring(0, 1).equals("-")) { // Если элемент начинается с "-"
                throw new RuntimeException("Код ошибки -11. " + item + " - не подходит ни под один формат данных!");
            } else if (item.toLowerCase().equals("f") || item.toLowerCase().equals("m")) {
                if (countGender == 0) {
                    gend = item.charAt(0);
                    countGender++;
                } else {
                    throw new RuntimeException("Код ошибки -9. Пол введен несколько раз!");
                }
            } else {
                for (int i = 0; i < item.length(); i++) {
                    if (!item.substring(i, i + 1).matches("[a-z]") &&
                            !item.substring(i, i + 1).matches("[A-Z]") &&
                            !item.substring(i, i + 1).matches("[а-я]") &&
                            !item.substring(i, i + 1).matches("[А-Я]") &&
                            !item.substring(i, i + 1).matches("-")) {
                        throw new RuntimeException("Код ошибки -11. " + item + " - не подходит ни под один формат данных!");
                    }
                }
                if (countFIO <=2) {
                    FIO.add(item);
                    countFIO++;
                } else {
                    throw new RuntimeException("Код ошибки -10. Количество данных для ФИО введено больше, чем нужно!");
                }
            }
        }

        FIOMatches(FIO);
        Data data = new Data(FIO.get(0), FIO.get(1), FIO.get(2), dateBirth, phoneNumb, gend);
        return data;
    }

    // Запись данных в файл
    @Override
    public void writeData(Data data) {
        fileOperation.saveData(data);
    }

    // Распознавание ФИО
  //  @Override
    public void FIOMatches(ArrayList<String> FIO) {
        Scanner scan = new Scanner(System.in);
        System.out.println(FIO.get(0) + " - это фамилия? ('y' или 'n')");
        String dataString = scan.nextLine();
        if (dataString.toLowerCase().equals("y")) {
            System.out.println(FIO.get(1) + " - это имя? ('y' или 'n')");
            dataString = scan.nextLine();
            if (dataString.toLowerCase().equals("n")) {
                String temp = FIO.get(1);
                FIO.set(1, FIO.get(2));
                FIO.set(2, temp);
            } else if (!dataString.toLowerCase().equals("y")) {
                throw new RuntimeException("Код ошибки -12. Неверный формат ответа!");
            }
        } else if (dataString.toLowerCase().equals("n")) {
            System.out.println(FIO.get(0) + " - это имя? ('y' или 'n')");
            dataString = scan.nextLine();
            if (dataString.toLowerCase().equals("y")) {
                System.out.println(FIO.get(1) + " - это фамилия? ('y' или 'n')");
                dataString = scan.nextLine();
                if (dataString.toLowerCase().equals("y")) {
                    String temp = FIO.get(0);
                    FIO.set(0, FIO.get(1));
                    FIO.set(1, temp);
                } else if (dataString.toLowerCase().equals("n")) {
                    String temp1 = FIO.get(0);
                    String temp2 = FIO.get(1);
                    FIO.set(0, FIO.get(2));
                    FIO.set(1, temp1);
                    FIO.set(2, temp2);
                } else {
                    throw new RuntimeException("Код ошибки -12. Неверный формат ответа!");
                }
            } else if (dataString.toLowerCase().equals("n")) {
                System.out.println(FIO.get(1) + " - это имя? ('y' или 'n')");
                dataString = scan.nextLine();
                if (dataString.toLowerCase().equals("y")) {
                    String temp = FIO.get(0);
                    FIO.set(0, FIO.get(2));
                    FIO.set(2, temp);
                } else if (dataString.toLowerCase().equals("n")) {
                    String temp1 = FIO.get(0);
                    String temp2 = FIO.get(1);
                    FIO.set(1, FIO.get(2));
                    FIO.set(0, temp2);
                    FIO.set(2, temp1);
                } else {
                    throw new RuntimeException("Код ошибки -12. Неверный формат ответа!");
                }
            } else {
                throw new RuntimeException("Код ошибки -12. Неверный формат ответа!");
            }
        } else {
            throw new RuntimeException("Код ошибки -12. Неверный формат ответа!");
        }
    }

    // Проверка даты рождения на валидность
    public void dataBirthCheck(String dataBirth) {
        LocalDate localDate = LocalDate.now();
        String[] strBirth = dataBirth.split("\\.");
        if (Integer.parseInt(strBirth[2]) > localDate.getYear()) {
            throw new RuntimeException("Код ошибки -14. " + dataBirth +
                    " - Ошибка в дате рождения - введенная дата больше текущей даты!");
        } else if (Integer.parseInt(strBirth[2]) == localDate.getYear() &&
            Integer.parseInt(strBirth[1]) > localDate.getMonthValue()) {
            throw new RuntimeException("Код ошибки -14. " + dataBirth +
                    " - Ошибка в дате рождения - введенная дата больше текущей даты!");
        } else if (Integer.parseInt(strBirth[2]) == localDate.getYear() &&
                Integer.parseInt(strBirth[1]) == localDate.getMonthValue() &&
                Integer.parseInt(strBirth[0]) > localDate.getDayOfMonth()) {
            throw new RuntimeException("Код ошибки -14. " + dataBirth +
                    " - Ошибка в дате рождения - введенная дата больше текущей даты!");
        }
        if (strBirth[2].length() != 4 || strBirth[2].charAt(0) == '0') {
            throw new RuntimeException("Код ошибки -13. " + dataBirth +
                    " - Ошибка в формате даты - проверьте год!");
        }
        if (strBirth[1].length() != 2 ||
                Integer.parseInt(strBirth[1]) < 1 || Integer.parseInt(strBirth[1]) > 12) {
            throw new RuntimeException("Код ошибки -13. " + dataBirth +
                    " - Ошибка в формате даты - проверьте месяц!");
        }
        if (strBirth[0].length() != 2 ||
                Integer.parseInt(strBirth[0]) < 1 || Integer.parseInt(strBirth[0]) > 31) {
            throw new RuntimeException("Код ошибки -13. " + dataBirth +
                    " - Ошибка в формате даты - проверьте день месяца!");
        } else if ((strBirth[1].equals("04") || strBirth[1].equals("06") ||
                strBirth[1].equals("09") || strBirth[1].equals("11")) && strBirth[0].equals("31")) {
            throw new RuntimeException("Код ошибки -13. " + dataBirth +
                    " - Ошибка в формате даты - проверьте день месяца!");
        } else if (strBirth[1].equals("02")) {
            if (Integer.parseInt(strBirth[2]) % 4 == 0 && Integer.parseInt(strBirth[0]) > 29) {
                throw new RuntimeException("Код ошибки -13. " + dataBirth +
                        " - Ошибка в формате даты - проверьте день месяца!");
            } else if (Integer.parseInt(strBirth[2]) % 4 != 0 && Integer.parseInt(strBirth[0]) > 28) {
                throw new RuntimeException("Код ошибки -13. " + dataBirth +
                        " - Ошибка в формате даты - проверьте день месяца!");
            }
        }
    }
}
