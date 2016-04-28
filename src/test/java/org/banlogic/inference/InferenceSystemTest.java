package org.banlogic.inference;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.banlogic.inference.rules.InferMessageMeaningForPublicKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedSecrets;
import org.banlogic.inference.rules.InferenceRule;
import org.junit.Test;

public class InferenceSystemTest {

    private final InferenceRule inferMessageMeaningForPublicKeys = new InferMessageMeaningForPublicKeys();
    private final InferenceRule inferMessageMeaningForSharedKeys = new InferMessageMeaningForSharedKeys();
    private final InferenceRule inferMessageMeaningForSharedSecrets = new InferMessageMeaningForSharedSecrets();

    @Test
    public void testMessageMeaningForSharedKey() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("B|=A<-K->B", "B<|{X}_K");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }

    @Test
    public void testMessageMeaningForSharedKeyAndComposedMessage() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("B|=A<-K->B", "B<|{X,{Y}_Z}_K");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
    
    @Test
    public void testMessageMeaningForSharedKeyAndMultipleMessages() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("B|=A<-K->B", "B<|{X}_K,{Y}_K");

        assertEquals(0, inferedMessages.size());
    }

    @Test
    public void testMessageMeaningForPublicKey() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys.apply("B|=pk(A,K)", "B<|{X}_inv(K)");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }

    @Test
    public void testMessageMeaningForPublicKeyAndComposedMessage() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys.apply("B|=pk(A,K)", "B<|{X,{Y}_Z}_inv(K)");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
    
    @Test
    public void testMessageMeaningForPublicKeyAndMultipleMessage() {
        List<String> inferedMessages = inferMessageMeaningForPublicKeys.apply("B|=pk(A,K)", "B<|{X}_inv(K),{Y}_inv(K)");

        assertEquals(0, inferedMessages.size());
    }
    
    @Test
    public void testMessageMeaningForSharedSecret() {
        List<String> inferedMessages = inferMessageMeaningForSharedSecrets.apply("B|=A<=K=>B", "B<|<X>_K");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }

    @Test
    public void testMessageMeaningForSharedSecretAndComposedMessage() {
        List<String> inferedMessages = inferMessageMeaningForSharedSecrets.apply("B|=A<=K=>B", "B<|<X,{Y}_Z>_K");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
    
    @Test
    public void testMessageMeaningForSharedSecretAndMultipleMessages() {
        List<String> inferedMessages = inferMessageMeaningForSharedSecrets.apply("B|=A<-K->B", "B<|<X>_K,<Y>_K");

        assertEquals(0, inferedMessages.size());
    }
}
