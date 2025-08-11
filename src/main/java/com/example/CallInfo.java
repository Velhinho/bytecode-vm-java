package com.example;

import java.util.ArrayList;

public record CallInfo(int ret_pc, ArrayList<Integer> locals) {
}