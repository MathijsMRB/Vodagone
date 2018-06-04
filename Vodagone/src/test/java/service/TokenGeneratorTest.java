package service;


import Vodagone.service.TokenGenerator;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TokenGeneratorTest {
    private final String token = TokenGenerator.generateToken();

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[100][0];
    }

    @Test
    public void shouldReturnInCorrectPattern() {
        boolean equalsPattern;
        equalsPattern = token.matches("\\d\\d\\d\\d-\\d\\d\\d\\d-\\d\\d\\d\\d");
        assertTrue(equalsPattern);
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<TokenGenerator> constructor = TokenGenerator.class.getDeclaredConstructor();
        TestCase.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}

