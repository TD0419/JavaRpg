package com.example.student.rpg.FieldMap;

import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

public class ObjSymbolEnemy extends Obj
{
    GraphicUtil.Rect m_rect = new GraphicUtil.Rect(); // 画像の切り取り位置
    ObjMap m_objmap;

    public ObjSymbolEnemy(float x, float y, ObjMap objmap)
    {
        m_x = x;
        m_y = y;

        m_rect.top = 0.f;
        m_rect.left = 0.f;
        m_rect.right = 1.f / 4.f;
        m_rect.bottom = 1.f / 4.f;

        m_objmap = objmap;
    }

    @Override
    public void Update()
    {
        
    }

    @Override
    public void Draw(GL10 gl)
    {
        // 位置はマップのスクロールによって変わる
        GraphicUtil.drawTexture(gl, m_x + m_objmap.GetPosX(), m_y + m_objmap.GetPosY(),
                0.25f, 0.25f, MyRenderer.m_player_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }
}
