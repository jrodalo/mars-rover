package es.leanmind.marsrover.usecases.factories;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class RandomFactory {
    private static final Random randomNumberGenerator = new Random();

    public int generateNumber(int lowerLimit, int upperLimit) {
        return randomNumberGenerator.nextInt(lowerLimit, upperLimit);
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
