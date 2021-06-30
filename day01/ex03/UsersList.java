
public interface UsersList {
    public void    addUser(User user);
    public User    retrieveByID(int id) throws UserNotFoundException;
    public User    retrieveByIndex(int index);
    public int     numberOfUsers();
}