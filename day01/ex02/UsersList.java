
public interface UsersList {
    void    addUser(User user);
    void    addUsers(User... users);
    User    retrieveByID(int id);
    User    retrieveByIndex(int index);
    int     numberOfUsers();
}

