package JavaToyStore;

public class Toy {
    int id;
    String name;
    int amount;
    float toyDropFrequency;

    public Toy(int id, String name, int amount, float toyDropFrequency) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.toyDropFrequency = toyDropFrequency;

    }

    @Override
    public String toString() {
        return "Toy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", toyDropFrequency=" + toyDropFrequency +
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

    public float getToyDropFrequency() {
        return toyDropFrequency;
    }

    public void setToyDropFrequency(float toyDropFrequency) {
        this.toyDropFrequency = toyDropFrequency;
    }
}
