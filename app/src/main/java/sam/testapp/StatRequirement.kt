package sam.testapp

import io.realm.RealmObject

open class StatRequirement( var strength_lower: Int = 0,
                            var strength_upper: Int = 99,
                            var dexterity_lower: Int = 0,
                            var dexterity_upper: Int = 99,
                            var intelligence_lower: Int = 0,
                            var intelligence_upper: Int = 99
                            ):RealmObject()