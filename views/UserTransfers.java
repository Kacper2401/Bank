package sample.views;

import sample.users.AccountManagement;

public class UserTransfers extends java.awt.print.Book {
    private String transferDate;
    private String fromAccountId;
    private String toAccountId;
    private String amount;
    private String title;
    private String description;

    public UserTransfers(int fromAccountId, int toAccountId, float amount, String transferDate, String title, String description) {
        AccountManagement accountManagement = new AccountManagement();

        this.fromAccountId = accountManagement.checkAccountName(fromAccountId);
        this.toAccountId = accountManagement.checkAccountName(toAccountId);
        this.amount = Float.toString(amount);
        this.transferDate = transferDate;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }
}
