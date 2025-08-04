package com.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Optional;

public class Environment {
  private final Deque<Integer> stack = new ArrayDeque<>();
  private final HashMap<String, Integer> globals = new HashMap<>();
  private int pc = 0;
  private boolean halted = false;

  public int getGlobal(String name) {
    return Optional.ofNullable(globals.get(name))
        .orElseThrow(IllegalArgumentException::new);
  }

  public void setGlobal(String name, int value) {
    if (!globals.containsKey(name)) {
      throw new IllegalArgumentException(String.format("global variable %s not defined", name));
    } else {
      globals.put(name, value);
    }
  }

  public void defineGlobal(String name, int value) {
    if (globals.containsKey(name)) {
      throw new IllegalArgumentException(String.format("global variable %s already defined", name));
    } else {
      globals.put(name, value);
    }
  }

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
    var popped = stack.pop();
    System.out.println("stack = " + stack);
    return popped;
  }
}
