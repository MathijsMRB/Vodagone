package Vodagone.service;

import java.util.Random;

public class TokenGenerator {
    private TokenGenerator(){
    }

    public static String generateToken(){
        int x = new Random().nextInt(9999-1000)+1000;
        int y = new Random().nextInt(9999-1000)+1000;
        int z = new Random().nextInt(9999-1000)+1000;
        return String.format("%d-%d-%d", x, y, z);
    }
}
