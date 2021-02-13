package fr.lpiot.qcm.modele;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("response_code")
    private int response_code;
    @SerializedName("results")
    private Question[] results;

    public ApiResponse(int response_code, Question[] results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public Question[] getResults() {
        return results;
    }

    public void setResults(Question[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "response_code=" + response_code +
                '}';
    }
}
