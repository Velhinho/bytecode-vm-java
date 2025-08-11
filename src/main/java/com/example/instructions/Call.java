package com.example.instructions;

import com.example.CallInfo;
import com.example.Environment;

import java.util.ArrayList;

public class Call implements Instruction {
  private final int func_address;
  private final int numLocals;

  public Call(int func_address, int numArgs) {
    this.func_address = func_address;
    this.numLocals = numArgs;
  }

  @Override
  public void exec(Environment env) {
    var args = new ArrayList<Integer>();
    for (var i = 0; i < numLocals; i++) {
      args.add(env.pop());
    }
    env.pushCall(new CallInfo(env.getPc(), args));
    env.setPc(func_address);
  }
}
