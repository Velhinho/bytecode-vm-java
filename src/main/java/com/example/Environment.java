package com.example;

import java.util.ArrayDeque;
import java.util.Deque;

public class Environment {
  private final Deque<Integer> stack = new ArrayDeque<>();
  private int pc = 0;
  private boolean halted = false;

  public void setHalted(boolean halted) {
    this.halted = halted;
  }

  public boolean isHalted() {
    return halted;
  }

  public int getPc() {
    return pc;
  }

  public void setPc(int pc) {
    this.pc = pc;
  }

  public void incPc() {
    pc++;
  }

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

  public int peekTop() {
    if (stack.isEmpty()) {
      throw new RuntimeException("empty stack");
    }
    return stack.peek();
  }
}
