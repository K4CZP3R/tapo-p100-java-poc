import java.util.HashMap;
import java.util.Map;

public class DeviceInfoParams {
    private String avatar;
    private Boolean device_on;
    private Integer latitude;
    @Deprecated
    private String location;
    private Integer longitude;
    private String nickname;

    public DeviceInfoParams() {
    }

    public DeviceInfoParams(String str, String str2) {
        this.nickname = str;
        this.avatar = str2;
    }

    public DeviceInfoParams(Integer num, Integer num2) {
        this.longitude = num;
        this.latitude = num2;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String str) {
        this.location = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public Boolean getDeviceOn() {
        return this.device_on;
    }

    public void setDeviceOn(Boolean bool) {
        this.device_on = bool;
    }

    public Integer getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Integer num) {
        this.longitude = num;
    }

    public Integer getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Integer num) {
        this.latitude = num;
    }


    public Map<String, Object> toMap() {
        return toMap((Map<String, Object>) null);
    }

    public Map<String, Object> toMap(Map<String, Object> map) {
        HashMap hashMap;
        if (map == null) {
            hashMap = new HashMap();
        } else {
            hashMap = new HashMap(map);
        }
        Boolean bool = this.device_on;
        if (bool != null) {
            hashMap.put("on", bool);
        }
        return hashMap;
    }

}
