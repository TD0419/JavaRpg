package com.example.student.rpg.Battle;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

public class ObjBattleBackGround extends Obj
{
    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(Global.gl, m_x, m_y,
                3.f, 2.f, MyRenderer.m_battle_background);
    }
}
