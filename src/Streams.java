/**
 * Created by bibou on 10/14/14.
 */
public class Streams {
    public static <T> Stream<T> of(T[] src) {
        return new Source(src);
    }
}