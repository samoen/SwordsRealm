package sam.testapp

open class Player(var name: String = "default_player",
                  var stats: Stats = Stats(),
                  var items: MutableList<Item> = mutableListOf<Item>(),
                  var equipped: Equipment = Equipment(),
                  var gold: Int = 0,
                  var room: String = "GameRoom1")