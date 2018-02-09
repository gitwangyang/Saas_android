package com.king.app.m_easelib.model;

import com.king.app.m_easelib.R;
import com.king.app.m_easelib.domain.EaseEmojicon;
import com.king.app.m_easelib.domain.EaseEmojiconGroupEntity;

import java.util.Arrays;

/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 15:30
 * Description  大表情组对象
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class EmojiconGroupData {
    private static int[] icons = new int[]{
            R.drawable.icon_002_cover,
            R.drawable.icon_007_cover,
            R.drawable.icon_010_cover,
            R.drawable.icon_012_cover,
            R.drawable.icon_013_cover,
            R.drawable.icon_018_cover,
            R.drawable.icon_019_cover,
            R.drawable.icon_020_cover,
            R.drawable.icon_021_cover,
            R.drawable.icon_022_cover,
            R.drawable.icon_024_cover,
            R.drawable.icon_027_cover,
            R.drawable.icon_029_cover,
            R.drawable.icon_030_cover,
            R.drawable.icon_035_cover,
            R.drawable.icon_040_cover,
    };
    private static int[] bigIcons = new int[]{
            R.drawable.icon_002,
            R.drawable.icon_007,
            R.drawable.icon_010,
            R.drawable.icon_012,
            R.drawable.icon_013,
            R.drawable.icon_018,
            R.drawable.icon_019,
            R.drawable.icon_020,
            R.drawable.icon_021,
            R.drawable.icon_022,
            R.drawable.icon_024,
            R.drawable.icon_027,
            R.drawable.icon_029,
            R.drawable.icon_030,
            R.drawable.icon_035,
            R.drawable.icon_040,
    };

    private static final EaseEmojiconGroupEntity DATA = createData();

    private static EaseEmojiconGroupEntity createData() {
        EaseEmojiconGroupEntity emojiconGroupEntity = new EaseEmojiconGroupEntity();
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for (int i = 0; i < icons.length; i++) {
            datas[i] = new EaseEmojicon(icons[i], null, EaseEmojicon.Type.BIG_EXPRESSION);
            datas[i].setBigIcon(bigIcons[i]);
            datas[i].setName("示例" + (i + 1));
            datas[i].setIdentityCode("em" + (1000 + i + 1));
        }
        emojiconGroupEntity.setEmojiconList(Arrays.asList(datas));
        emojiconGroupEntity.setIcon(R.drawable.ee_2);
        emojiconGroupEntity.setType(EaseEmojicon.Type.BIG_EXPRESSION);
        return emojiconGroupEntity;
    }

    public static EaseEmojiconGroupEntity getData() {
        return DATA;
    }
}
