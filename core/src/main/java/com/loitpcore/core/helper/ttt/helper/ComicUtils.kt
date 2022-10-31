package com.loitpcore.core.helper.ttt.helper

import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.ttt.model.comic.Comic
import com.loitpcore.core.helper.ttt.model.comictype.ComicType

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ComicUtils {
    companion object {
        @JvmStatic
        val comicTypeList: ArrayList<ComicType>
            get() {
                val comicTypeList = ArrayList<ComicType>()
                comicTypeList.add(ComicType(ComicType.TAT_CA, Constants.MAIN_LINK_TRUYENTRANHTUAN))
                comicTypeList.add(
                    ComicType(
                        ComicType.TOP_50,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/top/top-50"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.DANG_TIEN_HANH,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/dang-tien-hanh"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TAM_NGUNG,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/tam-dung"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HOAN_THANH,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/hoan-thanh/"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ACTION,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/action"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ADVENTURE,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/adventure"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ANIME,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/anime"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.COMEDY,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/comedy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.COMIC,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/comic"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.DRAMA,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/drama"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.FANTASY,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/fantasy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.GENDER_BENDER,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/gender-bender"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HISTORICAL,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/historical"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HORROR,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/horror"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.JOSEI,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/josei"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.LIVE_ACTION,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/live-action"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MANHUA,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/manhua"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MANHWA,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/manhwa"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MARTIAL_ARTS,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/martial-arts"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MECHA,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/mecha"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MYSTERY,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/mystery"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ONESHOT,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/one-shot"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.PSYCHOLOGICAL,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/psychological"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ROMANCE,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/romance"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHCOOL_LIFE,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/school-life"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SCIFI,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/sci-fi"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHOUJO,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/shoujo"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHOUNEN,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/shounen"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SLICE_OF_LIFE,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/slice-of-life"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SMUT,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/smut"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SPORTS,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/sports"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SUPERNATURAL,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/supernatural"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRAGEDY,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/tragedy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRUYEN_SCAN,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/truyen-scan"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRUYEN_TRANH_VIET_NAM,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/truyen-tranh-viet-nam"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.WEBTOON,
                        "${Constants.MAIN_LINK_TRUYENTRANHTUAN}/the-loai/webtoon"
                    )
                )
                return comicTypeList
            }

        fun getComicTypeAll(): ComicType {
            return ComicType(ComicType.TAT_CA, Constants.MAIN_LINK_TRUYENTRANHTUAN)
        }

        @JvmStatic
        @Suppress("unused")
        fun isComicExistIn(comic: Comic?, comicList: List<Comic>?): Boolean {
            if (comic == null || comicList == null) {
                throw NullPointerException("isComicExistIn comic == null || comicList == null")
            }
            for (i in comicList.indices) {
                if (comic.url.trim { it <= ' ' } == comicList[i].url.trim { it <= ' ' }) {
                    return true
                }
            }
            return false
        }

        @JvmStatic
        @Suppress("unused")
        fun isComicExistAt(comic: Comic?, comicList: List<Comic>?): Int {
            if (comic == null || comicList == null) {
                throw NullPointerException("isComicExistIn comic == null || comicList == null")
            }
            for (i in comicList.indices) {
                if (comic.url.trim { it <= ' ' } == comicList[i].url.trim { it <= ' ' }) {
                    return i
                }
            }
            return Constants.NOT_FOUND
        }
    }
}
