package com.example.student.rpg.Scene;

import com.example.student.rpg.FieldMap.ObjMap;
import com.example.student.rpg.FieldMap.ObjPlayer;
import com.example.student.rpg.ObjTouchButton;
import com.example.student.rpg.ObjectManager;
import com.example.student.rpg.Title.ObjBackGround;

// ゲームシーン
public class SceneGame extends Scene
{
    private ObjPlayer m_player;
    private ObjMap m_map;

    SceneGame()
    {
        m_map = new ObjMap();
        ObjectManager.Insert(m_map);

        m_player = new ObjPlayer(0.2f, -0.2f, m_map);
        ObjectManager.Insert(m_player);
    }
}
