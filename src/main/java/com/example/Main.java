package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static Instruction parseLine(String line) {
    var parts = line.trim().toUpperCase().split("\\s+");
    if (parts.length == 0) {
      throw new IllegalArgumentException("Empty instruction line");
    }

    return switch (parts[0]) {
      case "PUSH" -> {
        if (parts.length != 2) {
          throw new IllegalArgumentException("PUSH requires a number");
        }
        yield new Push(Integer.parseInt(parts[1]));
      }
      case "JMP" -> {
        if (parts.length != 2) {
          throw new IllegalArgumentException("JMP requires an address");
        }
        yield new Jmp(Integer.parseInt(parts[1]));
      }
      case "ADD" -> new Add();
      case "SUB" -> new Sub();
      case "PRINT" -> new Print();
      case "READ" -> new Read();
      case "HALT" -> new Halt();
      default -> throw new IllegalArgumentException("Unknown instruction: " + parts[0]);
    };
  }

  public static List<Instruction> parse(String data) {
    var instructions = new ArrayList<Instruction>();
    var lines = data.split("\\r?\\n");
    for (String line : lines) {
      if (!line.trim().isEmpty()) {
        instructions.add(parseLine(line));
      }
    }
    return instructions;
  }

  public static void execInstructions(List<Instruction> instructions) {
    var env = new Environment();
    try {
      while (!env.isHalted()) {
        instructions.get(env.getPc()).exec(env);
        env.incPc();
      }
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace(System.err);
      System.err.println("Illegal jump");
    }
  }

  public static void main(String[] args) {
    try {
      var data = Files.readString(Paths.get("test.byte"));
      var instructions = parse(data);
      execInstructions(instructions);
    } catch (IOException e) {
      System.err.println("Failed to read file: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Execution error: " + e.getMessage());
    }
  }
}
