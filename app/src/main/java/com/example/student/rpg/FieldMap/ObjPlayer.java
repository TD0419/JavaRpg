package com.example.student.rpg.FieldMap;

import com.example.student.rpg.Global;
import com.example.student.rpg.GraphicUtil.Rect;
import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import javax.microedition.khronos.opengles.GL10;

public class ObjPlayer extends Obj
{
    Rect m_rect = new Rect();
    ObjMap m_objmap;

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
        if(Global.touch_push == true)
        {
            // プレイヤーの現在地をマップ位置に変換
            int player_map_pos[] = m_objmap.PosConvertMapPos(m_x, m_y);

            // タッチした位置をマップ位置に変換
            int touch_map_pos[] = m_objmap.PosConvertMapPos(Global.touch_x, Global.touch_y);

            // 最短ルート取得
            m_objmap.Shortest_Route(player_map_pos[0], player_map_pos[1], touch_map_pos[0], touch_map_pos[1]);
        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        GraphicUtil.drawTexture(gl, m_x, m_y, 0.25f, 0.25f, MyRenderer.m_player_texture,
                m_rect, 1.f, 1.f, 1.f, 1.f);
    }
}
