package benchmarks;

import org.openjdk.jmh.annotations.*;
import streams.FusedPullFactory;
import streams.PullFactory;
import streams.PushFactory;

import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class Benchmark_SimpleBoxedPipelines {

    // For map, count, operations
    private static int N =  Integer.getInteger("benchmark.N", 1000);
    // For filtering operations
    private static int F =  Integer.getInteger("benchmark.F", 1000);
    // For cartesian product operations
    private static int N_outer =  Integer.getInteger("benchmark.N_outer", 100);
    private static int N_inner =  Integer.getInteger("benchmark.N_inner", 10);

    public Long[] v, v_outer, v_inner, v_forSorting_Baseline ,v_forSorting_Algebras, v_forSorting_Java8Streams;
    public Long[] v_for_megamorphic_filter;

    public Long[] fillArray(int range){
        Long[] array = new Long[range];
        for (int i = 0; i < range; i++) {
            array[i] = i % 1000L;
        }
        return array;
    }

    @Setup
    public void setUp() {
        v  = fillArray(N);
        v_outer = fillArray(N_outer);
        v_inner = fillArray(N_inner);
        v_forSorting_Baseline = fillArray(N);
        v_forSorting_Java8Streams = fillArray(N);
        v_forSorting_Algebras = fillArray(N);
        v_for_megamorphic_filter = fillArray(F);
    }

    @Benchmark
    public Long filter_count_Baseline() {
        Long value = 0L;
        for (int i =0 ; i < v.length ; i++) {
            if (v[i] % 2L == 0L)
                value ++;
        }
        return value;
    }

    @Benchmark
    public Long cart_Baseline() {
        Long value = 0L;
        for (int d = 0 ; d < v_outer.length ; d++) {
            for (int dp = 0 ; dp < v_inner.length ; dp++){
                value += v_outer[d] * v_inner[dp];
            }
        }
        return value;
    }

    @Benchmark
    public Long filter_count_Java8Streams() {
        Long value = java.util.stream.Stream.of(v)
                .filter(x -> x % 2L == 0L)
                .count();

        return value;
    }

    @Benchmark
    public Long cart_Java8Streams() {
        Long value = Stream.of(v_outer)
                .flatMap(d -> Stream.of(v_inner).map(dP -> dP * d))
                .reduce(0L, Long::sum);

        return value;
    }

    @Benchmark
    public Long filter_count_Algebras_Push() {
        PushFactory alg = new PushFactory();

        Long value = alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)));

        return value;
    }

    @Benchmark
    public Long cart_Algebras_Push() {

        PushFactory alg = new PushFactory();

        Long value =  alg.<Long>reduce(0L, Long::sum, alg.flatMap(x -> {
            PushFactory inner = new PushFactory();
            return inner.map(y -> x * y, alg.source(v_inner));
        }, alg.source(v_outer)));

        return value;
    }

    @Benchmark
    public Long filter_count_Algebras_Pull() {
        PullFactory alg = new PullFactory();

        Long value = alg.count(alg.filter(x -> x % 2L == 0, alg.source(v)));

        return value;
    }

    @Benchmark
    public Long cart_Algebras_Pull() {

        PullFactory alg = new PullFactory();

        Long value = alg.<Long>reduce(0L, Long::sum, alg.flatMap(x -> {
            PullFactory inner = new PullFactory();
            return inner.<Long, Long>map(y -> x * y, alg.source(v_inner));
        }, alg.source(v_outer)));

        return value;
    }

    @Benchmark
    public Long filters_Algebras_NotFusedPull() {
        PullFactory alg = new PullFactory();

        Long value = alg.count(
                alg.filter(x -> x > 7,
                        alg.filter(x -> x > 6,
                                alg.filter(x -> x > 5,
                                        alg.filter(x -> x > 4,
                                                alg.filter(x -> x > 3,
                                                        alg.filter(x -> x > 2,
                                                                alg.filter(x -> x > 1,
                                                                        alg.filter(x -> x > 0, alg.source(v_for_megamorphic_filter))))))))));

        return value;
    }


    @Benchmark
    public Long filters_Algebras_FusedPull() {
        FusedPullFactory alg = new FusedPullFactory();

        Long value = alg.count(
                alg.filter(x -> x > 7,
                        alg.filter(x -> x > 6,
                                alg.filter(x -> x > 5,
                                        alg.filter(x -> x > 4,
                                                alg.filter(x -> x > 3,
                                                        alg.filter(x -> x > 2,
                                                                alg.filter(x -> x > 1,
                                                                        alg.filter(x -> x > 0, alg.source(v_for_megamorphic_filter))))))))));

        return value;
    }

    @Benchmark
    public Long maps_Algebras_NotFusedPull() {
        PullFactory alg = new PullFactory();

        Long value = alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                        alg.<Long, Long>map(x -> x + 1,
                                alg.<Long, Long>map(x -> x + 1,
                                        alg.<Long, Long>map(x -> x + 1,
                                                alg.<Long, Long>map(x -> x + 1,
                                                        alg.<Long, Long>map(x -> x + 1,
                                                                alg.<Long, Long>map(x -> x + 1, alg.source(v)))))))));

        return value;
    }

    @Benchmark
    public Long maps_Algebras_FusedPull() {
        FusedPullFactory alg = new FusedPullFactory();

        Long value = alg.reduce(0L, Long::sum,
                alg.<Long, Long>map(x -> x + 1,
                        alg.<Long, Long>map(x -> x + 1,
                                alg.<Long, Long>map(x -> x + 1,
                                        alg.<Long, Long>map(x -> x + 1,
                                                alg.<Long, Long>map(x -> x + 1,
                                                        alg.<Long, Long>map(x -> x + 1,
                                                                alg.<Long, Long>map(x -> x + 1, alg.source(v)))))))));

        return value;
    }
}
