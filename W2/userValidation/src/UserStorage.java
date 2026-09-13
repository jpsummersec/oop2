import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserStorage
{
    private final List<UserAccount> users = new ArrayList<>();

    public List<UserAccount> getUsers()
    {
        List<UserAccount> copies = new ArrayList<>();
        for (UserAccount user : this.users)
        {
            copies.add(UserStorage.copy(user));
        }

        return List.copyOf(copies);
    }

    public void save(UserAccount user)
    {
        this.users.add(UserStorage.copy(Objects.requireNonNull(user, "User is required.")));
    }

    public boolean usernameExists(String name)
    {
        return this.users.stream().anyMatch(user -> Objects.equals(user.getName(), name));
    }

    // Keep later edits to an account from changing an already validated entry.
    private static UserAccount copy(UserAccount user)
    {
        return new UserAccount(user.getName(), user.getPassword(), user.getEmail(), user.getDateOfBirth());
    }
}
