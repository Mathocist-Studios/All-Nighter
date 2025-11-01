package com.mathochist.mazegame.Rendering;

import java.util.Comparator;
import java.util.LinkedList;

public class RenderBuffer {

    private LinkedList<RenderObject> buffer;

    public RenderBuffer() {
        this.buffer = new LinkedList<RenderObject>();
    }

    public synchronized void addToBuffer(RenderObject obj) {
        this.buffer.add(obj);
    }

    public synchronized LinkedList<RenderObject> getRawBuffer() {
        return this.buffer;
    }

    public synchronized LinkedList<RenderObject> getBufferOrderedByZIndex() {
        LinkedList<RenderObject> ordered = new LinkedList<RenderObject>(this.buffer);
        ordered.sort(Comparator.comparingInt(RenderObject::getzIndex));
        return ordered;
    }

    public synchronized void clearBuffer() {
        this.buffer.clear();
    }

    public synchronized boolean isEmpty() {
        return this.buffer.isEmpty();
    }

    public synchronized int size() {
        return this.buffer.size();
    }

    public synchronized void sortBufferByZIndex() {
        this.buffer.sort((a, b) -> Integer.compare(a.getzIndex(), b.getzIndex()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RenderBuffer: [\n");
        for (RenderObject obj : this.buffer) {
            sb.append("  ").append(obj.toString()).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
