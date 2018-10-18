package com.example.student.rpg.FieldMap;

import com.example.student.rpg.Obj;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObjMap extends Obj
{
    Map<Integer, Map<Integer, Integer>> m_map = new LinkedHashMap<Integer, Map<Integer, Integer>>();

    //
    enum Map_Kind
    {
        // 通行可能
        // 通行不可
        // マップ遷移
    }

    final int final_map_height = 10;
    final int final_map_width = 10;
    final float final_object_size = 0.1f;

    public ObjMap()
    {
        // マップ情報を初期化
        for(int y = 0; y < final_map_height; y++)
        {
            Map<Integer, Integer> tempMap = new LinkedHashMap<Integer, Integer>();
            for(int x = 0; x < final_map_width; x++)
            {
                tempMap.put(x,0);
            }
            m_map.put(y, tempMap);
        }
    }

    // ファイルからマップデータ取得
    public void LoadMapData(char filename)
    {

    }

    // マップから最短ルートを取得
    // 引数は全てのマップの要素数にする
    public void Shortest_Route(float start_pos_x, float start_pos_y, float end_pos_x, float end_pos_y)
    {

    }

    // 位置をマップの配列の要素に変換
    public int[] PosConvertMapPos(float x, float y)
    {
        int[] out_pos = new int[2];
        float map_pos_x, map_pos_y;
        // 計算が見づらい・・・修正案件
        map_pos_x = (final_map_width  / 2.f + ((x - m_x) / final_object_size));
        map_pos_y = (final_map_height / 2.f - ((y - m_y) / final_object_size));

        out_pos[0] = (int)map_pos_x;
        out_pos[1] = (int)map_pos_y;

        // -0.01fは 0から-1に変える
        // -0.3f は-3から-4に変える
        if(map_pos_x < 0.f)
            out_pos[0]--;
        if(map_pos_y < 0.f)
            out_pos[1]--;

        return out_pos;
    }
}
