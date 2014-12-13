import org.junit.Before;
import org.junit.Test;
import streams.FusedPullFactory;
import streams.PullFactory;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by bibou on 12/13/14.
 */
public class TestAlgebrasFused {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> new Long(i)).toArray(Long[]::new);
    }

    @Test
    public void testMultipleFilterFusedPull(){

        FusedPullFactory alg = new FusedPullFactory();

        Long actual = alg.count(
                alg.filter(x -> x > 5,
                alg.filter(x -> x > 4,
                alg.filter(x -> x > 3,
                alg.filter(x -> x > 2,
                alg.filter(x -> x > 1,
                alg.filter(x -> x > 0, alg.source(v))))))));

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x > 0)
                .filter(x -> x > 1)
                .filter(x -> x > 2)
                .filter(x -> x > 3)
                .filter(x -> x > 4)
                .filter(x -> x > 5)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultipleMapFusedPull(){

        FusedPullFactory alg = new FusedPullFactory();

        Long actual = alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1,
                alg.<Long, Long>map(x -> x + 1, alg.source(v)))))));

        Long expected = java.util.stream.Stream.of(v)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .map(x -> x + 1)
                .reduce(0L, Long::sum);

        assertEquals(expected, actual);
    }


}
