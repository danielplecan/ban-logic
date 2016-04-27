package org.banlogic.model;

import java.util.List;
import lombok.Data;

@Data
public class ProtocolStep {

    private String sender;
    private String receiver;
    private List<String> messages;
}
