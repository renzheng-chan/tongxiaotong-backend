package scu.train.backend.utils;


import org.springframework.stereotype.Component;


@Component
public class DefaultImage {

    private final String IPADDRESS = "HTTP://127.0.0.1:8080/";

    public String getDefaultIcon(){
      return IPADDRESS+"defaultIcon.png";
    }
    public  String getDefaultProduct(){
        return IPADDRESS+"defaultProduct.png";
    }

    public  String getDefaultCover() {
        return IPADDRESS+"defaultCover.png";
    }
}
