import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import streams.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestAlgebrasLog {

    public Long[] v, v_inner;

    @Before
    public void setUp() {
        v = IntStream.range(0, 10).mapToObj(i -> new Long(i)).toArray(Long[]::new);
        v_inner = IntStream.range(0, 5).mapToObj(i -> new Long(i)).toArray(Long[]::new);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testPushLog(){
        LogFactory<Push.t> alg = new LogFactory<>(new PushFactory());

        alg.<Long>count(alg.map(x -> x + 2, alg.source(v)));

        assertEquals(
                "Starting Execution: \n" +
                "map: 0 -> 2\n" +
                "map: 1 -> 3\n" +
                "map: 2 -> 4\n" +
                "map: 3 -> 5\n" +
                "map: 4 -> 6\n" +
                "map: 5 -> 7\n" +
                "map: 6 -> 8\n" +
                "map: 7 -> 9\n" +
                "map: 8 -> 10\n" +
                "map: 9 -> 11\n",
                outContent.toString());
    }

    @Test
    public void testPullLog(){
        LogFactory<Pull.t> alg = new LogFactory<>(new PullFactory());

        alg.<Long>count(alg.map(x -> x + 2, alg.source(v)));

        assertEquals(
                "Starting Execution: \n" +
                        "map: 0 -> 2\n" +
                        "map: 1 -> 3\n" +
                        "map: 2 -> 4\n" +
                        "map: 3 -> 5\n" +
                        "map: 4 -> 6\n" +
                        "map: 5 -> 7\n" +
                        "map: 6 -> 8\n" +
                        "map: 7 -> 9\n" +
                        "map: 8 -> 10\n" +
                        "map: 9 -> 11\n",
                outContent.toString());
    }
}