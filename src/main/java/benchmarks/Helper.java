package benchmarks;

/**
 * Authors:
 * Aggelos Biboudis (@biboudis)
 * Nick Palladinos (@NickPalladinos)
 */
public class Helper {

    public static Long[] fillArray(int range){
        Long[] array = new Long[range];
        for (int i = 0; i < range; i++) {
            array[i] = i % 1000L;
        }
        return array;
    }

}
