package com.example.glucometrix.dataClass

class GlucoseData(private var subItemHour: String, private var subItemGlucose: String, private var subItemDesc: String) {

    fun getSubItemHour(): String {
        return subItemHour
    }

    fun setSubItemHour(subItemHour: String) {
        this.subItemHour = subItemHour
    }

    fun getSubItemGlucose(): String {
        return subItemGlucose
    }

    fun setSubItemGlucose(subItemGlucose: String) {
        this.subItemGlucose = subItemGlucose
    }

    fun getSubItemDesc(): String {
        return subItemDesc
    }

    fun setSubItemDesc(subItemDesc: String) {
        this.subItemDesc = subItemDesc
    }
}