package com.monster.makeover.ext

fun query(query: () -> Unit) {
    Thread(query).start()
}