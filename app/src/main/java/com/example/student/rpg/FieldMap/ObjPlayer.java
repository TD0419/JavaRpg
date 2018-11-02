package com.example.student.rpg.FieldMap;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil.Rect;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;
import com.example.student.rpg.Point;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.abs;

public class ObjPlayer extends Obj
{
    Rect m_rect = new Rect();
    ObjMap m_objmap;

    ArrayList<Point> m_root;

    float m_velocity_x;
    float m_velocity_y;

    final float m_final_speed = 0.04f;

    float m_move_amount;

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
    }

    @Override
    public void Update()
    {
        // プレイヤーの現在地をマップ位置に変換
        Point player_map_point = m_objmap.PosConvertMapPos(m_x, m_y);

        if(Global.touch_push == true && m_move_amount == 0.f)
        {
            // タッチした位置をマップ位置に変換
            Point touch_map_point = m_objmap.PosConvertMapPos(Global.touch_x, Global.touch_y);

            // 最短ルート取得
            m_root = m_objmap.Shortest_Route(player_map_point, touch_map_point);

            Global.touch_push = false;
        }

        // 最短ルートにリストのデータがあれば
        if(m_root != null && m_root.isEmpty() == false
                && m_move_amount == 0.f)
        {
            // 今の位置と後ろデータを使って次の進む位置を調べる
            Point next_point = m_root.get(m_root.size() - 1);

            // 進む向きを入れて主人公を動かす
            m_velocity_x = +(float)(next_point.x - player_map_point.x) * m_final_speed;
            m_velocity_y = -(float)(next_point.y - player_map_point.y) * m_final_speed;
        }

        // 移動処理
        //m_x += m_velocity_x;
        //m_y += m_velocity_y;
        // マップをスクロールさせる
        m_objmap.Map_Move_Scroll(m_velocity_x, m_velocity_y);

        m_move_amount += abs(m_velocity_x);
        m_move_amount += abs(m_velocity_y);

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
