package kz.javaworkshop;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Вариант А: емкость 4, лимит массы 6000 г
        Inventory inventory = new Inventory(4, 6000);

        try {
            inventory.add(new Item("Меч", "weapon", 2500, 100));
            inventory.add(new Item("Щит", "armor", 3000, 80));
            inventory.add(new Item("Зелье", "potion", 500, 20));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка инициализации предметов: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- ИНВЕНТАРЬ ГЕРОЯ (Вариант А) ---");
            System.out.println("1. Добавить предмет");
            System.out.println("2. Показать предметы");
            System.out.println("3. Удалить предмет");
            System.out.println("4. Использовать зелье");
            System.out.println("5. Показать сводку");
            System.out.println("6. Поиск предмета по имени");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт меню: ");

            if (!scanner.hasNextInt()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    System.out.println("Поток ввода завершен. Выход из программы.");
                    break;
                }
                System.out.println("Неверный ввод. Введите целое число от 0 до 6.");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Введите имя предмета: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите тип (weapon/armor/potion): ");
                    String type = scanner.nextLine();

                    System.out.print("Введите массу (граммы): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Ошибка: масса должна быть целым числом.");
                        scanner.nextLine();
                        break;
                    }
                    int weight = scanner.nextInt();

                    System.out.print("Введите стоимость (монеты): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Ошибка: стоимость должна быть целым числом.");
                        scanner.nextLine();
                        break;
                    }
                    int value = scanner.nextInt();
                    scanner.nextLine(); 

                    try {
                        Item item = new Item(name, type, weight, value);
                        boolean added = inventory.add(item);
                        System.out.println(added ? "Добавлено" : "Нет места или массы");
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Данные предмета: " + ex.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Список предметов:");
                    if (inventory.size() == 0) {
                        System.out.println("Инвентарь пуст.");
                    } else {
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println((i + 1) + ". " + inventory.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Введите номер предмета для удаления (1 - " + inventory.size() + "): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Неверный номер.");
                        scanner.nextLine();
                        break;
                    }
                    int removeNumber = scanner.nextInt();
                    scanner.nextLine();

                    int removeIndex = removeNumber - 1;
                    Item removed = inventory.remove(removeIndex);
                    if (removed != null) {
                        System.out.println("Успешно удалено: " + removed.getName());
                    } else {
                        System.out.println("Неверный номер предмета.");
                    }
                    break;

                case 4:
                    System.out.print("Введите номер зелья для использования (1 - " + inventory.size() + "): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Неверный номер.");
                        scanner.nextLine();
                        break;
                    }
                    int potionNumber = scanner.nextInt();
                    scanner.nextLine();

                    int potionIndex = potionNumber - 1;
                    Item targetItem = inventory.get(potionIndex);

                    if (targetItem != null && targetItem.getType().equals("potion")) {
                        inventory.remove(potionIndex);
                        System.out.println("Зелье использовано");
                    } else {
                        System.out.println("По указанному номеру нет зелья.");
                    }
                    break;

                case 5:
                    System.out.println("Ячейки: " + inventory.size() + "/" + inventory.getCapacity() +
                            "; масса: " + inventory.totalWeightGrams() + "/" + inventory.getMaxWeightGrams() +
                            " г; стоимость: " + inventory.totalValue() + " монет");
                    break;

                case 6:
                    System.out.print("Введите подстроку для поиска: ");
                    String query = scanner.nextLine();
                    try {
                        Item[] found = inventory.findByName(query);
                        if (found.length == 0) {
                            System.out.println("Ничего не найдено");
                        } else {
                            System.out.println("Найденные предметы (" + found.length + "):");
                            for (Item f : found) {
                                System.out.println("- " + f);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка поиска: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Завершение программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неизвестная команда. Выберите пункт от 0 до 6.");
                    break;
            }
        }
    }
}