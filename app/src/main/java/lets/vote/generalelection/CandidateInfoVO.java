package lets.vote.generalelection;

public class CandidateInfoVO{
    String candidateInfoTitle;
    String candidateInfoContent;

    public CandidateInfoVO(String candidateInfoTitle, String candidateInfoContent) {
        this.candidateInfoTitle = candidateInfoTitle;
        this.candidateInfoContent = candidateInfoContent;
    }

    public String getCandidateInfoTitle() {
        return candidateInfoTitle;
    }

    public void setCandidateInfoTitle(String candidateInfoTitle) {
        this.candidateInfoTitle = candidateInfoTitle;
    }

    public String getCandidateInfoContent() {
        return candidateInfoContent;
    }

    public void setCandidateInfoContent(String candidateInfoContent) {
        this.candidateInfoContent = candidateInfoContent;
    }

    @Override
    public String toString() {
        return "CandidateInfoVO{" +
                "candidateInfoTitle='" + candidateInfoTitle + '\'' +
                ", candidateInfoContent='" + candidateInfoContent + '\'' +
                '}';
    }
}
