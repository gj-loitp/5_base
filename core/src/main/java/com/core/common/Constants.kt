package com.core.common

import com.R

/**
 * Created by loitp
 */
class Constants {
    companion object {
        var IS_DEBUG = false

        const val NOT_FOUND = -404

        //region url img
        const val URL_IMG_THUMBNAIL = "https://c1.staticflickr.com/1/584/31506863796_53a3f24062_n.jpg"
        const val URL_IMG = "https://c1.staticflickr.com/9/8438/28818520263_c7ea1b3e3f_b.jpg"
        const val URL_IMG_1 = "https://c1.staticflickr.com/5/4564/38746630932_93b5b54925_h.jpg"
        const val URL_IMG_2 = "https://c1.staticflickr.com/5/4263/35746631876_0c45ec2943_h.jpg"
        const val URL_IMG_3 = "https://c1.staticflickr.com/5/4061/35617987102_0aa7913fa7_b.jpg"
        const val URL_IMG_4 = "https://c1.staticflickr.com/5/4140/35617984622_e3a1e202fb_h.jpg"
        const val URL_IMG_5 = "https://c1.staticflickr.com/5/4054/35746633426_14cf7ef143_h.jpg"
        const val URL_IMG_6 = "https://c1.staticflickr.com/5/4767/39983817172_f5462d0d0e_h.jpg"
        const val URL_IMG_7 = "https://c1.staticflickr.com/5/4698/26142588578_a085482efb_h.jpg"
        const val URL_IMG_8 = "https://c1.staticflickr.com/5/4742/39983819592_c23bfcf563_h.jpg"
        const val URL_IMG_9 = "https://c1.staticflickr.com/5/4720/39983821542_0a3a4506cf_o.jpg"
        const val URL_IMG_10 = "https://c1.staticflickr.com/5/4541/24904557088_3dc06c3bcf_h.jpg"
        const val URL_IMG_11 = "https://c2.staticflickr.com/6/5542/30333782080_ef19d1b037_b.jpg"
        const val URL_IMG_12 = "https://c2.staticflickr.com/6/5491/30597322996_f317c58a31_h.jpg"
        const val URL_IMG_LONG = "https://c2.staticflickr.com/6/5476/29412311793_8067369e64_b.jpg"
        const val URL_IMG_ANDROID = "https://live.staticflickr.com/4501/37154024293_0c1c0ea3ce.jpg"
        //endregion

        const val MAIN_LINK_TRUYENTRANHTUAN = "http://truyentranhtuan.com/danh-sach-truyen"

        const val TEST_0 = "6E0762FF2B272D5BCE89FEBAAB872E34"
        const val TEST_1 = "8FA8E91902B43DCB235ED2F6BBA9CAE0"
        const val TEST_2 = "58844B2E50AF6E33DC818387CC50E593"
        const val TEST_3 = "179198315EB7B069037C5BE8DEF8319A"
        const val TEST_4 = "7DA8A5B216E868636B382A7B9756A4E6"
        const val TEST_5 = "A1EC01C33BD69CD589C2AF605778C2E6"
        const val TEST_6 = "13308851AEDCA44443112D80A8D182CA"
        const val TEST_7 = "E3876538025DFE31EE38DC997F860C83"
        const val TEST_8 = "263712DA42399656FF39E197148C935A"
        const val TEST_9 = "F82667374DD56D3E0AFDBF331A34E61E"
        const val TEST_10 = "462FEA8E9EF7A8BBC6E2D24CC4945511"
        const val TEST_11 = "D1B593A99FF7E14DF4E2741AFF95645D"
        const val TEST_12 = "0ECE5CECE9A16F33BCAF16AB4DAB3B1D"

        const val LOITP = "LoitpDebug"

        //region activity transition
        var TYPE_ACTIVITY_TRANSITION_NO_ANIM = -1
        var TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT = 0
        var TYPE_ACTIVITY_TRANSITION_SLIDELEFT = 1
        var TYPE_ACTIVITY_TRANSITION_SLIDERIGHT = 2
        var TYPE_ACTIVITY_TRANSITION_SLIDEDOWN = 3
        var TYPE_ACTIVITY_TRANSITION_SLIDEUP = 4
        var TYPE_ACTIVITY_TRANSITION_FADE = 5
        var TYPE_ACTIVITY_TRANSITION_ZOOM = 6
        var TYPE_ACTIVITY_TRANSITION_WINDMILL = 7
        var TYPE_ACTIVITY_TRANSITION_DIAGONAL = 8
        var TYPE_ACTIVITY_TRANSITION_SPIN = 9
        //endregion

        const val PREFIXS = "https://"
        const val PREFIXS_SHORT = "https:"
        const val FCM_TOPIC = "/topics/loitp"
        const val URL_POLICY = "https://loitp.wordpress.com/2018/06/10/dieu-khoan-su-dung-chinh-sach-bao-mat-va-quyen-rieng-tu/"

        //region flickr
        const val SK_PHOTOSET_ID = "SK_PHOTOSET_ID"

        const val SK_PHOTOSET_SIZE = "SK_PHOTOSET_SIZE"
        const val SK_PHOTO_ID = "SK_PHOTO_ID"
        const val SK_PHOTO_PISITION = "SK_PHOTO_PISITION"
        const val ASSET_FILE_GIFT = "img/ic_gift.png"
        const val FONT_PATH = "fonts/android_font.TTF"
        const val FACEBOOK_COMMENT_URL = "FACEBOOK_COMMENT_URL"
        const val FLICKR_ID_IT = "72157680937087024"
        const val FLICKR_ID_MUSIC = "72157684069831365"
        const val FLICKR_ID_ICONS = "72157683121358194"
        const val FLICKR_ID_BIKE = "72157692892412235"
        const val FLICKR_ID_ART = "72157667969456808"
        const val FLICKR_ID_USER_UPLOADS = "72157670066573588"
        const val FLICKR_ID_MEMBERS = "72157672433861458"
        const val FLICKR_ID_AVATAR = "72157673422184280"
        const val FLICKR_ID_BABY = "72157679720928116"
        const val FLICKR_ID_SEA = "72157669926304101"
        const val FLICKR_ID_BALLOON = "72157668032570843"
        const val FLICKR_ID_BOY = "72157677252989126"
        const val FLICKR_ID_CHIBI = "72157670869668975"
        const val FLICKR_ID_DOG = "72157669895087692"
        const val FLICKR_ID_ROAD = "72157669873574592"
        const val FLICKR_ID_COSPLAY = "72157675738652316"
        const val FLICKR_ID_COUPLE = "72157670916472425"
        const val FLICKR_ID_ALONE = "72157669897058122"
        const val FLICKR_ID_TECHNOLOGY = "72157672417722165"
        const val FLICKR_ID_EDGE = "72157675404852704"
        const val FLICKR_ID_ENGLISH = "72157670208995046"
        const val FLICKR_ID_FAMOUSMANGA = "72157671531238161"
        const val FLICKR_ID_GIRL = "72157669928162421"
        const val FLICKR_ID_WOOD = "72157670177614812"
        const val FLICKR_ID_FLOWERS = "72157670498721345"
        const val FLICKR_ID_ANIME = "72157670869122155"
        const val FLICKR_ID_UNIQUEWALLPAPER = "72157668032683913"
        const val FLICKR_ID_COUPLEMANGA = "72157669822546690"
        const val FLICKR_ID_CUTEWALLPAPER = "72157668032744493"
        const val FLICKR_ID_FLAT = "72157672326123556"
        const val FLICKR_ID_LOVEWALLPAPER = "72157668022861664"
        const val FLICKR_ID_PAINT = "72157668022610724"
        const val FLICKR_ID_HAINAO = "72157669339297884"
        const val FLICKR_ID_LOVE = "72157670456827026"
        const val FLICKR_ID_MANGA = "72157669823155670"
        const val FLICKR_ID_LOCKSCREEN = "72157669823794360"
        const val FLICKR_ID_COLOR = "72157670777293936"
        const val FLICKR_ID_CAT = "72157669507399040"
        const val FLICKR_ID_NGAY83 = "72157669727167291"
        const val FLICKR_ID_ANNIVERSARY = "72157672844566633"
        const val FLICKR_ID_WATER = "72157670777350786"
        const val FLICKR_ID_PATTERN = "72157668023313114"
        const val FLICKR_ID_VIEW = "72157667707552544"
        const val FLICKR_ID_QUOTEANH = "72157670301922805"
        const val FLICKR_ID_STICKER = "72157677434798446"
        const val FLICKR_ID_HANDANDFEET = "72157667741981594"
        const val FLICKR_ID_CITY = "72157669873934172"
        const val FLICKR_ID_SPORT = "72157670442825956"
        const val FLICKR_ID_FOOD = "72157674607156645"
        const val FLICKR_ID_PLANT = "72157669906141681"
        const val FLICKR_ID_FRUITS = "72157670419305106"
        const val FLICKR_ID_UNIVERSE = "72157667749861263"
        const val FLICKR_ID_CAR = "72157671270508990"
        const val FLICKR_ID_OBJECT = "72157672417200715"
        const val FLICKR_ID_OTHERANIMAL = "72157670217556532"
        const val FLICKR_ID_HORROR = "72157699501215491"

        const val FLICKR_ID_VN_BANCOBIET = "72157667616563254"
        const val FLICKR_ID_VN_CUNGHOANGDAOFUNTFACT = "72157670402137766"
        const val FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO = "72157669860629292"
        const val FLICKR_ID_VN_DEVVUI = "72157672418190805"
        const val FLICKR_ID_VN_FUNNYMANGA = "72157672325785156"
        const val FLICKR_ID_VN_FUNNYMANHINH = "72157671531547221"
        const val FLICKR_ID_VN_FUNNYTHETHAO = "72157669339234524"
        const val FLICKR_ID_VN_HAIHUOC = "72157671531607921"
        const val FLICKR_ID_VN_KIEMHIEP = "72157670209046076"
        const val FLICKR_ID_VN_QUOTEVIET = "72157669801986381"
        const val FLICKR_ID_VN_STTBUON = "72157669727595861"
        const val FLICKR_ID_VN_STTTRUYENNGAN = "72157671847280471"
        const val FLICKR_ID_VN_STTTAMTRANG = "72157669727718221"
        const val FLICKR_ID_VN_STTVUI = "72157670301399735"
        const val FLICKR_ID_VN_STTDEUCHAT = "72157670301576595"
        const val FLICKR_ID_VN_THO = "72157667542396544"
        const val FLICKR_ID_VN_TRIETLY = "72157669728609121"
        const val FLICKR_ID_VN_TROLL = "72157669352124323"
        const val FLICKR_ID_VN_TRUYENBUA = "72157669352081793"
        const val FLICKR_ID_VN_TRUYENNGAN = "72157669352027883"
        const val FLICKR_ID_VN_TUOITHODUDOI = "72157669895520542"
        const val FLICKR_ID_VN_DOCDAOTHUVI = "72157672326565186"
        const val FLICKR_ID_VN_ANHCHESACHGIAOKHOA = "72157670443622896"
        const val FLICKR_ID_VN_ANHTHEOTEN = "72157670868460705"

        const val KEY_REMOVE_ALBUM_FLICKR_LIST = "KEY_REMOVE_ALBUM_FLICKR_LIST"
        //end region

        const val AD_HELPER_IS_ENGLISH_LANGUAGE = "AD_HELPER_IS_ENGLISH_LANGUAGE"
        const val IS_DARK_THEME = "IS_DARK_THEME"
        const val AD_UNIT_ID_BANNER = "AD_UNIT_ID_BANNER"

        const val BKG_ROOT_VIEW = "BKG_ROOT_VIEW"
        const val BKG_SPLASH_SCREEN = "BKG_SPLASH_SCREEN"
        const val IS_HIDE_SPACE_VIEW = "IS_HIDE_SPACE_VIEW"
        val ARR_RANDOM_BKG = intArrayOf(
                R.drawable.l_bkg_gradient_man_of_steel,
                R.drawable.l_bkg_shadow_black_white,
                R.drawable.l_bkg_shadow_black_white_dark,
                R.drawable.l_bkg_shadow_white_black,
                R.drawable.l_bkg_shadow_white_black_dark)
        val ARR_URL_BKG_FLICKR = arrayOf(URL_IMG,
                URL_IMG_1,
                URL_IMG_2,
                URL_IMG_3,
                URL_IMG_4,
                URL_IMG_5,
                URL_IMG_6,
                URL_IMG_7,
                URL_IMG_8,
                URL_IMG_9,
                URL_IMG_10,
                URL_IMG_11,
                URL_IMG_12)
        const val KEY_VIDEO_CURRENT_POSITION = "KEY_VIDEO_CURRENT_POSITION"
        const val KEY_VIDEO_LINK_PLAY = "KEY_VIDEO_LINK_PLAY"
        const val KEY_VIDEO_LINK_IMA_AD = "KEY_VIDEO_LINK_IMA_AD"

        const val PER_PAGE_SIZE = 50

        fun setIsDebug(isDebug: Boolean) {
            IS_DEBUG = isDebug
        }
    }
}
