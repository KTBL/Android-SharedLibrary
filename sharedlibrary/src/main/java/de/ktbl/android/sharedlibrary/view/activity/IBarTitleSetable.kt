package de.ktbl.android.sharedlibrary.view.activity

interface IBarTitleSetable {
    fun setBarTitle(title: CharSequence?)
    fun setBarTitle(resId: Int)
    fun getBarTitle(): CharSequence?
    fun resetBarTitle()
}