import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by bibou on 10/14/14.
 */
public class Map<T,R> extends Stream<R> {

    final Function<T,R> mapper;
    final Stream<T> stream;

    public Map(Function<T, R> mapper, Stream<T> stream) {
        this.mapper = mapper;
        this.stream = stream;
    }

    public Function<T, R> getMapper() {
        return mapper;
    }

    public Stream<T> getStream() {
        return stream;
    }

    @Override
    <C> App<C, R> accept(StreamVisitor<C> visitor) {
        return visitor.visit(this);
    }
}
