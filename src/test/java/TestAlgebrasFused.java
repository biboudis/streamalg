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
        v = IntStream.range(0, 50).mapToObj(i -> new Long(i)).toArray(Long[]::new);
    }

    @Test
    public void testMultipleFilterFusedPull(){

        FusedPullFactory alg = new FusedPullFactory();

        Long actual = alg.count(
                alg.filter(x -> x > 15,
                alg.filter(x -> x > 14,
                alg.filter(x -> x > 13,
                alg.filter(x -> x > 12,
                alg.filter(x -> x > 11,
                alg.filter(x -> x > 10, alg.source(v))))))));

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x > 10)
                .filter(x -> x > 11)
                .filter(x -> x > 12)
                .filter(x -> x > 14)
                .filter(x -> x > 15)
                .count();

        assertEquals(expected, actual);
    }


}
