import java.time.LocalDate;

public class Main
{
    public static void main(String[] args)
    {
        UserStorage storage = new UserStorage();
        ValidationModule module = new ValidationModule(storage);
        module.addValidationRule(new PasswordValidationRule(false, true, true, true, true));
        module.addValidationRule(new MinimumAgeValidationRule(18));
        module.addValidationRule(new EmailValidationRule());
        module.addValidationRule(new UniqueUsernameValidationRule(storage));

        UserAccount user = new UserAccount("Alex", "Strong1!", "alex@example.com",
                LocalDate.now().minusYears(20));
        System.out.println("Valid account stored: " + module.validateAndStore(user));
        System.out.println("Duplicate account stored: " + module.validateAndStore(user));
        System.out.println("Reason: " + module.getErrorMessage());
        System.out.println("Stored accounts: " + storage.getUsers().size());
    }
}
