import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidationModule
{
    private final List<ValidationRule> validationRules = new ArrayList<>();
    private final UserStorage storage;
    private String errorMessage = "";

    public ValidationModule(UserStorage storage)
    {
        this.storage = Objects.requireNonNull(storage, "Storage is required.");
    }

    public List<ValidationRule> getValidationRules()
    {
        return List.copyOf(this.validationRules);
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void addValidationRule(ValidationRule rule)
    {
        this.validationRules.add(Objects.requireNonNull(rule, "Rule is required."));
    }

    public void removeValidationRule(ValidationRule rule)
    {
        this.validationRules.remove(rule);
    }

    public boolean validateAndStore(UserAccount user)
    {
        this.errorMessage = "";
        if (user == null)
        {
            this.errorMessage = "User is required.";

            return false;
        }

        for (ValidationRule rule : this.validationRules)
        {
            if (!rule.validate(user))
            {
                this.errorMessage = rule.getErrorMessage();

                return false;
            }
        }

        this.storage.save(user);

        return true;
    }
}
