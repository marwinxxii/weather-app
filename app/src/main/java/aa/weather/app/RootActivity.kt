package aa.weather.app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class RootActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root_activity)
    }
}