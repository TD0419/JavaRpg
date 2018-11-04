package com.example.student.rpg.Title;

import com.example.student.rpg.Button_Data;
import com.example.student.rpg.FontTexture;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

public class ObjStartButton extends Obj
{
    Button_Data start_button;

    public ObjStartButton(float x, float y)
    {
        m_x = x;
        m_y = y;
        start_button = new Button_Data(x - 0.3f, y);
    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(GL10 gl)
    {
        // ボタンの枠
        GraphicUtil.drawaRectangle(gl, start_button.x, start_button.y, 0.6f, 0.6f,
                0.5f, 0.5f, 0.5f, 0.5f);

        // 「はじめる」文字描画
        FontTexture.DrawString(gl, m_x, m_y, 0.1f, 0.1f, "はじめる");
    }
}
