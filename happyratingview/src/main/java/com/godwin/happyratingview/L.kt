package com.godwin.happyratingview

import android.util.Log
import java.util.logging.Logger

/**
 * Created by Godwin on 9/24/2019 8:22 AM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2019
 */
class L{
    companion object{
        var DEBUG: Boolean =true
        fun v(tag: String, msg: String){
            Log.v(tag, msg)
        }
        fun d(tag: String, msg: String){
            Log.d(tag, msg)
        }
        fun i(tag: String, msg: String){
            Log.i(tag, msg)
        }
        fun e(tag: String, msg: String){
            Log.e(tag, msg)
        }
        fun w(tag: String, msg: String){
            Log.w(tag, msg)
        }
    }
}