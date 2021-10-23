package de.dennisguse.opentracks.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.content.data.Layout;
import de.dennisguse.opentracks.util.CsvConstants;


@RunWith(AndroidJUnit4.class)
public class PreferencesOpenHelperTest {

    private final Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void test_upgradeFrom1To2() {
        // given the version 1
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String oldCustomLayoutCsv = context.getString(R.string.activity_type_unknown) + CsvConstants.ITEM_SEPARATOR
                + "distance,1,1" + CsvConstants.ITEM_SEPARATOR + "speed,1,1" + CsvConstants.ITEM_SEPARATOR;
        editor.putString(context.getString(R.string.stats_custom_layouts_key), oldCustomLayoutCsv);
        editor.putInt(context.getString(R.string.prefs_last_version_key), 1);

        editor.commit();

        // when update to version 2
        PreferencesOpenHelper.newInstance(2).checkForUpgrade();

        // then there should be two layouts with the new default and the old custom layout in the second place
        List<Layout> layouts = PreferencesUtils.getAllCustomLayouts();
        assertNotNull(layouts);
        assertEquals(layouts.size(), 2);
        assertTrue(layouts.get(0).toCsv().startsWith(context.getString(R.string.stats_custom_layout_default_profile) + CsvConstants.ITEM_SEPARATOR));
        assertEquals(layouts.get(1).toCsv(), oldCustomLayoutCsv);
    }
}
