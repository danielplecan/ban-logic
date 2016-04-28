package org.banlogic.inference;

import java.util.List;
import org.banlogic.inference.rules.InferJurisdiction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.banlogic.inference.rules.InferMessageMeaningForPublicKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedKeys;
import org.banlogic.inference.rules.InferMessageMeaningForSharedSecrets;
import org.banlogic.inference.rules.InferNonceVerification;
import org.banlogic.inference.rules.InferenceRule;
import org.junit.Test;

public class InferenceSystemTest {

    private final InferenceRule inferMessageMeaningForPublicKeys = new InferMessageMeaningForPublicKeys();
    private final InferenceRule inferMessageMeaningForSharedKeys = new InferMessageMeaningForSharedKeys();
    private final InferenceRule inferMessageMeaningForSharedSecrets = new InferMessageMeaningForSharedSecrets();
    private final InferenceRule inferNonceVerification = new InferNonceVerification();
    private final InferenceRule inferJurisdiction = new InferJurisdiction();

    @Test
    public void testMessageMeaningForSharedKey() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("B|=A<-K->B", "B<|{X}_K");

        assertEquals(1, inferedMessages.size());
        assertEquals("B|=A|~X", inferedMessages.get(0));
    }
    
    @Test
    public void testMessageMeaningForReverseSharedKey() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("B|=B<-K->A", "B<|{X}_K");

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
    public void testMessageMeaningForReverseSharedSecret() {
        List<String> inferedMessages = inferMessageMeaningForSharedSecrets.apply("B|=B<=K=>A", "B<|<X>_K");

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

    @Test
    public void testNonceVerification() {
        List<String> inferedMessages = inferNonceVerification.apply("P|=#(XX)", "P|=Q|~XX");

        assertEquals(1, inferedMessages.size());
        assertEquals("P|=Q|=XX", inferedMessages.get(0));
    }

    @Test
    public void testJurisdiction() {
        List<String> inferedMessages = inferJurisdiction.apply("P|=Q=>X", "P|=Q|=X");

        assertEquals(1, inferedMessages.size());
        assertEquals("P|=X", inferedMessages.get(0));
    }

    @Test
    public void testJurisdictionForMultipleMessages() {
        List<String> inferedMessages = inferJurisdiction.apply("P|=Q=>X,Y", "P|=Q|=X,Y");

        assertEquals(3, inferedMessages.size());
        assertTrue(inferedMessages.contains("P|=X"));
        assertTrue(inferedMessages.contains("P|=X,Y"));
        assertTrue(inferedMessages.contains("P|=Y"));
    }
    
    @Test
    public void test() {
        List<String> inferedMessages = inferMessageMeaningForSharedKeys.apply("A|=S<-Kas->A", "A<|{Ts,A<-Kab->B,{Ts,A<-Kab->B}_Kbs}_Kas");

        assertEquals(7, inferedMessages.size());
//        assertTrue(inferedMessages.contains("B|=A|~X,{Y}_Z"));
//        assertTrue(inferedMessages.contains("B|=A|~{Y}_Z"));
//        assertTrue(inferedMessages.contains("B|=A|~X"));
    }
}
