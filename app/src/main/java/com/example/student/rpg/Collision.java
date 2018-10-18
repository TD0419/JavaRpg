package com.example.student.rpg;

public class Collision
{
    public static class AABB
    {
        public float top    = 0.f;
        public float left   = 0.f;
        public float right  = 0.f;
        public float bottom = 0.f;

        public AABB(){}
    }

    // 円と点の当たり判定
    public static boolean CollisioCircleAndPoint(float c_x, float c_y, float c_r, float p_x, float p_y)
    {
        if(Math.pow(c_x - p_x, 2) + Math.pow(c_y - p_y, 2) <= Math.pow(c_r, 2))
        {
            // 当たっている
            return true;
        }

        return false;
    }

    // 点とAABBの当たり判定
    public static boolean HitPointAndAABB(float p_x, float p_y, AABB aabb)
    {
        if(p_x <= aabb.left)   return false;
        if(p_x >= aabb.right)  return false;
        if(p_y >= aabb.top)    return false;
        if(p_y <= aabb.bottom) return false;

        // 当たっている
        return true;
    }
}
