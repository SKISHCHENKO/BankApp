package BankCard.Card;

import java.util.concurrent.atomic.AtomicLong;

// Кредитная карта
public class CreditCard extends BankCard {
    private long creditLimit; // кредитный лимит
    private long creditDebt;  // кредитная задолженность

    public CreditCard(String owner, long balance, long creditLimit) {
        super(owner);

        // Устанавливаем кредитный лимит (должен быть >= 0)
        this.creditLimit = Math.max(creditLimit, 0);

        if (balance >= 0) {
            // Положительный начальный баланс
            this.balance = new AtomicLong(balance);
            this.creditDebt = 0;
        } else {
            // Отрицательный начальный баланс, задолженность(balance<0) должна быть <= кредитный лимит
            this.balance = new AtomicLong(0);
            this.creditDebt = Math.min(Math.abs(balance), this.creditLimit);
        }
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0) {
            return false;
        }
        // Средства для оплаты = Баланс + Кредитный лимит - Задолженность
        long availableFunds = this.getBalance() + creditLimit - creditDebt;
        if (availableFunds < amount) {
            return false; // Недостаточно средств
        }

        if (amount <= this.getBalance()) {
            // Оплата из собственных средств
            balance.addAndGet(-amount);
        } else {
            // Часть суммы покрываем с баланса, остальное из кредита
            long fromBalance = getBalance();
            balance.set(0);
            creditDebt += (amount - fromBalance);
        }
        return true;
    }

    // Пополнение баланса. Возвращает false если пополняемая сумма < 0
    public boolean topUp(long amount) {
        if (amount <= 0) {
            System.out.println("Сумма пополнения должна быть положительной.");
            return false;
        }
        if (creditDebt > 0) {
            // Погашаем кредитный долг в первую очередь
            long paymentToDebt = Math.min(amount, creditDebt);
            creditDebt -= paymentToDebt;
            amount -= paymentToDebt;
        }
        // Оставшуюся сумму добавляем к собственным средствам
        balance.addAndGet(amount);
        return true; // Пополнение успешно
    }

    // Получение информации о текущем балансе
    @Override
    public long getBalance() {
        return balance.get();
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public long getCreditDebt() {
        return creditDebt;
    }

    public void setCreditDebt(long creditDebt) {
        this.creditDebt = creditDebt;
    }

    // Получение информации о доступных средствах.
    @Override
    public String getAvailableFunds() {
        return String.format(
                "Владелец карты: %s%nКредитная карта с лимитом: %d%nCобственные средства: %d%nКредитные средства: %d%nКредитная задолженность: %d%nДоступные средства: %d",
                owner, creditLimit, getBalance(), creditLimit-creditDebt, creditDebt,  (getBalance() + creditLimit - creditDebt)
        );
    }
}

