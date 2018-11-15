package com.example.student.rpg.Battle;

import android.graphics.Bitmap;

import com.example.student.rpg.FontTexture;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.TextureManager;

import javax.microedition.khronos.opengles.GL10;

import static android.graphics.Color.WHITE;

public class ObjBattleEnemy extends ObjBattle
{
    public ObjBattleEnemy(String name)
    {
        m_name = name;

        m_state_info.hp = 90;
        m_state_info.attack = 4;
        m_state_info.speed = 102;

        m_attack_info.enemy_number = 1;
    }

    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(gl, 0.f, 0.5f,
                1.f, 1.f, MyRenderer.m_bat_enemy_texture);

        FontTexture.DrawString(gl, -1.3f, 0.8f, 0.2f, 0.2f,
                m_name + " HP:" + String.valueOf(m_state_info.hp),
                1.f, 1.f, 1.f, 1.f);
    }
}
