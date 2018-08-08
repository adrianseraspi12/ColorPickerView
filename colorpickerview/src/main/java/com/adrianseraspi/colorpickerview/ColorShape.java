package com.adrianseraspi.colorpickerview;

public enum ColorShape {
    BOX(0),
    OVAL(1);
    int id;

    ColorShape(int id) {
        this.id = id;
    }

    static ColorShape fromId(int id) {
        for (ColorShape cs : values()) {
            if (cs.id == id) {
                return cs;
            }
        }
        throw new IllegalArgumentException();
    }
}
