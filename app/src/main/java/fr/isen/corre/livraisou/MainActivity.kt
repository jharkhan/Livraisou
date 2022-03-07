package fr.isen.corre.livraisou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ShopListFragment>(R.id.container)
            }

        }

        val bottomNav = findViewById<NavigationBarView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ShopListFragment>(R.id.container)
                }

                R.id.account -> supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ProfileFragment>(R.id.container)
                }

                R.id.search -> supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<MapsFragment>(R.id.container)
                }


            }
            true
        }


    }







}






