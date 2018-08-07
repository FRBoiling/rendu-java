package gate;

import static org.junit.Assert.assertTrue;

import core.base.common.AbstractSession;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class GlobalServiceTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        ConcurrentHashMap<Integer,Integer> connectingList = new ConcurrentHashMap<>(12);
        connectingList.put(1,11);
        connectingList.put(2,22);
        connectingList.put(3,33);
        connectingList.put(4,33);

        Set<Integer> allSession = new HashSet<>();
        allSession.addAll(connectingList.values());

        assertTrue( true );
    }
}
