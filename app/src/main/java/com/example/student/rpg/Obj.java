package com.example.student.rpg;

import javax.microedition.khronos.opengles.GL10;

// スーパークラス
public class Obj
{
    public void Update(){};
    public void Draw(GL10 gl){};

    protected float m_x; // 位置
    protected float m_y;

    protected boolean m_is_delete = false;

    public float GetPosX() { return m_x; }
    public float GetPosY() { return m_y; }
    public boolean GetIsDelete(){ return m_is_delete; }
    public void SetIsDelete(boolean f) {m_is_delete = f;}
}

