package com.wiflish.luban.core.dto;

import com.wiflish.luban.core.dto.exception.BaseErrorCodeConstant;

import java.io.Serial;

/**
 *
 * @author wiflish
 * @since 2023-08-31
 */
public class OneResponse<T> extends Response {
   @Serial
   private static final long serialVersionUID = 1L;

   private T data;

   public T getData() {
      return this.data;
   }

   public void setData(T data) {
      this.data = data;
   }

   public static <T> OneResponse<T> of(T data) {
      OneResponse<T> response = new OneResponse<>();
      response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
      response.setData(data);

      return response;
   }
}
