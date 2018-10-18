package com.example.student.rpg;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class ObjectManager
{
    private static ArrayList<Obj> object_list = new ArrayList<Obj>();

    // オブジェクト更新
    public static void Update()
    {
        for(Iterator<Obj>itr = object_list.iterator(); itr.hasNext();)
        {
            Obj obj = itr.next();

            if(obj.GetIsDelete() == true)
            {
                itr.remove();
                continue;
            }
            obj.Update();
        }
    }

    // オブジェクト表示
    public static void Draw(GL10 gl)
    {
        for(Iterator<Obj>itr = object_list.iterator(); itr.hasNext();)
        {
            itr.next().Draw(gl);
        }
    }

    // オブジェクト追加
    public static void Insert(Obj obj)
    {
        object_list.add(obj);
    }

    // オブジェクトデータ取得
    public static ArrayList<Obj> GetClassObjs(Class class_data)
    {
        ArrayList<Obj> out_list = new ArrayList<>();

        for(Iterator<Obj>itr = object_list.iterator(); itr.hasNext();)
        {
            Obj object = itr.next();
            if(object.getClass() == class_data)
            {
                out_list.add(object);
            }
        }

        return out_list;
    }
}
