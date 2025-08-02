package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
  private static HashMap<String, Integer> find_labels(String[] lines) {
    var labels = new HashMap<String, Integer>();
    for (var i = 0; i < lines.length; i++) {
      var line = lines[i];
      if (line.endsWith(":")) {
        labels.put(line.substring(0, line.length() - 1), i);
      }
    }
    return labels;
  }

  private static void check_consecutive_labels(HashMap<String, Integer> labels) {
    var addresses = new ArrayList<>(labels.values());
    var size = addresses.size();
    for (var i = 0; i < size - 1; i++) {
      if (addresses.get(i)+1 == addresses.get(i+1)) {
        var err_msg = String.format(
            "Consecutive labels at line: %d and line: %d",
            addresses.get(i),
            addresses.get(i+1));
        throw new IllegalArgumentException(err_msg);
      }
    }
  }

  private static String[] replace_labels(String[] lines, HashMap<String, Integer> labels) {
    var no_labels = new ArrayList<String>();
    for (var line : lines) {
      if (line.startsWith("JMP")) {
        var label = line.split(" ")[1];
        var address = labels.get(label);
        if (address == null) {
          throw new IllegalArgumentException(String.format("label %s never doesn't exist", label));
        }
        no_labels.add("JMP " + address);
      } else if (!line.endsWith(":")) {
        no_labels.add(line);
      }
    }
    return no_labels.toArray(new String[0]);
  }

  public static String[] parse_labels(String[] lines) {
    var labels = find_labels(lines);
    check_consecutive_labels(labels);
    return replace_labels(lines, labels);
  }

  private static Instruction parseJump(String opcode, String[] parts) {
    if (parts.length != 2) {
        throw new IllegalArgumentException("JZ requires an address");
    }
    var address = Integer.parseInt(parts[1]);
    return switch (opcode) {
      case "JMP" -> new Jmp(address);
      case "JNZ" -> new Jnz(address);
      case "JZ" -> new Jz(address);
      default -> throw new IllegalArgumentException("Unknown jump instruction");
    };
  }

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
      case "JMP", "JNZ", "JZ" -> parseJump(parts[0], parts);
      case "ADD" -> new Add();
      case "SUB" -> new Sub();
      case "PRINT" -> new Print();
      case "READ" -> new Read();
      case "HALT" -> new Halt();
      default -> throw new IllegalArgumentException("Unknown instruction: " + parts[0]);
    };
  }

  public static List<Instruction> parse(String[] lines) {
    var instructions = new ArrayList<Instruction>();
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
      var lines = data.split("\\r?\\n");
      var no_labels = parse_labels(lines);
      var instructions = parse(no_labels);
      execInstructions(instructions);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }
}
