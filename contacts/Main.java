package contacts;

import contacts.controllers.Controller;
import contacts.model.DataOperation;
import contacts.model.DataOperationImpl;
import contacts.model.FileOperation;
import contacts.model.FileOperationImpl;
import contacts.views.ViewUser;

public class Main {
    public static void main(String[] args) {
        FileOperation fileOperation = new FileOperationImpl();
        DataOperation dataOperation = new DataOperationImpl(fileOperation);
        Controller controller = new Controller(dataOperation);
        ViewUser view = new ViewUser(controller);
        view.run();

    }
}