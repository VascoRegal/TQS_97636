package com.stack.app;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TQSStack<T> {
    private Integer bound;
    private LinkedList<T> list;
    
    public TQSStack() {
        this.bound =  null;
        this.list = new LinkedList<>();
    };

    public TQSStack(int bound) {
        this.bound = bound;
        this.list = new LinkedList<>();
    };

    public void push(T element) {
        if ((this.bound != null) && ((this.size() + 1 > this.bound)) ) {
            throw new IllegalStateException();
        }


        list.add(element);
    }

    public T pop() {
        if (this.list.isEmpty()) {
            throw new NoSuchElementException();
        }


        return this.list.pop();
    }

    public T peek() {
        if (this.list.isEmpty()) {
            throw new NoSuchElementException();
        }

        return this.list.getLast();
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }
}
