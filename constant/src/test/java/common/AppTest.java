package common;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String strToken = UUID.randomUUID().toString();
        assertTrue( true );
    }
}
