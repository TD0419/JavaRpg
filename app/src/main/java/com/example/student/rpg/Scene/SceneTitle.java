package com.example.student.rpg.Scene;

import com.example.student.rpg.ObjectManager;
import com.example.student.rpg.Title.ObjBackGround;
import com.example.student.rpg.Title.ObjStartButton;

public class SceneTitle extends Scene
{
    ObjBackGround m_objbackground;
    ObjStartButton m_startbutton;

    SceneTitle()
    {
        // 背景作成
        m_objbackground = new ObjBackGround(0.f, 0.f);
        ObjectManager.Insert(m_objbackground);
        // ゲームスタートボタン作成
        m_startbutton = new ObjStartButton(0.f, -0.5f);
        ObjectManager.Insert(m_startbutton);
    }
}
