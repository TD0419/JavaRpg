package com.example.student.rpg.Title;

import com.example.student.rpg.GraphicUtil.Rect;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

public class ObjBackGround extends Obj
{
    int m_map_pos_x;
    int m_map_pos_y;

    Rect m_rect = new Rect();

    public ObjBackGround(float x, float y)
    {
        m_x = x;
        m_y = y;
    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(gl, m_x, m_y, 3.f, 2.f, MyRenderer.m_title_back_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }
}



