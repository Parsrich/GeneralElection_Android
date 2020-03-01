package lets.vote.generalelection;

public class CandidateVO {
    //이름
    public String name;
    //사진
    public String imageUrl;
    //성별
    public String gender;
    //정당
    public String party;
    //선거구
    public String electionDistrict;
    //생년월일
    public String birth;
    //주소
    public String address;
    //직업
    public String job;
    //학력
    public String education;
    //경력
    public String career;
    //전과
    public String criminalRecord;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getElectionDistrict() {
        return electionDistrict;
    }

    public void setElectionDistrict(String electionDistrict) {
        this.electionDistrict = electionDistrict;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(String criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    @Override
    public String toString() {
        return "CandidateVO{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", party='" + party + '\'' +
                ", electionDistrict='" + electionDistrict + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", job='" + job + '\'' +
                ", education='" + education + '\'' +
                ", career='" + career + '\'' +
                ", criminalRecord='" + criminalRecord + '\'' +
                '}';
    }
}
