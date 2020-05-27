package sample.transfer;

public class TransferException {
    public static class UserDoseNotExist extends Exception{
        public UserDoseNotExist() {
            super("User doesn't exist");
        }
    }

    public static class NotEnoughMoney extends Exception{
        public NotEnoughMoney() {
            super("Not enough money");
        }
    }
}
