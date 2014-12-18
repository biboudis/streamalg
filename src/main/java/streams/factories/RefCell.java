package streams.factories;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class RefCell<T> {
    public T value;

    public RefCell(T value) {
        this.value = value;
    }
}