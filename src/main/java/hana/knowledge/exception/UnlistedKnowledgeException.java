package hana.knowledge.exception;

import hana.common.exception.BaseCodeException;

public class UnlistedKnowledgeException extends BaseCodeException {
    public UnlistedKnowledgeException() {
        super(KnowledgeHttpCode.UNLISTED_KNOWLEDGE);
    }
}
