import java.util.function.Function;

/**
 * Created by bibou on 10/14/14.
 */
public class Map<T,R> extends Stream<R> implements StreamStep {

    final Function<T,R > mapper;
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
    public <Result> Result accept(StreamVisitor<Result> streamVisitor) {
        return streamVisitor.visit(this);
    }
}
