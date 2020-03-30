package lets.vote.generalelection;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PartyColor {
    static Map<String, String> partyColorMap;

    public static String getPartyColor(final String party){
        if (partyColorMap == null) {
            partyColorMap = new HashMap<String, String>() {{
                put("더불어민주당", "#53779E");
                put("미래통합당", "#FF5B63");
                put("민생당", "#4C7354");
                put("미래한국당", "#F2959F");
                put("정의당", "#FFBC2C");
                put("우리공화당", "#51957A");
                put("국민의당", "#FF8F3C");
                put("기독자유통일당", "#DA4D40");
                put("더불어시민당", "#5F9EAF");
                put("민중당", "#F26623");
                put("열린민주당", "#003E9B");
                put("친박신당", "#708E4B");
                put("국가혁명배당금당", "#E8141A");
                put("가자코리아", "#603027");
                put("가자!평화인권당", "#0000FF");
                put("가자환경당", "#007254");
                put("국민새정당", "#1F6DDC");
                put("국민참여신당", "#F8C401");
                put("기독당", "#765192");
                put("기본소득당", "#FE8971");
                put("깨어있는시민연대당", "#000478");
                put("남북통일당", "#ED000C");
                put("노동당", "#FF0000");
                put("녹색당", "#5DBE3F");
                put("대한당", "#4B3293");
                put("대한민국당", "#DF9B26");
                put("미래당", "#2E3192");
                put("미래민주당", "#01A7E7");
                put("미래자영업당", "#FFB42F");
                put("민중민주당", "#8F2650");
                put("사이버모바일국민정책당", "#EF5025");
                put("새누리당", "#D91E48");
                put("시대전환", "#5A147E");
                put("여성의당", "#44009A");
                put("자유당", "#30318B");
                put("자유의새벽당", "#101922");
                put("정치개혁연합", "#C78665");
                put("중소자영업당", "#56BA38");
                put("직능자영업당", "#752B88");
                put("충청의미래당", "#AE469F");
                put("친박연대", "#0C449B");
                put("통일민주당", "#8FA6A2");
                put("통합민주당", "#9ACE32");
                put("한국경제당", "#F58229");
                put("한국국민당", "#013588");
                put("한국복지당", "#EA5404");
                put("한나라당", "#D61921");
                put("한반도미래연합", "#E33434");
                put("홍익당", "#5D173");
                put("우리당", "#CC979797");
                put("공화당", "#CC979797");
                put("무소속", "#CC979797");
                put("기본값", "#8271CC");
            }};
        }

        return partyColorMap.get(party);
    }


}
