package lets.vote.generalelection;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PartyInfo {
    static Map<String, String> partyColorMap;
    static Map<String, String> partyLogoMap;
    static Map<String, Integer> partyNumberMap;
    static Map<String,Integer> proportionalMap;


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
                put("코리아", "#603027");
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
                put("새벽당", "#101922");
                put("자영업당", "#56BA38");
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
                put("홍익당", "#5D173C");
                put("우리당", "#CC979797");
                put("공화당", "#CC979797");
                put("무소속", "#CC979797");
                put("기본값", "#8271CC");
            }};
        }

        return partyColorMap.get(party);
    }

    public static Map<String,String> getPartyLogoMap(){
        if (partyLogoMap == null) {
            partyLogoMap = new HashMap<String, String>() {{
                put("더불어민주당", "https://ww.namu.la/s/3a08f0dd598d47115c0c140abf41a79309a3c45c2fb0035e06ef5888240f2cbd40ba5b9c00163b6477715e969e426fe558ea2a85d5534202de96cbf92af4b79c75ca8bc66bfe7400a00b0ce3136a87c7f458459fe61217c1a5c8ced03cb382b7");
                put("미래통합당", "https://ww.namu.la/s/5fa3fff8f4a15366373134e12225f632e9b104fad14cf89c01ef07be3318db917728d1dca9911f8d4325be53afa4de0ab8b9e668ce5258ace66985d90c639df4e31a2b475b7853b168988df9d4ea92d56f0da7d48024de072450fd926f1787f1");
                put("민생당", "https://w.namu.la/s/b662f975b70a1ca7994b91851bbef42f1a0e6e3cfb9cb62f9e2d7ba308279e1aeb8e0f90c21147f50283cb1a6be0dad2e04b5bd1055308af14d8661514d60fbdf1452b5992848e8cb2c02a114a0536ee0160003756564ebb3b8095f148542475");
                put("미래한국당", "https://ww.namu.la/s/66844454a29cea58c3104e1a6ba2ea059184d21926bae70ce27b5450aa5116739b9a5c35f169b45ec59420ae3d5fcca995e1ede787a37d6b471150159e000924cf842b1d1bdd4a5d20561b1cf28da889df2be40e83cb7518cc19ed21d865437e");
                put("정의당", "https://w.namu.la/s/a10172bcdf2e6d07181129de25746b9f03bce8212e65eb7143e2eab4e4bf4ab8a1c16cca0781d6c3f71093ed2d06c37993a037f59bd516147ff1c3f970041897d0a5d814831c9a6fdc7cbdc32042c21068a0fac7eb327a30221dfc16cd77e299");
                put("우리공화당", "https://w.namu.la/s/cf4b1f2a348b33be72016cb03ba84bfa268c03f61916eff9c517a2e149cbd47a9b7d11266191358c25ed0986675f1cb56c1c70b4d35b966eba6de09b761e66ad50ad76857475263bf8e3f528560269d567f5f8ea056daf23efcf8e0ded7b7765");
                put("국민의당", "https://ww.namu.la/s/7fe17cfffcf5e7bfa7d5a8eb988d45704f8b9b352d18ee46c63780c450a7b3ec59a1db1723fda3ce92c4f1e237b2ee7726bd0dd19600a5bfcec9c80b2fe3d3f358dab58150d591db669f26fd665e3e09b686b16c96b5784eba6b8367c967f224");
                put("기독자유통일당", "https://ww.namu.la/s/116b1d9646c4cf16f426a6db1a6e8344e7bf1c1e6b9d0d1c52295246a16df0b0eea0c4bab73c509044c321d46305990569e58dd0e2d59fa985030d8dde27d54c0d8909123565860e8661a658cdffa83a6077ffe74a855915a468063a6bc455cf");
                put("더불어시민당", "https://w.namu.la/s/2f4d327bd02839e4b4e7041631ebd1515e5891ee776951751440d3f9ded9c25790bff33e31ae35c6ed07135ff47970840e63499e51efd4ccbb9b1e86568874535714706c5953010df7223f8cfd6e66c8de08388ee6cdfb30f89509dd286caac3");
                put("민중당", "https://ww.namu.la/s/a2ca157f8d83d0afb16eeac2d2af5fc2a8f3df3f7dab6066a6fb0bba2459c6c6b8643f21c1979ae293e94fe9c5d604ac25d6bbc4ed4e98d5bdd0852ba8584565bebed56a201c591e548e948e1d959170cabd9c770dda3c498b82d770660cb01e");
                put("열린민주당", "https://w.namu.la/s/ee6bdb1ceb3e93317eb5ba1605e88117b16d43033e4920eca8d93c3a90251f55ec81160aa0d0af25b5ada8de25385fa8c32057e73e83b95e49c60d38bddbdad0dec9aec3265ce60671ee4b9286ae384ba3486095e86dbf8415d52d64cd835ce89c6d7732531361f1c9869656a34af2f9");
                put("친박신당", "https://ww.namu.la/s/6da6e06887e4fd5a4334df672e6f0b85ba513658eccf0cedc98af85df3455e470c313f719f9bdace0bd5106bf14b97188c44225f1d58612e534aa5dca6ed095a999dda5da9e0854410c1a4bc8bd151717718634ac300b338c1eb9b7ca328b480");
                put("국가혁명배당금당", "https://w.namu.la/s/fd400434e1dd7fc9eb6ea28f084b4b6861aaf8b0c2b4cd49279921583d4c2a5f5ef548dd68a1942fe811e09aac86fea8864395283cfac1e07e0a50dc88c7eebad74b9b3d96e1853b7b0ac2fb7477d092d64669158cfab9acba9736c049c4efeb5aa169937739638fc35324b36c69c631");
                put("코리아", "https://w.namu.la/s/4585b1556dbfa234dc0b6e14d638dbf9384916a9c1ba7a6a90605e07f1741a079a494523d395472dc03c1c7edb581956f8c2f0aa79d27959df2d1951e031727fc38fe84da84a0d24ee7f8859907278e179359f620b53df6fcb28b19a58b64bf2");
                put("가자!평화인권당", "https://ww.namu.la/s/cb0a67314f19a462dd4945eed20cf62820ff40cf0f246d374869f36e2f43197cc8adb126e64a28fcc2aed79842372fdc66a565aeec7a60f939a1ef196b4e2c24e0bcc3100930b03e71aff765271d4799a38e235f2df55064461afa1e4bbede2c");
                put("가자환경당", "https://w.namu.la/s/c3eb800578693ca09db3efc6166f43b39b0e38ad551825c93db425ddb847326531c1823450b59a86dc5b2ccb3a6af5cd458c7a254471c0f42c745c41389fc4d97403f0478d36c6388d4851795c1e9eb8674f59728265f3b9e8e5a728e578da0a");
                put("공화당", "https://w.namu.la/s/c13fd3838f34637ed5fc421eb4d61f710d33818e51384fc960484ad9034d436b2a671d10ca0b01cc7096e3ac406a1330c2b35652c61172698365a86a59dcdb4b38efb6e512acb10b0f4153773df9fd34d4d4628a28d7e6d08f4c679b1e04780b5b3b06e446b043c03397b9595baa3b13");
                put("국민새정당", "https://ww.namu.la/s/37813ed821a209f324c1cd4d93b8da4935923e0fbfff1895cce2de87446dce71da8a7305287d7d4d82659fe2840300cff9ce23636dc0fbe634f666dddca36837e4b921035ec0d2124b1d5f66053088144ae20f7f920b08f122c13b18a2ebbacc7bc30526ad83411d9f6099fe20169ca0");
                put("국민참여신당", "https://ww.namu.la/s/1d7b7d343170e8770f7fca4068b010374a59e341f16b0b774b1ed5fe39006787d7571df4eeed4a13b7a2d6534ca03e9307339dd255191eb2d95699f757479ed2a44fff01141edce3e6daa3d0669cdd885118101add618253dbb9744429fe134d");
                put("기독당", "https://w.namu.la/s/033ddef731b94b67045dc710ff3ae94037ed2caf2b3effd4fbc9ab040148d81f2ca3028f504375fe637e0e89849df4905a4bb5675e79e1ea31968437808a96af146bb0e112e17346149bd387bb7f22ed11e2bbfc92e9e52327446fa0d7157726bcadeca5b5188970b05edbc7a13b35d1");
                put("기본소득당", "https://w.namu.la/s/e5510ada3e0030bfc49f50663d257e9aa93bf83a37ab59feeed13b4430df48b9f30864acd3ae04f12e499a5eb3574a9f95952ec95443c38879c13a8b4bb9940bf5720747c4be63112af854a74b80aca09c91094a5daa46e47799a05e0cbda4b8");
                put("깨어있는시민연대당", "https://w.namu.la/s/245a0739e56efa0bce0e04ca82acead76f7b2353ceb2d69d09b906d6200e5db31930ab23e1961fd6d3993281d3fc80d9dc958fb600fd39ada94e29e098f63a71a3742710a4ddc15d07c27138e771342f0199b2cf5a0fc54591d0477a767957d3");
                put("남북통일당", "https://w.namu.la/s/a909dabdd1d0e7e48f50d810cddc3b9fed3bcb02988e1f02ee4d3f91f1a9b2580f3474736aab72b872fd23b7e3cc4fc16fd6b44db11e47346f1d730f3f120b5836977a039b064ba973864166243e5b80f87dbba8f26c9bd1fc45159898e3cad8");
                put("노동당", "https://ww.namu.la/s/750a55979cf306f21ddf67f295f78d8668cc7e5840ca3215014c67a1392bb912aea3ec99fa2ac00b67978593bfed49f396d091927b3cfb49e444177792624a666ae37208e3e140baa6d818d7713b302a22d3349232d407a1a8ab9dcda432187d");
                put("녹색당", "https://w.namu.la/s/3e394d8c7bf0ae5269e2814d6038fa329699979a4d3d2b2a1b65ce3d250c7f3e6bbdb7fc92f5ea6693038860a64dc0b5423b6586557e27ec0bfb64a9fdac6fa25dead6d41d16c1d697d988bfaa00e0a4a858ade646819dc024a8458e290c0ecb");
                put("대한당", "https://ww.namu.la/s/7e98a7d56c851de22892110a423f97760bea686bc4584faae177c2e0324ef07a5f47f54a035302aa36849c5f0047d419da3a375083f4645e7ebc4d4211d8c5d6656d42b6cb58fc73e481943599c1c6995047154aa90309d489785ba05f5c382e3902ea9795b7511b128c4f67e398ebf5");
                put("대한민국당", "https://ww.namu.la/s/8b0b0bdc19d2d4a00a3e535d9c79348e6ce0619f2354fe3a3753163fe6bbb4db27f27a400524f057c5c7e9ce291d37e0b128fe343cf8a237a80cb87f2aab17dc17dc9d0a8475b7722a3a6be371085b7cd8d6fdb5e03e0bf49ff98646c19ac5f5");
                put("미래당", "https://w.namu.la/s/6ba4ff9c0b4e1f86f1e1b543320c5e7fae84d24712e1047361ff22626de99694d1246168587680f3710cf1b6a230998612d43766dd487d580a0f2c87be267cdaa862b2e865f222152c4767df749e953dba549f578a118c626d6e08d0d45c0313");
                put("미래민주당", "https://ww.namu.la/s/dd4076dd8610f49b6cf90bbfd144ea5fe2be1269f6d9a63b57b7972fcd86341b0807c002fb3a5002c158e2cab436540cbe5b48b7ed5785e5ffd3ae569b62434d9e61ccde4f22b91a0c4389530f5d2876b8db4529acca17d75931eaaa8acebc90");
                put("미래자영업당", "https://ww.namu.la/s/e013baf2fdcc745158870c0122783955dc95415992bc421f8d8c304e9159f4370b5bfe34d3f6ba2bfb2b838614b30674e1106ac10b21e123006553c58688362a2e5a27de8c4c71bd08322fc012190129560c01dc69c914cf2a3092045304dcf9");
                put("민중민주당", "https://ww.namu.la/s/cb59fb7d3af7dc00c9b0baaffb7c7b09b018402c1b76ac4aca3a087ef534beebd59736293fea6948036fa4e0c78044251c6d5b07c9e72b8f8d0ca5fa8afae3de42d45e20df7aaa6bae711db0f6f70228932a39da744942eace2d448c886b7c32803a0f9be0e961fa82ab2fbbf95340a4");
                put("사이버모바일국민정책당", "https://ww.namu.la/s/7f30ce6c529eea50ccccdd2692249ee843c4ccdcc4f790fd2768addfd03259d95af89efe64665b9aee3da989fd79bc3977f56a3cd252838676b224deac311bfbc4152067400a81a8f271041df9afb81c6a930c4e46a37bb12f60e4b7ca9bd5e8");
                put("새누리당", "https://ww.namu.la/s/d09a3374cdb9a28a43b645c687106977b43426dabf4daed48d1d7c35df1d022ca634878369feaaea5d0869bfced47ae6ce10a7490a22b137f470dda9e8f3d42e1ca7799719acc40a3db0d5e3f1ac6ae221e72dcd35340871cd2b5f063804990a");
                put("시대전환", "https://w.namu.la/s/a943fb8beeb3b3e85ad0edeaf4ef53289a86bad4651438525844d0ceedcf7355c9ef108cd328776fc691ecf84be794d5d1b69b6009dc55b9e850fcd2ec79288a5ce00cc18fd106adf8cba856ab02b65e81254a82c2a92dbb5a41f526e98f0cdd");
                put("여성의당", "https://ww.namu.la/s/9a85a5ffd2d7214b7137e13c6ae1f6219f2dbecf481e4dad62ba52eacb9b8da6759e9aac58a66992663b9d0c4cd824995790e7db74f465d3ee89b3b1b4be4b26b2970be0691627111ae44b116138fd414747cbe1fc285043df6a5da622216f57");
                put("우리당", "https://ww.namu.la/s/c4c21deafe799f2eeb422752815063e7f23eb531ef122fe8f103310c6a3082f2a98323c4246beb936aed53ff73eb2090af63de098014881729d388ded1b74ceeb6227cf57a1ec96163c206770f7b6c39e67946877f2766f4df4c85a57e7f9c0a");
                put("자유당", "https://w.namu.la/s/143f82a6e9b868d781789d9a58cc3e8b0d4c5b98dc77137420a84db9e52f187191e72e51b0d18c48c328e68eb1a24bb7feebca8ccbfaa8c7190746aa5e3d872e6629094ff67125fe2d0365eaf717d7a2e3edd55af3f2f8a4d3693ffcabc21ab2");
                put("새벽당", "https://ww.namu.la/s/f9fd61107a8e1150f8040c2d330af00da40e920a57ce8f606bc534600a8e57bb82f630000872157e3771ace2ca001cf6a8fc07e7df86e5bed8b94fe0da44810eee3da78bc233fe3a0fd44a2e8309632bf5fb1bc9e1bf4b6e785380bc5d0aba61");
                put("자영업당", "https://w.namu.la/s/1bcf3b4132ce7ca199ca03b5f7f92e1101114e018b4ddc85eeaf8c669dd5400740135b9c4ce9ba3e554ca89b452f35044d3774f0a9ca13efe023d4e9242ed44012aa07744ab0761ecfbdc99ed3137a867ca58ca33b45f1ae7d0b10027f0befb8");
                put("직능자영업당", "https://ww.namu.la/s/2e7be01db9fa2e09dc47d26f0dc75be33549ebebba11b6ed427a8ec23bab0962c9c4933078c02052dd3ac91d48f8720b569df03b5eb8126b574ab4ea110bd4c6506915e202c53571d2bf3453485af25638e23634fc9b60b039b1f797594da61f");
                put("충청의미래당", "https://w.namu.la/s/11478dbaaababff1e057d97c6950f7c589bef427eb832a00a2ddbbdfcca3f3ece98110032b029da2d5ab8c2de6a30bf293fb8cefed1418184af488cd1bee04a8f53d119708b2536801f605958e5589e98afc3739b47ff17a2001d6b00f9ab3f2");
                put("친박연대", "https://ww.namu.la/s/5aec70db12f60a9b3dafb515605a96b012aed3a8ce1f58b884214c1b13c9e60925549de4fc5e5c6350868261af6adc9d26add05520c82daaf1ebd80c3d508883e383c783255be252fee520761ebba794d4305737557c7a013d434527330c24db");
                put("통일민주당", "https://w.namu.la/s/1bf67fea7919b1c66effbfa23e1bb547291fc16c5037948899f3f31a537796270d43b2054a962dda099d41fa649875c59ae26d24bf6579374384d65b70fb1d881a3b47fc6e755064fa26d932ff489126cb84d5031c7c557d37c9000e579112d3");
                put("통합민주당", "https://w.namu.la/s/0c878e0c19b1f620e791eabf1eb65c86f81527b40103117f6227b22d169a0d4f84696bd18698e95a285166337d541cf882800f0c3d580491f8d208fa0ba4d79ecb8a41f1aff61cf78f116f03872a4d728a3e0c3a17622ab6335c030861319cbdd978003fecf2256565b77cacc2d0b144");
                put("한국경제당", "https://ww.namu.la/s/7e89b71ebc09d9989db410aae45030ba4b4200efec714cc7872e803a7630938038df96e6a0f231233d7c633c3736352f3734de0c0c98136f56ed6d7b39b926b9b91640c028535a3822f23221a22727db5b48ea35305820171229ee9db19d0df9");
                put("한국국민당", "https://w.namu.la/s/32dd81c561e4b50896f58b96982701923a2a61beb1afee00e0bfc4c38cd719803f73cda7fb20f446e99ab04e7f924849ec58a28bfb40ad5531235aa7f515c45662967c74f33cbaa6348682847bbf3deffa71a44e3570a71999ad83786c0f896e");
                put("한국복지당", "https://ww.namu.la/s/f862eb0d64b64e9adaf3a1d8a4a7dc671e1dd36b119457f4e5e5157033a8a61cb408a0bf2e01b12330de6cae884085c625d533f1b2115b33de37c763b2e3ceb0ff4618aa4391a976565c608ad52d44a947e60323f61334e4c5f2702da1576bdd");
                put("한나라당", "https://ww.namu.la/s/1bcf285218a663bf282a955046665cf8a0c86d1c60ad4d1fcec3376803bfa154971bdb73dcb572c7606d19d4e6553d8eba5f8551993fe64d95f37c28e2a9006d831e0f6a24425903628a25b0ed9d0f7af451a13c8e0cdbd1e94d1063df7207b5");
                put("한반도미래연합", "https://ww.namu.la/s/a972d419fbacfa881cec8a11f8de70faeb5f765aec4a6902ba696da9ee9255a635bb97ed704aec3ece8816b0f647ba5c04032a1108af886b3166cf26774ad66d50a011b3a8e0d9e685d6c2da16f6927c76a89d438c6225dd40826e02509ddc28a07ac40b6e9c62d5da46f65272f2a7b2");
                put("홍익당", "https://w.namu.la/s/57256c814b6f3da93b84253a78b8d3ca8c586be6437ea5c36e3d8c5ba60ed3baf24a119b5b21d043b8de137c7ddc1c01af703ca3cec733afe27435d751c7bfe885fc37ef251ef28b5a2adf14de79e2d4c5424749e1934179b4e38e1ce90524d8");

            }};
        }

        return partyLogoMap;
    }

    public static Map<String, Integer> getPartyNumberMap() {
        if (partyNumberMap == null) {
            partyNumberMap = new HashMap<String, Integer>() {{
                put("더불어민주당", 1);
                put("미래통합당", 2);
                put("민생당", 3);
                put("미래한국당", 4);
                put("더불어시민당", 5);
                put("정의당", 6);
                put("우리공화당", 7);
                put("민중당",8);
                put("한국경제당", 9);
                put("국민의당", 10);
                put("친박신당", 11);
                put("열린민주당", 12);
                put("코리아", 13);
                put("가자!평화인권당", 14);
                put("가자환경당", 15);
                put("공화당", 16);
                put("국가혁명배당금당", 17);
                put("국민새정당", 18);
                put("국민참여신당", 19);
                put("기독당", 20);
                put("기독자유통일당", 21);
                put("기본소득당", 22);
                put("깨어있는시민연대당", 23);
                put("남북통일당", 24);
                put("노동당", 25);
                put("녹색당", 26);
                put("대한당", 27);
                put("대한민국당", 28);
                put("미래당", 29);
                put("미래민주당", 30);
                put("미래자영업당", 31);
                put("민중민주당", 32);
                put("사이버모바일국민정책당", 33);
                put("새누리당", 34);
                put("시대전환", 35);
                put("여성의당", 36);
                put("우리당", 37);
                put("자유당", 38);
                put("새벽당", 39);
                put("자영업당", 40);
                put("직능자영업당", 41);
                put("충청의미래당", 42);
                put("친박연대", 43);
                put("통일민주당", 44);
                put("통합민주당", 45);
                put("한국국민당", 46);
                put("한국복지당", 47);
                put("한나라당", 48);
                put("한반도미래연합", 49);
                put("홍익당", 50);
            }};
        }

        return partyNumberMap;
    }


    public static Map<String,Integer> getProportionalMap() {
        if (proportionalMap == null) {
            proportionalMap = new HashMap<String, Integer>() {{
                put("더불어민주당", 0);
                put("미래통합당", 0);
                put("민생당", 3);
                put("미래한국당", 4);
                put("더불어시민당", 5);
                put("정의당", 6);
                put("우리공화당", 7);
                put("민중당", 8);
                put("한국경제당", 9);
                put("국민의당", 10);
                put("친박신당", 11);
                put("열린민주당", 12);
                put("코리아", 13);
                put("가자!평화인권당", 14);
                put("가자환경당", 15);
                put("국가혁명배당금당", 16);
                put("국민새정당", 17);
                put("국민참여신당", 18);
                put("기독자유통일당", 19);
                put("깨어있는시민연대당", 20);
                put("남북통일당", 21);
                put("노동당", 22);
                put("녹색당", 23);
                put("대한당", 24);
                put("대한민국당", 25);
                put("미래당", 26);
                put("미래민주당", 27);
                put("새누리당", 28);
                put("여성의당", 29);
                put("우리당", 30);
                put("자유당", 31);
                put("새벽당", 32);
                put("자영업당", 33);
                put("충청의미래당", 34);
                put("한국복지당", 35);
                put("통일민주당", 36);
                put("홍익당", 37);
                put("기독당", 0);
                put("기본소득당", 0);
                put("미래자영업당", 0);
                put("민중민주당", 0);
                put("사이버모바일국민정책당", 0);
                put("시대전환", 0);
                put("자유의새벽당", 0);
                put("직능자영업당", 0);
                put("친박연대", 0);
                put("통합민주당", 0);
                put("한국국민당", 0);
                put("한나라당", 0);
                put("한반도미래연합", 0);
            }};
        }
        return proportionalMap;
    }
}
