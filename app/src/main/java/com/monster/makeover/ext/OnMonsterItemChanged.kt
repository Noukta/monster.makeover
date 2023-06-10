package com.monster.makeover.ext

import com.monster.makeover.MainViewModel
import com.monster.makeover.constants.Game
import com.monster.makeover.constants.ItemType
import com.monster.makeover.db.DatabaseHolder.Companion.Database
import com.monster.makeover.db.obj.MonsterItem
import com.monster.makeover.utils.PreferencesHelper
import com.monster.makeover.utils.SoundHelper

fun onMonsterItemChanged(
    id: Int, unlocked: Boolean, itemType: ItemType, viewModel: MainViewModel, onUnlock: () -> Unit
): Boolean {
    if (unlocked) {
        when (itemType) {
            ItemType.Head -> viewModel.updateMonsterHead(id)
            ItemType.Eye -> viewModel.updateMonsterEye(id)
            ItemType.Mouth -> viewModel.updateMonsterMouth(id)
            ItemType.Accessory -> viewModel.updateMonsterAcc(id)
            ItemType.Body -> viewModel.updateMonsterBody(id)
        }
        SoundHelper.playSound(SoundHelper.selectSounds.random())
        return true
    } else {
        return if (PreferencesHelper.addCoins(-Game.ItemValue)) {
            onUnlock()
            SoundHelper.playSound(SoundHelper.unlockSound)
            query { Database.unlockedItemsDao().insert(MonsterItem(id)) }
            true
        } else false
    }
}