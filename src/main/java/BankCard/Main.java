package BankCard;

import BankCard.Card.*;
import Messages.Messages;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(Messages.SELECT_MODE);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                autoTest();
                break;
            case 2:
                testMenu(scanner);
                break;
            default:
                System.out.println(Messages.WRONG_CHOICE);
        }
        scanner.close();
    }

    public static void autoTest() {
        DebitCard debitCard = new DebitCard("Алиса", 10000);
        CreditCard creditCard = new CreditCard("Петя", 0, 10000);

        System.out.println("Проверка класса DebitCard:");
        System.out.println(Messages.BALANCE_INFO + debitCard.getBalance());
        System.out.println(debitCard.getAvailableFunds());
        System.out.println("Пополнение на 5000");
        debitCard.topUp(5000);
        System.out.println(Messages.BALANCE_INFO + debitCard.getBalance());
        System.out.println("Оплата товара на 7000");
        debitCard.pay(7000);
        System.out.println(Messages.BALANCE_INFO + debitCard.getBalance());
        System.out.println();
        System.out.println("Проверка класса CreditCard:");
        System.out.println(creditCard.getAvailableFunds());
        System.out.println("Пополнение на 5000");
        creditCard.topUp(5000);
        System.out.println(creditCard.getAvailableFunds());
        System.out.println("Оплата товара на 5000");
        creditCard.pay(5000);
        System.out.println(creditCard.getAvailableFunds());
        System.out.println("Оплата товара на 3000");
        creditCard.pay(3000);
        System.out.println(creditCard.getAvailableFunds());
        System.out.println("Пополнение на 2000");
        creditCard.topUp(2000);
        System.out.println(creditCard.getAvailableFunds());
        System.out.println("Пополнение на 2000");
        creditCard.topUp(2000);
        System.out.println(creditCard.getAvailableFunds());
    }

    public static void testMenu(Scanner scanner) {
        DebitCard debitCard = new DebitCard("Алиса", 10000);
        CreditCard creditCard = new CreditCard("Петя", 0, 10000);
        DebitCardBonus debitCardBonus = new DebitCardBonus("Маша", 10000);
        CreditCardBonus creditCardBonus = new CreditCardBonus("Иван", 0, 10000);

        BankCard selectedCard = null;

        while (true) {
            if (selectedCard == null) {
                System.out.print("Выберите карту" + Messages.SELECT_CARD);
                int cardChoice = scanner.nextInt();
                switch (cardChoice) {
                    case 1:
                        selectedCard = debitCard;
                        break;
                    case 2:
                        selectedCard = creditCard;
                        break;
                    case 3:
                        selectedCard = debitCardBonus;
                        break;
                    case 4:
                        selectedCard = creditCardBonus;
                        break;
                    default:
                        System.out.println(Messages.WRONG_CHOICE);
                        continue;
                }
            }
            System.out.print(Messages.MAIN_MENU);
            int choice = scanner.nextInt();
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.print(Messages.INPUT_AMOUNT);
                    long topUpAmount = scanner.nextLong();
                    if (topUpAmount < 0) {
                        System.out.println(Messages.NEGATIVE_AMOUNT);
                    } else if (selectedCard.topUp(topUpAmount)) {
                        System.out.println(Messages.TOP_UP_SUCCESS);
                    } else {
                        System.out.println(Messages.TOP_UP_FAILURE);
                    }
                    break;

                case 2:
                    System.out.print(Messages.INPUT_AMOUNT);
                    long payAmount = scanner.nextLong();
                    boolean permission = false;
                    if (selectedCard instanceof DebitCardBonus || selectedCard instanceof CreditCardBonus) {
                        System.out.print("Использовать бонусы для оплаты? (да/нет): ");
                        String input = scanner.next().trim().toLowerCase();
                        permission = input.equals("да");
                    }
                    if (selectedCard.pay(payAmount, permission)) {
                        System.out.println(Messages.PAYMENT_SUCCESS);
                    } else {
                        System.out.println(Messages.INSUFFICIENT_FUNDS);
                    }
                    break;

                case 3:
                    System.out.print(Messages.INPUT_AMOUNT);
                    long transferAmount = scanner.nextLong();
                    System.out.print("Выберите карту" + Messages.SELECT_CARD);
                    int targetChoice = scanner.nextInt();
                    BankCard targetCard;
                    switch (targetChoice) {
                        case 1:
                            targetCard = debitCard;
                            break;
                        case 2:
                            targetCard = creditCard;
                            break;
                        case 3:
                            targetCard = debitCardBonus;
                            break;
                        case 4:
                            targetCard = creditCardBonus;
                            break;
                        default:
                            System.out.println(Messages.WRONG_CHOICE);
                            continue;
                    }
                    if (selectedCard.transfer(targetCard, transferAmount)) {
                        System.out.println(Messages.TRANSFER_SUCCESS);
                    } else {
                        System.out.println(Messages.TRANSFER_FAILURE);
                    }
                    break;

                case 4:
                    System.out.println(Messages.BALANCE_INFO + selectedCard.getBalance());
                    break;

                case 5:
                    System.out.println(selectedCard.getAvailableFunds());
                    break;

                default:
                    System.out.println(Messages.WRONG_CHOICE);
            }
        }
        System.out.println(Messages.EXIT);
    }
}
