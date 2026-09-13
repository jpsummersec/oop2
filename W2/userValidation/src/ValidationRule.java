public interface ValidationRule
{
    boolean validate(UserAccount user);

    String getErrorMessage();
}
