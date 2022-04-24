package com.mytunes.enums;

public enum WrapperType {

    //Response içerisinde farklı bir WrapperType göremedim.
    Track("track");


    private final String value;

    WrapperType(String value){
        this.value = value;
    }

    public static WrapperType parse(String value){
        for (WrapperType type : WrapperType.values()){
            if (value.equals(type.getValue())){
                return type;
            }
        }

        return Track;
    }

    public String getValue(){
        return value;
    }

}
