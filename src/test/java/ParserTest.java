import org.banlogic.parser.ProtocolParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Alexandru on 4/26/2016.
 */
public class ParserTest {

    private String toBeParsed="{X}_k,{Y}_p,{{Inside}_i,blals}_a";
    private String toBeParsedAtomicEntries="{X}_b,{Y}_e,{Z}_c,{V}_d";
    private String toBeParsedSimple="{X,Y}_k";
    private String toBeParsedAtomic="{{X}_t,Y}_k,A,{Z}_c";


    @Test
    public void shouldParseOneEntry(){
        List<String> tokens = ProtocolParser.parseMessage(toBeParsedSimple);
        System.out.println(tokens);
        Assert.assertTrue(tokens.size() == 1);
    }

    @Test
    public void shouldParseAllEntries(){
        List<String> tokens = ProtocolParser.parseMessage(toBeParsed);
        System.out.println(tokens);
        Assert.assertTrue(tokens.size() == 3);
    }

    @Test
    public void shouldParseAllAtomicEntries(){
        List<String> tokens = ProtocolParser.parseMessage(toBeParsedAtomicEntries);
        System.out.println(tokens);
        Assert.assertTrue(tokens.size() == 4);
    }

    @Test
    public void shouldParseAllAtomicEntriesCombined(){
        List<String> tokens = ProtocolParser.parseMessage(toBeParsedAtomic);
        System.out.println(tokens);
        Assert.assertTrue(tokens.size() == 3);
    }
}
