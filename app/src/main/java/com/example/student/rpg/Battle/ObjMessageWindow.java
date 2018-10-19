package com.example.student.rpg.Battle;

import android.graphics.Bitmap;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

import static android.graphics.Color.WHITE;
import static com.example.student.rpg.MyRenderer.m_messege_window_texture;

public class ObjMessageWindow extends Obj
{
    private int m_window_font_texture = -1;
    boolean a = true;

    public ObjMessageWindow()
    {
    }

    @Override
    public void Draw(GL10 gl)
    {
        // メッセージウィンドウ表示
        GraphicUtil.drawTexture(Global.gl,0.f, -0.5f, 3.f,1.f, m_messege_window_texture);

        // 文字表示
        if(m_window_font_texture != -1)
            GraphicUtil.drawTexture(Global.gl,-1.2f, -0.3f, 0.3f,0.3f, m_window_font_texture);
    }

    // メッセージウィンドウから文字を表示
    public void StringDraw(String text)
    {
        StringTextureUpdate(text, WHITE);
    }

    // 文字テクスチャ更新
    private void StringTextureUpdate(String text, int color)
    {
        Bitmap bmp = GraphicUtil.createStrImage(text, color);
        m_window_font_texture = GraphicUtil.loadTexture(
                Global.gl, Global.context.getResources(), bmp, 0);
    }
}
