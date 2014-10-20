import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

public class TestSimplePipeline {

    public Long[] array;

    @Before
    public void setUp() {
        array = IntStream.range(0, 10000).mapToObj(i -> new Long(i % 1000)).toArray(Long[]::new);
    }
    @Test
    public void testFilter(){
        long size = Streams.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        long size2 = java.util.stream.Stream.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        assert size==size2;
    }

    @Test
    public void testMap(){
        assert false;
    }

    @Test
    public void testCount() {
        assert false;
    }
}