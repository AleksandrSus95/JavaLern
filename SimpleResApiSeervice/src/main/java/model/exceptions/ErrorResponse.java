package model.exceptions;

public class ErrorResponse {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorBuilder builder(){
        return new ErrorBuilder();
    }

    public static class ErrorBuilder {
        ErrorResponse errorResponse = new ErrorResponse();

        public ErrorBuilder setCode(Integer code){
            errorResponse.code = code;
            return this;
        }

        public ErrorBuilder setMessage(String message){
            errorResponse.message = message;
            return this;
        }

        public ErrorResponse build(){
            return this.errorResponse;
        }
    }
}
