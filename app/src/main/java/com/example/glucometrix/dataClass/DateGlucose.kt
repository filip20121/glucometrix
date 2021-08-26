package com.example.glucometrix.dataClass

class DateGlucose(private var itemDate: String, private var subItemList: List<GlucoseData>) {

    fun getItemDate(): String {
        return itemDate
    }

    fun setItemDate(itemTitle: String) {
        this.itemDate = itemTitle
    }

    fun getSubItemList(): List<GlucoseData> {
        return subItemList
    }

    fun setSubItemList(subItemList: List<GlucoseData>) {
        this.subItemList = subItemList
    }
}