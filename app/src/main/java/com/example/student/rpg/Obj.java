package com.example.student.rpg;

import javax.microedition.khronos.opengles.GL10;

// スーパークラス
public class Obj
{
    // オブジェクトの現在の状態(Update関数を動かすかや、削除されるかなど)
    public enum Obj_State
    {
        Obj_Delete(0001), // オブジェクト削除フラグ
        No_Update(0010),// Update関数を止めるフラグ
        No_Draw(0100),// Draw関数を止めるフラグ
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

    public void Update(){};
    public void Draw(GL10 gl){}

    protected float m_x; // 位置
    protected float m_y;

    protected int m_obj_state = 0;
//    protected boolean m_is_delete = false;
//    protected boolean m_can_update = true;
//    protected boolean m_can_draw = true;

    public float GetPosX() { return m_x; }
    public float GetPosY() { return m_y; }
    public int GetObjState() { return m_obj_state; }
    public void InitObjState() { m_obj_state = 0; } // フラグ初期化
    public void SetObjState(Obj_State obj_state) { m_obj_state = m_obj_state | obj_state.getInt(); }
//    public boolean GetIsDelete(){ return m_is_delete; }
//    public void SetIsDelete(boolean f) { m_is_delete = f;}
//    public void SetCanUpdate(boolean f) { m_can_update = f;}
//    public void SetCanDraw(boolean f){ m_can_draw = f; }
}

