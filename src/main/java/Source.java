import java.util.function.Consumer;

/**
 * Created by bibou on 10/14/14.
 */
public class Source<T> extends Stream<T> {

    final T[] array;

    public Source(T[] array) {
        this.array = array;
    }

    public T[] getArray() {
        return array;
    }

    @Override
    Consumer<Consumer<T>> push() {
        return k -> {
            for(int i=0 ; i < array.length ; i++){
                k.accept(array[i]);
            }
        };
    }
}
