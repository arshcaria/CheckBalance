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
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Intent intent = new Intent(context, MainActivity.class);
		intent.setAction("ACTION_WIDGET_REQUEST_UPDATE");
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.cb_appwidget);
		views.setOnClickPendingIntent(R.id.button1, pendingIntent);

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		appWidgetManager.updateAppWidget(new ComponentName(context,
				CBAppWidgetProvider.class), views);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			Intent intent = new Intent(context, MainActivity.class);
			intent.setAction("ACTION_WIDGET_REQUEST_UPDATE");
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.cb_appwidget);
			views.setOnClickPendingIntent(R.id.button1, pendingIntent);

			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals("ACTION_UPDATE_WIDGET")) {
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.cb_appwidget);
			views.setTextViewText(R.id.textView1,
					"您的当前话费余额是:" + intent.getStringExtra("BALANCE") + "元");
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			appWidgetManager.updateAppWidget(new ComponentName(context,
					CBAppWidgetProvider.class), views);
		}
	}

}
