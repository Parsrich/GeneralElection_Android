package lets.vote.generalelection;

public enum Election {
        // 상수("연결할 문자")
        congress(0,"국회의원선거"),
        guMayor(1,"구·시·군의 장선거"),
        siCouncil(2,"시·도의회의원선거"),
        guCouncil(3,"구·시·군의회의원선거");

        final private String name;
        final private int index;

        private Election( int index, String name) { //enum에서 생성자 같은 역할
            this.index = index;
            this.name = name;
        }
        public String getName() { // 문자를 받아오는 함수
            return name;
        }

        public int getIndex() {
            return index;
        }
}
