package de.dennisguse.opentracks.content.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import de.dennisguse.opentracks.R;

@RunWith(AndroidJUnit4.class)
public class LayoutTest extends TestCase {

    private final Context context = ApplicationProvider.getApplicationContext();
    private final Resources resources = ApplicationProvider.getApplicationContext().getResources();

    @Test
    public void testFromCsv() {
        // given a csv line
        String csv = "running;" + context.getString(R.string.stats_custom_layout_speed_key) + ",1,1,0;" + context.getString(R.string.stats_custom_layout_distance_key) + ",1,0,0;" + context.getString(R.string.stats_custom_layout_altitude_key) + ",0,1,0;" + context.getString(R.string.stats_custom_layout_gain_key) + ",0,0,0;";

        // when create a layout from CSV line
        Layout layout = Layout.fromCsv(csv, resources);
        List<DataField> dataFieldList = layout.getFields();

        // then layout and data fields are built correctly
        assertEquals(layout.getProfile(), "running");
        assertEquals(dataFieldList.size(), 4);
        assertEquals(dataFieldList.get(0).getKey(), context.getString(R.string.stats_custom_layout_speed_key));
        assertEquals(dataFieldList.get(1).getKey(), context.getString(R.string.stats_custom_layout_distance_key));
        assertEquals(dataFieldList.get(2).getKey(), context.getString(R.string.stats_custom_layout_altitude_key));
        assertEquals(dataFieldList.get(3).getKey(), context.getString(R.string.stats_custom_layout_gain_key));
        assertEquals(dataFieldList.get(0).getTitle(), context.getString(R.string.stats_speed));
        assertEquals(dataFieldList.get(1).getTitle(), context.getString(R.string.stats_distance));
        assertEquals(dataFieldList.get(2).getTitle(), context.getString(R.string.stats_altitude));
        assertEquals(dataFieldList.get(3).getTitle(), context.getString(R.string.stats_gain));
        assertTrue(dataFieldList.get(0).isVisible());
        assertTrue(dataFieldList.get(0).isPrimary());
        assertFalse(dataFieldList.get(0).isWide());
        assertTrue(dataFieldList.get(1).isVisible());
        assertFalse(dataFieldList.get(1).isPrimary());
        assertFalse(dataFieldList.get(1).isWide());
        assertFalse(dataFieldList.get(2).isVisible());
        assertTrue(dataFieldList.get(2).isPrimary());
        assertFalse(dataFieldList.get(2).isWide());
        assertFalse(dataFieldList.get(3).isVisible());
        assertFalse(dataFieldList.get(3).isPrimary());
        assertFalse(dataFieldList.get(3).isWide());

    }

    @Test
    public void testToCsv() {
        // given a layout's object
        Layout layout = new Layout("Test Layout");
        layout.addField("key1", "Title 1", false, false, false);
        layout.addField("key2", "Title 2", false, true, false);
        layout.addField("key3", "Title 3", true, false, false);
        layout.addField("key4", "Title 4", true, true, false);
        layout.addField("key5", "Title 5", true, true, true);

        // when converts it to CSV
        String csv = layout.toCsv();

        // then csv is well built
        assertEquals(csv, "Test Layout;key1,0,0,0;key2,0,1,0;key3,1,0,0;key4,1,1,0;key5,1,1,1;");
    }
}