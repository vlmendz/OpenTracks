package de.dennisguse.opentracks.settings;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.util.CsvConstants;

class PreferencesOpenHelper {

    private final int version;

    private PreferencesOpenHelper(int version) {
        this.version = version;
    }

    static PreferencesOpenHelper newInstance(int version) {
        return new PreferencesOpenHelper(version);
    }

    void checkForUpgrade() {
        int lastVersion = PreferencesUtils.getInt(R.string.prefs_last_version_key, 0);
        if (version > lastVersion) {
            onUpgrade();
        }
    }

    private void onUpgrade() {
        PreferencesUtils.setInt(R.string.prefs_last_version_key, version);
        switch (version) {
            case 1:
                upgradeFrom0to1();
                break;
            case 2:
                upgradeFrom1to2();
                break;
        }
    }

    private void upgradeFrom0to1() {
        PreferencesUtils.setString(R.string.stats_custom_layouts_key, PreferencesUtils.buildDefaultLayout());
    }

    private void upgradeFrom1to2() {
        PreferencesUtils.setString(
                R.string.stats_custom_layouts_key,
                PreferencesUtils.buildDefaultLayout() + CsvConstants.LINE_SEPARATOR + PreferencesUtils.getCustomLayout().toCsv()
        );
    }
}
