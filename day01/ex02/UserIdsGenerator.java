
public class UserIdsGenerator {
    private static UserIdsGenerator instance = new UserIdsGenerator();
    private Integer id = 0;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return id++;
    }
}
