import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);
        User Tom = new User("Tom", 500);
        User Kate = new User("Kate", 670);
        User test1 = new User("test1" , 10);
        User test2 = new User("test2" , 10);
        User test3 = new User("test3" , 15);
        User test4 = new User("test4" , 15);
        User test5 = new User("test5" , 20);
        User test6 = new User("test6" , 20);
        User test7 = new User("test7" , 30);

        UsersArrayList usersList = new UsersArrayList();
        System.out.println(usersList);

        usersList.addUser(Bob);
        usersList.addUser(Nick);
        usersList.addUser(Tom);
        usersList.addUser(Kate);
        System.out.println(usersList);

        usersList.addUser(test1);
        usersList.addUser(test2);
        usersList.addUser(test3);
        usersList.addUser(test4);
        usersList.addUser(test5);
        usersList.addUser(test6);
        usersList.addUser(test7);
        System.out.println(usersList);

        System.out.println("Retrieve the number of users - " + usersList.numberOfUsers());
        System.out.println("Get by id 7 - " + usersList.retrieveByID(7));
        System.out.println("Get by index 10 - " + usersList.retrieveByIndex(10));
        System.out.println("Try to get incorrect index 14 - " + usersList.retrieveByIndex(14));
    }
}