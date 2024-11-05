package BankCard;

public abstract class BankCard {
    private long balance;

    public BankCard(long initialBalance) {
        this.balance = initialBalance;
    }

    public long getBalance() {
        return balance;
    }

    // «Получить информацию о балансе»
    public abstract long infoBalance(); // «Получить информацию о балансе»

    // Возвращает информацию о балансе, кредитном лимите и любых других средствах.
    public abstract String getAvailableFundsInfo();

    // «Оплатить» — списывает с карты переданную сумму и возвращает результат типа Boolean;
    public boolean withdraw(long amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true; // успешная оплата
        } else {
            return false; // неуспешная оплата
        }
    }
}