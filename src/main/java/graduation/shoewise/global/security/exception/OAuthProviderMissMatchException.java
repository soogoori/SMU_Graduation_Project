package graduation.shoewise.global.security.exception;

public class OAuthProviderMissMatchException extends RuntimeException{
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
