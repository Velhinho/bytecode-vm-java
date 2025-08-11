package com.example.instructions.locals;

import com.example.Environment;
import com.example.instructions.Instruction;

public class GetLocal implements Instruction {
  private final int index;

  public GetLocal(int index) {
    this.index = index;
  }

  @Override
  public void exec(Environment env) {
    env.push(env.currentStack().locals().get(index));
  }
}
