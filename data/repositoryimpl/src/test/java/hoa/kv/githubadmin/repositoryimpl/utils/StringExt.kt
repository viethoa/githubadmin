package hoa.kv.githubadmin.repository.utils

import org.json.JSONException
import org.json.JSONObject

fun String.toUserMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    try {
        val jsonObject = JSONObject(this)
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = jsonObject.get(key)
            map[key] = value
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return map
}