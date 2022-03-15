package com.stack.app;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TQSStackTest {

    private TQSStack<String> stack;

    @BeforeEach
    void init() {
        stack = new TQSStack<String>();
    }

    @DisplayName("Stack should be empty on init")
    @org.junit.jupiter.api.Test
    public void emptyOnInit()
    {
        assertTrue( stack.isEmpty() );
    }

    @DisplayName("Stack should have size 0 on init")
    @Test
    public void sizeOnInit()
    {
        assertTrue( stack.size() == 0 );
    }

    @DisplayName("Stack should be have size N after N pushes")
    @Test
    public void sizeAfterNPushes()
    {
        String elem;
        int N = (int) ((Math.random() * (10 - 1)) + 1);
        
        for (int i = 0; i < N; i++) {
            elem = String.valueOf(i);
            stack.push(elem);
        }

        assertTrue( (!stack.isEmpty()) && (stack.size() == N) );
    }

    @DisplayName("Stack should pop X after pushing X")
    @Test
    public void popAfterPush()
    {
        String elem = "teste";
        stack.push(elem);
        assertTrue( stack.pop().equals(elem) );
    }

    @DisplayName("Stack peek should show last push and size stays the same")
    @Test
    public void peekAfterPush()
    {
        int sizeBeforePeek;
        String elem, peek;

        elem = "teste";
        stack.push(elem);
        sizeBeforePeek = stack.size();
        peek = stack.peek();

        assertTrue( (peek == elem) && (stack.size() == sizeBeforePeek) );
    }

    @DisplayName("Stack with size N should have size 0 after N pops")
    @Test
    public void emptyAfterPops()
    {
        String elem;
        int N = (int) ((Math.random() * (10 - 1)) + 1);
        
        for (int i = 0; i < N; i++) {
            elem = String.valueOf(i);
            stack.push(elem);
        }

        for (int i = 0; i < N; i++) {
            stack.pop();
        }

        assertTrue( stack.size() == 0 );
    }

    @DisplayName("Empty stack throws exception on pop")
    @Test
    public void exceptionOnPop()
    {
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @DisplayName("Empty stack throws exception on peel")
    @Test
    public void exceptionOnPeek()
    {
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @DisplayName("Bounded stack max length")
    @Test
    public void boundPush()
    {
        TQSStack boundStack = new TQSStack<>(5);

        boundStack.push("a");
        boundStack.push("a");
        boundStack.push("a");
        boundStack.push("a");
        boundStack.push("a");
        

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            boundStack.push("a");
        });
    }
}
