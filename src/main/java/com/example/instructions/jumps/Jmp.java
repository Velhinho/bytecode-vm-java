package com.example.instructions.jumps;

import com.example.Environment;
import com.example.instructions.Instruction;

public class Jmp implements Instruction {
  private final int address;

  public Jmp(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    env.setPc(address - 1);
  }
}
