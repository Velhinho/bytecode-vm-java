package com.example;

import java.util.*;

public class Environment {
  private final Deque<Integer> stack = new ArrayDeque<>();
  private final HashMap<String, Integer> globals = new HashMap<>();
  private int pc = 0;
  private boolean halted = false;
  private Deque<CallInfo> callStack = new ArrayDeque<>();

  public void pushCall(CallInfo callInfo) {
    callStack.push(callInfo);
  }

  public CallInfo popCall() {
    return callStack.pop();
  }

  public CallInfo currentStack() {
    return callStack.peek();
  }

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
