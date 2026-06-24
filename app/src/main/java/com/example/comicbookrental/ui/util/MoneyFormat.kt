package com.example.comicbookrental.ui.util

import java.util.Locale

/**
 * Format a rental price (USD) for display, e.g. `3.49` -> `"$3.49"`.
 *
 * Money is stored as a [Double] in the data layer; formatting to a label is a UI concern and
 * lives here so screens/mappers don't hand-build price strings.
 */
fun Double.toPriceLabel(): String = "$%.2f".format(Locale.US, this)
