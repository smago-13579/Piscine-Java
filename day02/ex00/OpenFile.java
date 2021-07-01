package com.company;

import java.io.*;
import java.util.*;

public class OpenFile {
    private byte[]  buffer = new byte[8];
    private String  buf = "";

    public OpenFile(String str) {
        try (FileInputStream fin = new FileInputStream(str)) {
            fin.read(buffer, 0, 8);
            this.toStr();
            fin.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getBuffer() {
        return this.buf;
    }

    private String toHex(int i) {
        String str = "";

        int x = i / 16;
        i = i % 16;
        if (x >= 10) {
            str += (char)(x + 55);
        }
        else {
            str += x;
        }
        if (i >= 10) {
            str += (char)(i + 55);
        }
        else {
            str += i;
        }
        return (str);
    }

    private void    toStr() {
        String str = "";
        for (int i = 0; i < 8; i++) {
            if (buffer[i] < 0) {
                str += this.toHex(256 + (int)buffer[i]) + " ";
            }
            else {
                str += this.toHex((int)buffer[i]) + " ";
            }
        }
        str = str.substring(0, str.length() - 1);
        this.buf = str;
    }
}