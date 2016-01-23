package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by catgualter on 07/12/2015.
 */
public class WidgetRemoteViewsFactory  implements RemoteViewsService.RemoteViewsFactory
{
    private static final String[] SCORE_COLUMNS = {

            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.MATCH_DAY,

    };

//    // Defines a string to contain the selection clause
//    String mSelectionClause = null;
//
//    // Initializes an array to contain selection arguments
//    String[] mSelectionArgs = {""};

    public double MATCH_ID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_MATCHTIME = 2;
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_LEAGUE = 5;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_MATCHDAY = 8;
    private Context context = null;
    private int appWidgetId;
    private Cursor data;
    private ArrayList<Scores> scoresList;

    public WidgetRemoteViewsFactory(Context context, Intent intent)
    {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }
    private void updateWidgetListView()
    {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        SQLiteDatabase db = new ScoresDBHelper(this.context).getReadableDatabase();
         data = db.query(
                DatabaseContract.SCORES_TABLE,
                SCORE_COLUMNS,
                DatabaseContract.scores_table.DATE_COL + "= ?",
                new String[]{today},
                null,
                null,
                DatabaseContract.scores_table.DATE_COL + " ASC");

        scoresList =  new ArrayList<>();
        while(data.moveToNext()) {
            Scores score = new Scores();
            score.setAway(data.getString(COL_AWAY));
            score.setHome(data.getString(COL_HOME));
            score.setAway_goals(data.getInt(COL_AWAY_GOALS));
            score.setHome_goals(data.getInt(COL_HOME_GOALS));
            score.setLeague(data.getInt(COL_LEAGUE));
            score.setmTime(data.getString(COL_MATCHTIME));
            score.setmDate(data.getString(COL_DATE));

            scoresList.add(score);
        }

    }

    @Override
    public void onCreate() {
        updateWidgetListView();
    }

    @Override
    public void onDataSetChanged() {
        updateWidgetListView();
    }

    @Override
    public void onDestroy() {
        scoresList.clear();
        data.close();
    }

    @Override
    public int getCount() {
        Log.d("DEBUG","data: count "+ scoresList.size());
        return scoresList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.scores_widget_list_item);

        Log.d("DEBUG", "data: " + scoresList.get(position).getHome());
        views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(scoresList.get(position).getHome()));

        views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(scoresList.get(position).getAway()));

        //Add content description for images
        views.setContentDescription(R.id.away_crest, scoresList.get(position)
                .getAway());
        views.setContentDescription(R.id.away_crest, scoresList.get(position)
                .getAway());

        views.setTextViewText(R.id.home_name, scoresList.get(position).getHome());
        views.setTextViewText(R.id.away_name, scoresList.get(position).getAway());
        views.setTextViewText(R.id.date_textview, scoresList.get(position).getmTime());
        views.setTextViewText(R.id.widgetTitle, scoresList.get(position).getmDate() + " - " + Utilies.getLeague(scoresList.get(position).getLeague()));
        views.setTextViewText(R.id.score_textview, Utilies.getScores(scoresList.get(position).getHome_goals(), scoresList.get(position).getAway_goals()));


        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
