package contacts.model;

public class Data {
    private String lastName; // Фамилия
    private String firstName; // Имя
    private String fatherName; // Отчество
    private String birthDay; // Дата рождения
    private int phoneNumber; // Телефон
    private char gender; // Пол

    public Data(String lastName, String firstName, String fatherName, String birthDay, int phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return lastName + ' ' + firstName + ' ' + fatherName +
                ' ' + birthDay + ' ' + phoneNumber + ' ' + gender;
    }

}
