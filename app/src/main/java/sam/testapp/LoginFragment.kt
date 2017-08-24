package sam.testapp

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_login, container, false)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_new_game.setOnClickListener {
            (activity as MainActivity).currentPlayer = Player(name = editText_name.text.toString(), gold = 10)
            fragmentManager.beginTransaction().replace(R.id.frameLayout_main,MainFragment()).commit()
        }
        button_continue.setOnClickListener {
            (activity as MainActivity).LoadPlayer()
            fragmentManager.beginTransaction().replace(R.id.frameLayout_main,MainFragment()).commit()
        }
    }
}