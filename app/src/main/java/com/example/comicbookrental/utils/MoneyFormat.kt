package com.example.comicbookrental.utils

import java.util.Locale


fun Double.toPriceLabel(): String = "$%.2f".format(Locale.US, this)
