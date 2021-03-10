package com.example.okcredit.Views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.okcredit.Views.fragments.CustomerFragment
import com.example.okcredit.Views.fragments.SupplierFragment

class ViewPagerFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return CustomerFragment.newInstance()
            1 -> return SupplierFragment.newInstance()
        }
        return CustomerFragment.newInstance()
    }

    override fun getCount(): Int {
        return 2;
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var tabTitle: String = ""
        when (position) {
            0 -> tabTitle = "Customer";
            1 -> tabTitle = "Supplier"
        }
        return tabTitle
    }
}