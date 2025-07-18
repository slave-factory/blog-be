/**
 * @introduce
 * <p>
 *     <ul>
 *         <li>예외처리 통일화를 위한 클래스</li>
 *         <li>CustomException.java</li>
 *     </ul>
 * </p>
 *
 * @author Hwang junsik
 */

package com.blog.lastdance.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int status;

    public CustomException(String message, int status) {
        super(message);
        this.status = status;
    }

}
