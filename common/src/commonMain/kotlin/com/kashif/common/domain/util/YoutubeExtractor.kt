import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.String
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


fun getDictionaryFrom(string: String): Map<String, String> {
    val dic = mutableMapOf<String, String>()
    val parts = string.split("&")
    for (part in parts) {
        val keyval = part.split("=")
        if (keyval.size > 1 && keyval.first() == "url_encoded_fmt_stream_map") {
            for (video in keyval[1].decode()?.split(",") ?: emptyList()) {
                val videoDetail = video.split("&")
                var quality = ""
                var url = ""
                for (v in videoDetail) {
                    if (v.startsWith("quality")) {
                        quality = v.split("=")[1]
                    }
                    if (v.startsWith("url")) {
                        url = v.split("=")[1].decode()
                    }
                }
                dic[quality] = url
            }
        }
    }
    return dic
}



suspend fun extractVideos(youtubeId: String, httpClient: HttpClient): Map<String, String> = withContext(Dispatchers.IO) {
    val strUrl = "http://www.youtube.com/get_video_info?video_id=$youtubeId&el=embedded&ps=default&eurl=&gl=US&hl=en"


    val response: HttpResponse = httpClient.get(strUrl)

    if (response.status.isSuccess()) {
        val data = response.bodyAsText()
        return@withContext getDictionaryFrom(data)
    } else {
        throw Exception("Error fetching data from YouTube API")
    }
}

fun String.decode( charset: Charset = Charsets.UTF_8): String {
    var decoded = false
    val l = this.length
    val sb = StringBuilder(if (l > 1024) l / 3 else l)

    var state: ParseState = ParseState.sText
    var i = 0
    var code = 0
    var c: Char
    var pos = 0
    var ofs = 0
    var buf: ByteArray? = null
    var processDig = false
    while (i < l) {
        c = this[i]
        when (c) {
            '+' -> {
                decoded = true
                when (state) {
                    ParseState.sText -> {
                        sb.append(' ')
                    }
                    ParseState.s2Dig -> {
                        sb.append(String(buf!!, 0, pos + 1, charset))
                        state = ParseState.sText
                        sb.append(' ')
                    } else -> throw IllegalArgumentException("decode: unexpected + at pos: $i, of: $this")
                }
            }
            in '0'..'9' -> {
                ofs = '0'.toInt()
                processDig = true
            }
            in 'a'..'f' -> {
                ofs = 'a'.toInt() - 10
                processDig = true
            }
            in 'A'..'F' -> {
                ofs = 'A'.toInt() - 10
                processDig = true
            }
            '%' -> {
                decoded = true
                when (state) {
                    ParseState.sText -> {
                        state = ParseState.sEscape
                        if (buf == null)
                            buf = ByteArray((l - i) / 3)
                        pos = 0
                    }
                    ParseState.s2Dig -> {
                        state = ParseState.sEscape
                        pos++
                    } else -> throw IllegalArgumentException("decode: unexpected escape % at pos: $i, of: $this")
                }
            }
            'u' -> when (state) {
                ParseState.sEscape -> {
                    if (pos > 0) {
                        sb.append(String(buf!!, 0, pos, charset))
                        pos = 0
                    }
                    state = ParseState.sU1
                }
                ParseState.sText -> {
                    sb.append(c)
                }
                ParseState.s2Dig -> {
                    sb.append(String(buf!!, 0, pos + 1, charset))
                    state = ParseState.sText
                    sb.append(c)
                } else ->  throw IllegalArgumentException("decode: unexpected char in hex at pos: $i, of: $this")
            }
            else -> when (state) {
                ParseState.sText -> {
                    sb.append(c)
                }
                ParseState.s2Dig -> {
                    sb.append(String(buf!!, 0, pos + 1, charset))
                    state = ParseState.sText
                    sb.append(c)
                } else -> throw IllegalArgumentException("decode: unexpected char in hex at pos: $i, of: $this")
            }
        }
        i++
        if (processDig) {
            when (state) {
                ParseState.sEscape -> {
                    code = c.toInt() - ofs
                    state = ParseState.s1Dig
                }
                ParseState.s1Dig -> {
                    buf!![pos] = (code * 16 + (c.toInt() - ofs)).toByte()
                    state = ParseState.s2Dig
                }
                ParseState.s2Dig -> {
                    // escape finished
                    sb.append(String(buf!!, 0, pos + 1, charset))
                    state = ParseState.sText
                    sb.append(c)
                }
                ParseState.sU1 -> {
                    code = c.toInt() - ofs
                    state = ParseState.sU2
                }
                ParseState.sU2 -> {
                    code = code * 16 + c.toInt() - ofs
                    state = ParseState.sU3
                }
                ParseState.sU3 -> {
                    code = code * 16 + c.toInt() - ofs
                    state = ParseState.sU4
                }
                ParseState.sU4 -> {
                    sb.append((code * 16 + c.toInt() - ofs).toChar())
                    state = ParseState.sText
                }
                else -> {
                    sb.append(c)
                }
            }
            processDig = false
        }
    }
    if (state == ParseState.s2Dig) {
        sb.append(String(buf!!, 0, pos + 1, charset))
    }
    return if (decoded) sb.toString() else this
}

enum class ParseState {
    sText,
    sEscape,
    s1Dig,
    s2Dig,
    sU1,
    sU2,
    sU3,
    sU4
}

