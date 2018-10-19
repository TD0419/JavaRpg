package com.example.student.rpg.Battle;

import com.example.student.rpg.Global;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjectManager;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class ObjBattleManager extends Obj implements Runnable
{
    boolean m_is_battle_command; // バトルコマンドが押されたか
    boolean m_is_battle_exit;    // バトルが終了したか

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

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {
        //m_message_window.StringDraw("どうする？あいふる？");

        Stop_Attack_Move();

        m_player_command.SetLookAt(false);
        // 速さの速い順に並べる

        // 行動
        for(Iterator<ObjBattle>itr = object_list.iterator(); itr.hasNext();)
        {
             ObjBattle obj_battle = itr.next();
             ObjBattle.Attack_info attack = obj_battle.Attack();
             ObjBattle.State_Info state = obj_battle.GetState();

             int count = 0;
             // 無限ループなのでbreakできるかどうかの確認は怠らないようにする
             while(true)
             {
                count++;
                ObjBattle enemy_obj_battle = itr.next();

                if(count == attack.enemy_number)
                {
                    // 計算式

                    // ダメージを与える
                    enemy_obj_battle.Defense(state.attack);
                    m_message_window.StringDraw(enemy_obj_battle.m_name + "に" + state.attack + "のダメージを与えた");
                    break;
                }
             }

             Stop_Touch_Move();
        }

        // 全員行動が終わったら、
        m_is_battle_command = false; // 一時的に戦闘シーンを止める

        // 戦闘コマンドを復活させる
        m_player_command.SetLookAt(true);

    }

    @Override
    public void Draw(GL10 gl)
    {
        for(Iterator<ObjBattle> itr = object_list.iterator(); itr.hasNext();)
        {
            itr.next().Draw(gl);
        }
    }

    // 画面をタッチするまで動かなくする関数
    private void Stop_Touch_Move()
    {
        while(true)
        {
            if(Global.touch_push == true) break;
        }
    }

    // 戦闘コマンドをタッチするまで動かなくする関数
    private void Stop_Attack_Move()
    {
        while (true)
        {
            if(m_player_command.m_attack_button.GetTouchButton() == true)
            {
                m_player_command.m_attack_button.SetTouchButton(false);
                break;
            }
        }
    }
}
