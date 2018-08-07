package com.wegrzyn.marcin.fuelbills;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AvgAppWidget extends AppWidgetProvider {

    public static final String ACTION_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String EXTRA_DATA ="extra_data";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.avg_app_widget);
        views.setTextViewText(R.id.avg_widget_tv, context.getString(R.string.add_refueling));
        appWidgetManager.updateAppWidget(appWidgetId, views);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_image_btn,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.hasExtra(EXTRA_DATA)){
            String avg = intent.getStringExtra(EXTRA_DATA);

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.avg_app_widget);
            views.setTextViewText(R.id.avg_widget_tv, avg);

            ComponentName appWidget = new ComponentName(context, AvgAppWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {

    }
}

