package base;

import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.*;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class BaseTest {
    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

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


    protected <T> T runWithExpectedException(Callable<T> task) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<T> result = service.submit(task);

        return result.get(1000, TimeUnit.MILLISECONDS);
    }
}
