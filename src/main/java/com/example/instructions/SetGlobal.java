package com.example.instructions;

import com.example.Environment;

public class SetGlobal implements Instruction {
  private final String name;
  public SetGlobal(String name) {
    this.name = name;
  }

  @Override
  public void exec(Environment env) {
    env.setGlobal(name, env.pop());
  }
}
