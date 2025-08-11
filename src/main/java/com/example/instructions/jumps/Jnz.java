package com.example.instructions.jumps;

import com.example.Environment;
import com.example.instructions.Instruction;

public class Jnz implements Instruction {
  private final int address;

  public Jnz(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    var top = env.pop();
    if (top != 0) {
      env.setPc(address - 1);
    }
  }
}
