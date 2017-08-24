package sam.testapp

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_respec.*

class RespecFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_respec,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RefreshTextViews()
        button_dexterity_down.setOnClickListener { Select(-1,"dexterity") }
        button_dexterity_up.setOnClickListener { Select(1,"dexterity") }
        button_strength_down.setOnClickListener { Select(-1,"strength") }
        button_strength_up.setOnClickListener { Select(1,"strength") }
        button_intelligence_down.setOnClickListener { Select(-1,"intelligence") }
        button_intelligence_up.setOnClickListener { Select(1,"intelligence") }

        button_backToMain.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.frameLayout_main, MainFragment()).commit()
        }
    }

    fun Select(direction:Int, stat:String){
        if (CP().CanChangeStat(direction, stat)) {
            when(stat){
                "strength"-> CP().stats.strength = CP().stats.strength + direction
                "dexterity"-> CP().stats.dexterity = CP().stats.dexterity + direction
                "intelligence"-> CP().stats.intelligence = CP().stats.intelligence + direction
            }
            RefreshTextViews()
        } else Toast.makeText(context, "Your equipped items prevent you from changing that stat", Toast.LENGTH_SHORT).show()
    }

    fun RefreshTextViews(){
        textView_strength.setText(CP().stats.strength.toString())
        textView_dexterity.setText(CP().stats.dexterity.toString())
        textView_intelligence.setText(CP().stats.intelligence.toString())
    }

    fun CP() = (activity as MainActivity).currentPlayer
}