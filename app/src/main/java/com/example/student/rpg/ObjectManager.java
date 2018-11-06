package com.example.student.rpg;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class ObjectManager
{
    private static ArrayList<Obj> m_object_list = new ArrayList<Obj>();

    // オブジェクト更新
    public static void Update()
    {
        for(Iterator<Obj>itr = m_object_list.iterator(); itr.hasNext();)
        {
            Obj obj = itr.next();

            // 削除フラグがたっていれば、リストから削除する
            if((obj.m_obj_state & Obj.Obj_State.Obj_Delete.getInt()) ==
                    Obj.Obj_State.Obj_Delete.getInt())
            {
                itr.remove();
                continue;
            }

            // Update停止フラグが立っていれば、Updateさせないようにする
            if((obj.m_obj_state & Obj.Obj_State.No_Update.getInt()) ==
                    Obj.Obj_State.No_Update.getInt())
            {
                continue;
            }

            obj.Update();
        }
    }

    // オブジェクト描画
    public static void Draw(GL10 gl)
    {
        for(Iterator<Obj>itr = m_object_list.iterator(); itr.hasNext();)
        {
            Obj obj = itr.next();

            // Draw停止フラグが立っていれば、Drawさせないようにする
            if((obj.m_obj_state & Obj.Obj_State.No_Draw.getInt()) ==
                    Obj.Obj_State.No_Draw.getInt())
            {
                continue;
            }

            obj.Draw(gl);
        }
    }

    // オブジェクト追加
    public static void Insert(Obj obj)
    {
        m_object_list.add(obj);
    }

    // オブジェクトデータ取得
    public static ArrayList<Obj> GetClassObjs(Class class_data)
    {
        ArrayList<Obj> out_list = new ArrayList<>();

        for(Iterator<Obj>itr = m_object_list.iterator(); itr.hasNext();)
        {
            Obj object = itr.next();
            if(object.getClass() == class_data)
            {
                out_list.add(object);
            }
        }

        return out_list;
    }

    // リスト内にあるオブジェクトをすべて削除
    public static void ObjectClear()
    {
        m_object_list.clear();
    }

    // リストの中にあるオブジェクトを全てを取得する関数(ただし、メンバのリストの本体ではなくコピーを渡す)
    public static ArrayList<Obj> GetObjectListCopy()
    {
        ArrayList<Obj> out_list = new ArrayList<>();

        for(Iterator<Obj>itr = m_object_list.iterator(); itr.hasNext();)
        {
            out_list.add(itr.next());
        }

        return out_list;
    }
}
