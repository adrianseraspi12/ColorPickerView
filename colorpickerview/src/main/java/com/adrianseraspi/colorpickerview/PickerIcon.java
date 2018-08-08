package com.adrianseraspi.colorpickerview;

public enum PickerIcon {
    CHECK(500),
    CROSS(501),
    DOT(502);
    int id;

    PickerIcon(int id) {
        this.id = id;
    }

    static PickerIcon fromId(int id) {
        for (PickerIcon pi: values()) {
            if (pi.id == id) {
                return pi;
            }
        }
        throw new IllegalArgumentException();
    }
}
