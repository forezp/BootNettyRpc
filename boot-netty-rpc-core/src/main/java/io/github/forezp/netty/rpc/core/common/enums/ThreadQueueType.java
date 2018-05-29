package io.github.forezp.netty.rpc.core.common.enums;


public enum ThreadQueueType {
    LINKED_BLOCKING_QUEUE( "LinkedBlockingQueue" ),
    ARRAY_BLOCKING_QUEUE( "ArrayBlockingQueue" ),
    SYNCHRONOUS_QUEUE( "SynchronousQueue" );

    private String value;

    ThreadQueueType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ThreadQueueType fromString(String value) {
        for (ThreadQueueType type : ThreadQueueType.values()) {
            if (type.getValue().equalsIgnoreCase( value.trim() )) {
                return type;
            }
        }

        throw new IllegalArgumentException( "Mismatched type with value=" + value );
    }

    @Override
    public String toString() {
        return value;
    }
}