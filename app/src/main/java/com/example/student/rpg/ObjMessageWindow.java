package com.example.student.rpg;

import com.example.student.rpg.FontTexture;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

import static com.example.student.rpg.MyRenderer.m_messege_window_texture;

public class ObjMessageWindow extends Obj
{
    String m_messeage_text;

    @Override
    public void Draw(GL10 gl)
    {
        // メッセージウィンドウ表示
        GraphicUtil.drawTexture(Global.gl,0.f, -0.5f, 3.f,1.f, m_messege_window_texture);

        // 文字表示
        FontTexture.DrawString(Global.gl, -1.3f, -0.3f, 0.2f, 0.2f, m_messeage_text,
                1.f, 1.f, 1.f, 1.f);
    }

    // メッセージウィンドウの文字変更
    public void SetMsseageText(String text)
    {
         m_messeage_text = text;
    }
}
