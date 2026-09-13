public class PasswordValidationRule implements ValidationRule
{
    private boolean spacesAllowed;
    private boolean specialCharacterRequired;
    private boolean numberRequired;
    private boolean lowercaseRequired;
    private boolean uppercaseRequired;

    public PasswordValidationRule(boolean spacesAllowed, boolean specialCharacterRequired,
                                  boolean numberRequired, boolean lowercaseRequired,
                                  boolean uppercaseRequired)
    {
        this.spacesAllowed = spacesAllowed;
        this.specialCharacterRequired = specialCharacterRequired;
        this.numberRequired = numberRequired;
        this.lowercaseRequired = lowercaseRequired;
        this.uppercaseRequired = uppercaseRequired;
    }

    @Override
    public String getErrorMessage()
    {
        return "Password does not meet the configured requirements.";
    }

    public boolean isSpacesAllowed()
    {
        return this.spacesAllowed;
    }

    public void setSpacesAllowed(boolean value)
    {
        this.spacesAllowed = value;
    }

    public boolean isSpecialCharacterRequired()
    {
        return this.specialCharacterRequired;
    }

    public void setSpecialCharacterRequired(boolean value)
    {
        this.specialCharacterRequired = value;
    }

    public boolean isNumberRequired()
    {
        return this.numberRequired;
    }

    public void setNumberRequired(boolean value)
    {
        this.numberRequired = value;
    }

    public boolean isLowercaseRequired()
    {
        return this.lowercaseRequired;
    }

    public void setLowercaseRequired(boolean value)
    {
        this.lowercaseRequired = value;
    }

    public boolean isUppercaseRequired()
    {
        return this.uppercaseRequired;
    }

    public void setUppercaseRequired(boolean value)
    {
        this.uppercaseRequired = value;
    }

    @Override
    public boolean validate(UserAccount user)
    {
        if (user == null || user.getPassword() == null)
        {
            return false;
        }

        String password = user.getPassword();
        boolean hasSpecialCharacter = false;
        boolean hasNumber = false;
        boolean hasLowercase = false;
        boolean hasUppercase = false;

        for (int index = 0; index < password.length(); index++)
        {
            char character = password.charAt(index);
            boolean isSpace = Character.isWhitespace(character) || Character.isSpaceChar(character);

            if (isSpace && !this.spacesAllowed)
            {
                return false;
            }

            if (Character.isDigit(character))
            {
                hasNumber = true;
            }

            if (Character.isLowerCase(character))
            {
                hasLowercase = true;
            }

            if (Character.isUpperCase(character))
            {
                hasUppercase = true;
            }

            if (!Character.isLetterOrDigit(character) && !isSpace && !Character.isISOControl(character))
            {
                hasSpecialCharacter = true;
            }
        }

        if (this.specialCharacterRequired && !hasSpecialCharacter)
        {
            return false;
        }

        if (this.numberRequired && !hasNumber)
        {
            return false;
        }

        if (this.lowercaseRequired && !hasLowercase)
        {
            return false;
        }

        if (this.uppercaseRequired && !hasUppercase)
        {
            return false;
        }

        return true;
    }
}
