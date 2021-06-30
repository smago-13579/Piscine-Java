import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);
        User Tom = new User("Tom", 500);
        User Kate = new User("Kate", 670);
        System.out.println("Last generated Id: " + UserIdsGenerator.getInstance().getId());

        UsersArrayList list = new UsersArrayList();

        for (int i = 0; i < 11; i++) {
            list.addUser(Bob);
            list.addUser(Nick);
            list.addUser(Tom);
            list.addUser(Kate);
            list.printInfo();
        }
        System.out.println("User name: " + list.retrieveByIndex(30).getName());
        System.out.println("User name: " + list.retrieveByID(2).getName());
        System.out.println("Number of Users: " + list.numberOfUsers());
        list.retrieveByID(5);
    }
}