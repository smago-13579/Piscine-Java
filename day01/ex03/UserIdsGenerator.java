public class UserIdsGenerator {
    private static UserIdsGenerator INSTANCE = new UserIdsGenerator();
    private int id = 0;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }

    public int generateId() {
        return ++id;
    }

    public int lastGeneratedId() {
        return this.id;
    }
}
