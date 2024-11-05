package Messages;

// Дополнительный класс для унификации сообщений

public class Messages {
    public static final String SELECT_MODE = "Выберите проверку созданных классов (1 - Автоматическое, 2 - Ручное через меню): ";
    public static final String WRONG_CHOICE = "Неверный выбор. Попробуйте снова.";
    public static final String SELECT_CARD = " (1 - Дебетовая, 2 - Кредитная, 3 - Дебетовая с бонусами, 4 - Кредитная с бонусами): ";
    public static final String BALANCE_INFO = "Текущий баланс: ";
    public static final String EXIT = "Выход из программы.";
    public static final String INPUT_AMOUNT = "Введите сумму: ";
    public static final String TOP_UP_SUCCESS = "Пополнение выполнено успешно.";
    public static final String TOP_UP_FAILURE = "Пополнение не удалось.";
    public static final String NEGATIVE_AMOUNT = "Сумма не может быть отрицательной.";
    public static final String PAYMENT_SUCCESS = "Оплата выполнена успешно.";
    public static final String INSUFFICIENT_FUNDS = "Недостаточно средств для оплаты.";
    public static final String TRANSFER_SUCCESS = "Перевод выполнен успешно.";
    public static final String TRANSFER_FAILURE = "Перевод не удался.";
    public static final String MAIN_MENU = "\nПожалуйста выберите интересующий Вас пункт Меню:\n1. Пополнение баланса\n2. Оплата\n3. Перевод между картами\n4. Просмотр баланса\n5. Просмотр доступных средств\n0. Выход\nВыберите действие: ";
}