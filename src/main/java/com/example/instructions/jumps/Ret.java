package com.example.instructions.jumps;

import com.example.Environment;
import com.example.instructions.Instruction;

public class Ret implements Instruction {
  @Override
  public void exec(Environment env) {
    var info = env.popCall();
    env.setPc(info.ret_pc());
  }
}
