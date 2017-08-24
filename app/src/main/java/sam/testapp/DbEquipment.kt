package sam.testapp

import io.realm.RealmObject

open class DbEquipment(var headid:Int = 0,
                       var shouldersid:Int=0,
                       var legsid:Int = 0,
                       var offhandid:Int = 0,
                       var mainhandid:Int = 0): RealmObject()