package com.example.student.rpg.Battle;

import com.example.student.rpg.Global;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjMessageWindow;
import com.example.student.rpg.ObjectManager;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Thread.sleep;

public class ObjBattleManager extends Obj implements Runnable
{
    private ObjMessageWindow m_message_window;
    private ObjPlayerCommand m_player_command;
    private static ArrayList<ObjBattle> m_object_list = new ArrayList<ObjBattle>();

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
        m_object_list.add(m_battle_player);

        m_battle_enemy = new ObjBattleEnemy();
        m_object_list.add(m_battle_enemy);

        m_thread = new Thread(this);
        m_thread.start();
    }

    @Override
    public void run()
    {
        // 速さの速い順に並べる(未実装)

        while(true)
        {
            m_message_window.SetMsseageText("どうする？ ▽");

            // 戦闘コマンドを押すまで処理を止める
            Stop_Attack_Move();

            // 戦闘コマンドをみえなくして、使えなくする
            m_player_command.SetLookAt(false);

            // 行動
            for (Iterator<ObjBattle> itr_attack = m_object_list.iterator(); itr_attack.hasNext(); )
            {
                ObjBattle obj_battle = itr_attack.next();
                ObjBattle.Attack_info attack = obj_battle.GetAttack();
                BattleStateInfo state_info = obj_battle.GetState();

                int count = 0;

                for (Iterator<ObjBattle> itr_defence = m_object_list.iterator(); itr_defence.hasNext(); )
                {
                    count++;
                    ObjBattle enemy_obj_battle = itr_defence.next();

                    if (count == attack.enemy_number)
                    {
                        // ダメージを与える
                        enemy_obj_battle.Defense(state_info.attack);
                        m_message_window.SetMsseageText(
                                enemy_obj_battle.m_name + "は" + state_info.attack + "のダメージを受けた ▽");


                        break;
                    }
                }

                // 戦闘終了判定
                //if()

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
        for(Iterator<ObjBattle> itr = m_object_list.iterator(); itr.hasNext();)
        {
            itr.next().Draw(gl);
        }
    }

    // 画面をタッチするまで動かなくする関数
    private void Stop_Touch_Move()
    {
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
                    break;
                }
            }
            catch (RuntimeException run) { ; }
        }
    }

    // 戦闘コマンドをタッチするまで動かなくする関数
    private void Stop_Attack_Move()
    {
        while (true)
        {
            try
            {
                if (m_player_command.m_attack_button.GetTouchButton() == true)
                {
                    m_player_command.m_attack_button.SetTouchButton(false);
                    break;
                }
            }
            catch (RuntimeException run) { ; }
        }
    }

    // 戦闘が終わったかどうかを調べる関数
    private boolean BattleEndCheck()
    {
        return false;
    }

    // フィールドマップに戻す関数
    private void GoFieldMap()
    {
        // メッセージウィンドウ削除
        m_message_window.SetObjState(Obj_State.Obj_Delete);

        // プレイヤーコマンド削除
        m_player_command.SetObjState(Obj_State.Obj_Delete);

        for(Iterator<ObjBattle>itr = m_object_list.iterator(); itr.hasNext();)
        {
            itr.next().SetObjState(Obj_State.Obj_Delete);
        }

        // バトルマネージャーも削除
        SetObjState(Obj_State.Obj_Delete);

        m_thread.interrupt();
    }
}
