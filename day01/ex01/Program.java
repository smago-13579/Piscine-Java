import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User Bob = new User("Bob", 1000);
        User Nick = new User("Nick", 1000);
        User Tom = new User("Tom", 500);
        User Kate = new User("Kate", 670);
        System.out.println("Last generated Id: " + UserIdsGenerator.getInstance().getId());
    }
}