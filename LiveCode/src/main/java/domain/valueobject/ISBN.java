package domain.valueobject;

// TOGAF: Business Architecture — Value Object
// Value Object: no identity, equality by value
public class ISBN {

    private final String value;


    public ISBN(String value) {
        if (value == null || !value.matches("\\d{3}-\\d{10}")) {
            throw new IllegalArgumentException(
                    "Invalid ISBN: " + value + ". Format: 978-XXXXXXXXXX"
            );
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        return value.equals(((ISBN) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}