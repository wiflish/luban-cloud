package com.wiflish.luban.core.dto;

import com.wiflish.luban.core.dto.exception.BaseErrorCodeConstant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author wiflish
 * @since 2023-08-31
 */
@Getter
@Setter
public class ListResponse<T> extends Response {
   @Serial
   private static final long serialVersionUID = 4257159692539083113L;
   private Long total;
   private Integer page;
   private Integer size;
   private Collection<T> data;

   public List<T> getData() {
      return (null == this.data ? Collections.emptyList() : new ArrayList<>(this.data));
   }

   public void setData(Collection<T> data) {
      this.data = data;
   }

   public boolean isEmpty() {
      return this.data == null || this.data.isEmpty();
   }

   public static <T> ListResponse<T> of(List<T> data) {
      ListResponse<T> response = new ListResponse<>();
      response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
      response.setData(data);

      return response;
   }

   public static <T> ListResponse<T> of(List<T> data, long total) {
      ListResponse<T> response = of(data);
      response.setTotal(total);

      return response;
   }

   public static <T> ListResponse<T> of(List<T> data, long total, int page, int size) {
      ListResponse<T> response = of(data, total);
      response.setPage(page);
      response.setSize(size);

      return response;
   }
}
