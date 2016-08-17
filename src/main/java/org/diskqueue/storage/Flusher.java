package org.diskqueue.storage;

import org.diskqueue.option.Syncer;

import java.io.Flushable;
import java.io.IOException;

public abstract class Flusher {

    protected Flushable flushable;

    private Flusher(Flushable flushable) {
        this.flushable = flushable;
    }

    public abstract void flushAll() throws IOException;

    public static Flusher policy(Flushable flushable, Syncer syncer) {
        switch (syncer) {
        case NONE:
            return new NoneFlusher(flushable);
        case ONCE:
            return new OnceFlusher(flushable);
        case MMAP_PAGECACHE:
            return new PageCacheFlusher(flushable);
        case EVERY_SECOND:
            return new EverySecondFlusher(flushable);
        default:
            throw new UnsupportedOperationException();
        }
    }

    static class NoneFlusher extends Flusher {
        public NoneFlusher(Flushable flushable) {
            super(flushable);
        }

        @Override
        public void flushAll() {
        }
    }

    static class OnceFlusher extends Flusher {
        public OnceFlusher(Flushable flushable) {
            super(flushable);
        }

        @Override
        public void flushAll() throws IOException {
            flushable.flush();
        }
    }

    static class EverySecondFlusher extends Flusher {
        private long timestamp = System.currentTimeMillis();

        public EverySecondFlusher(Flushable flushable) {
            super(flushable);
        }

        @Override
        public void flushAll() throws IOException {
            if (System.currentTimeMillis() - timestamp >= 1000) {
                // TODO : the order of following invocation is right ?
                timestamp = System.currentTimeMillis();
                flushable.flush();
            }
        }
    }

    static class PageCacheFlusher extends Flusher {
        public PageCacheFlusher(Flushable flushable) {
            super(flushable);
        }

        @Override
        public void flushAll() throws IOException {
            // TODO : call memory map msync() here ?!
        }
    }
}