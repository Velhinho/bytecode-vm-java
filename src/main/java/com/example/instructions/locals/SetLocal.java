package com.example.instructions.locals;

import com.example.Environment;
import com.example.instructions.Instruction;

public class SetLocal implements Instruction {
  private final int index;

  public SetLocal(int index) {
    this.index = index;
  }

  @Override
  public void exec(Environment env) {
    env.currentStack().locals().set(index, env.pop());
  }
}
