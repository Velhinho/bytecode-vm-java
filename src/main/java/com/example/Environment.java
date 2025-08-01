package com.example;

import java.util.ArrayDeque;
import java.util.Deque;

public class Environment {
  private final Deque<Integer> stack = new ArrayDeque<>();

  public void push(int n) {
    stack.push(n);
    System.out.println("stack = " + stack);
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw new RuntimeException("empty stack");
    }
    return stack.pop();
  }

  public void printTop() {
    if (stack.isEmpty()) {
      throw new RuntimeException("empty stack");
    }
    System.out.println(stack.peek());
  }
}
