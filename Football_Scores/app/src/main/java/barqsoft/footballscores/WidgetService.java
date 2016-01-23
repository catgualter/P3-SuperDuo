package barqsoft.footballscores;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by catgualter on 07/12/2015.
 */
public class WidgetService  extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
