package com.example.instructions;

import com.example.Environment;

public class Sub implements Instruction {
  @Override
  public void exec(Environment env) {
    var b = env.pop();
    var a = env.pop();
    env.push(a - b);
  }
}
