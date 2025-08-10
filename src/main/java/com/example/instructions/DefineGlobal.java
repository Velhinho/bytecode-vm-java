package com.example.instructions;

import com.example.Environment;

public class DefineGlobal implements Instruction {
  private final String name;
  public DefineGlobal(String name) {
    this.name = name;
  }

  @Override
  public void exec(Environment env) {
    env.defineGlobal(name, env.pop());
  }
}
