package BankCard.Card;

import java.util.concurrent.atomic.AtomicLong;

// Дебетовая карта
public class DebitCard extends BankCard {

    public DebitCard(String owner, long balance) {
        super(owner);
        // у дебетовой карты баланс не может быть < 0
        this.balance = new AtomicLong(Math.max(balance, 0));
    }

    // Оплата. Возвращает true, если операция успешна, иначе false
    @Override
    public boolean pay(long amount) {
        // Проверка на положительную сумму и достаточность средств
        if (amount > 0 && balance.get() >= amount) {
            balance.addAndGet(-amount);
            return true;
        }
        return false;
    }

    // Пополнение баланса. Возвращает false если пополняемая сумма < 0
    @Override
    public boolean topUp(long amount) {
        if (amount <= 0) {
            System.out.println("Сумма пополнения должна быть положительной.");
            return false;
        }
        balance.addAndGet(amount);
        return true;
    }

    // Получение информации о доступных средствах.
    @Override
    public String getAvailableFunds() {
        return String.format("Владелец карты: %s\nCобственные средства: %d", owner, getBalance());
    }
}