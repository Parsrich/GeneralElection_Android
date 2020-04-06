package lets.vote.generalelection;

import java.io.Serializable;

public class PromiseVO implements Serializable {
    // 순서
    String prmsOrd;
    //분야
    String prmsRealmName;
    // title
    String prmsTitle;
    //내용
    String prmmCont ;

    public PromiseVO() {
    }

    public PromiseVO(String prmsOrd, String prmsRealmName, String prmsTitle, String prmmCont) {
        this.prmsOrd = prmsOrd;
        this.prmsRealmName = prmsRealmName;
        this.prmsTitle = prmsTitle;
        this.prmmCont = prmmCont;
    }

    public String getPrmsOrd() {
        return prmsOrd;
    }

    public void setPrmsOrd(String prmsOrd) {
        this.prmsOrd = prmsOrd;
    }

    public String getPrmsRealmName() {
        return prmsRealmName;
    }

    public void setPrmsRealmName(String prmsRealmName) {
        this.prmsRealmName = prmsRealmName;
    }

    public String getPrmsTitle() {
        return prmsTitle;
    }

    public void setPrmsTitle(String prmsTitle) {
        this.prmsTitle = prmsTitle;
    }

    public String getPrmmCont() {
        return prmmCont;
    }

    public void setPrmmCont(String prmmCont) {
        this.prmmCont = prmmCont;
    }

    @Override
    public String toString() {
        return "PromiseVO{" +
                "prmsOrd='" + prmsOrd + '\'' +
                ", prmsRealmName='" + prmsRealmName + '\'' +
                ", prmsTitle='" + prmsTitle + '\'' +
                ", prmmCont='" + prmmCont + '\'' +
                '}';
    }

    //    @Override
//    public int compareTo(PromiseVO o) {
//        if (this.number != 0 && o.getNumber() != 0){
//            if (this.number < o.getNumber()) {
//                return -1;
//            } else if (this.number > o.getNumber()) {
//                return 1;
//            }
//        }else if(this.number == 0 && o.getNumber() == 0){
//            return this.name.compareTo(o.name);
//        }else if(this.number == 0 && o.getNumber() != 0){
//            return 1;
//        }else if(this.number != 0 && o.getNumber() == 0){
//            return -1;
//        }else{
//            return 0;
//        }
//        return 0;
//    }
}
