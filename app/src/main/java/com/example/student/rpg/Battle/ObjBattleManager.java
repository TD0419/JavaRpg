package com.example.student.rpg.Battle;

import com.example.student.rpg.Global;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjectManager;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Thread.sleep;

public class ObjBattleManager extends Obj implements Runnable
{
    private ObjMessageWindow m_message_window;
    private ObjPlayerCommand m_player_command;
    private static ArrayList<ObjBattle> object_list = new ArrayList<ObjBattle>();

    private ObjBattlePlayer m_battle_player;
    private ObjBattleEnemy m_battle_enemy;

    Thread m_thread;

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

        //Thread thread = new Thread(this);
        //thread.start();
        m_thread = new Thread(this);
        m_thread.start();
    }

    @Override
    public void run()
    {
        // 速さの速い順に並べる(未実装)

        while(true)
        {
            m_message_window.SetMsseageText("どうする？");

            // 戦闘コマンドを押すまで処理を止める
            Stop_Attack_Move();

            // 戦闘コマンドをみえなくして、使えなくする
            m_player_command.SetLookAt(false);

            // 行動
            for (Iterator<ObjBattle> itr = object_list.iterator(); itr.hasNext(); )
            {
                ObjBattle obj_battle = itr.next();
                ObjBattle.Attack_info attack = obj_battle.Attack();
                ObjBattle.State_Info state = obj_battle.GetState();

                int count = 0;

                for (Iterator<ObjBattle> itr2 = object_list.iterator(); itr2.hasNext(); )
                {
                    count++;
                    ObjBattle enemy_obj_battle = itr2.next();

                    if (count == attack.enemy_number)
                    {
                        // 計算式

                        // ダメージを与える
                        enemy_obj_battle.Defense(state.attack);
                        //m_message_window.SetMsseageText(
                        //        enemy_obj_battle.m_name + "は" + state.attack + "のダメージを受けた");
                        break;
                    }
                }

                // 画面をタッチするまで処理を止める
                Stop_Touch_Move();
            }

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

    // 画面をタッチするまで動かなくする関数
    private void Stop_Touch_Move()
    {
        boolean a = false;
        while(true)
        {
            // トライキャッチ文を使ってタッチフラグを適当な変数aにいれたら、
            // 今まで動かなかったプログラムが動いた。
            // なぜ動いたのか、意味が分からなくて怖い。
            try
            {
                if (Global.touch_push == true)
                {
                    Global.touch_push = false;
                    m_message_window.SetMsseageText("画面をタッチしました。");
                    break;
                } else {
                    m_message_window.SetMsseageText("画面をタッチしてください。");
                }
            }
            catch (RuntimeException run)
            {
                a = Global.touch_push;
            }
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
                m_message_window.SetMsseageText("攻撃ボタンをタッチしました。");
                break;
            }
            else
            {
                m_message_window.SetMsseageText("攻撃ボタンをタッチしてください。");
            }
        }
    }
}
