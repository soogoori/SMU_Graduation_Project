package graduation.shoewise.domain.profile;

import graduation.shoewise.domain.purchase.Purchase;
import graduation.shoewise.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class Profile {

    private final User user;
    private final Purchase purchase;

    public Profile(User user, Purchase purchase) {
        this.user = user;
        this.purchase = purchase;
    }

    public Long getUserId(){
        return user.getId();
    }

    public String getImage(){
        return user.getProfileImage();
    }

    public int size(){
        return user.getSize();
    }

    public int width(){
        return user.getWidth();
    }

    public String getNickname(){
        return user.getNickname();
    }
}
