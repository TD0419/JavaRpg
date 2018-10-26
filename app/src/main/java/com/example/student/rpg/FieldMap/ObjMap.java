package com.example.student.rpg.FieldMap;

import android.graphics.Point;

import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class ObjMap extends Obj
{
    final int final_map_height = 10;
    final int final_map_width = 10;
    final float final_object_size = 0.2f;

    //Map<Integer, Map<Integer, Integer>> m_map = new LinkedHashMap<Integer, Map<Integer, Integer>>();
    int[][] m_map_data_array =
            {
                    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, },
                    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
            };

    public enum Map_Kind
    {
        None(0),
        Impassable(1),
        // マップ遷移
        ;

        private int id;

        private Map_Kind(final int id)
        {
            this.id = id;
        }

        public int getInt()
        {
            return this.id;
        }
    }

    public ObjMap()
    {
        // マップ情報を初期化
//        for(int y = 0; y < final_map_height; y++)
//        {
//            Map<Integer, Integer> tempMap = new LinkedHashMap<Integer, Integer>();
//            for(int x = 0; x < final_map_width; x++)
//            {
//                tempMap.put(x,0);
//            }
//            m_map.put(y, tempMap);
//        }
    }

    @Override
    public void Draw(GL10 gl)
    {
        for(int y = 0; y < final_map_height; y++)
        {
            for(int x = 0; x < final_map_width; x++)
            {
                if(m_map_data_array[y][x] == Map_Kind.Impassable.getInt())
                {
                    GraphicUtil.drawTexture(gl, m_x + x * final_object_size, m_y - y * final_object_size,
                            final_object_size, final_object_size, MyRenderer.m_block_texture);
                }
            }
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
        // 探索配列を作成
        int[][] search_map = new int [final_map_height][final_map_width];
        Arrays.fill(search_map, 0);
//
//        Route_Search(search_map, start_point, end_point, 0);
//
        int a = 0;
    }

    void Route_Search(int[][] search_map,
                                  Point start_point, Point end_point, int depth_num)
    {
//    int search_map[][MAP_WIDTH_MAX_ELEMENT],
//    Point start_point, Point end_point, int depth_num)
//    {
//        // 調べる方向			 上			 左			下		 右
//        Point direction[] = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
//
//        depth_num++;
//        search_map[start_point.y][start_point.x] = depth_num;
//
//        // 現在の位置が目的地ついていたら処理終了
//        if (start_point.x == end_point.x &&
//                start_point.y == end_point.y)
//        {
//            return;
//        }
//
//        for (int i = 0; i < 4; i++)
//        {
//            // 次に調べる位置
//            Point next_point;
//            next_point.x = start_point.x + direction[i].x;
//            next_point.y = start_point.y + direction[i].y;
//            // 通行可能かどうか
//            if (m_map_data[next_point.y][next_point.x] == BLOCK)
//            {
//                continue;
//            }
//
//            //
//            if (search_map[next_point.y][next_point.x] == 0 ||
//                    search_map[next_point.y][next_point.x] > depth_num)
//            {
//                Route_Search(search_map, next_point, end_point, depth_num);
//            }
//        }
//    }
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
