package BankCard.Card;

import java.util.concurrent.atomic.AtomicLong;

// Кредитная карта с бонусами (кэшбек и бонусные баллы)
public class CreditCardBonus extends CreditCard {
    private final AtomicLong bonuses; // Количество бонусов
    private static final int CASHBACK_PERCENTAGE = 5; // Процент кэшбека
    private static final int CASHBACK_LIMIT = 5000;  // При условии трат свыше указанного лимита
    private static final int BONUS_LIMIT = 1000;    // Начисление бонусов при покупке свыше указанного лимита
    private static final int BONUS_PERCENTAGE = 1; // Процент для начисления бонусов
    private static final double ACCUMULATION_PERCENTAGE = 0.005; // Процент накопления от суммы пополнения

    public CreditCardBonus(String owner, long balance, long limit) {
        super(owner, balance, limit);
        this.bonuses = new AtomicLong(0);
    }

    // Метод оплаты с возможностью использования бонусов
    public boolean pay(long amount, boolean useBonuses) {
        if (amount <= 0 || !hasSufficientFunds(amount)) {
            System.out.println("Недостаточно средств или некорректная сумма.");
            return false;
        }

        if (useBonuses && bonuses.get() > 0) {
            if (bonuses.get() >= amount) {
                bonuses.addAndGet(-amount); // Полная оплата бонусами
                System.out.println("Оплата выполнена полностью бонусами.");
                return true;
            } else {
                // Частичное покрытие бонусами, остальное из баланса/кредита
                long remainingAmount = amount - bonuses.get();
                bonuses.set(0); // Все бонусы израсходованы
                return processPayment(remainingAmount);
            }
        } else {
            return processPayment(amount);
        }
    }

    // Метод пополнения баланса с начислением бонусов
    @Override
    public boolean topUp(long amount) {
        if (amount <= 0) {
            System.out.println("Сумма пополнения должна быть положительной.");
            return false;
        }
        super.topUp(amount);
        long accumulatedBonus = (long) (amount * ACCUMULATION_PERCENTAGE);
        bonuses.addAndGet(accumulatedBonus);
        System.out.println("Баланс пополнен на " + amount + ". Начислено бонусов: " + accumulatedBonus);
        return true;
    }

    // Метод для выполнения оплаты с учетом кэшбека и начисления бонусов
    private boolean processPayment(long amount) {
        if (amount <= 0 || !hasSufficientFunds(amount)) {
            return false; // Недостаточно средств или некорректная сумма
        }

        // Рассчет и добавление бонусов
        addBonuses(amount);

        // Применение кэшбека
        long amountAfterCashback = applyCashback(amount);

        // Выполняем оплату
        if (balance.get() >= amountAfterCashback) {
            balance.addAndGet(-amountAfterCashback);
        } else {
            long delta = amountAfterCashback - balance.get();
            balance.set(0);
            addCreditDebt(delta);
        }
        System.out.println("Сумма после кэшбека: " + amountAfterCashback);
        return true;
    }

    // Начисление бонусов
    private void addBonuses(long amount) {
        if (amount >= BONUS_LIMIT) {
            long bonusAmount = amount * BONUS_PERCENTAGE / 100;
            bonuses.addAndGet(bonusAmount);
            System.out.println("Начислено бонусов: " + bonusAmount);
        }
    }

    // Применение кэшбека
    private long applyCashback(long amount) {
        if (amount >= CASHBACK_LIMIT) {
            long cashbackAmount = amount * CASHBACK_PERCENTAGE / 100;
            System.out.println("Кэшбек: " + cashbackAmount);
            return amount - cashbackAmount;
        }
        return amount;
    }

    // Проверка, достаточно ли средств для оплаты (учитывая кредитный лимит)
    private boolean hasSufficientFunds(long amount) {
        return (balance.get() + getAvailableCredit()) >= amount;
    }

    private void addCreditDebt(long amount) {
        setCreditDebt(getCreditDebt() + amount);
    }

    private long getAvailableCredit() {
        return getCreditLimit() - getCreditDebt();
    }

    // Метод для получения информации о доступных средствах и бонусах
    @Override
    public String getAvailableFunds() {
        return String.format(
                "%s,\nКоличество бонусов: %d,\nКэшбек: %d%% при покупках от %d,\nБонусы начисляются при покупках от %d",
                super.getAvailableFunds(),
                bonuses.get(),
                CASHBACK_PERCENTAGE,
                CASHBACK_LIMIT,
                BONUS_LIMIT
        );
    }
}