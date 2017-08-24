package sam.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    var currentPlayer:Player = Player()
    var itemList = mutableListOf<Item>()
    lateinit var realm: Realm
    var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        InitFirebase()
        fragmentManager.beginTransaction().replace(R.id.frameLayout_main, LoginFragment()).commit()
    }
    fun InitFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference("ItemList")
        mDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val maxItemId = dataSnapshot.child("maxitemid").getValue(String::class.java).toInt()
                for(i in 0..maxItemId){
                    val item = Item()
                    item.name = dataSnapshot.child(i.toString()).child("name").getValue(String::class.java).toString()
                    item.price = dataSnapshot.child(i.toString()).child("price").getValue(String::class.java).toInt()
                    item.equipment_slot = dataSnapshot.child(i.toString()).child("slot").getValue(String::class.java).toString()
                    item.id = i
                    item.image_resource = dataSnapshot.child(i.toString()).child("image").getValue(String::class.java).findItemImage()
                    item.stat_requirement.strength_lower = dataSnapshot.child(i.toString()).child("stats").child("strlower").getValue(String::class.java).toInt()
                    item.stat_requirement.strength_upper = dataSnapshot.child(i.toString()).child("stats").child("strupper").getValue(String::class.java).toInt()
                    item.stat_requirement.dexterity_lower = dataSnapshot.child(i.toString()).child("stats").child("dexlower").getValue(String::class.java).toInt()
                    item.stat_requirement.dexterity_upper = dataSnapshot.child(i.toString()).child("stats").child("dexupper").getValue(String::class.java).toInt()
                    item.stat_requirement.intelligence_lower = dataSnapshot.child(i.toString()).child("stats").child("intlower").getValue(String::class.java).toInt()
                    item.stat_requirement.intelligence_upper = dataSnapshot.child(i.toString()).child("stats").child("intupper").getValue(String::class.java).toInt()
                    val maxPairId = dataSnapshot.child(i.toString()).child("ability").child("maxpairid").getValue(String::class.java).toString()
                    for(k in 0..maxPairId.toInt() step 2){
                        item.ability.relative_pairs.add(Pair(dataSnapshot.child(i.toString()).child("ability").child("pairs").child(k.toString()).getValue(String::class.java).toInt(),dataSnapshot.child(i.toString()).child("ability").child("pairs").child((k+1).toString()).getValue(String::class.java).toInt()))
                    }
                    item.ability.cooldown = dataSnapshot.child(i.toString()).child("ability").child("cooldown").getValue(String::class.java).toInt()
                    item.ability.speed = dataSnapshot.child(i.toString()).child("ability").child("speed").getValue(String::class.java).toInt()
                    item.ability.type = dataSnapshot.child(i.toString()).child("ability").child("type").getValue(String::class.java).toLowerCase()
                    itemList.add(item)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun LoadPlayer(){
        val p = realm.where(DbPlayer::class.java).findFirst()
        if(p != null){
            currentPlayer.name = p.name
            currentPlayer.room = p.room
            currentPlayer.gold = p.gold
            currentPlayer.stats.strength = p.stats?.strength?:0
            currentPlayer.stats.dexterity = p.stats?.dexterity?:0
            currentPlayer.stats.intelligence = p.stats?.intelligence?:0
            for (i in p.itemIds){
                val item = itemList.filter { it.id == i.id }.firstOrNull()
                if (item != null){
                    currentPlayer.items.add(item)
                }
            }
            if (p.equipped?.headid != 999) currentPlayer.equipped.head = itemList.filter { it.id == p.equipped?.headid }.firstOrNull()
            if (p.equipped?.shouldersid != 999) currentPlayer.equipped.shoulders = itemList.filter { it.id == p.equipped?.shouldersid }.firstOrNull()
            if (p.equipped?.legsid != 999) currentPlayer.equipped.legs = itemList.filter { it.id == p.equipped?.legsid }.firstOrNull()
            if (p.equipped?.offhandid != 999) currentPlayer.equipped.offhand = itemList.filter { it.id == p.equipped?.offhandid }.firstOrNull()
            if (p.equipped?.mainhandid != 999) currentPlayer.equipped.mainhand = itemList.filter { it.id == p.equipped?.mainhandid }.firstOrNull()
        }
    }

    fun ReplacePlayerDB(){
        DeletePlayer()
        realm.executeTransaction {
            val dbplayer = realm.createObject(DbPlayer::class.java)
            dbplayer.name = currentPlayer.name
            dbplayer.gold = currentPlayer.gold
            dbplayer.room = currentPlayer.room

            for (item in currentPlayer.items){
                val mId = realm.createObject(ItemID::class.java)
                mId.id = item.id
                dbplayer.itemIds.add(mId)
            }

            val mStats = realm.createObject(Stats::class.java)
            mStats.strength = currentPlayer.stats.strength
            mStats.dexterity = currentPlayer.stats.dexterity
            mStats.intelligence = currentPlayer.stats.intelligence
            dbplayer.stats = mStats

            val dbequip = realm.createObject(DbEquipment::class.java)
            dbequip.headid = currentPlayer.equipped.head?.id?:0
            dbequip.shouldersid = currentPlayer.equipped.shoulders?.id?:0
            dbequip.legsid = currentPlayer.equipped.legs?.id?:0
            dbequip.offhandid = currentPlayer.equipped.offhand?.id?:0
            dbequip.mainhandid = currentPlayer.equipped.mainhand?.id?:0
            dbplayer.equipped = dbequip
        }
    }
    fun DeletePlayer(){
        realm.executeTransaction {
            realm.deleteAll()
        }
    }
}
