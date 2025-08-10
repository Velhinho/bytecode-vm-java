package com.example.instructions;

import com.example.Environment;

public class Pop implements Instruction {
  @Override
  public void exec(Environment env) {
    env.pop();
  }
}
