package lets.vote.generalelection;

import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;
import java.util.Map;

public class CandidateVO implements Serializable,Comparable<CandidateVO>{
    public String id;
    //이름
    public String name;
    //사진
    public String imageUrl;
    //성별
    public String gender;
    //정당
    public String party;
    //기호
    public String number;
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
    public String status;
    public String si;
    public String criminal;
    public String district;
    public String military;
    public String nameFull;
    public String property;
    public String taxArrears;
    public String taxArrears5;
    public String taxPayment;
    public String regCount;
    public String recommend;


    public CandidateVO(){

    }

    public CandidateVO(Map<String,Object>candidateObject){
        id = (String)candidateObject.get("Id");
        name = (String)candidateObject.get("Name");
        birth = (String)candidateObject.get("Age");
        address = (String)candidateObject.get("Address");
        imageUrl = (String)candidateObject.get("ImageUrl");
        status = (String)candidateObject.get("Status");
        si = (String)candidateObject.get("Si");
        criminal = (String)candidateObject.get("Criminal");
        education = (String)candidateObject.get("Education");
        career = (String)candidateObject.get("Career");
        job = (String)candidateObject.get("Job");
        district = (String)candidateObject.get("District");
        party = (String)candidateObject.get("Party");
        number = (String)candidateObject.get("Number");
        gender = (String)candidateObject.get("Gender");
        military = (String)candidateObject.get("Military");
        nameFull = (String)candidateObject.get("NameFull");
        property = (String)candidateObject.get("Property");
        taxArrears = (String)candidateObject.get("TaxArrears");
        taxArrears5 = (String)candidateObject.get("TaxArrears5");
        taxPayment = (String)candidateObject.get("TaxPayment");
        regCount = (String)candidateObject.get("RegCount");
        recommend = (String)candidateObject.get("recommend");

//        Log.d("test", "CandidateVO: " + candidateObject.toString());
    }

    public CandidateVO(Cursor cursor) {
        setId(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_ID)));
        setName(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_NAME)));
        setBirth(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_BIRTH)));
        setAddress(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_ADDRESS)));
        setImageUrl(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_IMAGE_URL)));
        setStatus(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_STATUS)));
        setSi(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_SI)));
        setCriminal(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_CRIMINAL)));
        setEducation(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_EDUCATION)));
        setCareer(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_CAREER)));
        setJob(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_JOB)));
        setDistrict(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_DISTRICT)));
        setParty(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_PARTY)));
        setNumber(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_NUMBER)));
        setGender(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_GENDER)));
        setMilitary(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_MILITARY)));
        setNameFull(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_NAME_FULL)));
        setProperty(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_PROPERTY)));
        setTaxArrears(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_ARREARS)));
        setTaxArrears5(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_ARREARS5)));
        setTaxPayment(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_TAX_PAYMENT)));
        setRegCount(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_REG_COUNT)));
        setRegCount(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_REG_COUNT)));
        setRecommend(cursor.getString(cursor.getColumnIndex(CandidateContract.CandidateEntry.COLUMN_NAME_RECOMMEND)));
        Log.d("test", "CandidateVO: " + getName());
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getMilitary() {
        return military;
    }

    public void setMilitary(String military) {
        this.military = military;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getTaxArrears() {
        return taxArrears;
    }

    public void setTaxArrears(String taxArrears) {
        this.taxArrears = taxArrears;
    }

    public String getTaxArrears5() {
        return taxArrears5;
    }

    public void setTaxArrears5(String taxArrears5) {
        this.taxArrears5 = taxArrears5;
    }

    public String getTaxPayment() {
        return taxPayment;
    }

    public void setTaxPayment(String taxPayment) {
        this.taxPayment = taxPayment;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRegCount() {
        return regCount;
    }

    public void setRegCount(String regCount) {
        this.regCount = regCount;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public int compareTo(CandidateVO c) {
        if (c.number.equals("")){
            c.number = "999";
        }
        if (this.number.equals("")){
            this.number = "999";
        }


        if (Integer.parseInt(this.number) < Integer.parseInt(c.getNumber())) {
            return -1;
        } else if (Integer.parseInt(this.number) > Integer.parseInt(c.getNumber())) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "CandidateVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", party='" + party + '\'' +
                ", number='" + number + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                ", job='" + job + '\'' +
                ", education='" + education + '\'' +
                ", career='" + career + '\'' +
                ", status='" + status + '\'' +
                ", si='" + si + '\'' +
                ", criminal='" + criminal + '\'' +
                ", district='" + district + '\'' +
                ", military='" + military + '\'' +
                ", nameFull='" + nameFull + '\'' +
                ", property='" + property + '\'' +
                ", taxArrears='" + taxArrears + '\'' +
                ", taxArrears5='" + taxArrears5 + '\'' +
                ", taxPayment='" + taxPayment + '\'' +
                ", regCount='" + regCount + '\'' +
                ", recommend='" + recommend + '\'' +
                '}';
    }
}
