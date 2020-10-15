package com.core.helper.mup.comic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDonationFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.l_bottom_sheet_donation_fragment, container, false)
    }
}
