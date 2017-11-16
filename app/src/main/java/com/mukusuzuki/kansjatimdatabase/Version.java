package com.mukusuzuki.kansjatimdatabase;

/**
 * Created by Muku Suzuki on 8/11/2017.
 */

public class Version {
    private String update, version;

    public Version() {
    }

    public Version(String update, String version) {
        this.update = update;
        this.version = version;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
