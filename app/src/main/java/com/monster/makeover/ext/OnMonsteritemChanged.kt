package com.monster.makeover.ext

import com.monster.makeover.R
import com.monster.makeover.constants.Game
import com.monster.makeover.constants.ItemType
import com.monster.makeover.data.DataSource.sounds
import com.monster.makeover.db.DatabaseHolder.Companion.Database
import com.monster.makeover.db.obj.MonsterItem
import com.monster.makeover.ui.MainViewModel
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.playRandomSound
import com.monster.makeover.utils.playSound
import kotlinx.coroutines.launch

fun onMonsterItemChanged(
    id: Int,
    locked: Boolean,
    itemType: ItemType,
    viewModel: MainViewModel,
    onUnlock: () -> Unit
): Boolean {
    if(locked){
        return if(PreferencesHelper.addCoins(-Game.ItemValue)) {
            onUnlock()
            val soundId = sounds.find { it.resId == R.raw.btn_unlock}?.soundId
            viewModel.soundScope.launch { playSound(viewModel.soundPool, soundId, viewModel.isSoundMute) }
            query {  Database.lockedItemsDao().delete(MonsterItem(id)) }
            false
        } else true
    }
    else {
        when(itemType){
            ItemType.Head -> viewModel.updateMonsterHead(id)
            ItemType.Eye -> viewModel.updateMonsterEye(id)
            ItemType.Mouth -> viewModel.updateMonsterMouth(id)
            ItemType.Accessory -> viewModel.updateMonsterAcc(id)
            ItemType.Body -> viewModel.updateMonsterBody(id)
        }
        viewModel.soundScope.launch { playRandomSound(viewModel.soundPool, viewModel.isSoundMute) }
        return false
    }
}