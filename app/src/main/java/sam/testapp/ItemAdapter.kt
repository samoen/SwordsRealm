package sam.testapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shop_item.view.*

class ItemAdapter(val viewActions: onViewSelectedListener, shopItems: MutableList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = shopItems as ArrayList<Item>
    interface onViewSelectedListener {
        fun onItemSelected(item: Item)
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val h = holder as ItemViewHolder
        h.bind(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(parent)

    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shop_item,parent,false)) {
        fun bind(item: Item) = with(itemView) {
            textView1.text = item.name
            textView4.text = "${item.ability.type}, speed ${item.ability.speed}, cooldown ${item.ability.cooldown-1}, price ${item.price}"
            textView3.text = "stat requirements: str ${item.stat_requirement.strength_lower}-${item.stat_requirement.strength_upper}  dex ${item.stat_requirement.dexterity_lower}-${item.stat_requirement.dexterity_upper}  int ${item.stat_requirement.intelligence_lower}-${item.stat_requirement.intelligence_upper}"
            textView2.text = "${item.equipment_slot}, range ${item.ability.findRange()}, options ${item.ability.relative_pairs.size}"
            imageView_item_image.setImageResource(item.image_resource)
            super.itemView.setOnClickListener { viewActions.onItemSelected(item)}
        }
    }
}