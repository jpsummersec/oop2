public class EmailValidationRule implements ValidationRule
{
    @Override
    public String getErrorMessage()
    {
        return "Email address has an invalid format.";
    }

    @Override
    public boolean validate(UserAccount user)
    {
        if (user == null || user.getEmail() == null)
        {
            return false;
        }

        String email = user.getEmail();
        int atPosition = email.indexOf('@');

        // There must be exactly one @, with text on both sides
        if (atPosition <= 0 || atPosition != email.lastIndexOf('@') || atPosition == email.length() - 1)
        {
            return false;
        }

        String name = email.substring(0, atPosition);
        String domain = email.substring(atPosition + 1);

        if (email.length() > 254 || name.length() > 64 || email.contains(".."))
        {
            return false;
        }

        // Check common characters before the @. Dots cannot be at either end
        if (!name.matches("[A-Za-z0-9._%+-]+") || name.startsWith(".") || name.endsWith("."))
        {
            return false;
        }

        // The domain needs a dot, like example.com.
        if (!domain.contains(".") || domain.endsWith("."))
        {
            return false;
        }

        for (String part : domain.split("\\."))
        {
            if (!part.matches("[A-Za-z0-9-]+") || part.length() > 63
                    || part.startsWith("-") || part.endsWith("-"))
            {
                return false;
            }
        }

        return true;
    }
}
