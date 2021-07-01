package com.company;

import java.io.*;
import java.util.*;

public class Handle {
    private String buffer = "";

    public Handle() {}

    public void handling(Map<String, String> formats, String buf) {
        String[] str = new String[7];
        for (int i = 5, num = 0; i <= buf.length(); i += 3, num++) {
            str[num] = buf.substring(0, i);
        }
        for (int i = 0; i < str.length; i++) {
            this.findValue(formats, str[i]);
        }
    }

    private void    findValue(Map<String, String> formats, String str) {
        for (Map.Entry <String, String> item : formats.entrySet()) {
            if (item.getValue().equals(str)) {
                this.buffer += item.getKey();
                this.buffer += '\n';
                break ;
            }
        }
    }

    public String getBuffer() {
        return buffer;
    }
}