package com.example.student.rpg;

import javax.microedition.khronos.opengles.GL10;

public class ObjTouchButton extends Obj
{
    private final float final_circle_diameter = 0.3f; // ボタンの当たり判定の直径
    private final float final_circle_radius = final_circle_diameter / 2.f;

    Button_Data m_up_button;
    Button_Data m_left_button;
    Button_Data m_right_button;
    Button_Data m_bottom_button;
    Button_Data m_A_button;
    //Button_Data m_B_button;

    ObjTouchButton()
    {
        //m_up_button     = new Button_Data(-0.75f, -0.5f, );
//        m_left_button   = new Button_Data(-1.f, -0.5f);
//        m_right_button  = new Button_Data(-0.5f, -0.5f);
//        m_bottom_button = new Button_Data(-0.75f, -0.75f);
        //m_A_button      = new Button_Data(1.f, -0.5f,);
    }

    @Override
    public void Update()
    {
//        m_left_button.touch = false;

//        if(Global.touch_push == true)
//        {
            // 全てのボタンの当たり判定チェック
//            if(CollisioCircleAndPoint(m_left_button.x, m_left_button.y,
//                    final_circle_radius, Global.touch_x, Global.touch_y))
//            {
//                m_left_button.touch = true;
//            }
//
//            if(CollisioCircleAndPoint(m_right_button.x, m_right_button.y,
//                    final_circle_radius, Global.touch_x, Global.touch_y))
//            {
//                m_right_button.touch = true;
//            }
//
//            if(CollisioCircleAndPoint(m_bottom_button.x, m_bottom_button.y,
//                    final_circle_radius, Global.touch_x, Global.touch_y))
//            {
//                m_bottom_button.touch = true;
//            }
//        }
    }

    @Override
    public void Draw(GL10 gl)
    {
//        GraphicUtil.drawTexture(
//                gl, m_left_button.x, m_left_button.y, final_circle_diameter, final_circle_diameter,
//                MyRenderer.m_left_botton_texture, 1.f, 1.f, 1.f, 0.5f
//        );
//
    }


}

