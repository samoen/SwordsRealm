package sam.testapp

import android.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), ItemAdapter.onViewSelectedListener {

    var listItems = mutableListOf<Item>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RefreshListItems()
        InitViews()
        RefreshTextViews()
    }

    override fun onItemSelected(item: Item) {
        val simpleAlert = AlertDialog.Builder(activity).create()
        simpleAlert.setTitle("Equip Item")
        simpleAlert.setMessage(item.name)
        simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (CP().StatsMet(item)){
                    when(item.equipment_slot){
                        "head"->CP().equipped.head = item
                        "shoulders"->CP().equipped.shoulders = item
                        "legs"->CP().equipped.legs = item
                        "offhand"->CP().equipped.offhand = item
                        "mainhand"->CP().equipped.mainhand = item
                    }
                    Toast.makeText(activity,"Item equipped",Toast.LENGTH_SHORT).show()
                    RefreshTextViews()
                }else{
                    Toast.makeText(activity,"You cannot equip this due to your stats",Toast.LENGTH_SHORT).show()
                }
            }
        })
        simpleAlert.show()
    }

    fun InitViews(){
        textView_playerInfo.setText("${CP().name} The Awesome\n Gold: ${CP().gold}\n Strength: ${CP().stats.strength}, Intelligence: ${(activity as MainActivity).currentPlayer.stats.intelligence}, Dexterity: ${CP().stats.dexterity}")
        button_game_room_1.setOnClickListener {
            (activity as MainActivity).fragmentManager.beginTransaction().remove(OnlineFragment()).commitAllowingStateLoss()
            fragmentManager.beginTransaction().replace(R.id.frameLayout_main,OnlineFragment()).commit()
        }
        button_save.setOnClickListener { (activity as MainActivity).ReplacePlayerDB() }
        button_itemShop.setOnClickListener { fragmentManager.beginTransaction().replace(R.id.frameLayout_main,ItemShopFragment()).commit() }
        button_respec.setOnClickListener { fragmentManager.beginTransaction().replace(R.id.frameLayout_main,RespecFragment()).commit() }
        recyclerView_playerItems.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
        recyclerView_playerItems.adapter = ItemAdapter(this, listItems)
    }

    fun RefreshTextViews(){
        textView_helmet.text = CP().equipped.head?.name
        textView_shoulders.text = CP().equipped.shoulders?.name
        textView_legs.text = CP().equipped.legs?.name
        textView_offhand.text = CP().equipped.offhand?.name
        textView_right_hand.text = CP().equipped.mainhand?.name
    }
    fun RefreshListItems(){
        listItems = CP().items
    }
    fun CP() = (activity as MainActivity).currentPlayer

}