package com.example.instructions.locals;

import com.example.Environment;
import com.example.instructions.Instruction;

public class CreateLocal implements Instruction {

  @Override
  public void exec(Environment env) {
    env.currentStack().locals().add(env.pop());
  }
}
