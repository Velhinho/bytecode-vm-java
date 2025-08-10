package com.example.instructions;

import com.example.Environment;

public class Push implements Instruction {
  private final int num;

  public Push(int num) {
    this.num = num;
  }

  @Override
  public void exec(Environment env) {
    env.push(num);
  }
}
