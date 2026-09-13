import java.time.LocalDate;

/**
 * Standalone checks; run this class without external test libraries.
 */
public class ValidationModuleTest
{
    private static int checks;

    public static void main(String[] args)
    {
        UserAccount user = new UserAccount("Alex", "Strong1!", "alex@example.com",
                LocalDate.now().minusYears(18));
        PasswordValidationRule password = new PasswordValidationRule(false, true, true, true, true);
        ValidationModuleTest.check(password.validate(user), "Complete password");
        for (String invalid : new String[]{"Strong1! ", "Strong1", "Strong!", "STRONG1!", "strong1!", null})
        {
            user.setPassword(invalid);
            ValidationModuleTest.check(!password.validate(user), "Missing password requirement: " + invalid);
        }
        password.setSpacesAllowed(true);
        user.setPassword("Strong1! ");
        ValidationModuleTest.check(password.validate(user), "Spaces allowed");
        password.setSpecialCharacterRequired(false);
        user.setPassword("Strong1");
        ValidationModuleTest.check(password.validate(user), "Special character optional");
        password.setNumberRequired(false);
        user.setPassword("Strong");
        ValidationModuleTest.check(password.validate(user), "Number optional");
        password.setLowercaseRequired(false);
        user.setPassword("STRONG");
        ValidationModuleTest.check(password.validate(user), "Lowercase optional");
        password.setUppercaseRequired(false);
        user.setPassword("");
        ValidationModuleTest.check(password.validate(user), "All requirements disabled; no minimum length specified");
        password.setSpecialCharacterRequired(true);
        user.setPassword(" ");
        ValidationModuleTest.check(!password.validate(user), "Whitespace is not a special character");

        MinimumAgeValidationRule age = new MinimumAgeValidationRule(18);
        ValidationModuleTest.check(age.validate(user), "Birthday today");
        user.setDateOfBirth(LocalDate.now().minusYears(18).plusDays(1));
        ValidationModuleTest.check(!age.validate(user), "Birthday tomorrow");
        age.setMinimumAge(0);
        user.setDateOfBirth(LocalDate.now().plusDays(1));
        ValidationModuleTest.check(!age.validate(user), "Future birth date");
        user.setDateOfBirth(null);
        ValidationModuleTest.check(!age.validate(user), "Missing birth date");
        try
        {
            age.setMinimumAge(-1);
            throw new AssertionError("Negative minimum age accepted");
        }
        catch (IllegalArgumentException expected)
        {
            ValidationModuleTest.checks++;
        }

        EmailValidationRule email = new EmailValidationRule();
        for (String valid : new String[]{"alex@example.com", "alex.smith+school@sub.example.nl"})
        {
            user.setEmail(valid);
            ValidationModuleTest.check(email.validate(user), "Valid email");
        }

        for (String invalid : new String[]{"alex", "alex@", "@example.com", "a@@example.com",
                "a b@example.com", ".a@example.com", "a..b@example.com", "a@-example.com",
                "a@example", null})
        {
            user.setEmail(invalid);
            ValidationModuleTest.check(!email.validate(user), "Invalid email: " + invalid);
        }

        UserStorage storage = new UserStorage();
        ValidationModule module = new ValidationModule(storage);
        UniqueUsernameValidationRule unique = new UniqueUsernameValidationRule(storage);
        module.addValidationRule(email);
        module.addValidationRule(unique);
        ValidationModuleTest.check(!module.validateAndStore(user), "Invalid email rejected");
        ValidationModuleTest.check(storage.getUsers().isEmpty(), "Failure does not store user");
        user.setEmail("alex@example.com");
        ValidationModuleTest.check(module.validateAndStore(user), "Only selected rules are applied");
        ValidationModuleTest.check(module.getErrorMessage().isEmpty(), "Success clears previous error");
        ValidationModuleTest.check(!module.validateAndStore(user), "Duplicate rejected");
        ValidationModuleTest.check(storage.getUsers().size() == 1, "Duplicate not stored");
        user.setName("Other");
        ValidationModuleTest.check(storage.usernameExists("Alex"), "Input mutation does not change storage");
        storage.getUsers().get(0).setName("Changed");
        ValidationModuleTest.check(storage.usernameExists("Alex"), "Returned account mutation does not change storage");
        user.setName("alex");
        ValidationModuleTest.check(unique.validate(user), "Usernames are case-sensitive");
        user.setName(" ");
        ValidationModuleTest.check(!unique.validate(user), "Blank username rejected");
        module.removeValidationRule(unique);
        user.setName("Alex");
        ValidationModuleTest.check(module.validateAndStore(user), "Uniqueness can be disabled");
        ValidationModuleTest.check(!module.validateAndStore(null), "Null account rejected");

        ValidationModule empty = new ValidationModule(new UserStorage());
        ValidationModuleTest.check(empty.validateAndStore(user), "Empty rule list accepts account");
        empty.addValidationRule(new ValidationRule()
        {
            public String getErrorMessage()
            {
                return "Custom failure";
            }

            public boolean validate(UserAccount account)
            {
                return false;
            }
        });
        empty.addValidationRule(new ValidationRule()
        {
            public String getErrorMessage()
            {
                return "Unreachable";
            }

            public boolean validate(UserAccount account)
            {
                throw new AssertionError("Validation did not stop at first failure");
            }
        });
        ValidationModuleTest.check(!empty.validateAndStore(user), "Custom rule and early exit");
        ValidationModuleTest.check(empty.getErrorMessage().equals("Custom failure"), "Failure reason exposed");
        System.out.println("All " + ValidationModuleTest.checks + " checks passed.");
    }

    private static void check(boolean condition, String description)
    {
        if (!condition)
        {
            throw new AssertionError(description);
        }

        ValidationModuleTest.checks++;
    }
}
