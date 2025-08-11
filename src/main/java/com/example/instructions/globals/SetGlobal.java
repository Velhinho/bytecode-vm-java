package com.example.instructions.globals;

import com.example.Environment;
import com.example.instructions.Instruction;

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
