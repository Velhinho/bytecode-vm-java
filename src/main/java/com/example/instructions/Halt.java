package com.example.instructions;

import com.example.Environment;

public class Halt implements Instruction {
  @Override
  public void exec(Environment env) {
    env.setHalted(true);
  }
}
