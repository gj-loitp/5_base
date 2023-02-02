package vn.loitp.up.a.demo.nfc

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Bundle
import android.provider.Settings
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.loitp.core.helper.nfc.TagWrapper
import com.loitp.core.helper.nfc.buildMACAddressString
import com.loitp.core.helper.nfc.bytesToHex
import com.loitp.core.helper.nfc.bytesToHexAndString
import vn.loitp.R
import vn.loitp.databinding.ADemoNfcBinding
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.experimental.and

@LogTag("NFCActivity")
@IsFullScreen(false)
class NFCActivity : BaseActivityFont() {
    private val tags: ArrayList<TagWrapper> = ArrayList()

    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private lateinit var binding: ADemoNfcBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoNfcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NFCActivity::class.java.simpleName
        }
        binding.currentTagView.text = getString(R.string.loading)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    @SuppressLint("SetTextI18n", "UnspecifiedImmutableFlag")
    override fun onResume() {
        super.onResume()

        if (nfcAdapter?.isEnabled == false) {
            val dialog = this.showDialog1(
                title = "NFC is disabled",
                msg = "You must enable NFC to use this app.",
                button1 = "OK",
                onClickButton1 = {
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                    this@NFCActivity.tranIn()
                }
            )
            dialog.setCancelable(false)
            return
        }
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//                0
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            binding.currentTagView.text = "Scan a tag"
        }
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        logD("buildMACAddressString " + tag?.id.buildMACAddressString())

        val tagId = tag?.id.bytesToHex()
        tagId?.let {
            val tagWrapper = TagWrapper(id = it)
            val misc = ArrayList<String>()
            misc.add("scanned at: " + now())

            val rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var tagData = ""
            if (rawMsg != null) {
                val msg = rawMsg[0] as NdefMessage
                val cardRecord = msg.records[0]
                tagData = try {
                    readRecord(cardRecord.payload)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    return
                }
            }
            misc.add("tag data: $tagData")
            tagWrapper.techList["Misc"] = misc
            tag?.let { t ->
                for (tech in t.techList) {
                    val item = tech.replace("android.nfc.tech.", "")
                    val info = getTagInfo(tag = tag, tech = item)
                    tagWrapper.techList["Technology: $item"] = info
                }
            }

            if (tags.size == 1) {
                showShortInformation("Swipe right to see previous tags")
            }
            tags.add(tagWrapper)

            binding.tvResult.printBeautyJson(o = tags)
        }
    }

    @Throws(UnsupportedEncodingException::class)
    fun readRecord(payload: ByteArray): String {
        // val textEncoding = if (payload[0] and 128 == 0) "UTF-8" else "UTF-16"
        val textEncoding = if (payload[0] and 0x80.toByte() == 0.toByte()) "UTF-8" else "UTF-16"

        val languageCodeLength: Int = (payload[0] and 63).toInt()
        return String(
            payload,
            languageCodeLength + 1,
            payload.size - languageCodeLength - 1,
            Charset.forName(textEncoding)
        )
    }

    private fun getTagInfo(
        tag: Tag,
        tech: String
    ): List<String> {
        val info: MutableList<String> = ArrayList()
        when (tech) {
            "NfcA" -> {
                info.add("aka ISO 14443-3A")
                val nfcATag = NfcA.get(tag)
                info.add("atqa: " + nfcATag.atqa.bytesToHexAndString())
                info.add("sak: " + nfcATag.sak)
                info.add("maxTransceiveLength: " + nfcATag.maxTransceiveLength)
            }
            "NfcF" -> {
                info.add("aka JIS 6319-4")
                val nfcFTag = NfcF.get(tag)
                info.add("manufacturer: " + nfcFTag.manufacturer.bytesToHex())
                info.add("systemCode: " + nfcFTag.systemCode.bytesToHex())
                info.add("maxTransceiveLength: " + nfcFTag.maxTransceiveLength)
            }
            "NfcV" -> {
                info.add("aka ISO 15693")
                val nfcVTag = NfcV.get(tag)
                info.add("dsfId: " + nfcVTag.dsfId)
                info.add("responseFlags: " + nfcVTag.responseFlags)
                info.add("maxTransceiveLength: " + nfcVTag.maxTransceiveLength)
            }
            "Ndef" -> {
                val ndefTag = Ndef.get(tag)
                val ndefMessage: NdefMessage?
                try {
                    ndefTag.connect()
                    ndefMessage = ndefTag.ndefMessage
                    ndefTag.close()
                    for (record in ndefMessage.records) {
                        val id = if (record.id.isEmpty()) "null" else record.id.bytesToHex()
                        info.add("record[" + id + "].tnf: " + record.tnf)
                        info.add("record[" + id + "].type: " + record.type.bytesToHexAndString())
                        info.add(
                            "record[$id].payload: " + record.payload.bytesToHexAndString()
                        )
                    }
                    info.add("messageSize: " + ndefMessage.byteArrayLength)
                } catch (e: Exception) {
                    e.printStackTrace()
                    info.add("error reading message: $e")
                }
                val typeMap = HashMap<String, String>()
                typeMap[Ndef.NFC_FORUM_TYPE_1] = "typically Innovision Topaz"
                typeMap[Ndef.NFC_FORUM_TYPE_2] = "typically NXP MIFARE Ultralight"
                typeMap[Ndef.NFC_FORUM_TYPE_3] = "typically Sony Felica"
                typeMap[Ndef.NFC_FORUM_TYPE_4] = "typically NXP MIFARE Desfire"
                var type = ndefTag.type
                if (typeMap[type] != null) {
                    type += " (" + typeMap[type] + ")"
                }
                info.add("type: $type")
                info.add("canMakeReadOnly: " + ndefTag.canMakeReadOnly())
                info.add("isWritable: " + ndefTag.isWritable)
                info.add("maxSize: " + ndefTag.maxSize)
            }
            "NdefFormatable" -> info.add("nothing to read")
            "MifareUltralight" -> {
                val mifareUltralightTag = MifareUltralight.get(tag)
                info.add("type: " + mifareUltralightTag.type)
                info.add("tiemout: " + mifareUltralightTag.timeout)
                info.add("maxTransceiveLength: " + mifareUltralightTag.maxTransceiveLength)
            }
            "IsoDep" -> {
                info.add("aka ISO 14443-4")
                val isoDepTag = IsoDep.get(tag)
                info.add("historicalBytes: " + isoDepTag.historicalBytes.bytesToHexAndString())
                info.add("hiLayerResponse: " + isoDepTag.hiLayerResponse.bytesToHexAndString())
                info.add("timeout: " + isoDepTag.timeout)
                info.add("extendedLengthApduSupported: " + isoDepTag.isExtendedLengthApduSupported)
                info.add("maxTransceiveLength: " + isoDepTag.maxTransceiveLength)
            }
            else -> info.add("unknown tech!")
        }
        return info
    }
}
