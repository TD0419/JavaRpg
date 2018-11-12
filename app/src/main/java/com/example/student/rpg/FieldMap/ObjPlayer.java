package com.example.student.rpg.FieldMap;

import com.example.student.rpg.Battle.BattleStateInfo;
import com.example.student.rpg.Battle.ObjBattle;
import com.example.student.rpg.Battle.ObjBattleManager;
import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil.Rect;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjectManager;
import com.example.student.rpg.Point_Int;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.abs;

public class ObjPlayer extends Obj
{
    Rect m_rect = new Rect(); // 画像の切り取り位置
    ObjMap m_objmap;

    ArrayList<Point_Int> m_root; // 移動ルート

    float m_velocity_x; // 移動ベクトル
    float m_velocity_y;
    final float m_final_speed = 0.05f; // 移動速度
    float m_move_amount; // 移動量(1マス進んだかの確認用)
    float m_direction;   // キャラクターが向いている向き
    int m_animation_time;
    int m_animation_count;
    final int m_final_animation_interval = 5;

    // ステータス情報
    BattleStateInfo m_battle_state_info = new BattleStateInfo();

    public ObjPlayer(float x, float y, ObjMap objmap)
    {
        m_x = x;
        m_y = y;

        m_velocity_x = 0.f;
        m_velocity_y = 0.f;

        // 移動量(マス移動が完了したかどうかの判定用なのでそれ以外で使わないようにしてください)
        m_move_amount = 0;

        m_rect.top    = 127.f / 256.f;
        m_rect.left   = 224.f / 382.f;
        m_rect.right  = m_rect.left + 32.f / 382.f;
        m_rect.bottom = m_rect.top + 32.f / 256.f;

        m_objmap = objmap;

        // ステータス設定
        m_battle_state_info.attack = 10;
        m_battle_state_info.max_hp = 100;
        m_battle_state_info.hp = m_battle_state_info.max_hp;
        m_battle_state_info.speed = 10;

        // アニメーション情報
        m_animation_time = 0;
        m_animation_count = 1;
    }

    @Override
    public void Update()
    {
        // プレイヤーの現在地をマップ位置に変換
        Point_Int player_map_pointInt = m_objmap.PosConvertMapPos(m_x, m_y);

        if(Global.touch_push == true && m_move_amount == 0.f)
        {
            // タッチした位置をマップ位置に変換
            Point_Int touch_map_pointInt = m_objmap.PosConvertMapPos(Global.touch_x, Global.touch_y);

            // 最短ルート取得
            m_root = m_objmap.Shortest_Route(player_map_pointInt, touch_map_pointInt);

            // 敵シンボルをタッチしていたら
            if(m_objmap.Get_Map_Element(touch_map_pointInt) == ObjMap.Map_Kind.SymbolEnemy.getInt())
            {
                // 敵シンボルの隣にいたら、敵の特有のイベントを起こす
                int distance = 0;
                distance += abs(player_map_pointInt.x - touch_map_pointInt.x);
                distance += abs(player_map_pointInt.y - touch_map_pointInt.y);

                if(distance == 1)
                {
                    // 現在あるオブジェクトをすべて取得
                    ArrayList<Obj> obj_list = ObjectManager.GetObjectListCopy();

                    // オブジェクトをすべて取得
                    for(Iterator<Obj> itr = obj_list.iterator(); itr.hasNext();)
                    {
                        Obj object = itr.next();
                        object.EnableObjState(Obj_State.StopUpdate); // 更新関数を止める
                        object.EnableObjState(Obj_State.StopDraw);   // 描画関数を止める
                    }

                    // 戦闘シーンへ
                    ObjBattleManager obj_battle_manager = new ObjBattleManager();
                    ObjectManager.Insert(obj_battle_manager);
                }
            }

            Global.touch_push = false;
        }

        // 最短ルートにリストのデータがあれば
        if(m_root != null && m_root.isEmpty() == false
                && m_move_amount == 0.f)
        {
            // 今の位置と後ろデータを使って次の進む位置を調べる
            Point_Int next_pointInt = m_root.get(m_root.size() - 1);

            // 進む向きを入れて主人公を動かす
            m_velocity_x = +(float)(next_pointInt.x - player_map_pointInt.x) * m_final_speed;
            m_velocity_y = -(float)(next_pointInt.y - player_map_pointInt.y) * m_final_speed;
        }

        // 移動量を記録(1マス進んだかを判定するため)
        m_move_amount += abs(m_velocity_x);
        m_move_amount += abs(m_velocity_y);

        // 移動処理
        //m_x += m_velocity_x;
        //m_y += m_velocity_y;
        // マップをスクロールさせる(主人公を動かさずに、スクロールによって動いているように見せる)
        m_objmap.Map_Move_Scroll(m_velocity_x, m_velocity_y);

        MoveAnimation();

        // キャラクターの移動の向きによって、画像の切り取り位置を変える
        if(m_velocity_y < 0.f)
        {
            m_rect.top = 128.f / 256.f;
            m_rect.bottom = m_rect.top + 32.f / 256.f;
        }
        else if(m_velocity_x < 0.f)
        {
            m_rect.top = 160.f / 256.f;
            m_rect.bottom = m_rect.top + 32.f / 256.f;
        }
        else if(m_velocity_x > 0.f)
        {
            m_rect.top = 192.f / 256.f;
            m_rect.bottom = m_rect.top + 32.f / 256.f;
        }
        else if(m_velocity_y > 0.f)
        {
            m_rect.top = 224.f / 256.f;
            m_rect.bottom = m_rect.top + 32.f / 256.f;
        }

        // 移動し終わったら、リストから後ろのデータを削除
        if(m_move_amount >= ObjMap.final_object_size)
        {
            m_move_amount = 0.f;
            m_velocity_x = 0.f;
            m_velocity_y = 0.f;
            m_root.remove(m_root.size() - 1);
        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(gl, m_x, m_y, 0.25f, 0.25f, MyRenderer.m_char_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }

    // ステータス情報を返す関数
    public BattleStateInfo GetBattleState()
    {
        return m_battle_state_info;
    }

    // ステータス情報を設定する関数
    public void SetBattleStateInfo(BattleStateInfo battle_state_info)
    {
        m_battle_state_info = battle_state_info;
    }

    private void MoveAnimation()
    {
        if(abs(m_velocity_x) > 0.f || abs(m_velocity_y) > 0.f)
        {
            if(m_animation_time > m_final_animation_interval)
            {
                m_animation_count++;
                m_animation_time = 0;

                if(m_animation_count > 2)
                {
                    m_animation_count = 0;
                }
            }

            m_animation_time++;
        }
        else
        {
            m_animation_count = 1;
            m_animation_time = 0;
        }

        m_rect.left = 192.f / 382.f + (32.f / 382.f) * m_animation_count;
        m_rect.right = m_rect.left + 32.f / 382.f;
    }
}
