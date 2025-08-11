package com.example;

import com.example.instructions.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main {
  private static HashMap<String, Integer> findLabels(String[] lines) {
    var labels = new HashMap<String, Integer>();
    for (var i = 0; i < lines.length; i++) {
      var line = lines[i];
      if (line.endsWith(":")) {
        var label_name = line.substring(0, line.length() - 1);
        if (labels.containsKey(label_name)) {
          throw new IllegalArgumentException(String.format("label %s define twice", label_name));
        }
        labels.put(label_name, i);
      }
    }
    return labels;
  }

  private static void checkConsecutiveLabels(HashMap<String, Integer> labels) {
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

  private static boolean isJumpInst(String line) {
    return line.startsWith("JMP")
        || line.startsWith("JZ")
        || line.startsWith("JNZ");
  }

  private static String[] replaceLabels(String[] lines, HashMap<String, Integer> labels) {
    var no_labels = new ArrayList<String>();
    for (var line : lines) {
      if (isJumpInst(line)) {
        var parts = line.split(" ");
        var op_code = parts[0];
        var label = parts[1];
        var address = labels.get(label);
        if (address == null) {
          throw new IllegalArgumentException(String.format("label %s doesn't exist", label));
        }
        no_labels.add(String.format("%s %s", op_code, address));
      } else if (line.startsWith("CALL")) {
        var parts = line.split(" ");
        var op_code = parts[0];
        var label = parts[1];
        var numLocals = parts[2];
        var address = labels.get(label);
        if (address == null) {
          throw new IllegalArgumentException(String.format("label %s doesn't exist", label));
        }
        no_labels.add(String.format("%s %s %s", op_code, address, numLocals));
      } else {
        no_labels.add(line);
      }
    }
    return no_labels.toArray(new String[0]);
  }

  public static String[] parseLabels(String[] lines) {
    var labels = findLabels(lines);
    checkConsecutiveLabels(labels);
    return replaceLabels(lines, labels);
  }

  private static Instruction parseJump(String opcode, String[] parts) {
    if (parts.length != 2) {
        throw new IllegalArgumentException(String.format("%s requires an address", opcode));
    }
    var address = Integer.parseInt(parts[1]);
    return switch (opcode) {
      case "JMP" -> new Jmp(address);
      case "JNZ" -> new Jnz(address);
      case "JZ" -> new Jz(address);
      default -> throw new IllegalArgumentException("Unknown jump instruction");
    };
  }

  private static Instruction parseGlobal(String opcode, String[] parts) {
    if (parts.length != 2) {
      throw new IllegalArgumentException(String.format("%s requires an name", opcode));
    }
    var name = parts[1];
    return switch (opcode) {
      case "DEFINE_GLOBAL" -> new DefineGlobal(name);
      case "GET_GLOBAL" -> new GetGlobal(name);
      case "SET_GLOBAL" -> new SetGlobal(name);
      default -> throw new IllegalArgumentException("Unknown global instruction");
    };
  }

  public static Instruction parseLocal(String opcode, String[] parts) {
    if (parts.length != 2) {
      throw new IllegalArgumentException(String.format("%s requires an index", opcode));
    }
    var index = Integer.parseInt(parts[1]);
    return switch (opcode) {
      case "SET_LOCAL" -> new SetLocal(index);
      case "GET_LOCAL" -> new GetLocal(index);
      default -> throw new IllegalArgumentException("Unknown local instruction");
    };
  }

  public static Instruction parseLine(String line) {
    var parts = line.trim().split("\\s+");
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
      case "CALL" -> {
        if (parts.length != 3) {
          for (String part : parts) {
            System.out.println(part);
          }
          throw new IllegalArgumentException("CALL requires a name and numLocals");
        }
        yield new Call(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
      }
      case "DEFINE_GLOBAL", "GET_GLOBAL", "SET_GLOBAL" ->
          parseGlobal(parts[0], parts);
      case "GET_LOCAL", "SET_LOCAL" ->
          parseLocal(parts[0], parts);
      case "JMP", "JNZ", "JZ" -> parseJump(parts[0], parts);
      case "ADD" -> new Add();
      case "SUB" -> new Sub();
      case "MUL" -> new Mul();
      case "REM" -> new Rem();
      case "POP" -> new Pop();
      case "PRINT" -> new Print();
      case "READ" -> new Read();
      case "HALT" -> new Halt();
      case "RET" -> new Ret();
      default -> {
        if (line.endsWith(":")) {
          yield new Label();
        }
        else {
          throw new IllegalArgumentException("Unknown instruction: " + parts[0]);
        }
      }
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
    var size = instructions.size();
    try {
      while (!env.isHalted() && env.getPc() < size) {
        var inst = instructions.get(env.getPc());
        System.out.println(inst);
        inst.exec(env);
        env.incPc();
      }
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace(System.err);
      System.err.println("Illegal jump");
    } catch (Exception e) {
      e.printStackTrace(System.err);
      System.err.println("PC =" + env.getPc());
    }
  }

  private static String[] removeEmptyLines(String[] lines) {
    var ret = new ArrayList<String>();
    for (String line : lines) {
      if (!Objects.equals(line, "")) {
        ret.add(line);
      }
    }
    return ret.toArray(new String[0]);
  }

  public static void main(String[] args) {
    try {
      var data = Files.readString(Paths.get("gcd.byte"));
      var lines = removeEmptyLines(data.split("\\r?\\n"));
      var no_labels = parseLabels(lines);
      var instructions = parse(no_labels);
      execInstructions(instructions);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }
}
