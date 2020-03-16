package lets.vote.generalelection;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CandidateVO {
    public String id;
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
    public String status;
    public String si;
    public String gu;
    public String criminal;
    public String district;

    public CandidateVO(){

    }

    public CandidateVO(QueryDocumentSnapshot firebaseDocument){
        id = (String)firebaseDocument.get("Id");
        name = (String)firebaseDocument.get("Name");
        birth = (String)firebaseDocument.get("Age");
        address = (String)firebaseDocument.get("Address");
        imageUrl = (String)firebaseDocument.get("ImageUrl");
        status = (String)firebaseDocument.get("Status");
        si = (String)firebaseDocument.get("Si");
        gu = (String)firebaseDocument.get("Gu");
        criminal = (String)firebaseDocument.get("Criminal");
        education = (String)firebaseDocument.get("Education");
        career = (String)firebaseDocument.get("Career");
        job = (String)firebaseDocument.get("Job");
        district = (String)firebaseDocument.get("District");
        party = (String)firebaseDocument.get("Party");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getGu() {
        return gu;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public String getCriminal() {
        return criminal;
    }

    public void setCriminal(String criminal) {
        this.criminal = criminal;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "CandidateVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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
                ", status='" + status + '\'' +
                ", si='" + si + '\'' +
                ", gu='" + gu + '\'' +
                ", criminal='" + criminal + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
