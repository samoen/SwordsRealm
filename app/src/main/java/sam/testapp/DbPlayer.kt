package sam.testapp

import io.realm.RealmList
import io.realm.RealmObject

open class DbPlayer(var name: String = "default_player",
                    var stats: Stats? = null,
                    var itemIds: RealmList<ItemID> = RealmList(),
                    var equipped: DbEquipment? = null,
                    var gold: Int = 0,
                    var room: String = "GameRoom1"
                    ) : RealmObject()