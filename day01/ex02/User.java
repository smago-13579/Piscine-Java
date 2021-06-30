import java.util.UUID;

public class User {

    private int id;
    private String name;
    private int balance;

    User(String name, int balance) {
        if (balance < 0) {
            System.err.println("Can't create user " + name);
            System.err.println("Negative balance!!!");
        }
        else {
            this.name = name;
            this.balance = balance;
            this.id = UserIdsGenerator.getInstance().generateId();
            System.out.println("New User Created!");
            userInfo();
        }
    }

    public  void    userInfo() {
        System.out.print("Name: " + this.name);
        System.out.print("; Balance: " + this.balance);
        System.out.println("; Id: " + this.id);
    }

    public  String    getName() {
        return this.name;
    }

    public  int     getId() {
        return this.id;
    }

    public  int     getBalance() {
        return this.balance;
    }

    public  void    setBalance(int balance) {
        if (balance < 0) {
            System.err.println("Can't set negative balance");
        }
        else {
            this.balance = balance;
        }
    }
}
