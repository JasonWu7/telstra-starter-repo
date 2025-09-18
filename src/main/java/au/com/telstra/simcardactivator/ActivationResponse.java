package au.com.telstra.simcardactivator;

public class ActivationResponse {
    private boolean success;
    public ActivationResponse(boolean success) {
        this.success = success;
    }

    public ActivationResponse() {}
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
