package kz.javaworkshop;

public final class Item {
    private final String name;
    private final String type;
    private final int weightGrams;
    private final int value;

    public Item(String name, String type, int weightGrams, int value) {
        if (name == null || name.strip().isEmpty() || name.strip().length() > 40) {
            throw new IllegalArgumentException("Имя предмета должно содержать от 1 до 40 символов.");
        }
        if (type == null || (!type.equals("weapon") && !type.equals("armor") && !type.equals("potion"))) {
            throw new IllegalArgumentException("Тип предмета должен быть 'weapon', 'armor' или 'potion'.");
        }
        if (weightGrams < 1 || weightGrams > 100000) {
            throw new IllegalArgumentException("Масса должна быть в диапазоне от 1 до 100000 граммов.");
        }
        if (value < 0 || value > 1000000) {
            throw new IllegalArgumentException("Стоимость должна быть в диапазоне от 0 до 1000000 монет.");
        }

        this.name = name.strip();
        this.type = type;
        this.weightGrams = weightGrams;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " [" + type + "], " + weightGrams + " г, " + value + " монет";
    }
}