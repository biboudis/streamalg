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
    <C> App<C, T> fold(StreamAlg<C> algebra) {
        return algebra.source(array);
    }
}
