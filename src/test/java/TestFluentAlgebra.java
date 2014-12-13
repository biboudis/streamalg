import org.junit.Before;
import org.junit.Test;
import streams.App;
import streams.Push;
import streams.PushFactory;
import streams.StreamAlg;

import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class TestFluentAlgebra {

    public Long[] v;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> new Long(i % 5)).toArray(Long[]::new);
    }

    @Test
    public void testFluentFilterCountPush(){

        PushFactory alg = new PushFactory();

        Function<PushFactory, Function<PushFactory, Function<PushFactory, Long>>> then =
                StreamAlg.<PushFactory, Push.t, Long>Source(v).andThen(
                        app -> StreamAlg.<PushFactory, Push.t, Long>Filter(algebra -> app, x -> x % 2 == 0).andThen(
                                app2 -> StreamAlg.<PushFactory, Push.t, Long>Count(algebra -> app2)));

        Long expected = java.util.stream.Stream.of(v)
                .filter(x -> x % 2 == 0)
                .count();

        assertEquals(expected, then.apply(alg).apply(alg).apply(alg));
    }
}
