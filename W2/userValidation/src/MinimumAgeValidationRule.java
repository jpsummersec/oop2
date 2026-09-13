import java.time.LocalDate;
import java.time.Period;

public class MinimumAgeValidationRule implements ValidationRule
{
    private int minimumAge;

    public MinimumAgeValidationRule(int minimumAge)
    {
        this.setMinimumAge(minimumAge);
    }

    @Override
    public String getErrorMessage()
    {
        return "User must have a valid date of birth and be at least " + this.minimumAge + " years old.";
    }

    public int getMinimumAge()
    {
        return this.minimumAge;
    }

    public void setMinimumAge(int minimumAge)
    {
        if (minimumAge < 0)
        {
            throw new IllegalArgumentException("Minimum age cannot be negative.");
        }

        this.minimumAge = minimumAge;
    }

    @Override
    public boolean validate(UserAccount user)
    {
        if (user == null || user.getDateOfBirth() == null)
        {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = user.getDateOfBirth();

        return !birthDate.isAfter(today)
                && Period.between(birthDate, today).getYears() >= this.minimumAge;
    }
}
