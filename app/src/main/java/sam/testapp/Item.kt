package sam.testapp

import io.realm.RealmObject

open class Item(var name: String = "no item",
                var stat_requirement: StatRequirement = StatRequirement(),
                var equipment_slot: String = "default_slot",
                var ability: Ability = Ability(),
                var price: Int = 0,
                var image_resource: Int = 0,
                var id:Int = 999)