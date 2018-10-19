package com.example.student.rpg.Battle;

import android.graphics.Bitmap;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

import static android.graphics.Color.WHITE;

public class ObjBattlePlayer extends ObjBattle
{
    int m_hp_font_texture;

    public ObjBattlePlayer()
    {
        m_name = "主人公";

        m_state_info.hp = 100;
        m_state_info.attack = 10;
        m_state_info.speed = 100;

        m_attack_info.enemy_number = 2;
    }

    @Override
    public void Draw(GL10 gl)
    {
        StringTextureUpdate(m_name + " HP : " + String.valueOf(m_state_info.hp),
                WHITE);

        GraphicUtil.drawTexture(gl, 0.f, 0.f,
                1.f, 0.5f, m_hp_font_texture);
    }

    // 文字テクスチャ更新(コンストラクタに置くとまだglとcontextが生成されていないのでエラーが出る)
    private void StringTextureUpdate(String text, int color)
    {
        Bitmap bmp = GraphicUtil.createStrImage(text, color);
        m_hp_font_texture = GraphicUtil.loadTexture(
               Global.gl, Global.context.getResources(), bmp, 0);
    }
}
