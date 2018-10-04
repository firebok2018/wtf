package musicp.firebok.com.music.lastfm;

import org.w3c.dom.Document;

/**
 * The <code>Result</code> class contains the response sent by the server, i.e.
 * the status (either ok or failed), an error code and message if failed and the
 * xml response sent by the server.
 *
 * @author Janni Kovacs
 */
public class Result {

    public enum Status {
        OK, FAILED
    }

    protected Status status;

    protected String errorMessage = null;

    protected int errorCode = -1;

    protected int httpErrorCode = -1;

    protected Document resultDocument;

    /**
     * @param resultDocument
     */
    protected Result(final Document resultDocument) {
        status = Status.OK;
        this.resultDocument = resultDocument;
    }

    /**
     * @param errorMessage
     */
    protected Result(final String errorMessage) {
        status = Status.FAILED;
        this.errorMessage = errorMessage;
    }

    /**
     * @param resultDocument
     * @return
     */
    static Result createOkResult(final Document resultDocument) {
        return new Result(resultDocument);
    }

    /**
     * @param httpErrorCode
     * @param errorMessage
     * @return
     */
    static Result createHttpErrorResult(final int httpErrorCode, final String errorMessage) {
        final Result r = new Result(errorMessage);
        r.httpErrorCode = httpErrorCode;
        return r;
    }

    /**
     * @param errorCode
     * @param errorMessage
     * @return
     */
    static Result createRestErrorResult(final int errorCode, final String errorMessage) {
        final Result r = new Result(errorMessage);
        r.errorCode = errorCode;
        return r;
    }

    /**
     * Returns if the operation was successful. Same as
     * <code>getStatus() == Status.OK</code>.
     *
     * @return <code>true</code> if the operation was successful
     */
    public boolean isSuccessful() {
        return status == Status.OK;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public Status getStatus() {
        return status;
    }

    public Document getResultDocument() {
        return resultDocument;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public DomElement getContentElement() {
        if (!isSuccessful()) {
            return null;
        }
        return new DomElement(resultDocument.getDocumentElement()).getChild("*");
    }

    @Override
    public String toString() {
        return "Result[isSuccessful=" + isSuccessful() + ", errorCode=" + errorCode
                + ", httpErrorCode=" + httpErrorCode + ", errorMessage=" + errorMessage
                + ", status=" + status + "]";
    }
}
