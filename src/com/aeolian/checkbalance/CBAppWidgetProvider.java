package com.aeolian.checkbalance;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class CBAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			Intent intent = new Intent(context, UpdateService.class);
			intent.setAction(CBApplication.ACTION_WIDGET_REQUEST_UPDATE);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.cb_appwidget);
			views.setOnClickPendingIntent(R.id.button1, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.i("jiaqi", "Receive intent");
		if (intent.getAction().equals(CBApplication.ACTION_UPDATE_WIDGET)) {
			Log.i("jiaqi", "Receive ACTION_UPDATE_WIDGET");
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cb_appwidget);
			views.setTextViewText(R.id.textView1, "您的当前话费余额是:" + intent.getStringExtra("BALANCE") + "元");
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			appWidgetManager.updateAppWidget(new ComponentName(context, CBAppWidgetProvider.class), views);
		}
	}
}
