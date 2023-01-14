package com.loitp.core.helper.ttt.helper

import com.loitp.core.common.MAIN_LINK_TRUYENTRANHTUAN
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitp.core.helper.ttt.model.comictype.ComicType

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
                comicTypeList.add(ComicType(ComicType.TAT_CA, MAIN_LINK_TRUYENTRANHTUAN))
                comicTypeList.add(
                    ComicType(
                        ComicType.TOP_50,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/top/top-50"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.DANG_TIEN_HANH,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/dang-tien-hanh"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TAM_NGUNG,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/tam-dung"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HOAN_THANH,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/trang-thai/hoan-thanh/"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ACTION,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/action"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ADVENTURE,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/adventure"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ANIME,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/anime"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.COMEDY,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/comedy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.COMIC,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/comic"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.DRAMA,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/drama"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.FANTASY,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/fantasy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.GENDER_BENDER,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/gender-bender"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HISTORICAL,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/historical"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.HORROR,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/horror"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.JOSEI,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/josei"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.LIVE_ACTION,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/live-action"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MANHUA,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/manhua"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MANHWA,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/manhwa"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MARTIAL_ARTS,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/martial-arts"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MECHA,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/mecha"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.MYSTERY,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/mystery"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ONESHOT,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/one-shot"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.PSYCHOLOGICAL,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/psychological"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.ROMANCE,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/romance"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHCOOL_LIFE,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/school-life"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SCIFI,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/sci-fi"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHOUJO,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/shoujo"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SHOUNEN,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/shounen"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SLICE_OF_LIFE,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/slice-of-life"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SMUT,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/smut"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SPORTS,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/sports"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.SUPERNATURAL,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/supernatural"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRAGEDY,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/tragedy"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRUYEN_SCAN,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/truyen-scan"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.TRUYEN_TRANH_VIET_NAM,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/truyen-tranh-viet-nam"
                    )
                )
                comicTypeList.add(
                    ComicType(
                        ComicType.WEBTOON,
                        "${MAIN_LINK_TRUYENTRANHTUAN}/the-loai/webtoon"
                    )
                )
                return comicTypeList
            }

        fun getComicTypeAll(): ComicType {
            return ComicType(ComicType.TAT_CA, MAIN_LINK_TRUYENTRANHTUAN)
        }

        @JvmStatic
        @Suppress("unused")
        fun isComicExistIn(
            comic: Comic?,
            comicList: List<Comic>?
        ): Boolean {
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
        fun isComicExistAt(
            comic: Comic?,
            comicList: List<Comic>?
        ): Int {
            if (comic == null || comicList == null) {
                throw NullPointerException("isComicExistIn comic == null || comicList == null")
            }
            for (i in comicList.indices) {
                if (comic.url.trim { it <= ' ' } == comicList[i].url.trim { it <= ' ' }) {
                    return i
                }
            }
            return NOT_FOUND
        }
    }
}
