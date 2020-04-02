package com.example.nfctest.adapter

interface MultipleTypeSupport<T> {

    fun getLayoutId(t: T, position: Int): Int
}