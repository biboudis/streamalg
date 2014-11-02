import com.google.common.collect.Iterators;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestSimplePipeline {

    public Long[] array;

    @Before
    public void setUp() {
        array = IntStream.range(0, 10000).mapToObj(i -> new Long(i % 1000)).toArray(Long[]::new);
    }

    @Test
    public void testFilterPush(){
        long size = Streams.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        long size2 = java.util.stream.Stream.of(array)
                .filter(x -> x % 2L == 0L)
                .count();

        assert size==size2;
    }

    @Test
    public void testMapPush(){

        long size = Streams.of(array)
                .map(x -> x + 1L)
                .count();

        long size2 = java.util.stream.Stream.of(array)
                .map(x -> x + 1L)
                .count();

        assert size==size2;
    }

    @Test
    public void testFilterPull(){
        Iterator<Long> it1 = Streams.of(array)
                .filter(x -> x % 2L == 0L)
                .iterator();

        Iterator<Long> it2 = java.util.stream.Stream.of(array)
                .filter(x -> x % 2L == 0L)
                .iterator();

        ArrayList<Long> l1 = new ArrayList<>();
        ArrayList<Long> l2 = new ArrayList<>();

        Iterators.addAll(l1, it1);
        Iterators.addAll(l2, it2);

        assertEquals(l1, l2) ;
    }

    @Test
    public void testMapPull(){
        Iterator<Long> it1 = Streams.of(array)
                .map(x -> x + 1)
                .iterator();

        Iterator<Long> it2 = java.util.stream.Stream.of(array)
                .map(x -> x + 1)
                .iterator();

        ArrayList<Long> l1 = new ArrayList<>();
        ArrayList<Long> l2 = new ArrayList<>();

        Iterators.addAll(l1, it1);
        Iterators.addAll(l2, it2);

        assertEquals(l1, l2) ;
    }
}