package com.example.student.rpg;

import javax.microedition.khronos.opengles.GL10;

// スーパークラス
public class Obj
{
    // オブジェクトの現在の状態(Update関数を動かすかや、削除されるかなど)
    public enum Obj_State
    {
        ObjDelete(1), // オブジェクト削除フラグ
        StopUpdate(2),// Update関数を止めるフラグ
        StopDraw(4),// Draw関数を止めるフラグ
        ;

        private int id;

        Obj_State(final int id)
        {
            this.id = id;
        }

        public int getInt()
        {
            return this.id;
        }
    }

    public void Update(){}
    public void Draw(GL10 gl){}

    protected float m_x; // 位置
    protected float m_y;

    protected int m_obj_state = 0;

    public float GetPosX() { return m_x; }
    public float GetPosY() { return m_y; }
    public int GetObjState() { return m_obj_state; }
    public void InitObjState() { m_obj_state = 0; } // フラグ初期化
    public void EnableObjState(Obj_State obj_state) { m_obj_state = m_obj_state | obj_state.getInt();}
    public void DisableObjState(Obj_State obj_state) { m_obj_state = m_obj_state & (~obj_state.getInt()); }
}

