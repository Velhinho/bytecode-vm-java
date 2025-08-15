package com.example.instructions.locals;

import com.example.Environment;
import com.example.instructions.Instruction;

public class PushLocal implements Instruction {
  @Override
  public void exec(Environment env) {
    env.currentStack().locals().add(0);
  }
}
