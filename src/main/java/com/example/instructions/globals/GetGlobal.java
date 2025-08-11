package com.example.instructions.globals;

import com.example.Environment;
import com.example.instructions.Instruction;

public class GetGlobal implements Instruction {
  private final String name;
  public GetGlobal(String name) {
    this.name = name;
  }

  @Override
  public void exec(Environment env) {
    env.push(env.getGlobal(name));
  }
}
