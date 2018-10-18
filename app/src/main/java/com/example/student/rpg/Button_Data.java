package com.example.student.rpg;

public class Button_Data
{
    public float x;
    public float y;
    public boolean touch = false;

    public Button_Data(float pos_x, float pos_y)
    {
        x = pos_x;
        y = pos_y;
    }

    public boolean GetTouchBotton() { return touch; }
}
