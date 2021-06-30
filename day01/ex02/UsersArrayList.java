
public class UsersArrayList implements UsersList {

    private User[] storage = new User[10];
    private int capacity = 10;
    private int size = 0;

    public UsersArrayList() {
    }

    public void    addUser(User user) {
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

    public void    printInfo() {
        System.out.println("capacity: " + this.capacity);
        System.out.println("size: " + this.size);
    }

    public User    retrieveByID(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++ ) {
            if (storage[i].getId() == id) {
                return storage[i];
            }
        }
        throw new UserNotFoundException("Exception: User Not Found");
    }

    public User    retrieveByIndex(int index) {
        if (index >= size) {
            return null;
        }
        return storage[index];
    }

    public int     numberOfUsers() {
        return size;
    }
}