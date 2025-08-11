package com.example.instructions.arithmetic;

import com.example.Environment;
import com.example.instructions.Instruction;

public class Sub implements Instruction {
  @Override
  public void exec(Environment env) {
    var b = env.pop();
    var a = env.pop();
    env.push(a - b);
  }
}
