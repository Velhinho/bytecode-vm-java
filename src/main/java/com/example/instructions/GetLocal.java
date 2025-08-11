package com.example.instructions;

import com.example.Environment;

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
