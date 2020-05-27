package sample.users;

public class AccountException {
    public static class AccountNotFound extends Exception{
        public AccountNotFound() {
            super("Account not found");
        }
    }

    public static class AccountExist extends Exception{
        public AccountExist() {
            super("Account exist");
        }
    }
}
