package org.diskqueue.storage.block;

public class Record {

    // record length that tell us how many bytes will be read
    private int length;
    // original body object
    private Object object;
    // serialized body represents by byte array
    private byte[] body;

    public int getLength() {
        return length;
    }

    public Record setLength(int length) {
        this.length = length;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public Record setObject(Object object) {
        this.object = object;
        return this;
    }

    public byte[] getBody() {
        return body;
    }

    public Record setBody(byte[] body) {
        this.body = body;
        return this;
    }
}