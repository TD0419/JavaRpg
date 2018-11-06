package com.example.student.rpg.FieldMap;

import com.example.student.rpg.GraphicUtil;
import com.example.student.rpg.MyRenderer;
import com.example.student.rpg.Obj;
import com.example.student.rpg.ObjectManager;
import com.example.student.rpg.Point_Int;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class ObjMap extends Obj
{
    final int final_map_height = 10;
    final int final_map_width = 10;
    public final static float final_object_size = 0.2f;
    final float final_object_size_half = final_object_size / 2.f;
    final int final_not_search = 99999;

    int[][] m_map_data_array =
            {
                    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
                    { 1, 0, 0, 0, 2, 0, 0, 0, 0, 1, },
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
        None(0),       // 何もない通れる道
        Impassable(1), // 通れない道
        SymbolEnemy(2),// 敵シンボル
        ;

        private int id;

        Map_Kind(final int id)
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
        for(int y = 0; y < final_map_height; y++)
        {
            for(int x = 0; x < final_map_width; x++)
            {
                // 敵シンボルがあれば、敵を生成
                if(m_map_data_array[y][x] == Map_Kind.SymbolEnemy.getInt())
                {
                    // マップの位置からOpenGl座標に変換
                    ObjSymbolEnemy obj_symbol_enemy = new ObjSymbolEnemy(
                            x * final_object_size, -(y * final_object_size),this);
                    ObjectManager.Insert(obj_symbol_enemy);
                }
            }
        }
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
    // 引数1 Point_Int : 探索のスタート座標
    // 引数2 Point_int : 探索のゴール座標
    public ArrayList<Point_Int> Shortest_Route(Point_Int start_pointInt, Point_Int end_pointInt)
    {
        // スタートとゴールの場所がマップの範囲内じゃなければ、処理を終了させる
        if (Map_In_A_Range_Check(start_pointInt) == false ||
                Map_In_A_Range_Check(end_pointInt)   == false)
        {
            return null;
        }

        // スタートまたはゴールが通れない位置にある場合は、処理を終了させる
        if (m_map_data_array[start_pointInt.y][start_pointInt.x] == Map_Kind.Impassable.getInt() ||
                m_map_data_array[end_pointInt.y][end_pointInt.x] == Map_Kind.Impassable.getInt())
        {
            return null;
        }

        // スタートまたはゴールが通れない位置にある場合は、処理を終了させる
        if (m_map_data_array[start_pointInt.y][start_pointInt.x] == Map_Kind.SymbolEnemy.getInt() ||
                m_map_data_array[end_pointInt.y][end_pointInt.x] == Map_Kind.SymbolEnemy.getInt())
        {
            return null;
        }

        // 現在の位置が目的地についていたら処理終了させる
        if (start_pointInt.x == end_pointInt.x &&
                start_pointInt.y == end_pointInt.y)
        {
            return null;
        }

        // 探索配列を作成
        int[][] search_map = new int[final_map_height][final_map_width];
        for(int y = 0; y < final_map_height; y++)
        {
            for(int x = 0; x < final_map_width; x++)
            {
                search_map[y][x] = final_not_search;
            }
        }

        Route_Search(search_map, start_pointInt, end_pointInt, 0);

        // 最短ルート取得
        return Route_Get(search_map, end_pointInt);
    }

    // ルート検索(再帰関数)
    void Route_Search(int[][] search_map, Point_Int start_pointInt,
                      Point_Int end_pointInt, int depth_num)
    {
        // 調べる方向                               上                        左                         下                        右
        final Point_Int direction[] = { new Point_Int(0, -1), new Point_Int(-1, 0), new Point_Int(0, 1), new Point_Int(1, 0) };
        depth_num++;
        search_map[start_pointInt.y][start_pointInt.x] = depth_num;

        // 現在の位置が目的についていたら処理終了
        if(start_pointInt.x == end_pointInt.x && start_pointInt.y == end_pointInt.y)
        {
            return;
        }

        for(int i = 0; i < 4; i++)
        {
            // 次に調べる位置
            Point_Int next_pointInt = new Point_Int();
            next_pointInt.x = start_pointInt.x + direction[i].x;
            next_pointInt.y = start_pointInt.y + direction[i].y;

            // 範囲内外なら違う道を探す
            if (Map_In_A_Range_Check(next_pointInt) == false) continue;

            // 通行可能かどうか
            if(m_map_data_array[next_pointInt.y][next_pointInt.x] == Map_Kind.Impassable.getInt()) continue;
            if(m_map_data_array[next_pointInt.y][next_pointInt.x] == Map_Kind.SymbolEnemy.getInt()) continue;

            // その次の先を調べるかどうか
            if(search_map[next_pointInt.y][next_pointInt.x] > depth_num)
            {
                Route_Search(search_map, next_pointInt, end_pointInt, depth_num);
            }
        }
    }

    // 最短ルート取得(検索配列を使って、ゴールの位置から調べる)
    private ArrayList<Point_Int> Route_Get(int search_map[][], Point_Int end_pointInt)
    {
        // 調べる方向            上       左      下      右
        final Point_Int direction[] = { new Point_Int(0, -1), new Point_Int(-1, 0),
                new Point_Int(0, 1), new Point_Int(1, 0) };

        // 現在地
        Point_Int now_pointInt = new Point_Int();

        // 最短ルートを入れる配列
        ArrayList<Point_Int> shortest_route = new ArrayList<Point_Int>();

        // ゴールからスタートに戻るまでの距離
        int distance;
        distance = search_map[end_pointInt.y][end_pointInt.x] - 1;

        shortest_route.add(new Point_Int(end_pointInt.x, end_pointInt.y));
        now_pointInt = end_pointInt;

        for(int i = 0; i < 4; i++)
        {
            // 次の調べる位置
            Point_Int next_pointInt = new Point_Int();
            next_pointInt.x = now_pointInt.x + direction[i].x;
            next_pointInt.y = now_pointInt.y + direction[i].y;

            // スタート位置に戻ってきたら終了
            if(distance == 1) break;

            // 次に進むべき道を探す
            if(search_map[next_pointInt.y][next_pointInt.x] == distance)
            {
                i = -1; // ループを最初からにする
                distance--;
                // 最短ルートに登録
                shortest_route.add(new Point_Int(next_pointInt.x, next_pointInt.y));
                now_pointInt = next_pointInt;
            }
        }

        return shortest_route;
    }

    // OpenGL上の位置をマップの配列の要素に変換
    public Point_Int PosConvertMapPos(float x, float y)
    {
        Point_Int out_pointInt = new Point_Int(); // 戻り値
        float map_pos_x, map_pos_y;

        map_pos_x = +(x - m_x + final_object_size_half) / final_object_size;
        map_pos_y = -(y - m_y - final_object_size_half) / final_object_size;

        out_pointInt.x = (int)map_pos_x;
        out_pointInt.y = (int)map_pos_y;

        return out_pointInt;
    }

    // マップの範囲内チェック
    // 戻り値 boolean 範囲内ならtrue 範囲外ならfalse
    public boolean Map_In_A_Range_Check(Point_Int pointInt)
    {
        if (pointInt.x < 0) return false;
        if (pointInt.y < 0) return false;
        if (pointInt.x >= final_map_width) return false;
        if (pointInt.y >= final_map_height) return false;

        return true;
    }

    // マップをスクロールで移動させる関数
    // 引数1 move_x キャラクターの移動量X
    // 引数2 move_y キャラクターの移動量Y
    public void Map_Move_Scroll(float move_x, float move_y)
    {
        m_x -= move_x;
        m_y -= move_y;
    }

    // マップ位置の要素を返す関数
    public int Get_Map_Element(Point_Int point)
    {
        return m_map_data_array[point.y][point.x];
    }
}