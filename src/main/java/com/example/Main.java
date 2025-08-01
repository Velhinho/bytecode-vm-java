package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static Instruction parseLine(String line) {
        String[] parts = line.trim().toUpperCase().split("\\s+");
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
        case "ADD" -> new Add();
        case "SUB" -> new Sub();
        case "PRINT" -> new Print();
        default -> throw new IllegalArgumentException("Unknown instruction: " + parts[0]);
      };
    }

    public static List<Instruction> parse(String data) {
        List<Instruction> instructions = new ArrayList<>();
        String[] lines = data.split("\\r?\\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                instructions.add(parseLine(line));
            }
        }
        return instructions;
    }

    public static void execInstructions(List<Instruction> instructions) {
        Environment env = new Environment();
        for (Instruction instr : instructions) {
            instr.exec(env);
        }
    }

    public static void main(String[] args) {
        try {
            String data = Files.readString(Paths.get("test.byte"));
            List<Instruction> instructions = parse(data);
            execInstructions(instructions);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Execution error: " + e.getMessage());
        }
    }
}
