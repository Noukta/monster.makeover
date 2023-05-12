package com.example.createmonster.data

import com.example.createmonster.R

object DataSource {
    val tabs = listOf(
        TabInfo(R.string.head, R.drawable.ico_head, R.drawable.ico_head_selected, 36),
        TabInfo(R.string.eye, R.drawable.ico_eye, R.drawable.ico_eye_selected, 63),
        TabInfo(R.string.mouth, R.drawable.ico_mouth, R.drawable.ico_mouth_selected, 35),
        TabInfo(R.string.accessory, R.drawable.ico_acc, R.drawable.ico_acc_selected, 30),
        TabInfo(R.string.body, R.drawable.ico_body, R.drawable.ico_body_selected, 37),
    )

    val sounds = listOf(
        Sound(R.raw.btn_select_1),
        Sound(R.raw.btn_select_2),
        Sound(R.raw.btn_select_3),
        Sound(R.raw.btn_select_4),
        Sound(R.raw.btn_select_5),
        Sound(R.raw.btn_select_6),
        Sound(R.raw.btn_select_7),
        Sound(R.raw.btn_select_8),
        Sound(R.raw.btn_select_9),
        Sound(R.raw.btn_select_10),
        Sound(R.raw.btn_select_11),
        Sound(R.raw.btn_select_12),
        Sound(R.raw.btn_select_13),
        Sound(R.raw.btn_select_14),
        Sound(R.raw.btn_select_15),
        Sound(R.raw.btn_select_16),
        Sound(R.raw.btn_common),
        Sound(R.raw.btn_next),
        Sound(R.raw.btn_done),
        Sound(R.raw.btn_start_remake),
//        Sound(R.raw.sfx_coin),
//        Sound(R.raw.sfx_unlock)
    )

    private val heads = listOf(
        Pair(R.drawable.btn_head_00, R.drawable.head_00),
        Pair(R.drawable.btn_head_01, R.drawable.head_01),
        Pair(R.drawable.btn_head_02, R.drawable.head_02),
        Pair(R.drawable.btn_head_03, R.drawable.head_03),
        Pair(R.drawable.btn_head_04, R.drawable.head_04),
        Pair(R.drawable.btn_head_05, R.drawable.head_05),
        Pair(R.drawable.btn_head_06, R.drawable.head_06),
        Pair(R.drawable.btn_head_07, R.drawable.head_07),
        Pair(R.drawable.btn_head_08, R.drawable.head_08),
        Pair(R.drawable.btn_head_09, R.drawable.head_09),
        Pair(R.drawable.btn_head_10, R.drawable.head_10),
        Pair(R.drawable.btn_head_11, R.drawable.head_11),
        Pair(R.drawable.btn_head_12, R.drawable.head_12),
        Pair(R.drawable.btn_head_13, R.drawable.head_13),
        Pair(R.drawable.btn_head_14, R.drawable.head_14),
        Pair(R.drawable.btn_head_15, R.drawable.head_15),
        Pair(R.drawable.btn_head_16, R.drawable.head_16),
        Pair(R.drawable.btn_head_17, R.drawable.head_17),
        Pair(R.drawable.btn_head_18, R.drawable.head_18),
        Pair(R.drawable.btn_head_19, R.drawable.head_19),
        Pair(R.drawable.btn_head_20, R.drawable.head_20),
        Pair(R.drawable.btn_head_21, R.drawable.head_21),
        Pair(R.drawable.btn_head_22, R.drawable.head_22),
        Pair(R.drawable.btn_head_23, R.drawable.head_23),
        Pair(R.drawable.btn_head_24, R.drawable.head_24),
        Pair(R.drawable.btn_head_25, R.drawable.head_25),
        Pair(R.drawable.btn_head_26, R.drawable.head_26),
        Pair(R.drawable.btn_head_27, R.drawable.head_27),
        Pair(R.drawable.btn_head_28, R.drawable.head_28),
        Pair(R.drawable.btn_head_29, R.drawable.head_29),
        Pair(R.drawable.btn_head_30, R.drawable.head_30),
        Pair(R.drawable.btn_head_31, R.drawable.head_31),
        Pair(R.drawable.btn_head_32, R.drawable.head_32),
        Pair(R.drawable.btn_head_33, R.drawable.head_33),
        Pair(R.drawable.btn_head_34, R.drawable.head_34),
        Pair(R.drawable.btn_head_35, R.drawable.head_35),
    )

    private val eyes = listOf(
        Pair(R.drawable.btn_eye_00, R.drawable.eye_00),
        Pair(R.drawable.btn_eye_01, R.drawable.eye_01),
        Pair(R.drawable.btn_eye_02, R.drawable.eye_02),
        Pair(R.drawable.btn_eye_03, R.drawable.eye_03),
        Pair(R.drawable.btn_eye_04, R.drawable.eye_04),
        Pair(R.drawable.btn_eye_05, R.drawable.eye_05),
        Pair(R.drawable.btn_eye_06, R.drawable.eye_06),
        Pair(R.drawable.btn_eye_07, R.drawable.eye_07),
        Pair(R.drawable.btn_eye_08, R.drawable.eye_08),
        Pair(R.drawable.btn_eye_09, R.drawable.eye_09),
        Pair(R.drawable.btn_eye_10, R.drawable.eye_10),
        Pair(R.drawable.btn_eye_11, R.drawable.eye_11),
        Pair(R.drawable.btn_eye_12, R.drawable.eye_12),
        Pair(R.drawable.btn_eye_13, R.drawable.eye_13),
        Pair(R.drawable.btn_eye_14, R.drawable.eye_14),
        Pair(R.drawable.btn_eye_15, R.drawable.eye_15),
        Pair(R.drawable.btn_eye_16, R.drawable.eye_16),
        Pair(R.drawable.btn_eye_17, R.drawable.eye_17),
        Pair(R.drawable.btn_eye_18, R.drawable.eye_18),
        Pair(R.drawable.btn_eye_19, R.drawable.eye_19),
        Pair(R.drawable.btn_eye_20, R.drawable.eye_20),
        Pair(R.drawable.btn_eye_21, R.drawable.eye_21),
        Pair(R.drawable.btn_eye_22, R.drawable.eye_22),
        Pair(R.drawable.btn_eye_23, R.drawable.eye_23),
        Pair(R.drawable.btn_eye_24, R.drawable.eye_24),
        Pair(R.drawable.btn_eye_25, R.drawable.eye_25),
        Pair(R.drawable.btn_eye_26, R.drawable.eye_26),
        Pair(R.drawable.btn_eye_27, R.drawable.eye_27),
        Pair(R.drawable.btn_eye_28, R.drawable.eye_28),
        Pair(R.drawable.btn_eye_29, R.drawable.eye_29),
        Pair(R.drawable.btn_eye_30, R.drawable.eye_30),
        Pair(R.drawable.btn_eye_31, R.drawable.eye_31),
        Pair(R.drawable.btn_eye_32, R.drawable.eye_32),
        Pair(R.drawable.btn_eye_33, R.drawable.eye_33),
        Pair(R.drawable.btn_eye_34, R.drawable.eye_34),
        Pair(R.drawable.btn_eye_35, R.drawable.eye_35),
        Pair(R.drawable.btn_eye_36, R.drawable.eye_36),
        Pair(R.drawable.btn_eye_37, R.drawable.eye_37),
        Pair(R.drawable.btn_eye_38, R.drawable.eye_38),
        Pair(R.drawable.btn_eye_39, R.drawable.eye_39),
        Pair(R.drawable.btn_eye_40, R.drawable.eye_40),
        Pair(R.drawable.btn_eye_41, R.drawable.eye_41),
        Pair(R.drawable.btn_eye_42, R.drawable.eye_42),
        Pair(R.drawable.btn_eye_43, R.drawable.eye_43),
        Pair(R.drawable.btn_eye_44, R.drawable.eye_44),
        Pair(R.drawable.btn_eye_45, R.drawable.eye_45),
        Pair(R.drawable.btn_eye_46, R.drawable.eye_46),
        Pair(R.drawable.btn_eye_47, R.drawable.eye_47),
        Pair(R.drawable.btn_eye_48, R.drawable.eye_48),
        Pair(R.drawable.btn_eye_49, R.drawable.eye_49),
        Pair(R.drawable.btn_eye_50, R.drawable.eye_50),
        Pair(R.drawable.btn_eye_51, R.drawable.eye_51),
        Pair(R.drawable.btn_eye_52, R.drawable.eye_52),
        Pair(R.drawable.btn_eye_53, R.drawable.eye_53),
        Pair(R.drawable.btn_eye_54, R.drawable.eye_54),
        Pair(R.drawable.btn_eye_55, R.drawable.eye_55),
        Pair(R.drawable.btn_eye_56, R.drawable.eye_56),
        Pair(R.drawable.btn_eye_57, R.drawable.eye_57),
        Pair(R.drawable.btn_eye_58, R.drawable.eye_58),
        Pair(R.drawable.btn_eye_59, R.drawable.eye_59),
        Pair(R.drawable.btn_eye_60, R.drawable.eye_60),
        Pair(R.drawable.btn_eye_61, R.drawable.eye_61),
        Pair(R.drawable.btn_eye_62, R.drawable.eye_62)
    )

    private val mouths = listOf(
        Pair(R.drawable.btn_mouth_00, R.drawable.mouth_00),
        Pair(R.drawable.btn_mouth_01, R.drawable.mouth_01),
        Pair(R.drawable.btn_mouth_02, R.drawable.mouth_02),
        Pair(R.drawable.btn_mouth_03, R.drawable.mouth_03),
        Pair(R.drawable.btn_mouth_04, R.drawable.mouth_04),
        Pair(R.drawable.btn_mouth_05, R.drawable.mouth_05),
        Pair(R.drawable.btn_mouth_06, R.drawable.mouth_06),
        Pair(R.drawable.btn_mouth_07, R.drawable.mouth_07),
        Pair(R.drawable.btn_mouth_08, R.drawable.mouth_08),
        Pair(R.drawable.btn_mouth_09, R.drawable.mouth_09),
        Pair(R.drawable.btn_mouth_10, R.drawable.mouth_10),
        Pair(R.drawable.btn_mouth_11, R.drawable.mouth_11),
        Pair(R.drawable.btn_mouth_12, R.drawable.mouth_12),
        Pair(R.drawable.btn_mouth_13, R.drawable.mouth_13),
        Pair(R.drawable.btn_mouth_14, R.drawable.mouth_14),
        Pair(R.drawable.btn_mouth_15, R.drawable.mouth_15),
        Pair(R.drawable.btn_mouth_16, R.drawable.mouth_16),
        Pair(R.drawable.btn_mouth_17, R.drawable.mouth_17),
        Pair(R.drawable.btn_mouth_18, R.drawable.mouth_18),
        Pair(R.drawable.btn_mouth_19, R.drawable.mouth_19),
        Pair(R.drawable.btn_mouth_20, R.drawable.mouth_20),
        Pair(R.drawable.btn_mouth_21, R.drawable.mouth_21),
        Pair(R.drawable.btn_mouth_22, R.drawable.mouth_22),
        Pair(R.drawable.btn_mouth_23, R.drawable.mouth_23),
        Pair(R.drawable.btn_mouth_24, R.drawable.mouth_24),
        Pair(R.drawable.btn_mouth_25, R.drawable.mouth_25),
        Pair(R.drawable.btn_mouth_26, R.drawable.mouth_26),
        Pair(R.drawable.btn_mouth_27, R.drawable.mouth_27),
        Pair(R.drawable.btn_mouth_28, R.drawable.mouth_28),
        Pair(R.drawable.btn_mouth_29, R.drawable.mouth_29),
        Pair(R.drawable.btn_mouth_30, R.drawable.mouth_30),
        Pair(R.drawable.btn_mouth_31, R.drawable.mouth_31),
        Pair(R.drawable.btn_mouth_32, R.drawable.mouth_32),
        Pair(R.drawable.btn_mouth_33, R.drawable.mouth_33),
        Pair(R.drawable.btn_mouth_34, R.drawable.mouth_34)
    )

    private val accs = listOf(
        Pair(R.drawable.btn_acc_00, R.drawable.acc_00),
        Pair(R.drawable.btn_acc_01, R.drawable.acc_01),
        Pair(R.drawable.btn_acc_02, R.drawable.acc_02),
        Pair(R.drawable.btn_acc_03, R.drawable.acc_03),
        Pair(R.drawable.btn_acc_04, R.drawable.acc_04),
        Pair(R.drawable.btn_acc_05, R.drawable.acc_05),
        Pair(R.drawable.btn_acc_06, R.drawable.acc_06),
        Pair(R.drawable.btn_acc_07, R.drawable.acc_07),
        Pair(R.drawable.btn_acc_08, R.drawable.acc_08),
        Pair(R.drawable.btn_acc_09, R.drawable.acc_09),
        Pair(R.drawable.btn_acc_10, R.drawable.acc_10),
        Pair(R.drawable.btn_acc_11, R.drawable.acc_11),
        Pair(R.drawable.btn_acc_12, R.drawable.acc_12),
        Pair(R.drawable.btn_acc_13, R.drawable.acc_13),
        Pair(R.drawable.btn_acc_14, R.drawable.acc_14),
        Pair(R.drawable.btn_acc_15, R.drawable.acc_15),
        Pair(R.drawable.btn_acc_16, R.drawable.acc_16),
        Pair(R.drawable.btn_acc_17, R.drawable.acc_17),
        Pair(R.drawable.btn_acc_18, R.drawable.acc_18),
        Pair(R.drawable.btn_acc_19, R.drawable.acc_19),
        Pair(R.drawable.btn_acc_20, R.drawable.acc_20),
        Pair(R.drawable.btn_acc_21, R.drawable.acc_21),
        Pair(R.drawable.btn_acc_22, R.drawable.acc_22),
        Pair(R.drawable.btn_acc_23, R.drawable.acc_23),
        Pair(R.drawable.btn_acc_24, R.drawable.acc_24),
        Pair(R.drawable.btn_acc_25, R.drawable.acc_25),
        Pair(R.drawable.btn_acc_26, R.drawable.acc_26),
        Pair(R.drawable.btn_acc_27, R.drawable.acc_27),
        Pair(R.drawable.btn_acc_28, R.drawable.acc_28),
        Pair(R.drawable.btn_acc_29, R.drawable.acc_29)
    )

    private val bodies = listOf(
        Pair(R.drawable.btn_body_00, R.drawable.body_00),
        Pair(R.drawable.btn_body_01, R.drawable.body_01),
        Pair(R.drawable.btn_body_02, R.drawable.body_02),
        Pair(R.drawable.btn_body_03, R.drawable.body_03),
//        Pair(R.drawable.btn_body_04, R.drawable.body_04),
        Pair(R.drawable.btn_body_05, R.drawable.body_05),
//        Pair(R.drawable.btn_body_06, R.drawable.body_06),
//        Pair(R.drawable.btn_body_07, R.drawable.body_07),
        Pair(R.drawable.btn_body_08, R.drawable.body_08),
//        Pair(R.drawable.btn_body_09, R.drawable.body_09),
        Pair(R.drawable.btn_body_10, R.drawable.body_10),
//        Pair(R.drawable.btn_body_11, R.drawable.body_11),
        Pair(R.drawable.btn_body_12, R.drawable.body_12),
//        Pair(R.drawable.btn_body_13, R.drawable.body_13),
        Pair(R.drawable.btn_body_14, R.drawable.body_14),
        Pair(R.drawable.btn_body_15, R.drawable.body_15),
//        Pair(R.drawable.btn_body_16, R.drawable.body_16),
//        Pair(R.drawable.btn_body_17, R.drawable.body_17),
//        Pair(R.drawable.btn_body_18, R.drawable.body_18),
        Pair(R.drawable.btn_body_19, R.drawable.body_19),
//        Pair(R.drawable.btn_body_20, R.drawable.body_20),
//        Pair(R.drawable.btn_body_21, R.drawable.body_21),
//        Pair(R.drawable.btn_body_22, R.drawable.body_22),
        Pair(R.drawable.btn_body_23, R.drawable.body_23),
        Pair(R.drawable.btn_body_24, R.drawable.body_24),
        Pair(R.drawable.btn_body_25, R.drawable.body_25),
        Pair(R.drawable.btn_body_26, R.drawable.body_26),
        Pair(R.drawable.btn_body_27, R.drawable.body_27),
        Pair(R.drawable.btn_body_28, R.drawable.body_28),
//        Pair(R.drawable.btn_body_29, R.drawable.body_29),
        Pair(R.drawable.btn_body_30, R.drawable.body_30),
//        Pair(R.drawable.btn_body_31, R.drawable.body_31),
//        Pair(R.drawable.btn_body_32, R.drawable.body_32),
//        Pair(R.drawable.btn_body_33, R.drawable.body_33),
//        Pair(R.drawable.btn_body_34, R.drawable.body_34),
        Pair(R.drawable.btn_body_35, R.drawable.body_35),
//        Pair(R.drawable.btn_body_36, R.drawable.body_36)
    )

    val allParts = listOf(heads, eyes, mouths, accs, bodies)
}