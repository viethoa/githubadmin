package hoa.kv.githubadmin.landing.utils

import org.json.JSONArray
import org.json.JSONException

fun String.toListOfUserMap(): List<Map<String, String>> {
    val list = mutableListOf<Map<String, String>>()
    try {
        val jsonArray = JSONArray(this)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val map = mutableMapOf<String, String>()
            val iterator = jsonObject.keys()
            while (iterator.hasNext()) {
                val key = iterator.next()
                val value = jsonObject.get(key) as? String ?: ""
                map[key] = value
            }
            list.add(map)
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return list
}