package com.example.instructions.logic;

import com.example.Environment;
import com.example.instructions.Instruction;

public class GT implements Instruction {
  @Override
  public void exec(Environment env) {
    var b = env.pop();
    var a = env.pop();
    env.push(a > b ? 1 : 0);
  }
}
