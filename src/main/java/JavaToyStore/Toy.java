package JavaToyStore;

public class Toy {
    private int id;
    private String name;
    private int amount;
    private int dropFrequency;

    public Toy(int id, String name, int amount, int dropFrequency) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dropFrequency = dropFrequency;

    }

    @Override
    public String toString() {
        return "Toy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", toyDropFrequency=" + dropFrequency +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getDropFrequency() {
        return dropFrequency;
    }

    public void setDropFrequency(int dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    public void decreaseAmount() {
        amount--;
    }
}
