package com.example;

import java.util.Scanner;

public class Read implements Instruction {
  @Override
  public void exec(Environment env) {
    var scanner = new Scanner(System.in);
    var n = scanner.nextInt();
    env.push(n);
  }
}
