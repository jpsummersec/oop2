import java.util.Objects;

public class UniqueUsernameValidationRule implements ValidationRule
{
    private UserStorage storage;

    public UniqueUsernameValidationRule(UserStorage storage)
    {
        this.setStorage(storage);
    }

    @Override
    public String getErrorMessage()
    {
        return "Username must be nonblank and must not already exist.";
    }

    public UserStorage getStorage()
    {
        return this.storage;
    }

    public void setStorage(UserStorage storage)
    {
        this.storage = Objects.requireNonNull(storage, "Storage is required.");
    }

    @Override
    public boolean validate(UserAccount user)
    {
        return user != null && user.getName() != null && !user.getName().isBlank()
                && !this.storage.usernameExists(user.getName());
    }
}
