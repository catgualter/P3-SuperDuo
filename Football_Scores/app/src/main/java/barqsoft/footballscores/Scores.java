package barqsoft.footballscores;


/**
 * Created by catgualter on 07/12/2015.
 */
public class Scores {
    String away;
    String home;
    int away_goals;
    int home_goals;
    int league;
    String mTime;
    String mDate;

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getAway_goals() {
        return away_goals;
    }

    public void setAway_goals(int away_goals) {
        this.away_goals = away_goals;
    }

    public int getHome_goals() {
        return home_goals;
    }

    public void setHome_goals(int home_goals) {
        this.home_goals = home_goals;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
