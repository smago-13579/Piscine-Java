public class User {

    private Integer id;
    private String name;
    private Integer balance;

    User(String name, int balance, int id) {
        this.name = name;

        if (balance < 0) {
            this.balance = 0;
            System.err.println("Incorrect balance!!!");
        } else {
            this.balance = balance;
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{ id=" + this.id +
                ", name='" + this.name + "\'" +
                ", balance=" + this.balance + " }";
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

    public  void    setBalance(Integer balance) {
        if (balance < 0) {
            System.err.println("Can't set negative balance");
        } else {
            this.balance = balance;
        }
    }
}
