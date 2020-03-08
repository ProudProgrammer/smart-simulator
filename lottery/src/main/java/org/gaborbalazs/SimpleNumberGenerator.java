package org.gaborbalazs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class SimpleNumberGenerator {

    private final Random random;
    private final MessageFactory messageFactory;

    SimpleNumberGenerator(Random threadLocalRandom, MessageFactory messageFactory) {
        this.random = threadLocalRandom;
        this.messageFactory = messageFactory;
    }

    /**
     * Number generator method based on quantity and pool size.
     * It draws as many numbers as quantity from a pool with lower limit of 1 (inclusive) and upper limit of pool size (inclusive).
     *
     * @param quantity is the number of drawn numbers
     * @param poolSize is the upper limit (inclusive) of drawn numbers
     * @return is set of drawn numbers
     * @throws IllegalArgumentException if quantity is larger or equals than pool size
     * @throws IllegalArgumentException if pool size is larger than 1000
     * @throws IllegalArgumentException if quantity is 0
     */
    public SortedSet<Integer> generate(int quantity, int poolSize) throws IllegalArgumentException {
        return generate(quantity, 1, poolSize);
    }

    /**
     * Number generator method based on quantity, lower limit and upper limit.
     * It draws as many numbers as quantity from a pool with lower limit (inclusive) and upper limit (inclusive).
     *
     * @param quantity   is the number of drawn numbers
     * @param lowerLimit is the lower limit (inclusive) of drawn numbers
     * @param upperLimit is the upper limit (inclusive) of drawn numbers
     * @return is set of drawn numbers
     * @throws IllegalArgumentException if quantity is larger or equals than pool size
     * @throws IllegalArgumentException if pool size is larger than 1000
     * @throws IllegalArgumentException if quantity is 0
     */
    public SortedSet<Integer> generate(int quantity, int lowerLimit, int upperLimit) throws IllegalArgumentException {
        int poolSize = upperLimit - lowerLimit + 1;
        validate(quantity, poolSize);
        SortedSet<Integer> result = new TreeSet<>();
        List<Integer> pool = createFilledPoolWithNumbersBetweenLimits(lowerLimit, upperLimit);
        int poolIndex;
        for (int i = 0; i < quantity; i++) {
            Collections.shuffle(pool, random);
            poolIndex = random.nextInt(poolSize - i);
            result.add(pool.remove(poolIndex));
        }
        return result;
    }

    private List<Integer> createFilledPoolWithNumbersBetweenLimits(int lowerLimit, int upperLimit) {
        List<Integer> pool = new ArrayList<>(upperLimit - lowerLimit);
        for (int i = lowerLimit; i <= upperLimit; i++) {
            pool.add(i);
        }
        return pool;
    }

    private void validate(int quantity, int poolSize) throws IllegalArgumentException {
        if (quantity == 0) {
            throw new IllegalArgumentException("Quantity must not be 0.");
        } else if (poolSize > 1000) {
            String msg = messageFactory.create("Pool size must not be larger than {0}. Pool size: {1}", 1000, poolSize);
            throw new IllegalArgumentException(msg);
        } else if (poolSize <= quantity) {
            String msg = messageFactory.create("Pool size must be larger than quantity. Quantity: {0}, pool size: {1}", quantity, poolSize);
            throw new IllegalArgumentException(msg);
        }
    }
}
