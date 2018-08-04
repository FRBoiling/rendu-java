package constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 14:24
 */
@Getter
public enum ErrorCode {
    SUCCESS(0),
    FAIL(1);
    private int value;
    private ErrorCode(int value){
        this.value=value;
    }
}

