package kz.javaworkshop;

import java.util.Arrays;
import java.util.Locale;

public class Inventory {
    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int capacity, int maxWeightGrams) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException("Ёмкость инвентаря должна быть от 1 до 20.");
        }
        if (maxWeightGrams < 1 || maxWeightGrams > 100000) {
            throw new IllegalArgumentException("Лимит массы должен быть от 1 до 100000 граммов.");
        }
        this.items = new Item[capacity];
        this.size = 0;
        this.maxWeightGrams = maxWeightGrams;
    }

    public boolean add(Item item) {
        if (item == null) {
            return false;
        }
        if (size >= items.length) {
            return false;
        }
        if (totalWeightGrams() + item.getWeightGrams() > maxWeightGrams) {
            return false;
        }

        items[size] = item;
        size++;
        return true;
    }

    public Item remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Item removedItem = items[index];

        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }

        items[size - 1] = null;
        size--;

        return removedItem;
    }

    public Item get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[index];
    }

    public int size() {
        return size;
    }

    public int totalWeightGrams() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += items[i].getWeightGrams();
        }
        return sum;
    }

    public long totalValue() {
        long sum = 0;
        for (int i = 0; i < size; i++) {
            sum += items[i].getValue();
        }
        return sum;
    }

    public Item[] snapshot() {
        return Arrays.copyOf(items, size);
    }

    public int getMaxWeightGrams() {
        return maxWeightGrams;
    }

    public int getCapacity() {
        return items.length;
    }

    public Item[] findByName(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Поисковый запрос не может быть null.");
        }

        String normalizedQuery = query.strip().toLowerCase(Locale.ROOT);
        
        Item[] tempMatches = new Item[size];
        int matchCount = 0;

        for (int i = 0; i < size; i++) {
            String normalizedName = items[i].getName().toLowerCase(Locale.ROOT);
            if (normalizedName.contains(normalizedQuery)) {
                tempMatches[matchCount] = items[i];
                matchCount++;
            }
        }

        return Arrays.copyOf(tempMatches, matchCount);
    }
}