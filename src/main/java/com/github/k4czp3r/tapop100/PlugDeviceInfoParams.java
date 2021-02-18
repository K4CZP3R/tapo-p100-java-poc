package com.github.k4czp3r.tapop100;

public class PlugDeviceInfoParams extends DeviceInfoParams{
    private Boolean led_enable;

    public PlugDeviceInfoParams() {
    }

    public PlugDeviceInfoParams(String str) {
        setLocation(str);
    }

    public PlugDeviceInfoParams(Boolean bool) {
        setDeviceOn(bool);
    }

    public PlugDeviceInfoParams(String str, String str2) {
        super(str, str2);
    }

    public PlugDeviceInfoParams(Integer num, Integer num2) {
        super(num, num2);
    }

    public Boolean getLedEnable() {
        return this.led_enable;
    }

    public void setLedEnable(Boolean bool) {
        this.led_enable = bool;
    }
}
