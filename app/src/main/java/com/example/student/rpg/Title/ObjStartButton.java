package com.example.student.rpg.Title;

import com.example.student.rpg.Button_Data;
import com.example.student.rpg.Collision;
import com.example.student.rpg.FontTexture;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;
import com.example.student.rpg.Scene.SceneManager;

import javax.microedition.khronos.opengles.GL10;

public class ObjStartButton extends Obj
{
    Button_Data m_start_button;
    Collision.AABB m_start_button_aabb = new Collision.AABB();

    float m_start_button_width;
    float m_start_button_height;

    public ObjStartButton(float x, float y)
    {
        m_x = x;
        m_y = y;

        m_start_button = new Button_Data(x, y);
        // ボタンの大きさセット
        m_start_button_width = 0.8f;
        m_start_button_height = 0.4f;

        // AABB情報セット
        m_start_button_aabb.top    = m_start_button.y + m_start_button_height / 2.f;
        m_start_button_aabb.left   = m_start_button.x - m_start_button_width  / 2.f;
        m_start_button_aabb.right  = m_start_button.x + m_start_button_width  / 2.f;
        m_start_button_aabb.bottom = m_start_button.y - m_start_button_height / 2.f;
    }

    @Override
    public void Update()
    {
        if(Global.touch_push == true)
        {
            if(Collision.HitPointAndAABB(Global.touch_x, Global.touch_y,
                    m_start_button_aabb) == true)
            {
                // 始めるのボタンを押したら、ゲーム画面に遷移
                SceneManager.ChangeScene(SceneManager.Scene_Kind.Game);
            }

            Global.touch_push = false;
        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        // ボタンの枠
        GraphicUtil.drawaRectangle(gl, m_start_button.x, m_start_button.y,
                m_start_button_width, m_start_button_height,
                0.5f, 0.5f, 0.5f, 1.f);

        // 「はじめる」文字描画
        FontTexture.DrawString(gl, m_x - 0.3f, m_y, 0.2f, 0.2f, "はじめる");
    }
}
