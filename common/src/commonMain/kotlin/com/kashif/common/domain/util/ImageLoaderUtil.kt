package com.kashif.common.domain.util

import co.touchlab.kermit.Severity
import com.seiko.imageloader.ImageLoaderConfigBuilder
import com.seiko.imageloader.util.LogPriority
import com.seiko.imageloader.util.Logger

fun ImageLoaderConfigBuilder.commonConfig() {
    logger = object : Logger {
        override fun log(
            priority: LogPriority,
            tag: String,
            data: Any?,
            throwable: Throwable?,
            message: String,
        ) {
            co.touchlab.kermit.Logger.log(
                severity = when (priority) {
                    LogPriority.VERBOSE -> Severity.Verbose
                    LogPriority.DEBUG -> Severity.Debug
                    LogPriority.INFO -> Severity.Info
                    LogPriority.WARN -> Severity.Warn
                    LogPriority.ERROR -> Severity.Error
                    LogPriority.ASSERT -> Severity.Assert
                },
                tag = tag,
                throwable = throwable,
                message = buildString {
                    if (data != null) {
                        append("[image data] ")
                        append(data.toString().take(100))
                        append('\n')
                    }
                    append("[message] ")
                    append(message)
                },
            )
        }

        override fun isLoggable(priority: LogPriority) = priority >= LogPriority.DEBUG
    }

}
