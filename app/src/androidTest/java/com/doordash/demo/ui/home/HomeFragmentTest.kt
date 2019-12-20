package com.doordash.demo.ui.home

import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doordash.demo.R
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Test
    fun testHomeFragmentDefaultViews() {
        // Assumes that "MyDialogFragment" extends the DialogFragment class.
        with(launchFragment<HomeFragment>()) {
            onFragment { fragment ->
                //RecyclerView is present
                assertTrue(fragment.view?.findViewById<RecyclerView>(R.id.restaurant_list)?.isVisible ?: false)
                //Error View is hidden
                assertFalse(fragment.view?.findViewById<Button>(R.id.error_button)?.isVisible ?: false)
            }

        }
    }
}