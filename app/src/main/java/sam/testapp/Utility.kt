package sam.testapp

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun CalculatePairFromPosition(pos: Int): Pair<Int, Int> {
    val column = Math.ceil((pos.toDouble() + 1) / 10).toInt()
    val row = column * 10 - pos
    val mycol = Math.abs(column - 11)
    val myrow = Math.abs(row - 11)
    return Pair(myrow, mycol)
}

fun CalculatePositionFromPair(pair: Pair<Int, Int>): Int {
    when (pair.first) {
        1 -> return 100 - (pair.second * 10)
        2 -> return 101 - (pair.second * 10)
        3 -> return 102 - (pair.second * 10)
        4 -> return 103 - (pair.second * 10)
        5 -> return 104 - (pair.second * 10)
        6 -> return 105 - (pair.second * 10)
        7 -> return 106 - (pair.second * 10)
        8 -> return 107 - (pair.second * 10)
        9 -> return 108 - (pair.second * 10)
        10 -> return 109 - (pair.second * 10)
    }
    return 0
}

fun List<Pair<Int,Int>>.CalculatePairsFromRelative(mpNum: Int, boardPos: MutableMap<Int,Int>):List<Pair<Int,Int>> {
    val absolutes = mutableListOf<Pair<Int, Int>>()
    for (v in this) {
        val p = Pair(CalculatePairFromPosition(boardPos[mpNum]?:0).first + v.first, CalculatePairFromPosition(boardPos[mpNum]?:0).second+ v.second)
        absolutes.add(p)
    }
    return absolutes
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0)
}

fun Player.CanChangeStat(direction: Int, stat: String): Boolean {
    val strupperhead: Int = this.equipped.head?.stat_requirement?.strength_upper?:99
    val strlowerhead: Int = this.equipped.head?.stat_requirement?.strength_lower?:99
    val dexupperhead: Int = this.equipped.head?.stat_requirement?.dexterity_upper?:99
    val dexlowerhead: Int = this.equipped.head?.stat_requirement?.dexterity_lower?:99
    val intupperhead: Int = this.equipped.head?.stat_requirement?.intelligence_upper?:99
    val intlowerhead: Int = this.equipped.head?.stat_requirement?.intelligence_lower?:0
    val struppershoulders: Int = this.equipped.shoulders?.stat_requirement?.strength_upper?:99
    val strlowershoulders: Int = this.equipped.shoulders?.stat_requirement?.strength_lower?:0
    val dexuppershoulders: Int = this.equipped.shoulders?.stat_requirement?.dexterity_upper?:99
    val dexlowershoulders: Int = this.equipped.shoulders?.stat_requirement?.dexterity_lower?:0
    val intuppershoulders: Int = this.equipped.shoulders?.stat_requirement?.intelligence_upper?:99
    val intlowershoulders: Int = this.equipped.shoulders?.stat_requirement?.intelligence_lower?:0
    val strupperlegs: Int = this.equipped.legs?.stat_requirement?.strength_upper?:99
    val strlowerlegs: Int = this.equipped.legs?.stat_requirement?.strength_lower?:0
    val dexupperlegs: Int = this.equipped.legs?.stat_requirement?.dexterity_upper?:99
    val dexlowerlegs: Int = this.equipped.legs?.stat_requirement?.dexterity_lower?:0
    val intupperlegs: Int = this.equipped.legs?.stat_requirement?.intelligence_upper?:99
    val intlowerlegs: Int = this.equipped.legs?.stat_requirement?.intelligence_lower?:0
    val strupperoffhand: Int = this.equipped.offhand?.stat_requirement?.strength_upper?:99
    val strloweroffhand: Int = this.equipped.offhand?.stat_requirement?.strength_lower?:0
    val dexupperoffhand: Int = this.equipped.offhand?.stat_requirement?.dexterity_upper?:99
    val dexloweroffhand: Int = this.equipped.offhand?.stat_requirement?.dexterity_lower?:0
    val intupperoffhand: Int = this.equipped.offhand?.stat_requirement?.intelligence_upper?:99
    val intloweroffhand: Int = this.equipped.offhand?.stat_requirement?.intelligence_lower?:0
    val struppermainhand: Int = this.equipped.mainhand?.stat_requirement?.strength_upper?:99
    val strlowermainhand: Int = this.equipped.mainhand?.stat_requirement?.strength_lower?:0
    val dexuppermainhand: Int = this.equipped.mainhand?.stat_requirement?.dexterity_upper?:99
    val dexlowermainhand: Int = this.equipped.mainhand?.stat_requirement?.dexterity_lower?:0
    val intuppermainhand: Int = this.equipped.mainhand?.stat_requirement?.intelligence_upper?:99
    val intlowermainhand: Int = this.equipped.mainhand?.stat_requirement?.intelligence_lower?:0
    
    var canchange = true
    when (stat) {
        "strength" -> {
            if (! ( (strlowerhead..strupperhead).contains(this.stats.strength + direction) ) ) canchange = false
            if (! ( (strlowershoulders..struppershoulders).contains(this.stats.strength + direction) ) ) canchange = false
            if (! ( (strlowerlegs..strupperlegs).contains(this.stats.strength + direction) ) ) canchange = false
            if (! ( (strloweroffhand..strupperoffhand).contains(this.stats.strength + direction) ) ) canchange = false
            if (! ( (strlowermainhand..struppermainhand).contains(this.stats.strength + direction) ) ) canchange = false
        }
        "dexterity" -> {
            if (! ( (dexlowerhead..dexupperhead).contains(this.stats.dexterity + direction) ) ) canchange = false
            if (! ( (dexlowershoulders..dexuppershoulders).contains(this.stats.dexterity + direction) ) ) canchange = false
            if (! ( (dexlowerlegs..dexupperlegs).contains(this.stats.dexterity + direction) ) ) canchange = false
            if (! ( (dexloweroffhand..dexupperoffhand).contains(this.stats.dexterity + direction) ) ) canchange = false
            if (! ( (dexlowermainhand..dexuppermainhand).contains(this.stats.dexterity + direction) ) ) canchange = false
        }
        "intelligence" -> {
            if (! ( (intlowerhead..intupperhead).contains(this.stats.intelligence + direction) ) ) canchange = false
            if (! ( (intlowershoulders..intuppershoulders).contains(this.stats.intelligence + direction) ) ) canchange = false
            if (! ( (intlowerlegs..intupperlegs).contains(this.stats.intelligence + direction) ) ) canchange = false
            if (! ( (intloweroffhand..intupperoffhand).contains(this.stats.intelligence + direction) ) ) canchange = false
            if (! ( (intlowermainhand..intuppermainhand).contains(this.stats.intelligence + direction) ) ) canchange = false
        }
    }
    return canchange
}
fun Player.StatsMet(item: Item): Boolean = (this.stats.strength in item.stat_requirement.strength_lower..item.stat_requirement.strength_upper && this.stats.dexterity in item.stat_requirement.dexterity_lower..item.stat_requirement.dexterity_upper && this.stats.intelligence in item.stat_requirement.intelligence_lower..item.stat_requirement.intelligence_upper)
fun Player.CanAfford(item: Item): Boolean = this.gold >= item.price
fun Player.CalculateMyLootID():Int{
    var resultID = this.equipped.head?.id?:0
    var resultSlot = this.equipped.head?.equipment_slot?:""
    var resultPrice = this.equipped.head?.price?:0

    if (this.equipped.shoulders?.price?:0>resultPrice){
        resultID = this.equipped.shoulders?.id?:0
        resultSlot = this.equipped.shoulders?.equipment_slot?:""
        resultPrice = this.equipped.shoulders?.price?:0

    }
    if (this.equipped.legs?.price?:0>resultPrice){
        resultID = this.equipped.legs?.id?:0
        resultSlot = this.equipped.legs?.equipment_slot?:""
        resultPrice = this.equipped.legs?.price?:0
    }
    if (this.equipped.offhand?.price?:0>resultPrice){
        resultID = this.equipped.offhand?.id?:0
        resultSlot = this.equipped.offhand?.equipment_slot?:""
        resultPrice = this.equipped.offhand?.price?:0
    }
    if (this.equipped.mainhand?.price?:0>resultPrice){
        resultID = this.equipped.mainhand?.id?:0
        resultSlot = this.equipped.mainhand?.equipment_slot?:""
        resultPrice = this.equipped.mainhand?.price?:0
    }
    return resultID
}
fun Player.CalculateMyLootSlot():String{
    var resultID = this.equipped.head?.id?:0
    var resultSlot = this.equipped.head?.equipment_slot?:""
    var resultPrice = this.equipped.head?.price?:0

    if (this.equipped.shoulders?.price?:0>resultPrice){
        resultID = this.equipped.shoulders?.id?:0
        resultSlot = this.equipped.shoulders?.equipment_slot?:""
        resultPrice = this.equipped.shoulders?.price?:0

    }
    if (this.equipped.legs?.price?:0>resultPrice){
        resultID = this.equipped.legs?.id?:0
        resultSlot = this.equipped.legs?.equipment_slot?:""
        resultPrice = this.equipped.legs?.price?:0
    }
    if (this.equipped.offhand?.price?:0>resultPrice){
        resultID = this.equipped.offhand?.id?:0
        resultSlot = this.equipped.offhand?.equipment_slot?:""
        resultPrice = this.equipped.offhand?.price?:0
    }
    if (this.equipped.mainhand?.price?:0>resultPrice){
        resultID = this.equipped.mainhand?.id?:0
        resultSlot = this.equipped.mainhand?.equipment_slot?:""
        resultPrice = this.equipped.mainhand?.price?:0
    }
    return resultSlot
}


fun String.findItemImage(): Int{
    when(this){
        "head1"->return R.drawable.item_image_helmet
        "shoulders1"->return R.drawable.item_image_shoulders
        "legs1"->return R.drawable.item_image_legs
        "offhand1"->return R.drawable.item_image_shield
        "mainhand1"->return R.drawable.item_image_sword
    }
    return 0
}

fun Ability.findRange():Int{
    var result = 0
    for(pair in this.relative_pairs){
        val testR = Math.abs(pair.first).plus(Math.abs(pair.second))
        if (testR > result){
            result = testR
        }
    }
    return result
}