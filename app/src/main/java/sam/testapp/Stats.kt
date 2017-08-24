package sam.testapp

import io.realm.RealmObject

open class Stats(  var strength: Int = 5,
                   var dexterity: Int = 5,
                   var intelligence: Int = 5
                    ):RealmObject(){}