package jp.ceed.android.mylapslogger.network;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * APIリクエストクラスのパラメータとなるフィールド用のアノテーション
 *
 * @author UU023938
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RequestParameter {

    String serialiseName();

}
