package com.example.student.rpg.Battle;

import android.graphics.Bitmap;

import com.example.student.rpg.FontTexture;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

import static android.graphics.Color.WHITE;

public class ObjBattlePlayer extends ObjBattle
{
    // 動作確認用のコンストラクタ(後で削除します)
    public ObjBattlePlayer()
    {
        m_name = "主人公";

        m_state_info.hp = 100;
        m_state_info.attack = 10;
        m_state_info.speed = 100;

        m_attack_info.enemy_number = 2;
    }

    public ObjBattlePlayer(BattleStateInfo battle_state_info)
    {
        m_name = "主人公";
        m_state_info = battle_state_info;
        m_attack_info.enemy_number = 2;
    }

    @Override
    public void Draw(GL10 gl)
    {
        FontTexture.DrawString(gl, -1.3f, 0.f, 0.2f, 0.2f,
                m_name + " HP:" + String.valueOf(m_state_info.hp), 1.f, 1.f, 1.f, 1.f);
    }
}
