package lets.vote.generalelection;

import android.provider.BaseColumns;

public class CandidateContract implements BaseColumns {

    private CandidateContract(){}
    public static class CandidateEntry implements BaseColumns {

        public static final String TABLE_NAME = "candidate";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IMAGE_URL = "imageUrl";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_PARTY = "party";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_BIRTH = "birth";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_JOB = "job";
        public static final String COLUMN_NAME_EDUCATION = "education";
        public static final String COLUMN_NAME_CAREER = "career";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_SI = "si";
        public static final String COLUMN_NAME_CRIMINAL = "criminal";
        public static final String COLUMN_NAME_DISTRICT = "district";
        public static final String COLUMN_NAME_MILITARY = "military";
        public static final String COLUMN_NAME_NAME_FULL = "nameFull";
        public static final String COLUMN_NAME_PROPERTY = "property";
        public static final String COLUMN_NAME_TAX_ARREARS = "taxArrears";
        public static final String COLUMN_NAME_TAX_ARREARS5 = "taxArrears5";
        public static final String COLUMN_NAME_TAX_PAYMENT = "taxPayment";
        public static final String COLUMN_NAME_REG_COUNT = "regCount";
        public static final String COLUMN_NAME_RECOMMEND = "recommend";
    }



    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CandidateEntry.TABLE_NAME + " (" +
                    CandidateEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    CandidateEntry.COLUMN_NAME_NAME + " TEXT," +
                    CandidateEntry.COLUMN_NAME_IMAGE_URL + " TEXT," +
                    CandidateEntry.COLUMN_NAME_GENDER + " TEXT," +
                    CandidateEntry.COLUMN_NAME_PARTY + " TEXT," +
                    CandidateEntry.COLUMN_NAME_NUMBER + " TEXT," +
                    CandidateEntry.COLUMN_NAME_BIRTH + " TEXT," +
                    CandidateEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    CandidateEntry.COLUMN_NAME_JOB + " TEXT," +
                    CandidateEntry.COLUMN_NAME_EDUCATION + " TEXT," +
                    CandidateEntry.COLUMN_NAME_CAREER + " TEXT," +
                    CandidateEntry.COLUMN_NAME_STATUS + " TEXT," +
                    CandidateEntry.COLUMN_NAME_SI + " TEXT," +
                    CandidateEntry.COLUMN_NAME_CRIMINAL + " TEXT," +
                    CandidateEntry.COLUMN_NAME_DISTRICT + " TEXT," +
                    CandidateEntry.COLUMN_NAME_MILITARY + " TEXT," +
                    CandidateEntry.COLUMN_NAME_NAME_FULL + " TEXT," +
                    CandidateEntry.COLUMN_NAME_PROPERTY + " TEXT," +
                    CandidateEntry.COLUMN_NAME_TAX_ARREARS + " TEXT," +
                    CandidateEntry.COLUMN_NAME_TAX_ARREARS5 + " TEXT," +
                    CandidateEntry.COLUMN_NAME_TAX_PAYMENT + " TEXT," +
                    CandidateEntry.COLUMN_NAME_RECOMMEND + " TEXT," +
                    CandidateEntry.COLUMN_NAME_REG_COUNT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CandidateEntry.TABLE_NAME;
}
