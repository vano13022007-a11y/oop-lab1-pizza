package ru.leti.lab1;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop();
        Delivery delivery = new Delivery();
        List<User> users = createTemporaryUsers();
        seedTemporaryData(users, shop, delivery);

        try (Scanner scanner = new Scanner(System.in)) {
            runApplication(scanner, users, shop, delivery);
        }
    }

    private static void runApplication(Scanner scanner, List<User> users, Shop shop, Delivery delivery) {
        while (true) {
            printLoginMenu();
            if (!scanner.hasNextLine()) {
                return;
            }
            switch (scanner.nextLine().trim()) {
                case "1" -> loginAdmin(scanner, users, shop, delivery);
                case "2" -> loginUser(scanner, users, shop, delivery);
                case "3" -> loginCourier(scanner, delivery);
                case "0" -> {
                    System.out.println("Работа приложения завершена.");
                    return;
                }
                default -> System.out.println("Неизвестный пункт меню. Выберите пункт от 0 до 3.");
            }
        }
    }

    private static void printLoginMenu() {
        System.out.println("\n=== Служба доставки пиццы ===");
        System.out.println("1. Войти как администратор");
        System.out.println("2. Войти как пользователь");
        System.out.println("3. Войти как курьер");
        System.out.println("0. Выход");
        System.out.print("Выберите роль: ");
    }

    private static void loginAdmin(Scanner scanner, List<User> users, Shop shop, Delivery delivery) {
        if (authenticate(scanner, "adminP", "PizzaAdmin")) {
            AdminPanel.run(scanner, users, shop, delivery);
        } else {
            System.out.println("Неверный логин или пароль.");
        }
    }

    private static void loginUser(Scanner scanner, List<User> users, Shop shop, Delivery delivery) {
        User user = authenticateUser(scanner, users);
        if (user == null) {
            System.out.println("Неверный логин или пароль.");
            return;
        }
        UserPanel.run(scanner, user, shop, delivery);
    }

    private static void loginCourier(Scanner scanner, Delivery delivery) {
        Courier courier = authenticateCourier(scanner, delivery);
        if (courier == null) {
            System.out.println("Неверный логин или пароль.");
            return;
        }
        CourierPanel.run(scanner, courier, delivery);
    }

    private static boolean authenticate(Scanner scanner, String expectedLogin, String expectedPassword) {
        System.out.print("Логин: ");
        if (!scanner.hasNextLine()) {
            return false;
        }
        String login = scanner.nextLine().trim();
        System.out.print("Пароль: ");
        if (!scanner.hasNextLine()) {
            return false;
        }
        String password = scanner.nextLine().trim();
        return expectedLogin.equals(login) && expectedPassword.equals(password);
    }

    private static User authenticateUser(Scanner scanner, List<User> users) {
        System.out.print("Логин: ");
        if (!scanner.hasNextLine()) {
            return null;
        }
        String login = scanner.nextLine().trim();
        System.out.print("Пароль: ");
        if (!scanner.hasNextLine()) {
            return null;
        }
        String password = scanner.nextLine().trim();
        for (User user : users) {
            if (user.matches(login, password)) {
                return user;
            }
        }
        return null;
    }

    private static Courier authenticateCourier(Scanner scanner, Delivery delivery) {
        System.out.print("Логин: ");
        if (!scanner.hasNextLine()) {
            return null;
        }
        String login = scanner.nextLine().trim();
        System.out.print("Пароль: ");
        if (!scanner.hasNextLine()) {
            return null;
        }
        String password = scanner.nextLine().trim();
        for (Courier courier : delivery.getCouriers()) {
            if (courier.matches(login, password)) {
                return courier;
            }
        }
        return null;
    }

    private static List<User> createTemporaryUsers() {
        return List.of(
                new User(1, "Иван Вислобоков", "+7 927 666-66-66", "van0", "123456"),
                new User(2, "Артем Артемович", "+7 927 777-77-77", "smirnov", "654321"));
    }

    private static void seedTemporaryData(List<User> users, Shop shop, Delivery delivery) {
        shop.addProduct(new Product("Пицца Пепперони (30 см)", 25, 650));
        shop.addProduct(new Product("Пицца Сырный цыпленок (30 см)", 20, 820));
        shop.addProduct(new Product("Пицца Четыре Сыра (35 см)", 15, 790));
        shop.addProduct(new Product("Пицца Мясная (35 см)", 18, 840));
        shop.addProduct(new Product("Пицца Барбекю (30 см)", 15, 720));
        shop.addProduct(new Product("Кола 1 л", 50, 140));
        shop.addProduct(new Product("Соус Сырный", 40, 60));
        Courier artem = new Courier("Артем", true, "tema", "pizzaTema");
        Courier dima = new Courier("Дима", true, "dimon", "pizzaDimon");
        Courier max = new Courier("Максим", true, "max", "pizzaMax");
        delivery.addCourier(artem);
        delivery.addCourier(max);
        delivery.addCourier(dima);

        Product pepperoni = shop.getProducts().get(0);
        Product cheeseChiken = shop.getProducts().get(1);
        delivery.addOrder(new Order(1, users.get(0),
                List.of(new OrderItem(pepperoni, 1, pepperoni.getPrice())),
                "ул. Композиторов, 1к1", new StandardDelivery()));
        Order inDelivery = new Order(2, users.get(1),
                List.of(new OrderItem(cheeseChiken, 1, cheeseChiken.getPrice())),
                "ул. Пионерская, 3", new StandardDelivery());
        delivery.addOrder(inDelivery);
        delivery.claimOrder(inDelivery, max);
    }
}