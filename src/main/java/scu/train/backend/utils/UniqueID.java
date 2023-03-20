package scu.train.backend.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueID {

    public int generateAccount(){
        UUID idOne = UUID.randomUUID();
        String str=""+idOne;
        int uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
    public String generate(){
        return this.generateAccount()+"";

    }

}
