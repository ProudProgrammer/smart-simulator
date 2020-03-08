package org.gaborbalazs;

import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Runner {

    private SimpleNumberGenerator simpleNumberGenerator;

    public Runner(SimpleNumberGenerator simpleNumberGenerator) {
        this.simpleNumberGenerator = simpleNumberGenerator;
    }

    public static void main(String[] args) {
        System.out.println(">> lottery simulation started...");

        Runner runner = createRunner();
        Set<Integer> chosenNumbers = runner.simpleNumberGenerator.generate(5, 90);
        Set<Integer> drawnNumbers = runner.simpleNumberGenerator.generate(5, 90);
        int counter = 1;
        while (!chosenNumbers.equals(drawnNumbers)) {
            drawnNumbers = runner.simpleNumberGenerator.generate(5, 90);
            counter++;
        }
        System.out.println("Chosen numbers: " + chosenNumbers + ", Drawn numbers: " + drawnNumbers);
        System.out.println("Weeks: " + counter + ", Years: " + counter/52);

        System.out.println(">> lottery simulation stopped...");
    }

    private static Runner createRunner() {
        Random random = ThreadLocalRandom.current();
        MessageFactory messageFactory = new MessageFactory(Locale.getDefault());
        SimpleNumberGenerator simpleNumberGenerator = new SimpleNumberGenerator(random, messageFactory);
        return new Runner(simpleNumberGenerator);
    }
}
