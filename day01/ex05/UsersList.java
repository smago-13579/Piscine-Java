
public interface UsersList {
    void    addUser(User user);
    User    retrieveByID(int id);
    User    retrieveByIndex(int index);
    int     numberOfUsers();
}

