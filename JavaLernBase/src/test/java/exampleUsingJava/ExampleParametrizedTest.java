package exampleUsingJava;

import org.examples.stepickTask.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleParametrizedTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    private void provideInput(byte[] bytes) {
        System.setIn(new ByteArrayInputStream(bytes));
    }

    private byte[] getOutput() {
        return testOut.toByteArray();
    }

    @BeforeEach
    public void setupOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataTestMain")
    void testMain(byte[] testInput, byte[] expected) {
        provideInput(testInput);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(getOutput()).isEqualTo(expected);
    }

    @AfterEach
    public void restoreSystemInOut() {
        System.setIn(systemIn);
        System.setErr(systemOut);
    }
    private static Object[][] provideTestDataTestMain() {
        return new Object[][]{
                {
                        new byte[]{},
                        new byte[]{}
                },
                {
                        new byte[]{1, 2, 3},
                        new byte[]{1, 2, 3}
                },
                {
                        new byte[]{13, 10, 1, 2, 3, 4, 5, 6},
                        new byte[]{10, 1, 2, 3, 4, 5, 6}
                },
                {
                        new byte[]{1, 2, 3, 13, 10, 4, 5, 6},
                        new byte[]{1, 2, 3, 10, 4, 5, 6}
                },
                {
                        new byte[]{1, 2, 3, 4, 5, 6, 13, 10},
                        new byte[]{1, 2, 3, 4, 5, 6, 10}
                },
                {
                        new byte[]{1, 2, 3, 13, 13, 10, 4, 5, 6},
                        new byte[]{1, 2, 3, 13, 10, 4, 5, 6}
                },
                {
                        new byte[]{1, 2, 3, 10, 13, 4, 5, 6},
                        new byte[]{1, 2, 3, 10, 13, 4, 5, 6}
                },
                {
                        new byte[]{1, 2, 3, 13, 10, 13, 10, 4, 5, 6},
                        new byte[]{1, 2, 3, 10, 10, 4, 5, 6}
                },
                {
                        new byte[]{65, 13, 10, 10, 13},
                        new byte[]{65, 10, 10, 13}
                },
                {
                        new byte[]{65, 66, 13, 13, 10, 10, 13, 67, 13, 13},
                        new byte[]{65, 66, 13, 10, 10, 13, 67, 13, 13}
                },
        };
    }
}
