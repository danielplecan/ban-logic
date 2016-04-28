package org.banlogic.inference;

import java.util.List;
import static org.banlogic.inference.InferenceSystem.inferMessageMeaningForPublicKeys;
import static org.banlogic.inference.InferenceSystem.inferMessageMeaningForSharedKeys;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class InferenceSystemTest {

    @Test
    public void testMessageMeaningForSharedKey() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys("B|=A<-K->B", "B<|{X}_K");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }

    @Test
    public void testMessageMeaningForSharedKeyAndComposedMessage() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys("B|=A<-K->B", "B<|{X,{Y}_Z}_K");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
    
    @Test
    public void testMessageMeaningForSharedKeyAndMultipleMessages() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys("B|=A<-K->B", "B<|{X}_K,{Y}_K");

        assertEquals(0, inferedMessages.size());
    }

    @Test
    public void testMessageMeaningForPublicKey() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys("B|=pk(A,K)", "B<|{X}_inv(K)");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }

    @Test
    public void testMessageMeaningForPublicKeyAndComposedMessage() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys("B|=pk(A,K)", "B<|{X,{Y}_Z}_inv(K)");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
    
    @Test
    public void testMessageMeaningForPublicKeyAndMultipleMessage() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys("B|=pk(A,K)", "B<|{X}_inv(K),{Y}_inv(K)");

        assertEquals(0, inferedMessages.size());
    }
}
