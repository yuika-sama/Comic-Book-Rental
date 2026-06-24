package com.example.comicbookrental.ui.utils

import java.util.Locale


fun Double.toPriceLabel(): String = "$%.2f".format(Locale.US, this)
