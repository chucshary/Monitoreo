package rest;

/**
 * Created by Shary on 18/10/2015.
 */
public class AccesoToken {
    private int TokenId;
    private String Token;
    private int TutorId;

    public int getTokenId() {
        return TokenId;
    }

    public void setTokenId(int tokenId) {
        TokenId = tokenId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getTutorId() {
        return TutorId;
    }

    public void setTutorId(int tutorId) {
        TutorId = tutorId;
    }
}
