package com.navi.lakshayclosed.di

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class MyGlideApp : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val cacheSize200MB = 209715200

        builder.setDiskCache(
            InternalCacheDiskCacheFactory(context, cacheSize200MB.toLong())
        )

        super.applyOptions(context, builder)
    }
}