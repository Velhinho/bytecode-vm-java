package com.example.instructions.arithmetic;

import com.example.Environment;
import com.example.instructions.Instruction;

public class Add implements Instruction {
  @Override
  public void exec(Environment env) {
    int b = env.pop();
    int a = env.pop();
    env.push(a + b);
  }
}
