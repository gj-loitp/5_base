package com.loitpcore.utils.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri

class ClipboardUtils private constructor() {
    companion object {

        fun copyText(text: CharSequence?) {
            val clipboard =
                Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            clipboard?.setPrimaryClip(ClipData.newPlainText("text", text))
        }

        val text: CharSequence?
            get() {
                val clipboard = Utils.getContext()
                    ?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = clipboard?.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).coerceToText(Utils.getContext())
                } else {
                    null
                }
            }

        fun copyUri(
            uri: Uri?
        ) {
            val clipboard =
                Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            clipboard?.setPrimaryClip(
                ClipData.newUri(
                    Utils.getContext()?.contentResolver,
                    "uri",
                    uri
                )
            )
        }

        val uri: Uri?
            get() {
                val clipboard = Utils.getContext()
                    ?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = clipboard?.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).uri
                } else {
                    null
                }
            }

        fun copyIntent(intent: Intent?) {
            val clipboard =
                Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            clipboard?.setPrimaryClip(ClipData.newIntent("intent", intent))
        }

        val intent: Intent?
            get() {
                val clipboard = Utils.getContext()
                    ?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = clipboard?.primaryClip
                return if (clip != null && clip.itemCount > 0) {
                    clip.getItemAt(0).intent
                } else {
                    null
                }
            }
    }
}
