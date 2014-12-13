package streams;
/**
 * Authors:
 *      Aggelos Biboudis (@biboudis)
 *      Nick Palladinos (@NickPalladinos)
 */
public class RefCell<T> {
    public T value;
    RefCell(T value) {
        this.value = value;
    }
}