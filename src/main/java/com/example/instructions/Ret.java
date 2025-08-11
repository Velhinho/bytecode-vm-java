package com.example.instructions;

import com.example.Environment;

public class Ret implements Instruction {
  @Override
  public void exec(Environment env) {
    var info = env.popCall();
    env.setPc(info.ret_pc());
  }
}
