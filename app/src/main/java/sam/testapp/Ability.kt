package sam.testapp

open class Ability(var type: String = "default ability",
                   var relative_pairs: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int,Int>>(),
                   var speed: Int = 0,
                   var cooldown: Int = 1)