package JavaToyStore;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ToyStore {
    /**
     * Список доступных игрушек
     */
    private final List<Toy> toys = new ArrayList<>();
    /**
     * Список "выпавших" игрушек
     */
    private final List<Toy> droppedToys = new ArrayList<>();
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private FileWriter writer = new FileWriter("Выпавшие игрушки.txt");

    private int toyId = 1;

    ToyStore() throws IOException {
        toys.add(new Toy(toyId++, "Мишка", 10, 25));
        toys.add(new Toy(toyId++, "Заяц", 3, 63));
        toys.add(new Toy(toyId++, "Хагги Ваги", 15, 47));
        toys.add(new Toy(toyId++, "Свинка Пеппа", 16, 50));
        toys.add(new Toy(toyId++, "Лего", 12, 31));
        toys.add(new Toy(toyId++, "Железная дорога", 7, 70));
        toys.add(new Toy(toyId++, "Мяч", 16, 56));

        System.out.println("Добро пожаловать в магазин игрушек");
    }

    public static void main(String[] args) throws IOException {
        ToyStore store = new ToyStore();
        store.openStore();
    }

    /**
     * Открываем наш чудесный магазин с веселыми конкурсами
     */
    void openStore() {
        System.out.printf("В магазине доступно %d игрушек.%n", getToysCount());

        if (getToysCount() == 0) {
            System.out.println("В магазине закончились игрушки, давайте добавим");
            addToy();
            return;
        }

        System.out.println("Чтобы провести розыгрыш, напишите 1");
        System.out.println("Чтобы добавить игрушку, напишите 2");
        System.out.println("Чтобы изменить частоту выпадения игрушки, напишите 3");
        System.out.println("Чтобы выйти из магазина, напишите 0");

        Integer number = readNumber("Введите число от 1 до 2");
        // Если не прочитали неверное число - выполняем метод снова
        if (number == null) {
            openStore();
            return;
        }
        switch (number) {
            case 0 -> System.exit(0);
            case 1 -> startLottery();
            case 2 -> addToy();
            case 3 -> changeToyDropFrequency();
            default -> openStore();
        }
    }

    /**
     * Метод запускает "лотерею"
     */
    void startLottery() {
        System.out.println("Крутим барабан... (тут должна быть музыка из ПолеЧудес)");
        Toy toy = getRandomToy();
        System.out.println("Вам выпала игрушка: %s".formatted(toy.getName()));
        if (toy.getAmount() > 1) {
            toy.decreaseAmount();
        } else {
            toys.remove(toy);
        }
        addToyToFile(toy);
        openStore();
    }

    /**
     * Метод для добавления игрушки
     */
    void addToy() {
        System.out.println("Для добавления игрушки введите ее название");
        String name = readLine();
        if (name.isBlank()) {
            System.out.println("Название игрушки не должно быть пустым");
            addToy();
            return;
        }
        System.out.println("Введите количество игрушек");
        Integer toysCount = readNumber("Нужно ввести число больше 0");
        if (toysCount == null) {
            addToy();
            return;
        }
        System.out.println("Введите частоту выпадения игрушки от 1 до 100");
        Integer weight = readNumber("Нужно ввести число от 1 до 100");
        if (weight == null || weight < 0 || weight > 100) {
            System.out.println("Нужно ввести число от 1 до 100");
            addToy();
        }
        toys.add(new Toy(toyId++, name, toysCount, weight));

        System.out.println("Вы добавили игрушку %s".formatted(name));
        openStore();
    }

    /**
     * Метод для изменения частоты выпадения игрушки
     */
    void changeToyDropFrequency() {
        System.out.println("Чтобы изменить частоту выпадения игрушки введите ее индекс, от 0 до %d".formatted(toys.size() - 1));
        for (int i = 0; i < toys.size(); i++) {
            Toy toy = toys.get(i);
            System.out.println("%d - %s, частота выпадения %d".formatted(i, toy.getName(), toy.getDropFrequency()));
        }
        Integer index = readNumber("Нужно ввести число от 0 до %s".formatted(toys.size() - 1));
        if (index == null || index < 0 || index > toys.size() - 1) {
            System.out.println("Нужно ввести число от 0 до %s".formatted(toys.size() - 1));
            changeToyDropFrequency();
            return;
        }
        System.out.println("Введите новую частоту выпадения от 1 до 100");
        Integer newWeight = readNumber("Введите число от 1 до 100");
        if (newWeight == null || newWeight < 1 || newWeight > 100) {
            System.out.println("Нужно ввести число от 1 до 100");
            return;
        }
        toys.get(index).setDropFrequency(newWeight);
        openStore();
    }

    /**
     * Метод для получения случайной игрушки, с учетом веса
     * @return случайную игрушку
     */
    Toy getRandomToy() {
        int totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getDropFrequency();
        }
        int n = 0;
        int num = new Random().nextInt(1, totalWeight);

        Toy toy = null;

        // На всякий случай добавляем цикл, для получения игрушки, если вдруг получили null,
        // проверка на пустой список выполняется до вызовы текущего методы
        while (toy == null) {
            for (Toy t : toys) {
                n += t.getDropFrequency();
                if (n >= num) {
                    toy = t;
                    break;
                }
            }
        }
        return toy;
    }

    /**
     * Метод для чтения числа с консоли, который вводит пользователь
     * @param errorMessage - сообщение об ошибке, которое будет выводится в консоль
     * @return - число с консоли или null
     */
    private Integer readNumber(String errorMessage) {
        try {
            return Integer.parseInt(consoleReader.readLine());
        } catch (Exception e) {
            System.out.println(errorMessage);
            return null;
        }
    }

    private String readLine() {
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    private int getToysCount() {
        int count = 0;
        for (Toy toy :
                toys) {
            count += toy.getAmount();
        }
        return count;
    }

    private void addToyToFile(Toy toy) {
        try {
            writer.append(toy.getName());
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи файла =( ");
        }
    }
}
