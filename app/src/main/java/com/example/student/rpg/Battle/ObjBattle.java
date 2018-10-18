package com.example.student.rpg.Battle;

import com.example.student.rpg.Obj;

public class ObjBattle extends Obj
{
    public String m_name;

    // ステータス情報
    public class State_Info
    {
        public int hp;
        public int attack;
        public int speed;
    }

    public String GetName() { return m_name; }

    public int Attack()
    {
        return m_state_info.attack;
    }

    public void Defense(int attack)
    {
        // ダメージ計算
        m_state_info.hp -= attack;
    }
    // ステータス情報
    protected State_Info m_state_info = new State_Info();
}
