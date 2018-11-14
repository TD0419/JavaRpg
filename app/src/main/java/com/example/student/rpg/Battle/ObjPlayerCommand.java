package com.example.student.rpg.Battle;

import com.example.student.rpg.Button_Data;
import com.example.student.rpg.Collision;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

import static com.example.student.rpg.MyRenderer.m_attack_select_texture;

public class ObjPlayerCommand extends Obj
{
    boolean m_is_look;

    Button_Data m_attack_button =
            new Button_Data(0.f, -0.8f);
    float m_attack_button_width;
    float m_attack_button_height;
    Collision.AABB m_attack_button_aabb =
            new Collision.AABB();

    Button_Data m_escape_button =
            new Button_Data(0.5f, -0.5f);
    float m_escape_button_width;
    float m_escape_button_height;
    Collision.AABB m_escape_button_aabb =
            new Collision.AABB();

    public ObjPlayerCommand()
    {
        m_is_look = true;
        m_attack_button_width = 0.3f;
        m_attack_button_height = 0.3f;
        m_attack_button_aabb.SetAABB(m_attack_button.x, m_attack_button.y,
                m_attack_button_width, m_attack_button_height);

        m_escape_button_width = 0.4f;
        m_escape_button_height = 0.4f;
        m_escape_button_aabb.SetAABB(m_escape_button.x, m_escape_button.y,
                m_escape_button_width, m_escape_button_height);
    }

    @Override
    public void Update()
    {
        // このオブジェクトが見える状態なら
        if(m_is_look == true)
        {
            if(Global.touch_push == true)
            {
                if(Collision.HitPointAndAABB(Global.touch_x, Global.touch_y,
                        m_attack_button_aabb) == true)
                {
                    m_attack_button.touch = true;
                }

                Global.touch_push = false;
            }
        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        // このオブジェクトが見える状態なら
        if(m_is_look == true)
        {
            GraphicUtil.drawTexture(Global.gl, m_attack_button.x, m_attack_button.y, m_attack_button_width, m_attack_button_height, m_attack_select_texture);
        }
    }

    // 戦闘コマンドが見えて、使えるかどうかの設定
    public void SetLookAt(boolean flag)
    {
        m_is_look = flag;
    }
}
