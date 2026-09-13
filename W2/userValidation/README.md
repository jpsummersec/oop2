# User Validation

Assumptions:

- Username comparisons are case-sensitive, so John and john are different usernames. Blank usernames are not accepted.

- Age is calculated using full calendar years and the current local date. Birth dates in the future are not accepted. 
People born on February 29 become one year older on March 1 during non-leap years.

- Email validation checks that there is one @, that dots are placed correctly, and that the domain contains a dot. 
Before the @, letters, numbers, dots, underscores, percent signs, plus signs, and hyphens are allowed. It does not 
check whether the email address exists or support every uncommon email format.

- Spaces include all whitespace characters, such as spaces and tabs. Whitespace is not treated as a special character.
The assignment does not require a minimum password length. Therefore, an empty password can be accepted when all 
password requirements are turned off. A null password is always rejected.

- If the validation-rule list is empty, every non-null account is accepted.
Rules that are not added to the list are not used.

- Accounts are stored in memory, so they remain available only while the program is running.
Copies are stored to prevent later changes to the original account from changing the stored data.
This classroom example stores passwords exactly as entered and processes registrations one at a time.
It should not be used as a real authentication system.

- Clear capitalization and constructor mistakes in the class diagram are corrected to follow Java naming conventions. 
For example, UniqueUsernameValidationRule and getValidationRules.