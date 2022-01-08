
public class UsersArrayList implements UsersList {

    private User[] storage = new User[10];
    private int capacity = 10;
    private int size = 0;

    public UsersArrayList() {
        this.storage = new User[10];
        this.capacity = 10;
        this.size = 0;
    }

    public void    addUser(User user) throws UserExistException {
        for (int i = 0; i < size; i++) {
            if (storage[i].getId() == user.getId()) {
                throw new UserExistException("Exception: User already " +
                                                "exist - " + user.getName());
            }
        }

        if (size == capacity) {
            User[] tmp = storage;
            storage = new User[this.capacity * 2];

            for (int i = 0; i < capacity; i++) {
                storage[i] = tmp[i];
            }
            this.capacity *= 2;
        }
        storage[size] = user;
        size++;
        System.out.println("User added: " + user.getName());
    }

    @Override
    public String toString() {
        String str = "UsersArrayList { " +
                "capacity: " + this.capacity +
                ", size: " + this.size + "\n";

        for (int i = 0; i < this.size; i++) {
            str += "\t[" + i + "] " + storage[i] + "\n";
        }
        str += "}";

        return str;
    }

    public User    retrieveByID(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (storage[i].getId() == id) {
                return storage[i];
            }
        }
        throw new UserNotFoundException("Exception: User Not Found by id " + id);
    }

    public User    retrieveByIndex(int index) throws UserNotFoundException {
        if (index >= size) {
            throw new UserNotFoundException("Exception: User Not Found by index " + index);
        }

        return storage[index];
    }

    public int  numberOfUsers() {
        return size;
    }
}