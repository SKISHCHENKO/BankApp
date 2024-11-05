package BankCard.Card;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BankCard {
    protected AtomicLong balance;
    protected String owner;

    public BankCard(String owner) {
        this.owner = owner;
        this.balance = new AtomicLong(0);
    }

    // Метод для оплаты с параметром permission, по умолчанию без использования бонусов
    public boolean pay(long amount, boolean permission) {
        return pay(amount);  // Используем обычный метод pay без учета бонусов
    }
    // Абстрактный метод для оплаты без использования бонусов
    public abstract boolean pay(long amount);

    // Абстрактный метод для пополнения баланса
    public abstract boolean topUp(long amount);

    // Перевод с карты на карту
    public boolean transfer(BankCard targetCard, long amount) {
        if (amount <= 0) {
            System.out.println("Сумма перевода должна быть положительной.");
            return false;
        }
        if (!this.pay(amount)) {
            System.out.println("Недостаточно средств для перевода.");
            return false;
        }
        if (!targetCard.topUp(amount)) {
            this.topUp(amount); // Возвращаем средства при ошибке пополнения целевой карты
            System.out.println("Перевод не удался, средства возвращены на исходную карту.");
            return false;
        }
        System.out.println("Перевод выполнен успешно.");
        return true;
    }
    // Получение информации о текущем балансе
    public long getBalance() {
        return balance.get();
    }
    // Абстрактный метод для получения информации о доступных средствах
    public abstract String getAvailableFunds();
}