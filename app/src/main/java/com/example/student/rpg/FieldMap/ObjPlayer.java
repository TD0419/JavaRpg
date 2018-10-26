package com.example.student.rpg.FieldMap;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil.Rect;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;
import com.example.student.rpg.Point;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class ObjPlayer extends Obj
{
    Rect m_rect = new Rect();
    ObjMap m_objmap;

    ArrayList<Point> m_root;

    public ObjPlayer(float x, float y, ObjMap objmap)
    {
        m_x = x;
        m_y = y;

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

        if(Global.touch_push == true)
        {
            // タッチした位置をマップ位置に変換
            Point touch_map_point = m_objmap.PosConvertMapPos(Global.touch_x, Global.touch_y);

            // 最短ルート取得
            m_root = m_objmap.Shortest_Route(player_map_point, touch_map_point);

            Global.touch_push = false;
        }

        // 最短ルートにリストにデータがあれば、先頭データを取得

        // 今の位置と先頭データを使って次の進む位置を調べる

        // 進む向きを入れて主人公を動かす

        // 移動し終わったら、リストから先頭データを削除
    }

    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(gl, m_x, m_y, 0.25f, 0.25f, MyRenderer.m_player_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }
}
