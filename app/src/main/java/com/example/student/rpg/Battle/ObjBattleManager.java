package com.example.student.rpg.Battle;

import com.example.student.rpg.Global;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjectManager;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class ObjBattleManager extends Obj
{
    boolean m_is_battle;

    private ObjMessageWindow m_message_window;
    private ObjPlayerCommand m_player_command;
    private static ArrayList<ObjBattle> object_list = new ArrayList<ObjBattle>();

    private ObjBattlePlayer m_battle_player;
    private ObjBattleEnemy m_battle_enemy;

    // 引数にプレイヤーの操作キャラデータと敵キャラデータを指定予定
    public ObjBattleManager()
    {
        // メッセージウィンドウ作成
        m_message_window = new ObjMessageWindow();
        ObjectManager.Insert(m_message_window);
        // プレイヤーコマンド作成
        m_player_command = new ObjPlayerCommand();
        ObjectManager.Insert(m_player_command);

        m_battle_player = new ObjBattlePlayer();
        object_list.add(m_battle_player);

        m_battle_enemy = new ObjBattleEnemy();
        object_list.add(m_battle_enemy);
    }

    @Override
    public void Update()
    {
        if(m_player_command.m_attack_button.GetTouchBotton() == true)
        {
            m_is_battle = true;
        }
        // 速さの速い順に並べる

        if(m_is_battle == true)
        {
            // 行動
            for(Iterator<ObjBattle>itr = object_list.iterator(); itr.hasNext();)
            {
                itr.next().Attack();
                
            }

            // 全員行動が終わったら、
            m_is_battle = false; // 一時的に戦闘シーンを止める

            // 戦闘コマンドを復活させる
            m_player_command.SetLookAt(true);
        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        for(Iterator<ObjBattle> itr = object_list.iterator(); itr.hasNext();)
        {
            itr.next().Draw(gl);
        }
    }
}
