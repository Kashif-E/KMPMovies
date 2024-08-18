package com.kashif.common.domain.model

import com.benasher44.uuid.uuid4

abstract class UUID {
    val uuid: String = uuid4().toString()
}