package com.tutorialsbuzz.notificationimgload

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.concurrent.atomic.AtomicInteger


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notifyBtn = findViewById<Button>(R.id.notifyBtn)
        notifyBtn.setOnClickListener { v: View? ->
            createNotification(
                resources.getString(R.string.notification_title),
                resources.getString(R.string.notification_content),
                resources.getString(R.string.notification_channel),
            )
        }
    }

    /**
     * Create Notification
     * Param
     * 1. title
     * 2. content
     * 3. channelId
     * 4.priorty
     * 5. notificationId
     */
    private fun createNotification(
        title: String, content: String,
        channedId: String
    ) {

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channedId)
            .setSmallIcon(R.drawable.ic_notifications_active)
            .setAutoCancel(true)
            .setLights(Color.BLUE, 500, 500)
            .setVibrate(longArrayOf(500, 500, 500))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setContentText(content)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        // Since android Oreo notification channel is needed.
        val notificationManager = NotificationManagerCompat.from(this@MainActivity)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channedId,
                channedId,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

        val imageUrl = "https://raw.githubusercontent.com/TutorialsBuzz/cdn/main/android.jpg"

        GlideApp.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //largeIcon
                    notificationBuilder.setLargeIcon(resource)
                    //Big Picture
                    notificationBuilder.setStyle(
                        NotificationCompat.BigPictureStyle().bigPicture(resource)
                    )
                    val notification = notificationBuilder.build()
                    notificationManager.notify(NotificationID.iD, notification)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

    }

    internal object NotificationID {
        private val c = AtomicInteger(100)
        val iD: Int
            get() = c.incrementAndGet()
    }
}