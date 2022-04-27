package edu.school21.reflection.app;

import edu.school21.reflection.entities.User;
import edu.school21.reflection.orm.OrmManager;

public class Main {
    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager("reflection",
                "edu.school21.reflection.entities");

        User userOne = new User("Sylva", "Mago", 25);
        User userTwo = new User("Tom", "Jones", 27);
        User userThree = new User("Nick", "Smith", 30);

        System.out.println("\nSAVE NEW USERS: ");
        ormManager.save(userOne);
        ormManager.save(userTwo);
        ormManager.save(userThree);
        System.out.println(userOne);
        System.out.println(userTwo);
        System.out.println(userThree);

        System.out.println("\nUPDATE USERS:");
        userOne.setFirstName("Agent47");
        userTwo.setAge(null);
        userThree.setLastName(null);
        ormManager.update(userOne);
        ormManager.update(userTwo);
        ormManager.update(userThree);
        System.out.println(userOne);
        System.out.println(userTwo);
        System.out.println(userThree);

        System.out.println("\nFIND USERS BY ID: ");
        User tmp = ormManager.findById(1l, User.class);
        System.out.println(tmp);
        tmp = ormManager.findById(2l, User.class);
        System.out.println(tmp);
        tmp = ormManager.findById(3l, User.class);
        System.out.println(tmp);
    }
}
