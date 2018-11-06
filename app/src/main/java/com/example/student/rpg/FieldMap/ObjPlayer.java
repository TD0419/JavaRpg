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

    ArrayList<Point_Int> m_root;

    float m_velocity_x; // 移動ベクトル
    float m_velocity_y;

    final float m_final_speed = 0.05f; // 移動速度

    float m_move_amount; // 移動量(1マス進んだかの確認用)

    // ステータス情報
    BattleStateInfo battle_state_info = new BattleStateInfo();

    public ObjPlayer(float x, float y, ObjMap objmap)
    {
        m_x = x;
        m_y = y;

        m_velocity_x = 0.f;
        m_velocity_y = 0.f;

        // 移動量
        m_move_amount = 0;

        m_rect.top = 0.f;
        m_rect.left = 0.f;
        m_rect.right = 1.f / 4.f;
        m_rect.bottom = 1.f / 4.f;

        m_objmap = objmap;

        battle_state_info.attack = 10;
        battle_state_info.hp = 100;
        battle_state_info.speed = 10;
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
                    // 戦闘
                    ArrayList obj_list = ObjectManager.GetObjectListCopy();

                    for(Iterator<Obj> itr = obj_list.iterator(); itr.hasNext();)
                    {
                        Obj object = itr.next();
                        object.SetObjState(Obj_State.No_Update); // 更新関数を止める
                        object.SetObjState(Obj_State.No_Draw);   // 描画関数を止める
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
        GraphicUtil.drawTexture(gl, m_x, m_y, 0.25f, 0.25f, MyRenderer.m_player_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }
}
