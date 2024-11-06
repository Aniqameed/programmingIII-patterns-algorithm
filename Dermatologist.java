public class Dermatologist {
    private String id;
    private String name;
    private String specialization;

    public Dermatologist(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}
