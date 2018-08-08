package com.adrianseraspi.colorpickerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class ColorPickerView extends LinearLayout {

    private static final String TAG = "Test";

    private TableLayout tbLayout;
    private TableRow tr;

    private PickColorListener mListener;

    private ArrayList<Button> mButtons = new ArrayList<>();
    private ArrayList<String> mColorListHex;

    private int shape;
    private String colorPickHex;
    private String pickerIcon;

    private int colorPickPos = 0;
    private int colorRows;
    private float colorVerticalSize;
    private float colorPickerIconSize;

    private float verticalMargin;
    private float horizontalMargin;

    public ColorPickerView(Context context) {
        super(context);
        init(context, null);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void declareShape(int id) {
        switch (id) {
            case 0:
                shape = GradientDrawable.RECTANGLE;
                break;
            case 1:
                shape = GradientDrawable.OVAL;
                break;
            default:
                shape = GradientDrawable.RECTANGLE;
                break;
        }
    }

    private void declarePickerIcon(int id) {
        switch (id) {
            case 500:
                //check mark
                pickerIcon = getResources().getString(R.string.check);
                break;
            case 501:
                //cross mark
                pickerIcon = getResources().getString(R.string.cross);
                break;
            case 502:
                //dot mark
                pickerIcon = getResources().getString(R.string.dot);
                break;
            default:
                //check mark
                pickerIcon = getResources().getString(R.string.check);
                break;
        }

    }

    private void declareColors(int[] colorsArray) {
        mColorListHex = new ArrayList<>();
        for (int color : colorsArray) {
            String hexColor = String.format("#%06X", (0xFFFFFF & color));
            mColorListHex.add(hexColor);
        }
    }

    public void setOnColorPickListener(PickColorListener listener) {
        this.mListener = listener;
        this.invalidate();
    }

    public void removeOnColorPickListener() {
        if (mListener != null) {
            this.mListener = null;
            this.invalidate();
        }
    }

    public String getColorPickHex() {
        return colorPickHex;
    }

    public float getVerticalMargin() {
        return verticalMargin;
    }

    public float getHorizontalMargin() {
        return horizontalMargin;
    }

    public int getColorCount() {
        return mColorListHex.size();
    }

    public String getColorPickerIcon() {
        return pickerIcon;
    }

    public float getColorPickIconSize() {
        return colorPickerIconSize;
    }

    public float getVerticalSize() {
        return colorVerticalSize;
    }

    public int getColorsPerRow() {
        return colorRows;
    }

    public ArrayList<String> getColorListHex() {
        return mColorListHex;
    }

    private void removeAllButtonIcons() {
        for (int i = 0; i < getColorCount(); i++) {
            Button button = mButtons.get(i);
            button.setText("");
        }
    }

    private void addButtonIcon(int id) {
        for (int i = 0; i < getColorCount(); i++) {

            if (i == id) {
                Button button;
                button = mButtons.get(i);
                button.setText(pickerIcon);
            }
        }
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.color_picker_view, this);
        tbLayout = findViewById(R.id.table_layout);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerView);

            try {
                ColorShape cs = ColorShape.fromId(a.getInt(R.styleable.ColorPickerView_shape, 0));
                PickerIcon pi = PickerIcon.fromId(a.getInt(R.styleable.ColorPickerView_pickerIcon, 500));
                int colorsId = a.getResourceId(R.styleable.ColorPickerView_colorList, 0);
                int[] colorsArray = new int[0];

                if (colorsId != 0) {
                    colorsArray = a.getResources().getIntArray(colorsId);
                }

                colorPickPos = a.getInt(R.styleable.ColorPickerView_pickColorPosition, 0);
                colorPickerIconSize = a.getDimensionPixelSize(R.styleable.ColorPickerView_pickerIconSize, 14);
                colorVerticalSize = a.getDimensionPixelOffset(R.styleable.ColorPickerView_verticalSize, 30);
                colorRows = a.getInt(R.styleable.ColorPickerView_colorsPerRow, 5);
                verticalMargin = a.getDimensionPixelOffset(R.styleable.ColorPickerView_verticalMargin, 4);
                horizontalMargin = a.getDimensionPixelOffset(R.styleable.ColorPickerView_horizontalMargin, 4);

                declareShape(cs.id);
                declarePickerIcon(pi.id);
                declareColors(colorsArray);

                int i = 0;
                while (i < getColorCount()) {

                    if (i % colorRows == 0) {
                        tr = new TableRow(getContext());
                        tr.setGravity(Gravity.CENTER);
                        tbLayout.addView(tr);
                    }

                    Button button = getNewlyCreatedButton(i);

                    tr.addView(button);
                    i++;
                }

            } finally {
                a.recycle();
            }
        }
    }

    private Button getNewlyCreatedButton(int i) {
        Button btn = new Button(getContext());
        final String color = mColorListHex.get(i);

        GradientDrawable buttonGradientDrawable = new GradientDrawable();
        buttonGradientDrawable.setShape(shape);
        buttonGradientDrawable.setColor(Color.parseColor(mColorListHex.get(i)));

        TableRow.LayoutParams params = new TableRow.LayoutParams(0,
                (int) colorVerticalSize, 1f);

        params.setMargins((int) horizontalMargin / 2, (int) verticalMargin / 2,
                (int) horizontalMargin / 2, (int) verticalMargin / 2);

        btn.setId(i);
        btn.setBackground(buttonGradientDrawable);
        btn.setLayoutParams(params);
        btn.setTextSize(colorPickerIconSize);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                removeAllButtonIcons();
                addButtonIcon(v.getId());

                if (mListener != null) {
                    mListener.onColorPick(color);
                }
                colorPickHex = color;
            }
        });

        if (colorPickPos == i) {
            btn.setText(pickerIcon);
        }

        mButtons.add(btn);

        return btn;
    }

    public interface PickColorListener {
        void onColorPick(String coloredPickHex);
    }
}
