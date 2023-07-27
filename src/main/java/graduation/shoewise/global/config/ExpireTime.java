package graduation.shoewise.global.config;

public class ExpireTime {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 300 * 600 * 1000L;               //30분
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;     //7일
}
