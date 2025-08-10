package com.example.instructions;

import com.example.Environment;

public class Print implements Instruction {
  @Override
  public void exec(Environment env) {
    System.out.println(env.pop());
  }
}
