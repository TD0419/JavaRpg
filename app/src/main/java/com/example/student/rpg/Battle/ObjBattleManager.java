package com.example.student.rpg.Battle;

import com.example.student.rpg.FieldMap.ObjPlayer;
import com.example.student.rpg.Global;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjMessageWindow;
import com.example.student.rpg.ObjectManager;
import com.example.student.rpg.Title.ObjBackGround;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Thread.sleep;

public class ObjBattleManager extends Obj implements Runnable
{
    private ObjMessageWindow m_message_window;
    private ObjPlayerCommand m_player_command;
    private static ArrayList<ObjBattle> m_object_list = new ArrayList<ObjBattle>();
    private ObjBattleBackGround m_background;
    private ObjBattlePlayer m_battle_player;
    private ObjBattleEnemy m_battle_enemy;

    Thread m_thread;
    boolean m_is_thread_end;

    public ObjBattleManager()
    {
        // 背景作成
        m_background = new ObjBattleBackGround();
        ObjectManager.Insert(m_background);

        // メッセージウィンドウ作成
        m_message_window = new ObjMessageWindow();
        ObjectManager.Insert(m_message_window);
        // プレイヤーコマンド作成
        m_player_command = new ObjPlayerCommand();
        ObjectManager.Insert(m_player_command);

        // 主人公情報を取得
        ArrayList<Obj> objplayer = ObjectManager.GetClassObjs(ObjPlayer.class);
        // 主人公情報は一つしかないので、最初のデータだけ取得
        BattleStateInfo player_battle_state = ((ObjPlayer)objplayer.iterator().next()).GetBattleState();
        m_battle_player = new ObjBattlePlayer(player_battle_state);
        m_object_list.add(m_battle_player);

//        m_battle_player = new ObjBattlePlayer();
//        m_object_list.add(m_battle_player);

        m_battle_enemy = new ObjBattleEnemy("コウモリ");
        m_object_list.add(m_battle_enemy);

        // 戦闘を管理するマルチスレッドを作成、起動
        m_thread = new Thread(this);
        m_thread.start();
    }

    @Override
    public void run()
    {
        // 速さの速い順に並べる(未実装)

        while(true)
        {
            m_message_window.SetMsseageText("どうする？ ▼");

            // 戦闘コマンドを押すまで処理を止める
            Stop_Attack_Move();

            // 戦闘コマンドをみえなくして、使えなくする
            m_player_command.SetLookAt(false);

            // 行動
            for (Iterator<ObjBattle> itr_attack = m_object_list.iterator(); itr_attack.hasNext(); )
            {
                // 攻撃側
                ObjBattle obj_battle = itr_attack.next();
                ObjBattle.Attack_info attack = obj_battle.GetAttack();
                BattleStateInfo state_info = obj_battle.GetBattleState();

                int count = 0;

                for (Iterator<ObjBattle> itr_defence = m_object_list.iterator(); itr_defence.hasNext(); )
                {
                    // 防御側
                    count++;
                    ObjBattle defence_obj_battle = itr_defence.next();

                    if (count == attack.enemy_number)
                    {
                        // ダメージを与える
                        defence_obj_battle.Defense(state_info.attack);
                        m_message_window.SetMsseageText(
                                defence_obj_battle.m_name + "は" + state_info.attack + "のダメージを受けた ▼");

                        // 戦闘終了判定(誰かのHPが0以下になったら、戦闘終了)
                        if(defence_obj_battle.GetBattleState().hp <= 0)
                        {
                            GoFieldMap();
                            EnableObjState(Obj_State.ObjDelete);
                            return;
                        }

                        break;
                    }
                }

                // 画面をタッチするまで処理を止める
                Stop_Touch_Move();
            }

            if(m_is_thread_end == true) break; // マルチスレッドを閉じるフラグがたっていれば、

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
            catch (RuntimeException run) { }
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
            catch (RuntimeException run) { }
        }
    }

    // フィールドマップシーンに戻す関数
    private void GoFieldMap()
    {
        // 背景削除
        m_background.EnableObjState(Obj_State.ObjDelete);

        // メッセージウィンドウ削除
        m_message_window.EnableObjState(Obj_State.ObjDelete);

        // プレイヤーコマンド削除
        m_player_command.EnableObjState(Obj_State.ObjDelete);

        // 戦闘するキャラクター情報を全て削除
        m_object_list.clear();

        // フィールドマップシーンに戻すために、必要なオブジェクトを使えるようにする
        ArrayList obj_list = ObjectManager.GetObjectListCopy();

        // 主人公のステータスデータをフィールドマップシーンの主人公に返す
        // 主人公情報を取得
        ArrayList<Obj> objplayer = ObjectManager.GetClassObjs(ObjPlayer.class);
        // 主人公情報は一つしかないので、最初のデータだけ取得
        ((ObjPlayer)objplayer.iterator().next()).SetBattleStateInfo(m_battle_player.GetBattleState());

        for(Iterator<Obj> itr = obj_list.iterator(); itr.hasNext();)
        {
            Obj object = itr.next();
            // Update関数とDraw関数が動くように初期化
            object.DisableObjState(Obj_State.StopUpdate);
            object.DisableObjState(Obj_State.StopDraw);
        }
    }
}
