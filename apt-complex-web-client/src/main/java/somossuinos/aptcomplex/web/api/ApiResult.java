package somossuinos.aptcomplex.web.api;

public class ApiResult {

    private String status;

    public ApiResultStatusType getStatus() {
        return ApiResultStatusType.valueOf(this.status);
    }

    private Object data;

    public Object getData() {
        return data;
    }

    private ApiResult(final ApiResultStatusType status) {
        this.status = status.name();
    }

    public static class Builder {
        private ApiResult result;

        private Builder(final ApiResultStatusType status) {
            this.result = new ApiResult(status);
        }

        public static Builder get(final ApiResultStatusType status) {
            return new Builder(status);
        }

        public Builder withData(final Object data) {
            this.result.data = data;
            return this;
        }

        public ApiResult build() {
            return this.result;
        }
    }

}
